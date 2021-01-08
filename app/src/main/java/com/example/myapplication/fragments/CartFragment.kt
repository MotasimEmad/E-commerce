package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.CartAdapter
import com.example.myapplication.contracts.ProductContract
import com.example.myapplication.lists.*
import com.example.myapplication.sqlite.ProductLite
import kotlinx.android.synthetic.main.cart_fragment.*

class CartFragment: Fragment() {

    lateinit var mSqLiteDatabase: SQLiteDatabase
    var amount = 0

    lateinit var mContext: Context
    lateinit var countQueryCursor: Cursor

    lateinit var mArrayListCart: ArrayList<Cart_List>
    lateinit var CartRecyclerView: RecyclerView
    lateinit var adapter: CartAdapter

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.cart_fragment, container, false)

        getTotal()

        mContext = context!!

        mArrayListCart = ArrayList()

        CartRecyclerView = view.findViewById(R.id.CartRecyclerView) as RecyclerView
        CartRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)

        val mProductLite = ProductLite(mContext)
        mSqLiteDatabase = mProductLite.writableDatabase

        adapter = CartAdapter(mContext,  getAllData())
        CartRecyclerView.adapter = adapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeItem(viewHolder.itemView.tag as Long)
            }
        }).attachToRecyclerView(CartRecyclerView)

        loadCart()
        return view
    }


    private fun loadCart() {
//        mArrayListCart.add(Cart_List("شيبس 1","2","$200"))
//        mArrayListCart.add(Cart_List("حلويات 1","2","$200"))
//        mArrayListCart.add(Cart_List("هدية 1","1","$150"))
//        mArrayListCart.add(Cart_List("شيبس 2","5","$1000"))

//        val adapter = CartAdapter(mArrayListCart, mContext)
//        CartRecyclerView.adapter = adapter
    }

    companion object {
        fun newIntance(): CartFragment {
            return CartFragment()
        }
    }

    private fun getAllData(): Cursor {
        return mSqLiteDatabase.query(
            ProductContract.ProductEntry.TableName,
            null, null, null, null, null,
            ProductContract.ProductEntry.CoulmnTime + " DESC"
        )
    }

    private fun getTotal(): Cursor {
        val cursorTotal = mSqLiteDatabase.rawQuery(
            "SELECT SUM (price) as Total FROM " + ProductContract.ProductEntry.TableName,
            null
        )
        if (cursorTotal.moveToFirst()) {
            val total = cursorTotal.getInt(cursorTotal.getColumnIndex("Total"))
            Total.setText(total.toString())
        }
        return cursorTotal
    }

    private fun removeItem(id: Long) {
        mSqLiteDatabase.delete(
            ProductContract.ProductEntry.TableName,
            ProductContract.ProductEntry._ID + "=" + id, null)
        adapter.swapCursor(getAllData())
        getTotal()
    }
}