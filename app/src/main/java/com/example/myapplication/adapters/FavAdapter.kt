package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.lists.Category_List
import com.example.myapplication.lists.Fav_List

class FavAdapter(var FavList: ArrayList<Fav_List>, var context: Context) :RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_custom, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FavList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var FavInfo = FavList[position]

        holder.FavProduct_Name.text = FavInfo.name
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var FavProduct_Name = itemView.findViewById(R.id.FavProductName) as TextView

    }
}