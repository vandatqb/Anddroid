package com.example.phanv.camera.View.AccontView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.AccountModel.AccountProcess;
import com.example.phanv.camera.Model.DataLocalModel.Local;
import com.example.phanv.camera.Model.DataLocalModel.LocalData;
import com.example.phanv.camera.Model.ServerModel.ConnectServer;
import com.example.phanv.camera.Model.ServerModel.Property;
import com.example.phanv.camera.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditAccountActivity extends AppCompatActivity implements View.OnClickListener {
    String fullName;
    String phone;
    String email;
    String address;
    private StorageReference mStorage;
    EditText edFullName;
    EditText edPhone;
    EditText edEmail;
    EditText edAddress;
    EditText edPass;
    EditText edLoginName;
    Button btUpdate;
    AccountProcess process = new AccountProcess();
    ImageView img;
    Boolean changeImage = false;
    Uri linkImage;
    String linkDown;
    String id;
    ProgressDialog progressDialog;
    ConnectServer connect = new ConnectServer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        progressDialog = new ProgressDialog(this);
        edAddress = findViewById(R.id.edAddress);
        edEmail = findViewById(R.id.edEmail);
        edFullName = findViewById(R.id.edFullName);
        edPhone = findViewById(R.id.edPhone);
        edPass = findViewById(R.id.edPass);
        edLoginName = findViewById(R.id.edLGName);
        edLoginName.setFocusable(false);
        edPass.setFocusable(false);
        btUpdate = findViewById(R.id.btUpdateAccount);
        img = findViewById(R.id.imgAccount);
        img.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        loadInformation();
    }

    private void validData() {
        fullName = edFullName.getText().toString();
        phone = edPhone.getText().toString();
        email = edEmail.getText().toString();
        address = edAddress.getText().toString();
        if (fullName.length() > 1 & phone.length() > 6 & email.length() > 6 & address.length() > 10) {
            if (changeImage == true) {
                if (upLoadImage()) {

                } else {
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (updateInfor(fullName, phone, address, email) > 0) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (fullName.length() < 2) {
                Toast.makeText(getApplicationContext(), "Tên quá ngắn", Toast.LENGTH_SHORT).show();
            } else {
                if (phone.length() < 7) {
                    Toast.makeText(getApplicationContext(), "SĐT quá ngắn", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.length() < 6) {
                        if (phone.length() < 7) {
                            Toast.makeText(getApplicationContext(), "Email quá ngắn", Toast.LENGTH_SHORT).show();
                        } else {
                            if (address.length() < 11) {
                                if (phone.length() < 7) {
                                    Toast.makeText(getApplicationContext(), "Địa chỉ quá ngắn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean upLoadImage() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if (linkImage != null) {
            try {
                img.setDrawingCacheEnabled(true);
                img.buildDrawingCache();
                Bitmap bitmap = img.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                mStorage = FirebaseStorage.getInstance().getReference();
                StorageReference filePat = mStorage.child("camera").child("image").child("account").child(linkImage.getLastPathSegment());
                UploadTask uploadTask = filePat.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        progressDialog.dismiss();
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        linkDown = taskSnapshot.getDownloadUrl().toString();
                        if (updateInfor(fullName, phone, address, email) > 0) {
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            } catch (Exception e) {
                Toast.makeText(this, "Upload ảnh bị lỗi ", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return false;

    }

    private int updateInfor(String fullName, String phone, String address, String email) {
        int result;
        List<Property> list = new ArrayList<>();
        Property prId = new Property("id", id);
        Property prFullName = new Property("fullName", fullName);
        Property prPhone = new Property("phone", phone);
        Property prAddress = new Property("address", address);
        Property prEmail = new Property("email", email);
        Property prImage = new Property("image", linkDown);
        list.add(prId);
        list.add(prFullName);
        list.add(prPhone);
        list.add(prAddress);
        list.add(prEmail);
        list.add(prImage);
        String add = "http://tempuri.org/updateAccountInfor";
        String action = "updateAccountInfor";
        try {
            result = Integer.parseInt(connect.processString(list, add, action));
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

    private void loadInformation() {
        LocalData data = new LocalData(this);
        Local local = data.read();
        Account account = process.getAccountInformation(local.getId());
        id = local.getId();
        if (account != null) {
            edLoginName.setText(account.getLoginName());
            edPhone.setText(account.getPhone());
            edEmail.setText(account.getEmail());
            edAddress.setText(account.getAddress());
            edFullName.setText(account.getFullName());
            if (account.getImage().length() > 40) {
                Picasso.with(this).load(account.getImage()).into(img);
            } else {
                img.setImageResource(R.drawable.img_account);
            }
        } else {
            Toast.makeText(this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            btUpdate.setFocusable(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    changeImage = true;
                    linkImage = data.getData();
                    Picasso.with(getApplicationContext()).load(linkImage).into(img);
                } else {
                    changeImage = false;
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view == btUpdate) {
            validData();
        }
        if (view == img) {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                        1);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)
                openGallery();
        }
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 1234;
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        finish();
    }
}
