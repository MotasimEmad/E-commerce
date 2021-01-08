package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.FavAdapter
import com.example.myapplication.lists.Fav_List

class FavouriteFragment: Fragment() {

    lateinit var mContext: Context

    lateinit var mArrayListFav: ArrayList<Fav_List>
    lateinit var FavRecyclerView: RecyclerView

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.favourite_fragment, container, false)

        mContext = context!!

        mArrayListFav = ArrayList()
        FavRecyclerView = view.findViewById(R.id.FavRecyclerView) as RecyclerView
        FavRecyclerView.layoutManager = GridLayoutManager(mContext, 2)


        return view
    }

    private fun loadFav() {
//        mArrayListFav.add(Fav_List("شيبس"))
//        mArrayListFav.add(Fav_List("حلويات"))
//        mArrayListFav.add(Fav_List("هدايا"))
//        mArrayListFav.add(Fav_List("شيبس"))
//        mArrayListFav.add(Fav_List("حلويات"))
//        mArrayListFav.add(Fav_List("هدايا"))

        val adapter = FavAdapter(mArrayListFav, mContext)
        FavRecyclerView.adapter = adapter
    }

    companion object {
        fun newIntance(): FavouriteFragment {
            return FavouriteFragment()
        }
    }
}