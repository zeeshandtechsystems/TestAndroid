package com.example.sampleprojectsetup.persistence.dao

import androidx.room.*

/**
 * Develop By Messagemuse
 */

@Dao
interface ContactDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    /*@Insert
    fun insertContacts(contactRequestModel: ContactSyncResponseModel.Data)

    @Query("SELECT * from contacts_table")
    fun getRemoteContacts(): ContactSyncResponseModel.Data

    @Update
    fun updateContacts(contactRequestModel: ContactSyncResponseModel.Data)*/
}