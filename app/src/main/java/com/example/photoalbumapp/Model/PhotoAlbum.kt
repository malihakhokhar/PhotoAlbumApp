package com.example.photoalbumapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_album_table")
class PhotoAlbum(var imageTitle : String, var imageDescription : String, var imageAsString : String) {

    @PrimaryKey(autoGenerate = true)
    var imageId : Int = 0

}