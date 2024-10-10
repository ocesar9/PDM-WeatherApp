package com.weatherapp.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalCity::class], version = 1, exportSchema = false)
abstract class LocalCityDatabase : RoomDatabase() {
    abstract fun localCityDao(): LocalCityDAO
}