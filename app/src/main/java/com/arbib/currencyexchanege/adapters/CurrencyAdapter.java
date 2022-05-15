package com.arbib.currencyexchanege.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arbib.currencyexchanege.Item;
import com.arbib.currencyexchanege.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private ArrayList<Item> data;

    public CurrencyAdapter(ArrayList<Item> data) {
        this.data = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {
        Item item = data.get(position);
        holder.txt_name.setText(item.getName());
        holder.txt_rate.setText(new DecimalFormat("##.####").format(1/ Double.parseDouble(item.getRate())) +"€");
        double diff = item.getDifference();
        holder.txt_difference.setTextColor(diff<0 ? Color.parseColor("#B43552") : Color.parseColor("#39AF3E"));
        String formatDiff = new DecimalFormat("##.##").format(1/ item.getDifference());
        if(diff < 0.0001 && diff >=0 || diff >-0.0001 && diff <= 0) {
            holder.txt_difference.setText("+0.00 %");
            holder.txt_difference.setTextColor(Color.parseColor("#39AF3E"));
        } else holder.txt_difference.setText(diff < 0 ? formatDiff + "%" : "+" + formatDiff + "%");
        holder.txt_fullname.setText(item.getFullname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_name, txt_rate, txt_difference, txt_fullname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.name);
            txt_rate = itemView.findViewById(R.id.rate);
            txt_difference = itemView.findViewById(R.id.difference);
            txt_fullname = itemView.findViewById(R.id.fullname);
        }
    }
}
