package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activites.CategoryProductActivity
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.lists.Category_List
import com.squareup.picasso.Picasso

class AllCategoryAdapter(var CategoryList: ArrayList<Category_List>, var context: Context) :RecyclerView.Adapter<AllCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.all_category_custom, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return CategoryList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var CategoryInfo = CategoryList[position]

        Picasso.with(context).load(URLContract.URLEntry.BASE_UEL + "/category/" + CategoryInfo.image).into(holder.Category_Image)
        holder.Category_Name.text = CategoryInfo.name

        holder.View_Category.setOnClickListener {
            var intent = Intent(context, CategoryProductActivity::class.java)
            intent.putExtra("category_id", CategoryInfo.id)
            context.startActivities(arrayOf(intent))
        }

    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var Category_Image = itemView.findViewById(R.id.CategoryImage) as ImageView
        var Category_Name = itemView.findViewById(R.id.CategoryName) as TextView
        var View_Category = itemView.findViewById(R.id.btnViewCategory) as Button

    }
}