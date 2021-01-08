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
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.lists.Category_List
import com.example.myapplication.lists.Order_List
import com.squareup.picasso.Picasso

class OrderAdapter(var OrderList: ArrayList<Order_List>, var context: Context) :RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.order_custom, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return OrderList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var OrderInfo = OrderList[position]

        Picasso.with(context).load(URLContract.URLEntry.BASE_UEL2 + "/cash/public/uploads/products/" + OrderInfo.product_image).into(holder.Order_Image)

        holder.Order_Name.text = OrderInfo.product_name
        holder.Order_Total.text = OrderInfo.product_amount
        holder.Order_State.text = OrderInfo.product_state
        holder.Order_Date.text = OrderInfo.product_date
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var Order_Image = itemView.findViewById(R.id.OrderProductImage) as ImageView
        var Order_Name = itemView.findViewById(R.id.OrderProductName) as TextView
        var Order_Total = itemView.findViewById(R.id.OrderTotal) as TextView
        var Order_Date = itemView.findViewById(R.id.OrderDate) as TextView
        var Order_State = itemView.findViewById(R.id.OrderState) as TextView

    }
}