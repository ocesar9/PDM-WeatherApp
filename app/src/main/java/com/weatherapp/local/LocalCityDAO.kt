package com.weatherapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalCityDAO {
    @Upsert
    suspend fun upsert(city: LocalCity)

    @Delete
    suspend fun delete(city: LocalCity)

    @Query("SELECT * FROM LocalCity")
    fun getCities(): Flow<List<LocalCity>>
}