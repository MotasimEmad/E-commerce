package com.example.myapplication.sends;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.contracts.URLContract;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_Money extends StringRequest {

    private static final String Send_data_url= URLContract.URLEntry.BASE_UEL + "money.php";
    private HashMap MapData;

        public Send_Data_Money(String state, Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);
        MapData = new HashMap<>();
        MapData.put("State", state);
    }

    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }

}
