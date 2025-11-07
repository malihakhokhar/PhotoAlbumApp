package com.example.photoalbumapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.photoalbumapp.Model.PhotoAlbum
import com.example.photoalbumapp.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoViewModel(application : Application) : AndroidViewModel(application) {

    var repository : PhotoRepository
    var photoList : LiveData<List<PhotoAlbum>>

    init {
        repository = PhotoRepository(application)
        photoList = repository.getAllImages()

    }

    fun insert(photoAlbum: PhotoAlbum) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(photoAlbum)
    }
    fun update(photoAlbum: PhotoAlbum) = viewModelScope.launch(Dispatchers.IO){
        repository.update(photoAlbum)
    }
    fun delete(photoAlbum: PhotoAlbum) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(photoAlbum)
    }

    fun getAllImages() : LiveData<List<PhotoAlbum>>{
        return photoList
    }

    suspend fun getItemById(id : Int) : PhotoAlbum {
        return repository.getItemById(id)

    }

}