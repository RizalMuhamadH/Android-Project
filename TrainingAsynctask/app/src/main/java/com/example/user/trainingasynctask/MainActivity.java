package com.example.user.trainingasynctask;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView name_list;
    private  String[]users={
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state"
    };
    ProgressBar progress_bar;
    AddArrayToListView array_to_list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add progress bar
        progress_bar = (ProgressBar)findViewById(R.id.progressBar);
        progress_bar.setVisibility(View.VISIBLE);
        //setup adapter for populate data to listview
        name_list = (ListView)findViewById(R.id.listView);
        name_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));
        //process adapter with asynctask
        array_to_list_view = new AddArrayToListView();
        array_to_list_view.execute();


    }
    class AddArrayToListView extends AsyncTask<Void, String, Void>{
        private ArrayAdapter<String> adapter;
        private int counter = 0;

        ProgressDialog progress_dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute(){
            adapter = (ArrayAdapter<String>)name_list.getAdapter();
            //this for init progress dialog
            progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress_dialog.setTitle("On Progress.....");
            progress_dialog.setCancelable(false);
            progress_dialog.setProgress(0);

            //this will handle cacle asynctack when click cancel button
            progress_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    array_to_list_view.cancel(true);
                    progress_bar.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                }
            });
            progress_dialog.show();
        }

        protected  Void doInBackground(Void... params){
            for (String item: users){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (isCancelled()){
                    array_to_list_view.cancel(true);
                }
            }
            return null;
        }
        protected  void onProgressUpdate(String... values){
            adapter.add(values[0]);
            counter++;
            Integer current_status = (int)((counter/(float)users.length)*100);
            progress_bar.setProgress(current_status);
            //set progress only working for horizontal loading
            progress_dialog.setProgress(current_status);
            //setmessage will not working when using horizontal loading
            progress_dialog.setMessage(String.valueOf(current_status)+"%");
        }
        protected  void onPostExecute(Void result){
            //hide top progress bar
            progress_bar.setVisibility(View.INVISIBLE);
            //remove progress dialog
            progress_dialog.dismiss();
        }
    }
}
