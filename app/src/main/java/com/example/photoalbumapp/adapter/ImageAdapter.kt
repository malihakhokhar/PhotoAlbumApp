package com.example.photoalbumapp.adapter

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoalbumapp.Model.PhotoAlbum
import com.example.photoalbumapp.View.UpdateImageActivity
import com.example.photoalbumapp.databinding.ImageItemBinding
import com.example.photoalbumapp.util.ConvertImage

class ImageAdapter(val activity : Activity): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var imageList : List<PhotoAlbum> = ArrayList()

    fun setImage(images : List<PhotoAlbum>) {
        this.imageList = images
        notifyDataSetChanged()
    }

    class ImageViewHolder(val itemBinding : ImageItemBinding) : RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        var myImage = imageList[position]
        with(holder){
            itemBinding.textViewTitle.text = myImage.imageTitle
            itemBinding.textViewDescription.text = myImage.imageDescription
            val imageAsBitmap = ConvertImage.convertToBitmap(myImage.imageAsString)
            itemBinding.imageView.setImageBitmap(imageAsBitmap)

            itemBinding.cardView.setOnClickListener {
                val intent = Intent(activity, UpdateImageActivity::class.java)
                intent.putExtra("id", myImage.imageId)
                activity.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun returnItemAtGivenPosition(position : Int) : PhotoAlbum {
        return imageList[position]
    }


}