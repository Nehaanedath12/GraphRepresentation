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
import com.example.graphrepresentation.category_company_class.CategoryWiseAsset;
import com.example.graphrepresentation.DatabaseHelper;
import com.example.graphrepresentation.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GetCategoryWiseAsset extends JobService {
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
        getCategoryWiseAsset ();
        return true;
    }

    private void getCategoryWiseAsset() {
        AndroidNetworking.get(URL.CategoryWiseAsset).setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("categoryResponse",response.toString());
                importCategoryWiseAsset(response);
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    private void importCategoryWiseAsset(JSONObject response) {
       @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void>asyncTask=new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                int dataCount = 0;
                CategoryWiseAsset categoryWiseAsset=new CategoryWiseAsset();
                try {
                    JSONArray jsonArray=new JSONArray(response.getString("categoryWise"));
                    Log.d("syncCategory",dataCount+"");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        categoryWiseAsset.setsCategory(jsonObject.getString("sCategory"));
                        categoryWiseAsset.setfPurchaseValue(jsonObject.getString("fPurchaseValue"));
                        categoryWiseAsset.setfDepValue(jsonObject.getString("fDepValue"));
                        categoryWiseAsset.setfCurrentValue(jsonObject.getString("fCurrentValue"));

                        boolean status=helper.InsertCategoryWiseAsset(categoryWiseAsset);
                        if (jsonArray.length() == i + 1) {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(GetCategoryWiseAsset.this, "categoryWise Synced", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                        if(status){
                            dataCount++;
                            Log.d("syncCategory",dataCount+"");

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
               editor.putBoolean("categoryWise_synced", true).apply();
               super.onPostExecute(aVoid);
           }
       };
       asyncTask.execute();

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
