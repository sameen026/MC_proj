package com.example.myapplication.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.ui.MainScreenActivity;

public class InternetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
            ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            MainScreenActivity instance=MainScreenActivity.getInstance();
            instance.intenetError = ((Activity) context).findViewById(R.id.internet_tv);
            if(netInfo==null)
                instance.intenetError.setVisibility(View.VISIBLE);
            else{
                instance.intenetError.setVisibility(View.INVISIBLE);
                //instance.getInternetState();
            }

    }
}