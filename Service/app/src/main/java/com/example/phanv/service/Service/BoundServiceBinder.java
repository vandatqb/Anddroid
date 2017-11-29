package com.example.phanv.service.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.phanv.service.R;

/**
 * Created by phanv on 08-Nov-17.
 */

public class BoundServiceBinder extends Service {
    MediaPlayer mediaPlayer ;
    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        public BoundServiceBinder getService()
        {
            return BoundServiceBinder.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public void play()
    {
        if (mediaPlayer!=null)
            mediaPlayer.start();
    }
    public void pause()
    {
        if (mediaPlayer!=null)
            mediaPlayer.pause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null)
            mediaPlayer.release();
    }
}
