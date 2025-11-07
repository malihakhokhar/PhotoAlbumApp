package com.example.photoalbumapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.photoalbumapp.Model.PhotoAlbum

@Dao
interface PhotoDAO {

    @Insert
    suspend fun insert(myImages : PhotoAlbum)

    @Update
    suspend fun update(myImages : PhotoAlbum)

    @Delete
    suspend fun delete(myImages : PhotoAlbum)

    @Query("SELECT * FROM photo_album_table ORDER BY imageId ASC")
    fun getAllImages() : LiveData<List<PhotoAlbum>>

    @Query("SELECT * FROM photo_album_table WHERE imageId = :id")
    suspend fun getItemById(id : Int) : PhotoAlbum

}