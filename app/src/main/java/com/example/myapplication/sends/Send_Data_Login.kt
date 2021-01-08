package com.example.myapplication.sends

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.myapplication.contracts.URLContract


import java.util.HashMap

class Send_Data_Login(phone: String, pass: String, listener: Response.Listener<String>) : StringRequest(Request.Method.POST,
    Send_data_url, listener, null) {
    private val MapData: MutableMap<String, String>

    init {
        MapData = HashMap()
        MapData["Phone"] = phone
        MapData["Password"] = pass
    }

    override fun getParams(): Map<String, String> {
        return MapData
    }

    companion object {

        private val Send_data_url = URLContract.URLEntry.BASE_UEL + "login.php"
    }

}
