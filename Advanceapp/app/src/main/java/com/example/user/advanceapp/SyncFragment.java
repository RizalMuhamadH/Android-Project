package com.example.user.advanceapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class SyncFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ProgressBar progress_bar;
//    AddArrayToListView array_to_list_view;
    ListView name_list;
    DatabaseHelper myDB;

    int data;

    Cursor exp,inc;

    public SyncFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SyncFragment newInstance(String param1, String param2) {
        SyncFragment fragment = new SyncFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sync, container, false);
        progress_bar =(ProgressBar) view.findViewById(R.id.progressBar);
        progress_bar.setVisibility(view.VISIBLE);

        myDB = new DatabaseHelper(getActivity());
        exp = myDB.list_expanses();
        inc = myDB.list_income();

        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://private-008f3-transaction9.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Transaction addTrans;
        while (exp.moveToNext()){
            TransactionApi trans_api = retrofit.create(TransactionApi.class);
            addTrans = new Transaction(exp.getString(0),exp.getString(1),exp.getString(2));
            Call<Transaction> call = trans_api.addTrans(addTrans);
            call.enqueue(new Callback<Transaction>() {

                ProgressDialog progress_dialog = new ProgressDialog(getActivity());
                ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data","Please wait..",false,false);
                @Override
                public void onResponse(final Response<Transaction> response, Retrofit retrofit) {


                    try {
                        loading.dismiss();
                        Toast.makeText(getActivity(), "Data Success" + "\n" + "Synchronize to server", Toast.LENGTH_LONG).show();
                        Log.e("onResponse: ", String.valueOf(response.code()));
                    }catch (Exception e){
                        loading.dismiss();
                        progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress_dialog.setCancelable(false);
                        progress_dialog.setProgress(0);

                        progress_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progress_dialog.cancel();
                                progress_bar.setVisibility(View.INVISIBLE);
                                dialog.dismiss();
                            }
                        });
                        progress_dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progress_dialog.cancel();
                                progress_bar.setVisibility(View.INVISIBLE);
                                dialog.dismiss();
                            }
                        });
                        progress_dialog.show();
                    }
//                int counter=0;
//
//


//                for (Transactions.TransItem item : response.body().getItem()){
//                    item.getId_exp();
//                    counter++;
//                    Integer current_status = (int) (counter/(float) data * 100);
//                    Log.e("onProgressUpdate: ", String.valueOf(data));
//                    progress_bar.setProgress(current_status);
//                    progress_dialog.setProgress(current_status);
//                    progress_dialog.setMessage("Proccessing");
//                }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_LONG).show();
                }
            });
        }


        return view;
    }
}
