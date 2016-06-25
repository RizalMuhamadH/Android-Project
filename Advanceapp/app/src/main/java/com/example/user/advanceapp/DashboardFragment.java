package com.example.user.advanceapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class DashboardFragment extends Fragment {
    DatabaseHelper database;
    Cursor cr_exp, cr_inc;
    ListView list_view_exp,list_view_inc;
    int total_expenses = 0;
    int total_income = 0;
    int balance = 0;
    String update_desc,update_amount;

    TextView incomeTotal,expansesTotal, balanceTotal;


    protected ListAdapter adapter;

    public DashboardFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        list_view_exp = (ListView)view.findViewById(R.id.listViewExp);
        list_view_inc = (ListView)view.findViewById(R.id.listViewInc);
        incomeTotal = (TextView)view.findViewById(R.id.incomeTotal);
        expansesTotal = (TextView)view.findViewById(R.id.expensesTotal);
        balanceTotal =(TextView)view.findViewById(R.id.balanceTotal);



        database = new DatabaseHelper(getActivity());
        cr_exp = database.list_expanses();
        cr_inc = database.list_income();

        view_exp();
        view_inc();


        balance = total_income - total_expenses;
        //result
        incomeTotal.setText("$"+String.valueOf(total_income));
        expansesTotal.setText("$"+ String.valueOf(total_expenses));
        balanceTotal.setText("$"+ String.valueOf(balance));







        list_view_exp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View u = (View)view.getParent();
                Toast.makeText(getActivity(),"Click : "+i+" dan "+list_view_exp.getItemIdAtPosition(i),Toast.LENGTH_LONG).show();

                final int id = i+1;

                final EditText dialog_description;
                final EditText dialog_amount;
                final EditText descIn = new EditText(getActivity());
                final EditText amtIn = new EditText(getActivity());
                LinearLayout layoutD =new LinearLayout(getActivity());
                layoutD.setOrientation(LinearLayout.VERTICAL);
                layoutD.setPadding(5, 5, 5, 5);
                layoutD.setBackgroundColor(Color.parseColor("#E9D460"));
                layoutD.addView(descIn);
                layoutD.addView(amtIn);


                update_desc = ((TextView) view.findViewById(R.id.list_exp)).getText().toString();
                update_amount = ((TextView)view.findViewById(R.id.list_amount_exp)).getText().toString();

//                Log.e("Update",update_desc );

                cr_exp.moveToPosition(i);


                AlertDialog.Builder alert_builder = new AlertDialog.Builder(getActivity());
//                final Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.custom_dialog);
//                dialog.setTitle("Edit");


                alert_builder.setTitle("Edit");
//                alert_builder.setView(inflater.inflate(R.layout.custom_dialog,null));
                alert_builder.setView(layoutD);
                final int rowId= cr_exp.getInt(cr_exp.getColumnIndexOrThrow("ID"));
                final String rowDesc = cr_exp.getString(cr_exp.getColumnIndexOrThrow("DESC_EXPANSES"));
                final String rowAmount = cr_exp.getString(cr_exp.getColumnIndexOrThrow("Amount"));
//                dialog_description = (EditText) u.findViewById(R.id.desc_update);
//                dialog_amount = (EditText) u.findViewById(R.id.amount_update);
                descIn.setText(rowDesc);
                amtIn.setText(rowAmount);

//                dialog_description.setText("bebas");
//                dialog_amount.setText("mudah2an berhasil");
//                Log.e("onItemClick: ",update_desc);
//                txt_description.setText(String.valueOf(update_desc));
//                Log.e("txt description: ",txt_description.getText().toString() );
//                txt_amount.setText(String.valueOf(update_amount));
                alert_builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        database.update_exp(String.valueOf(id),descIn.getText().toString(),amtIn.getText().toString());
                    }
                })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.delete_exp(String.valueOf(id));
                            }
                        });
                alert_builder.show();
                view_exp();

            }
        });

        return view;





    }
    public void view_exp(){
        ArrayList<ListItem>listItems = new ArrayList<>();
        ListItem item;
        while (cr_exp.moveToNext()){
            item = new ListItem();
            item.setDesc_exp(cr_exp.getString(1));
            item.setAmount_exp(cr_exp.getString(2));
            listItems.add(item);
            total_expenses+=Integer.parseInt(cr_exp.getString(2));
        }
        ListAdapter list_adapter = new com.example.user.advanceapp.ListAdapter(getActivity(),listItems);
        list_view_exp.setAdapter(list_adapter);
    }
    public void view_inc(){
        ArrayList<ListItemIncome>listItemIncomes = new ArrayList<>();
        ListItemIncome itemIncome;

        while (cr_inc.moveToNext()){
            itemIncome = new ListItemIncome();
            itemIncome.setDesc_inc(cr_inc.getString(1));
            itemIncome.setAmount_inc(cr_inc.getString(2));
            listItemIncomes.add(itemIncome);
            total_income += Integer.parseInt(cr_inc.getString(2));
        }
        ListAdapterView list_adpter_view = new ListAdapterView(getActivity(),listItemIncomes);
        list_view_inc.setAdapter(list_adpter_view);
    }

    public class ListAdapterView extends BaseAdapter {
        private ArrayList<ListItemIncome>item;
        private Context context;

        public ListAdapterView(Context context, ArrayList<ListItemIncome>item){
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

            TextView income_desc = (TextView)convertView.findViewById(R.id.list_exp);
            TextView income_amont = (TextView)convertView.findViewById(R.id.list_amount_exp);

            //mengambil nilai dari item yang tersimpan pada Listitem
            String str_inc_desc = item.get(position).getDesc_inc();
            String str_amount_inc = item.get(position).getAmount_inc();

            //menggunbah teks dai list_exp
            income_desc.setText(str_inc_desc);
            income_amont.setText(str_amount_inc);
            return convertView;
        }
    }
}
