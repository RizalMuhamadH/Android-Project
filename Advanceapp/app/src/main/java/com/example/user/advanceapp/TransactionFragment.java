package com.example.user.advanceapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TransactionFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText desc_exp,desc_in, amount_exp,amount_in;
    Button btn_exp,btn_inc;

    DatabaseHelper myDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        desc_exp = (EditText)view.findViewById(R.id.desc_expanses);
        amount_exp = (EditText)view.findViewById(R.id.amount_exp);
        desc_in = (EditText)view.findViewById(R.id.desc_income);
        amount_in = (EditText)view.findViewById(R.id.amount_in);
        btn_exp = (Button)view.findViewById(R.id.btn_expanses);
        btn_inc = (Button)view.findViewById(R.id.btn_income);

        myDB = new DatabaseHelper(getActivity());

        btn_exp.setOnClickListener(this);
        btn_inc.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_expanses :
                boolean result_exp = myDB.save_expanses(desc_exp.getText().toString(),Integer.parseInt(amount_exp.getText().toString()));
                if (result_exp){
                    Toast.makeText(getActivity(),"Success Add",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(),"Fails Add", Toast.LENGTH_LONG).show();
                }
                desc_exp.setText("");
                amount_exp.setText("");
                break;
            case R.id.btn_income :
                boolean result_inc = myDB.save_income(desc_in.getText().toString(),Integer.parseInt(amount_in.getText().toString()));
                if (result_inc){
                    Toast.makeText(getActivity(),"Success Add",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(),"Fails Add", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
