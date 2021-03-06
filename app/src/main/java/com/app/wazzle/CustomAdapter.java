package com.app.wazzle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> colorsName;
    LayoutInflater flater;

    public CustomAdapter(Activity context, int resouceId, ArrayList<String> list, ArrayList<String> colorsName) {
        super(context, resouceId, list);
        this.colorsName = colorsName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        String rowItem = getItem(position);

        viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.color_adapter, null, false);

            holder.color_code = rowview.findViewById(R.id.color_code);
            holder.color_ = rowview.findViewById(R.id.color_);
            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }
        holder.color_code.setText(rowItem);
        holder.color_.setColorFilter(Color.parseColor(colorsName.get(position)));

        return rowview;
    }

    private class viewHolder {
        TextView color_code;
        ImageView color_;
    }
}