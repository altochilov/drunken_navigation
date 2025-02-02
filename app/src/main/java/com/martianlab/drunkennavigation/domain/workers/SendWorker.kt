package com.martianlab.drunkennavigation.domain.workers

import android.content.Context
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.martianlab.drunkennavigation.data.TOKEN
import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.data.db.TochResponse
import com.martianlab.drunkennavigation.domain.DNaviService
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class SendWorker constructor(
    private val context: Context,
    params : WorkerParameters,
    private val appExecutors: AppExecutors,
    private val pointsDao: PointsDao,
    private val dNaviService: DNaviService

): Worker(context, params) {


    override fun doWork(): Result {

        appExecutors.networkIO().execute { send() }
        return Result.success()
    }

    fun send(){
        val points = pointsDao.getAllUnsent()

        for( point in points ) {
            val id = point.id

            dNaviService.postValues( TOKEN, point.user_id, point.guid, point.time, point.text ).enqueue( object :
                Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    appExecutors.mainThread().execute {
                        //Toast.makeText(context,"send failure", Toast.LENGTH_LONG).show()
                    }
                    //do smth
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful()) {

                        appExecutors.diskIO().execute { pointsDao.setSent(id) }

                        appExecutors.mainThread().execute {

                            //Toast.makeText(context,"send success", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        appExecutors.mainThread().execute {
                            //Toast.makeText(context,"send failure", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            })
        }
    }

}