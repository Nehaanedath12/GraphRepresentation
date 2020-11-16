package com.example.graphrepresentation;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.graphrepresentation.Service.GetCategoryWiseAsset;
import com.example.graphrepresentation.Service.GetCompanyWise;

public class ScheduleJob{

    public static int MY_BACKGROUND_JOB = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void syncCompanyWise(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(MY_BACKGROUND_JOB,
                new ComponentName(context, GetCompanyWise.class)).
                setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).build();
        assert jobScheduler != null;
        jobScheduler.schedule(jobInfo);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void syncCategoryWiseAsset(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(MY_BACKGROUND_JOB,
                new ComponentName(context, GetCategoryWiseAsset.class)).
                setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).build();
        assert jobScheduler != null;
        jobScheduler.schedule(jobInfo);
    }
}
