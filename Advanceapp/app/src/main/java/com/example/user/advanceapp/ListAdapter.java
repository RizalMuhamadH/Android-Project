package com.example.user.advanceapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 14/06/2016.
 */
public class ListAdapter extends BaseAdapter {
    private ArrayList<ListItem>item;
    private Context context;

    public ListAdapter(Context context, ArrayList<ListItem>item){
        this.item = item;
        this.context = context;
    }
    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item,null);

        }

        TextView expenses_desc = (TextView)convertView.findViewById(R.id.list_exp);
        TextView expenses_amont = (TextView)convertView.findViewById(R.id.list_amount_exp);

        //mengambil nilai dari item yang tersimpan pada Listitem
        String str_exp_desc = item.get(position).getDesc_exp();
        String str_amount_exp = item.get(position).getAmount_exp();

        //menggunbah teks dai list_exp
        expenses_desc.setText(str_exp_desc);
        expenses_amont.setText(str_amount_exp);
        return convertView;
    }
}
