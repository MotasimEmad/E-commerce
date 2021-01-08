package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
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
import com.example.myapplication.adapters.ProductAdapterLite;
import com.example.myapplication.contracts.ProductContract;
import com.example.myapplication.lists.List_Order;
import com.example.myapplication.sqlite.ProductLite;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    SQLiteDatabase mSqLiteDatabase;
    int amount = 0;

    TextView Amount, Price;
    EditText ProductName, ProductPrice;
    Button btnMax, btnMin, btnAdd, btnCon;
    RecyclerView ProductRecyclerView;

    ProductAdapterLite mProductAdapterLite;

    String product_id, amountTotal, priceTotal;
    String product_name;

    String count_id, count_name;
    int count_price, count_amount, i;

    Cursor countQueryCursor;

    ArrayList<List_Order> List_Order = new ArrayList<>();

    int arraySize;
    String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ProductRecyclerView = findViewById(R.id.ProductRecyclerView);

        final ProductLite mProductLite = new ProductLite(this);
        mSqLiteDatabase = mProductLite.getWritableDatabase();
        ProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductAdapterLite = new ProductAdapterLite(this, getAllData());
        ProductRecyclerView.setAdapter(mProductAdapterLite);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(ProductRecyclerView);


        Amount = findViewById(R.id.TextProductNum);
        Price = findViewById(R.id.ProductPriceTxt);
        ProductName = findViewById(R.id.EditName);
        ProductPrice = findViewById(R.id.EditPrice);
        btnMax = findViewById(R.id.btnMax);
        btnMin = findViewById(R.id.btnMin);
        btnAdd = findViewById(R.id.btnAdd);
        btnCon = findViewById(R.id.btnCon);

        List_Order = new ArrayList<>();

        getTotal();

        btnMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount++;
                Amount.setText(String.valueOf(amount));
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 0) {
                    amount--;
                    Amount.setText(String.valueOf(amount));
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
                getTotal();
            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countQueryCursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ProductContract.ProductEntry.TableName, null);
                if (countQueryCursor.moveToFirst()) {
                    while (!countQueryCursor.isAfterLast()) {
                        count_id = countQueryCursor.getString(countQueryCursor.getColumnIndex("name"));
                        count_name = countQueryCursor.getString(countQueryCursor.getColumnIndex("name"));
                        count_price = countQueryCursor.getInt(countQueryCursor.getColumnIndex("price"));
                        count_amount = countQueryCursor.getInt(countQueryCursor.getColumnIndex("amount"));

                        String ArrayItem[] = {
                                count_id,
                                count_name,
                                String.valueOf(count_price),
                                String.valueOf(count_amount)
                        };

                        arraySize = ArrayItem.length;
                         for (i = 0; i < arraySize; i++) {
                             Toast.makeText(TestActivity.this, "Array: " + ArrayItem[i]
                                     , Toast.LENGTH_LONG).show();
                         }

                        // List_Order.add(new List_Order(count_id, count_name, count_price, count_amount));
                        countQueryCursor.moveToNext();
                    }
                }
                    final Response.Listener<String> Order = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    Toast.makeText(TestActivity.this, "Send", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(TestActivity.this, "Error", Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(TestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    };
//                Send_Data_Order mSend_Data_Order = new Send_Data_Order(String.valueOf(i), Order);
//                    RequestQueue order = Volley.newRequestQueue(TestActivity.this);
//                    order.add(mSend_Data_Order);

                mProductAdapterLite.swapCursor(getAllData());
                getTotal();
            }
        });
    }

    private void addItem() {
        String Name = ProductName.getText().toString().trim();
        String Price = ProductPrice.getText().toString().trim();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.ProductEntry.CoulmnName, Name);
        contentValues.put(ProductContract.ProductEntry.CoulmnPrice, Price);
        contentValues.put(ProductContract.ProductEntry.CoulmnAmount, String.valueOf(amount));

        mSqLiteDatabase.insert(ProductContract.ProductEntry.TableName, null, contentValues);
        mProductAdapterLite.swapCursor(getAllData());
        ProductName.getText().clear();
        ProductPrice.getText().clear();
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
            Price.setText(String.valueOf(total));
        }
        return cursorTotal;
    }

    private void removeItem(long id) {
        mSqLiteDatabase.delete(ProductContract.ProductEntry.TableName,
                ProductContract.ProductEntry._ID + "=" + id, null);
        mProductAdapterLite.swapCursor(getAllData());
        getTotal();
    }

    private Cursor getItemToAddToCart() {
        Cursor cursorTotal = mSqLiteDatabase.rawQuery("SELECT * FROM " + ProductContract.ProductEntry.TableName, null);
        if (cursorTotal.moveToFirst()) {
            int ProductID = cursorTotal.getInt((cursorTotal.getColumnIndex("id")));
            String ProductName = cursorTotal.getString((cursorTotal.getColumnIndex("name")));
            int ProductPrice = cursorTotal.getInt((cursorTotal.getColumnIndex("amount")));
            int ProductAmount = cursorTotal.getInt((cursorTotal.getColumnIndex("price")));
            product_id = String.valueOf(ProductID);
            amountTotal = String.valueOf(ProductAmount);
            priceTotal = String.valueOf(ProductPrice);
            product_name = ProductName;

            final Response.Listener<String> Order = new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            Toast.makeText(TestActivity.this, "Send", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TestActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(TestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            };
//            Send_Data_Order mSend_Data_Order = new Send_Data_Order(product_id, amountTotal, priceTotal, product_name, Order);
//            RequestQueue order = Volley.newRequestQueue(TestActivity.this);
//            order.add(mSend_Data_Order);

        }
        return cursorTotal;
    }
}