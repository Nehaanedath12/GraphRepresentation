package com.example.graphrepresentation.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.graphrepresentation.DatabaseHelper;
import com.example.graphrepresentation.R;
import com.example.graphrepresentation.ScheduleJob;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper helper;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button sync,graph,report;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sync =findViewById(R.id.sync);
        graph=findViewById(R.id.graph);
        report=findViewById(R.id.report);

        helper=new DatabaseHelper(this);
        preferences=getSharedPreferences("sync",MODE_PRIVATE);
        editor=preferences.edit();

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReportActivity.class));
            }
        });

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getBoolean("companyWise_synced", false) &&
                        preferences.getBoolean("categoryWise_synced", false)  ) {
                    Toasty.success(MainActivity.this,"Synced once",Toast.LENGTH_SHORT).show();
                }
                else if(networkConnected()){
                    ScheduleJob job = new ScheduleJob();
                    job.syncCompanyWise(MainActivity.this);
                    editor.putBoolean("companyWise_synced", false).apply();
                    editor.putBoolean("categoryWise_synced", false).apply();
                }
                else {

                    Toasty.warning(MainActivity.this,"You are Offline!!",Toast.LENGTH_LONG).show();
                }
            }
        });


        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getBoolean("companyWise_synced", false) &&
                        preferences.getBoolean("categoryWise_synced", false)  ) {
                    startActivity(new Intent(getApplicationContext(), GraphActivity.class));

                }
                else {
                    Toasty.warning(MainActivity.this,"please sync first!!",Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    private boolean networkConnected() {
        ConnectivityManager manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}