package com.example.myapplication.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.sends.Send_Data_Reg
import kotlinx.android.synthetic.main.activity_reqestration.*
import org.json.JSONObject

class RegestrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reqestration)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        btnReg.setOnClickListener {

            val Name = TextInputEditTextRegName.text.toString().trim()
            val PhoneNumber = TextInputEditTextRegNumber.text.toString().trim()
            val Password = TextInputEditTextRegPassword.text.toString().trim()

            val Reg = Response.Listener<String> { s ->
                try {
                    val jsonObject = JSONObject(s)
                    val success = jsonObject.getBoolean("success")

                    if (success) {
                        Toast.makeText(applicationContext, "Success ;-)", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, "Something is wrong !", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                }
            }
            val send_data =
                Send_Data_Reg(Name, PhoneNumber, Password, Reg)
            val reg = Volley.newRequestQueue(this)
            reg.add(send_data)
        }

        AlreadyText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
