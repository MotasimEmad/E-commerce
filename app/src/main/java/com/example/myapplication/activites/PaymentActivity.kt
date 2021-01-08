package com.example.myapplication.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.MoneyActivity
import com.example.myapplication.R
import com.example.myapplication.sends.Send_Data_Payment
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {

    var Total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        val IntentGetData = intent
        Total = IntentGetData.getExtras().getInt("total")

        Toast.makeText(this, "" + Total, Toast.LENGTH_LONG).show()

        btnNextPayment.setOnClickListener {

            var AccountNumber = TextInputEditTextAccountNumber.text.toString().trim()
            var AccountPassword = TextInputEditTextAccountPassword.text.toString().trim()

            var CheckData = Response.Listener<String> { s ->
                try {
                    val jsonObject = JSONObject(s)
                    var success = jsonObject.getBoolean("success")

                    if (success) {
                        var intent = Intent(this, MoneyActivity::class.java)
                        intent.putExtra("number",TextInputEditTextAccountNumber.text.toString().trim().toInt())
                        intent.putExtra("total",Total)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Something wrong !", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
            }

            val mSend_Data_Register =
                Send_Data_Payment(AccountNumber, AccountPassword, CheckData)
            val login = Volley.newRequestQueue(this)
            login.add(mSend_Data_Register)
        }
    }
}
