package com.example.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.contracts.ProductContract;

public class ProductAdapterLite extends RecyclerView.Adapter<ProductAdapterLite.ProductViewViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ProductAdapterLite(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class ProductViewViewHolder extends RecyclerView.ViewHolder {

        public TextView NameTxt, AmountTxt, PriceTxt;

        public ProductViewViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTxt = itemView.findViewById(R.id.CartProductName);
            AmountTxt = itemView.findViewById(R.id.CartAmount);
            PriceTxt = itemView.findViewById(R.id.CartProductPrice);
        }
    }

    @NonNull
    @Override
    public ProductViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cart_custom, parent, false);
        return new ProductAdapterLite.ProductViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(ProductContract.ProductEntry.CoulmnName));
        final int amount = mCursor.getInt(mCursor.getColumnIndex(ProductContract.ProductEntry.CoulmnAmount));
        int price = mCursor.getInt(mCursor.getColumnIndex(ProductContract.ProductEntry.CoulmnPrice));
        long id = mCursor.getLong(mCursor.getColumnIndex(ProductContract.ProductEntry._ID));

        holder.NameTxt.setText(name);
        holder.PriceTxt.setText(String.valueOf(price));
        holder.AmountTxt.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
