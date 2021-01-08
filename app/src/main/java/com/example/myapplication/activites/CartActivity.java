package com.example.myapplication.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.sends.Send_Data_Order;
import com.example.myapplication.adapters.CartAdapter;
import com.example.myapplication.contracts.ProductContract;
import com.example.myapplication.sqlite.ProductLite;

import org.json.JSONObject;

public class CartActivity extends AppCompatActivity {

    String count_id, count_name, user_id;
    int count_price, count_amount, i;

    SQLiteDatabase mSqLiteDatabase;
    Cursor countQueryCursor;

    TextView Total;

    CartAdapter mCartAdapter;
    RecyclerView CartRecyclerView;
    int UserID;

    Button btnCheckCart;
    int SendTotal;

    EditText Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Location = findViewById(R.id.TextInputEditTextLocation);

        Total = findViewById(R.id.Total);
        CartRecyclerView = findViewById(R.id.CartRecyclerView);
        CartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ProductLite mProductLite = new ProductLite(this);
        mSqLiteDatabase = mProductLite.getWritableDatabase();

        mCartAdapter = new CartAdapter(this, getAllData());
        CartRecyclerView.setAdapter(mCartAdapter);

        getTotal();
        getTotalSend();

        btnCheckCart = findViewById(R.id.btnCheckCart);
        btnCheckCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countQueryCursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ProductContract.ProductEntry.TableName, null);
                if (countQueryCursor.moveToFirst()) {
                    while (!countQueryCursor.isAfterLast()) {
                        count_id = countQueryCursor.getString(countQueryCursor.getColumnIndex("product_id"));
                        user_id = countQueryCursor.getString(countQueryCursor.getColumnIndex("user_id"));
                        count_name = countQueryCursor.getString(countQueryCursor.getColumnIndex("name"));
                        count_price = countQueryCursor.getInt(countQueryCursor.getColumnIndex("price"));
                        count_amount = countQueryCursor.getInt(countQueryCursor.getColumnIndex("amount"));

//                        String ArrayItem[] = {
//                                count_id,
//                                count_name,
//                                String.valueOf(count_price),
//                                String.valueOf(count_amount)
//                        };
//
//                        arraySize = ArrayItem.length;
//                        for (i = 0; i < arraySize; i++) {
//                            Toast.makeText(CartActivity.this, "Array: " + ArrayItem[i]
//                                    , Toast.LENGTH_LONG).show();
//                        }

                        String location = Location.getText().toString().trim();

                            final Response.Listener<String> Order = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {
                                            Toast.makeText(CartActivity.this, "Send", Toast.LENGTH_LONG).show();
                                            mSqLiteDatabase.execSQL("delete from " + ProductContract.ProductEntry.TableName);
                                            mCartAdapter.swapCursor(getAllData());
                                            getTotal();

                                            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                            intent.putExtra("total",SendTotal);
                                            startActivity(intent);


                                        } else {
                                            Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            Send_Data_Order mSend_Data_Order = new Send_Data_Order(count_id, String.valueOf(count_amount), String.valueOf(count_price), String.valueOf(user_id), location, Order);
                            RequestQueue order = Volley.newRequestQueue(CartActivity.this);
                            order.add(mSend_Data_Order);
                            countQueryCursor.moveToNext();
                    }
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(CartRecyclerView);
    }

    private Cursor getAllData() {
        return mSqLiteDatabase.query(
                ProductContract.ProductEntry.TableName,
                null,
                null,
                null,
                null,
                null,
                ProductContract.ProductEntry.CoulmnTime + " DESC"
        );
    }

    private Cursor getTotal() {
        Cursor cursorTotal = mSqLiteDatabase.rawQuery("SELECT SUM (price) as Total FROM " + ProductContract.ProductEntry.TableName, null);
        if (cursorTotal.moveToFirst()) {
            int total = cursorTotal.getInt((cursorTotal.getColumnIndex("Total")));
            Total.setText(String.valueOf(total + "$"));
        }
        return cursorTotal;
    }

    private Cursor getTotalSend() {
        Cursor cursorTotal = mSqLiteDatabase.rawQuery("SELECT SUM (price) as Total FROM " + ProductContract.ProductEntry.TableName, null);
        if (cursorTotal.moveToFirst()) {
            int total = cursorTotal.getInt((cursorTotal.getColumnIndex("Total")));
            SendTotal = total;
        }
        return cursorTotal;
    }

    private void removeItem(long id) {
        mSqLiteDatabase.delete(ProductContract.ProductEntry.TableName,
                ProductContract.ProductEntry._ID + "=" + id, null);
        mCartAdapter.swapCursor(getAllData());
        getTotal();
    }

}
