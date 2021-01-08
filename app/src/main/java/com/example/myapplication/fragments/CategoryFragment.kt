package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.adapters.AllCategoryAdapter
import com.example.myapplication.adapters.CategoryAdapter
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.lists.Category_List
import com.example.myapplication.lists.Product_List
import kotlinx.android.synthetic.main.category_fragment.*
import org.json.JSONException

class CategoryFragment: Fragment() {

    lateinit var mContext: Context
    lateinit var mRequestQueue: RequestQueue

    lateinit var mArrayListCategory: ArrayList<Category_List>
    lateinit var CategoryRecyclerView: RecyclerView

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.category_fragment, container, false)

        mContext = context!!

        mArrayListCategory = ArrayList()
        CategoryRecyclerView = view.findViewById(R.id.AllCategoryRecyclerView) as RecyclerView
        CategoryRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)

        loadCategory()
        return view
    }

    private fun loadCategory() {
        val CATEGORY_URL = URLContract.URLEntry.BASE_UEL + "show_categories.php"
        mRequestQueue = Volley.newRequestQueue(activity)
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.POST, CATEGORY_URL, null, Response.Listener { jsonObject ->
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

                val adapter = AllCategoryAdapter(mArrayListCategory, mContext)
                CategoryRecyclerView.adapter = adapter

            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }

    companion object {
        fun newIntance(): CategoryFragment {
            return CategoryFragment()
        }
    }
}