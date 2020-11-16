package com.example.graphrepresentation.Service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.graphrepresentation.category_company_class.CompanyWise;
import com.example.graphrepresentation.DatabaseHelper;
import com.example.graphrepresentation.ScheduleJob;
import com.example.graphrepresentation.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GetCompanyWise extends JobService {
    DatabaseHelper helper;
    JobParameters params;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    public boolean onStartJob(JobParameters params) {
        this.params=params;
        helper=new DatabaseHelper(this);
        AndroidNetworking.initialize(this);
        preferences=getSharedPreferences("sync",MODE_PRIVATE);
        editor=preferences.edit();
        getCompanyWise();
        return true;
    }

    private void getCompanyWise() {

        AndroidNetworking.get(URL.CompanyWise).setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("undrstatnd","fff");
                        Log.d("Status",response.toString());
                        importCompanyWiseValues(response);


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Status",anError.toString());
                    }
                });
    }

    private void importCompanyWiseValues(final JSONObject response) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void>asyncTask=new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                int dataCount = 0;
                CompanyWise companyWise=new CompanyWise();
                try {
                    JSONArray jsonArray=new JSONArray(response.getString("companyWise"));
                    Log.d("Status","anError.toString()");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        companyWise.setsName(jsonObject.getString("sName"));
                        companyWise.setfPurchaseValue(jsonObject.getString("fPurchaseValue"));
                        companyWise.setDepreciatedValue(jsonObject.getString("depreciatedValue"));
                        companyWise.setCurrentValue(jsonObject.getString("CurrentValue"));

                        boolean status=helper.InsertCompanyWise(companyWise);
                        if (jsonArray.length() == i + 1) {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(GetCompanyWise.this, "companyWise Synced", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                        if(status){
                            dataCount++;
                            Log.d("sync",dataCount+"");

                        }
                        else {
                            Log.d("sync","already added");                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

           @Override
           protected void onPreExecute() {
               super.onPreExecute();
           }

           @Override
           protected void onPostExecute(Void aVoid) {
               editor.putBoolean("companyWise_synced", true).apply();
               ScheduleJob job = new ScheduleJob();
               job.syncCategoryWiseAsset(getApplicationContext());
               jobFinished(params, false);
           }

       };
        asyncTask.execute();

    }


    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
