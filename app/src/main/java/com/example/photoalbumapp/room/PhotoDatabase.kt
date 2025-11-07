package com.example.photoalbumapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photoalbumapp.Model.PhotoAlbum

@Database(entities = [PhotoAlbum::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun getPhotoAlbumDao(): PhotoDAO

    companion object {

        @Volatile
        private var instance : PhotoDatabase? = null

        fun getInstanceDatabase(context: Context) : PhotoDatabase {

            synchronized(this) {

                if (instance == null) {

                    instance = Room.databaseBuilder(context.applicationContext, PhotoDatabase::class.java, "photo_database").build()

                }
                return instance as PhotoDatabase
            }
        }

    }

}