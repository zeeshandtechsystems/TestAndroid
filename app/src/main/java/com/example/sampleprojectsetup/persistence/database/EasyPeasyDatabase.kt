package com.example.sampleprojectsetup.persistence.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sampleprojectsetup.persistence.dao.ContactDao
import com.example.sampleprojectsetup.persistence.typeconvertor.TypeConverter
/**
 * Develop By Messagemuse
 */
//@Database(entities = [(ContactSyncResponseModel.Data::class)], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class EasyPeasyDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: EasyPeasyDatabase? = null

        fun getDatabase(context: Context): EasyPeasyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, EasyPeasyDatabase::class.java, "easypeasy.db").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}