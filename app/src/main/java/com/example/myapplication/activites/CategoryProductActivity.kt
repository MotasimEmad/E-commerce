package com.example.myapplication.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.adapters.ProductAdapter
import com.example.myapplication.lists.Product_List
import kotlinx.android.synthetic.main.activity_category_product.*
import org.json.JSONException

class CategoryProductActivity : AppCompatActivity() {

    lateinit var mRequestQueue: RequestQueue

    lateinit var mArrayListProduct: ArrayList<Product_List>
    lateinit var ProCategoryRecyclerView: RecyclerView

    var CategoryID: Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_product)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        btnBackMain.setOnClickListener {
            onBackPressed()
        }

        val IntentGetData = intent
        CategoryID = IntentGetData.getExtras().getInt("category_id")

        mArrayListProduct = ArrayList()
        ProCategoryRecyclerView = findViewById(R.id.ProCategoryRecyclerView) as RecyclerView
        ProCategoryRecyclerView.layoutManager = GridLayoutManager(this, 2)

        loadProduct()
    }

    private fun loadProduct() {
        val CATEGORY_URL = URLContract.URLEntry.BASE_UEL + "show_category_product.php?category_id=" + CategoryID
        mRequestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.POST, CATEGORY_URL, null, Response.Listener { jsonObject ->
                try {
                    val jsonArray = jsonObject.getJSONArray("products")
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        val id = `object`.getInt("id")
                        val image = `object`.getString("image")
                        val name = `object`.getString("name")
                        val price = `object`.getInt("price")

                        mArrayListProduct.add(Product_List(id, image, name, price))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                val adapter = ProductAdapter(mArrayListProduct, this)
                ProCategoryRecyclerView.adapter = adapter

            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }
}
