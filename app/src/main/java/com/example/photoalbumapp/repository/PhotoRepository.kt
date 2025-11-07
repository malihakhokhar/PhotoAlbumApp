package com.example.photoalbumapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.photoalbumapp.Model.PhotoAlbum
import com.example.photoalbumapp.room.PhotoDAO
import com.example.photoalbumapp.room.PhotoDatabase

class PhotoRepository(application: Application) {

    var photoDao : PhotoDAO
    var photoList : LiveData<List<PhotoAlbum>>

    init {
        val database = PhotoDatabase.getInstanceDatabase(application)
        photoDao = database.getPhotoAlbumDao()
        photoList = photoDao.getAllImages()
    }

    suspend fun insert(photoAlbum: PhotoAlbum){
        photoDao.insert(photoAlbum)
    }
    suspend fun update(photoAlbum: PhotoAlbum){
        photoDao.update(photoAlbum)
    }
    suspend fun delete(photoAlbum: PhotoAlbum) {
        photoDao.delete(photoAlbum)
    }

    fun getAllImages() : LiveData<List<PhotoAlbum>> {
        return photoList
    }

    suspend fun getItemById(id : Int) : PhotoAlbum {
        return photoDao.getItemById(id)
    }


}