package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.activites.MainActivity
import com.example.myapplication.contracts.URLContract
import com.example.myapplication.sends.Send_Data_Money
import kotlinx.android.synthetic.main.activity_money.*
import org.json.JSONException
import org.json.JSONObject

class MoneyActivity : AppCompatActivity() {

    var number = 0
    var Money = 0
    var total = 0

    lateinit var state: String

    lateinit var mRequestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        state = "new"

        val IntentGetData = intent
        number = IntentGetData.getExtras().getInt("number")
        total = IntentGetData.getExtras().getInt("total")

        Toast.makeText(this, "" + number, Toast.LENGTH_LONG).show()
        TextTotal.text = "$total$"

        loadAccountDetails()

        btnPayment.setOnClickListener {
            var Balance = total

            if (Balance > Money) {
                Toast.makeText(this, "Sorry you dont have enought money", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun loadAccountDetails() {
        val PRODUCTS_URL = URLContract.URLEntry.BASE_UEL + "get_account_data.php?number="+ number
        mRequestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.POST, PRODUCTS_URL, null, Response.Listener { jsonObject ->
                try {
                    val jsonArray = jsonObject.getJSONArray("account_info")
                    for (i in 0 until jsonArray.length()) {
                        val `object` = jsonArray.getJSONObject(i)
                        val number = `object`.getString("account_number")
                        val name = `object`.getString("name")
                        val bank = `object`.getString("bank")
                        val money = `object`.getString("money")

                        AccountUserName.text = name
                        AccountNumber.text = number
                        AccountBank.text = bank

                        Money = money.toInt()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }, Response.ErrorListener { })
        mRequestQueue.add(jsonObjectRequest)
    }
}
