package com.example.myapplication.activites

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import com.example.myapplication.R
import com.example.myapplication.sqlite.SQLiteDB
import kotlinx.android.synthetic.main.activity_product_details.*
import androidx.core.app.NotificationCompat.getExtras
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.adapters.ProductAdapter
import com.example.myapplication.contracts.ProductContract
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.lists.Product_List
import com.example.myapplication.sqlite.ProductLite
import com.squareup.picasso.Picasso
import org.json.JSONException

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductDetailsActivity : AppCompatActivity() {

    lateinit var mSqLiteDatabase: SQLiteDatabase

    var ProductID: Int = 0
    var UserID: Int = 0
    lateinit var mSQLiteDB: SQLiteDB
    lateinit var mRequestQueue: RequestQueue

    var counter = 1
    var Product_Price = 0
    lateinit var Product_Name: String
    lateinit var Product_Amount: String

    lateinit var Total: String


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        btnBackMain.setOnClickListener {
            onBackPressed()
        }

        val mProductLite = ProductLite(this)
        mSqLiteDatabase = mProductLite.writableDatabase

        val IntentGetData = intent
        ProductID = IntentGetData.getExtras().getInt("product_id")
        UserID = IntentGetData.getExtras().getInt("user_id")

        mSQLiteDB = SQLiteDB(this)

        btnMax.setOnClickListener {
            counter++
            TextProductNum.text = "" + counter
            ProductPrice.text = (Product_Price * counter).toString() + "$"
        }

        btnMin.setOnClickListener {
            if (counter <= 0)
                counter = 1
            else
                counter--
            TextProductNum.text = "" + counter
            ProductPrice.text = (Product_Price * counter).toString() + "$"
        }

        btnCheckCart.setOnClickListener {
            addItem()
            Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG).show()
        }

        Total = ProductPrice.text.toString()

        loadProductDetails()
    }

    private fun loadProductDetails() {
        val PRODUCTS_URL = URLContract.URLEntry.BASE_UEL + "show_product_details.php?product_id="+ ProductID
        mRequestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.POST, PRODUCTS_URL, null, Response.Listener { jsonObject ->
                try {
                    val jsonArray = jsonObject.getJSONArray("product_details")
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        val img = `object`.getString("image")
                        val name = `object`.getString("name")
                        val price = `object`.getInt("price")
                        val description = `object`.getString("descrption")
                        val size = `object`.getString("size")

                        Picasso.with(this).load(URLContract.URLEntry.BASE_UEL2 + "/cash/public/uploads/products/" + img).into(ProductImage)


                        ProductName.text = name
                        ProductPrice.text = "$price$"
                        ProductDetails.text = description

                        Product_Name = name
                        Product_Price =  price
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }

    private fun addItem() {
        val Name = Product_Name
        val Price =  ProductPrice.text
        val Amount =  counter
        var contentValues = ContentValues()
        contentValues.put(ProductContract.ProductEntry.CoulmnNum, ProductID)
        contentValues.put(ProductContract.ProductEntry.CoulmnUserID, UserID)
        contentValues.put(ProductContract.ProductEntry.CoulmnName, Name)
        contentValues.put(ProductContract.ProductEntry.CoulmnPrice, Price.toString())
        contentValues.put(ProductContract.ProductEntry.CoulmnAmount, Amount)

        mSqLiteDatabase.insert(ProductContract.ProductEntry.TableName, null, contentValues)
    }
}
