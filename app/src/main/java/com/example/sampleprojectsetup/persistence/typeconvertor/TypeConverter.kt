package com.example.sampleprojectsetup.persistence.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*
/**
 * Develop By Messagemuse
 */
open class TypeConverter {

    /*@TypeConverter
    fun fromString(value: String?): ArrayList<ContactRequestModel.ContactObject> {
        val listType = object : TypeToken<ArrayList<ContactRequestModel.ContactObject?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<ContactRequestModel.ContactObject?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }*/
}