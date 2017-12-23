package com.example.phanv.camera.Model.ProductModel;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.ProductView.ViewProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductService extends Service {
    private static int min = 0;
    private static int max = 10000000;
    private String idAccount = "";
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private boolean status = false;
    private boolean aBoolean = false;

    @Override
    public void onCreate() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("camera");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if (aBoolean == false && intent != null) {
            min = intent.getIntExtra("min", 0) * 1000000;
            max = intent.getIntExtra("max", 0) * 1000000;
            idAccount = intent.getStringExtra("idAccount");
            aBoolean = true;
        }

        reference.child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                if (status = true) {
                    if (product != null) {
                        int price = Integer.parseInt(product.getPrice());
                        if (!product.getIdAccount().equals(idAccount)) {
                            if (min <= price && price <= max) {
                                setNotification(product.getId(), product.getIdAccount(), product.getName(), product.getPrice());
                            }
                        }
                    }
                } else {
                    status = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }

    private void setNotification(String id, String idAccount, String name, String price) {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_camera)
                        .setContentTitle("Có sản phẩm mới")
                        .setContentText(name + " - " + price);
        int NOTIFICATION_ID = 12345;
        Intent intent = new Intent(this, ViewProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("idAccount", idAccount);
        intent.putExtra("data", bundle);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
