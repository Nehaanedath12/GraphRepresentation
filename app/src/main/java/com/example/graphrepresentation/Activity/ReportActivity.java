package com.example.graphrepresentation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.graphrepresentation.R;

import org.json.JSONObject;

public class ReportActivity extends AppCompatActivity {
    EditText search;
    RecyclerView recyclerView;
    String iVendor="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        search=findViewById(R.id.search);
        AndroidNetworking.initialize(this);
        recyclerView=findViewById(R.id.recyclerView_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iVendor=s.toString();
                getValueFromAPI(iVendor);
            }
        });
    }

    private void getValueFromAPI(String iVendor) {
        Log.d("Response",iVendor);

        AndroidNetworking.get("http://185.151.4.167/power/api/Data/GetPendingPODetails")
                .addQueryParameter("iVendor","0")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ReportActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("Response",response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Response",anError.getErrorBody());
                    }
                });
    }
}