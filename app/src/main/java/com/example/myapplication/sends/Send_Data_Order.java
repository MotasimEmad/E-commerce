package com.example.myapplication.sends;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.contracts.URLContract;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_Order extends StringRequest {

    private static final String Send_data_url= URLContract.URLEntry.BASE_UEL + "order.php";
    private HashMap MapData;

        public Send_Data_Order(String product_id, String product_amount, String product_total, String user_id, String location, Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);
        MapData = new HashMap<>();
        MapData.put("ProductID", product_id);
        MapData.put("ProductAmount", product_amount);
        MapData.put("ProductTotal", product_total);
        MapData.put("UserID", user_id);
        MapData.put("Location", location);
    }

    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }

}
