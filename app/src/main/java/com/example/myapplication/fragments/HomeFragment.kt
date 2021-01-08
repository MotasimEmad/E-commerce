package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Request.Method.POST
import com.example.myapplication.R
import com.example.myapplication.adapters.ProductAdapter
import com.example.myapplication.lists.Category_List
import com.example.myapplication.lists.Product_List
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.activites.CartActivity
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.adapters.CategoryAdapter
import com.example.myapplication.sqlite.SQLiteDB
import org.json.JSONException

@Suppress("UNREACHABLE_CODE")
class HomeFragment: Fragment() {

    lateinit var mContext: Context
    lateinit var mRequestQueue: RequestQueue

    lateinit var mArrayListProduct: ArrayList<Product_List>
    lateinit var ProductRecyclerView: RecyclerView

    lateinit var mArrayListCategory: ArrayList<Category_List>
    lateinit var CategoryRecyclerView: RecyclerView


    lateinit var Morning: TextView
    lateinit var imageview: ImageView

    var UserID: Int = 0
    lateinit var UserEmail: String

    lateinit var mSQLiteDB: SQLiteDB

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.home_fragment, container, false)

        mContext = context!!

        mArrayListCategory = ArrayList()
        CategoryRecyclerView = view.findViewById(R.id.AllCategoryRecyclerView) as RecyclerView
        CategoryRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false)

        mArrayListProduct = ArrayList()
        ProductRecyclerView = view.findViewById(R.id.ProductRecyclerViewHome) as RecyclerView
        ProductRecyclerView.layoutManager = GridLayoutManager(mContext, 2)


        loadCategory()
        loadProduct()

        imageview = view.findViewById(R.id.btnOpenCart)
        imageview.setOnClickListener {
            startActivity(Intent(mContext, CartActivity::class.java))
        }

        return view
    }

    private fun loadCategory() {
        val CATEGORY_URL = URLContract.URLEntry.BASE_UEL + "show_categories.php"
        mRequestQueue = Volley.newRequestQueue(activity)
        val jsonObjectRequest =
            JsonObjectRequest(POST, CATEGORY_URL, null, Response.Listener { jsonObject ->
                try {
                    val jsonArray = jsonObject.getJSONArray("categories")
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        val id = `object`.getInt("id")
                        val img = `object`.getString("image")
                        val name = `object`.getString("name")

                        mArrayListCategory.add(Category_List(id,img, name))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                val adapter = CategoryAdapter(mArrayListCategory, mContext)
                CategoryRecyclerView.adapter = adapter

            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }


    private fun loadProduct() {
        val CATEGORY_URL = URLContract.URLEntry.BASE_UEL + "show_products.php"
        mRequestQueue = Volley.newRequestQueue(activity)
        val jsonObjectRequest =
            JsonObjectRequest(POST, CATEGORY_URL, null, Response.Listener { jsonObject ->
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

                val adapter = ProductAdapter(mArrayListProduct, mContext)
                ProductRecyclerView.adapter = adapter

            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }

    companion object {
        fun newIntance(): HomeFragment {
            return HomeFragment()
        }
    }

}