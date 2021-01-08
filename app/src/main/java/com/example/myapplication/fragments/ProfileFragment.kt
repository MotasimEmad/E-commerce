package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.myapplication.R
import com.example.myapplication.activites.OrderActivity
import com.example.myapplication.adapters.CategoryAdapter
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.lists.Category_List
import com.example.myapplication.sqlite.SQLiteDB
import kotlinx.android.synthetic.main.profile_fragment.*
import org.json.JSONException

class ProfileFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var mSQLiteDB: SQLiteDB

    lateinit var UserName: String
    lateinit var UserNumber: String
    lateinit var mRequestQueue: RequestQueue

    lateinit var List_More: ListView
    var items = arrayOf(
        "My Order List",
        ""
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        mContext = context!!




        List_More = view.findViewById(R.id.ListMore)
        val Adapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, items)
        List_More.adapter = Adapter

        List_More.onItemClickListener =
            AdapterView.OnItemClickListener { _, view, position, _ ->
                if (position == 0) {
                    startActivity(Intent(mContext, OrderActivity::class.java))
                }

                if (position == 1) {

            }


            }

        return view
    }

    companion object {
        fun newIntance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    private fun loadUserData() {
        val CATEGORY_URL = URLContract.URLEntry.BASE_UEL + "get_user_data.php?user_email=" + UserNumber
        mRequestQueue = Volley.newRequestQueue(activity)
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.POST, CATEGORY_URL, null, Response.Listener { jsonObject ->
                try {
                    val jsonArray = jsonObject.getJSONArray("user_info")
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        val name = `object`.getString("name")


                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }


            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }

}
