package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.activites.ProductDetailsActivity
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.lists.Category_List
import com.example.myapplication.lists.Product_List
import com.example.myapplication.sqlite.SQLiteDB
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONException

class ProductAdapter(var ProductList: ArrayList<Product_List>, var context: Context) :RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    lateinit var UserEmail: String
    var UserID: Int = 0

    lateinit var mSQLiteDB: SQLiteDB
    lateinit var mRequestQueue: RequestQueue

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.product_custom, parent, false)

        mSQLiteDB = SQLiteDB(context)
        UserEmail = mSQLiteDB._Email
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ProductList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ProductInfo = ProductList[position]

        Picasso.with(context).load(URLContract.URLEntry.BASE_UEL2 + "/cash/public/uploads/products/" + ProductInfo.product_image).into(holder.Product_Image)

        holder.Product_Name.text = ProductInfo.product_name
        holder.Product_Price.text = ProductInfo.product_price.toString()

            val GET_URL = URLContract.URLEntry.BASE_UEL + "get_user_data.php?user_email="+ UserEmail
            mRequestQueue = Volley.newRequestQueue(context)
            val jsonObjectRequest =
                JsonObjectRequest(Request.Method.POST, GET_URL, null, Response.Listener { jsonObject ->
                    try {
                        val jsonArray = jsonObject.getJSONArray("user_info")
                        for (i in 0 until jsonArray.length()) {
                            val `object` = jsonArray.getJSONObject(i)
                            val id = `object`.getInt("id")

                            UserID = id
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }, Response.ErrorListener { })
            mRequestQueue.add(jsonObjectRequest)

        holder.ViewItem.setOnClickListener {
            Toast.makeText(context, "" + UserID, Toast.LENGTH_LONG).show()
            var intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("product_id", ProductInfo.product_id)
            intent.putExtra("user_id", UserID)
            context.startActivities(arrayOf(intent))
        }

//        holder.Increment.setOnClickListener {
//            counter++
//            holder.ItemsNum.text = "" + counter
//        }
//
//        holder.Decrement.setOnClickListener {
//            if (counter <= 0)
//                counter = 0
//            else
//                counter--
//            holder.ItemsNum.text = "" + counter
//        }

    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var Product_Name = itemView.findViewById(R.id.ProductName) as TextView
        var Product_Category = itemView.findViewById(R.id.ProductCategory) as TextView
        var Product_Price = itemView.findViewById(R.id.ProductPrice) as TextView

        var ViewItem = itemView.findViewById(R.id.btnViewItem) as Button

        var Product_Image = itemView.findViewById(R.id.ProductImage) as ImageView

        var ProductConstraintLayout = itemView.findViewById(R.id.ProductConstraintLayout) as ConstraintLayout
    }

}