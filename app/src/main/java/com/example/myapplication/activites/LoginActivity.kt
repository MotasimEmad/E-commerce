package com.example.myapplication.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.example.myapplication.sends.Send_Data_Login
import com.example.myapplication.sqlite.SQLiteDB

class LoginActivity : AppCompatActivity() {

    lateinit var mSQLiteDB: SQLiteDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        mSQLiteDB = SQLiteDB(this)
        btnLogin.setOnClickListener {

            val Phone = TextInputEditTextLoginNumber.text.toString().trim()
            val Password = TextInputEditTextLoginPassword.text.toString().trim()

            var Login = Response.Listener<String> { s ->
                try {
                    val jsonObject = JSONObject(s)
                    var success = jsonObject.getBoolean("success")

                    if (success) {
                        Toast.makeText(this, "Welcome ;-)", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        mSQLiteDB.update_Data_Login(Phone , Password)
                    } else {
                        Toast.makeText(this, "Something wrong !", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
            }

            val mSend_Data_Register =
                Send_Data_Login(Phone, Password, Login)
            val login = Volley.newRequestQueue(this)
            login.add(mSend_Data_Register)
        }

        CreateAccountText.setOnClickListener {
            startActivity(Intent(this, RegestrationActivity::class.java))
        }
    }
}
