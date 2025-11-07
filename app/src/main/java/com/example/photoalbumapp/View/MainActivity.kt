package com.example.photoalbumapp.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoalbumapp.R
import com.example.photoalbumapp.adapter.ImageAdapter
import com.example.photoalbumapp.databinding.ActivityMainBinding
import com.example.photoalbumapp.viewModel.PhotoViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    lateinit var photoViewModel: PhotoViewModel

    lateinit var myImageAdapter : ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)

        photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]

        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        myImageAdapter = ImageAdapter(this)
        mainBinding.recyclerView.adapter = myImageAdapter

        photoViewModel.getAllImages().observe(this , Observer { photos ->
            //Update UI
            myImageAdapter.setImage(photos)

        })

        mainBinding.floatingActionButton.setOnClickListener {
            //open addImageActivity
            val intent = Intent(this , AddImageActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                photoViewModel.delete(myImageAdapter.returnItemAtGivenPosition(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(mainBinding.recyclerView)

    }
}