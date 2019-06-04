package com.martianlab.drunkennavigation.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.martianlab.drunkennavigation.data.db.entities.Point

@Dao
interface PointsDao {

    @Query("SELECT * FROM point")
    fun getAll(): LiveData<List<Point>>

    @Query("SELECT * FROM point WHERE run_guid = :id")
    fun getAllBySessId(id: String): LiveData<List<Point>>

    @Query("SELECT * FROM point WHERE run_guid = :id and sent = 0")
    fun getAllUnsentBySessId(id: String): List<Point>

    @Query("SELECT * FROM point WHERE sent = 0")
    fun getAllUnsent(): List<Point>

    @Query("SELECT * FROM point WHERE id = :id")
    fun getById(id: Long): LiveData<Point>

    @Query("SELECT * FROM point WHERE run_guid = :run_guid ORDER BY time DESC LIMIT 1")
    fun getLastInRun(run_guid: String): List<Point>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg points: Point)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(points: Point)

    @Query("UPDATE point SET sent = 1 WHERE id = :id" )
    fun setSent(id : Long )
}