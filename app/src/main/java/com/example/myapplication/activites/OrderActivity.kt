package com.example.myapplication.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request.Method.POST
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.adapters.OrderAdapter
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.lists.Order_List
import com.example.myapplication.sqlite.SQLiteDB
import kotlinx.android.synthetic.main.activity_order.*
import org.json.JSONException

class OrderActivity : AppCompatActivity() {

    lateinit var mArrayListOrder: ArrayList<Order_List>
    lateinit var OrderRecyclerView: RecyclerView

    lateinit var mRequestQueue: RequestQueue

    lateinit var mSQLiteDB: SQLiteDB
    lateinit var UserNumber: String

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        btnBackMain.setOnClickListener {
            onBackPressed()
        }

        mSQLiteDB = SQLiteDB(this)
        UserNumber = mSQLiteDB._Email

        mArrayListOrder = ArrayList()
        OrderRecyclerView = findViewById(R.id.OrderRecyclerView)
        OrderRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        loadOrder()
    }

    private fun loadOrder() {
        val ORDER_URL = URLContract.URLEntry.BASE_UEL + "get_orders_list.php?phone_number=" + UserNumber
        mRequestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest =
            JsonObjectRequest(POST, ORDER_URL, null, Response.Listener { jsonObject ->
                try {
                    val jsonArray = jsonObject.getJSONArray("products")
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        val image = `object`.getString("product_image")
                        val name = `object`.getString("product_name")
                        val price = `object`.getString("product_price")
                        val amount = `object`.getString("product_amount")
                        val state = `object`.getString("order_state")
                        val date = `object`.getString("order_date")

                        mArrayListOrder.add(Order_List(image, name, price, amount, state, date))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                val adapter = OrderAdapter(mArrayListOrder, this)
                OrderRecyclerView.adapter = adapter

            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }
}
