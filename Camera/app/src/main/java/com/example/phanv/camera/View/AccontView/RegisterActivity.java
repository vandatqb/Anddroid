package com.example.phanv.camera.View.AccontView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private StorageReference mStorage;
    private ImageView img;
    private Button btRegister;
    private Uri uriFile;
    private ProgressDialog progressDialog;
    private EditText edLoginName;
    private EditText edFullName;
    private EditText edPhone;
    private EditText edPass;
    private EditText edAddress;
    private EditText edEmail;
    ConnectServer cn = new ConnectServer();
    private String linkImage = "";
    Boolean setImg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mStorage = FirebaseStorage.getInstance().getReference();

        img = findViewById(R.id.imageView);
        btRegister = findViewById(R.id.btRegister);
        img.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        edLoginName = findViewById(R.id.edLGName);
        edFullName = findViewById(R.id.edFullName);
        edPhone = findViewById(R.id.edPhone);
        edPass = findViewById(R.id.edPass);
        edAddress = findViewById(R.id.edAddress);
        edEmail = findViewById(R.id.edEmail);
    }

    private boolean upLoadImage() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        try {
            img.setDrawingCacheEnabled(true);
            img.buildDrawingCache();
            Bitmap bitmap = img.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            StorageReference filePat = mStorage.child("camera").child("image").child("account").child(uriFile.getLastPathSegment());
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
                    linkImage = taskSnapshot.getDownloadUrl().toString();
                }
            });
            return true;
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "Upload ảnh bị lỗi ", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    setImg = true;
                    uriFile = data.getData();
                    Picasso.with(getApplicationContext()).load(uriFile).into(img);
                } else {
                    setImg = false;
                }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view == img) {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                        1);
            }
        }
        if (view == btRegister) {
            //an ban phim
            InputMethodManager imm = (InputMethodManager) this.getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            validData();
        }
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 1234;
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
    }

    private void validData() {
        String loginName = edLoginName.getText().toString();
        String fullName = edFullName.getText().toString();
        String phone = edPhone.getText().toString();
        String pass = edPass.getText().toString();
        String email = edEmail.getText().toString();
        String address = edAddress.getText().toString();

        if (loginName.length() > 4 & checkLoginName(loginName).equals("ok") & fullName.length() > 1
                & phone.length() > 6 & pass.length() > 5 & email.length() > 6 & address.length() > 10) {
            if (setImg == true) {
                if (upLoadImage()) {
                    if (register(loginName, fullName, phone, pass, address, email, linkImage)) {
                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (register(loginName, fullName, phone, pass, address, email, "default")) {
                    Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }

            }


        } else {
            if (loginName.length() < 5) {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập quá ngắn!", Toast.LENGTH_SHORT).show();
            } else {
                if (checkLoginName(loginName).equals("notok")) {
                    Toast.makeText(getApplicationContext(), "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    if (fullName.length() < 2) {
                        Toast.makeText(getApplicationContext(), "Tên quá ngắn", Toast.LENGTH_SHORT).show();
                    } else {
                        if (phone.length() < 7) {
                            Toast.makeText(getApplicationContext(), "SĐT quá ngắn", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pass.length() < 6) {
                                if (phone.length() < 7) {
                                    Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
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
                }

            }
        }

    }

    private void checkLoginName() {
        String loginName = edLoginName.getText().toString();
        if (loginName.length() < 5) {
            edLoginName.setTextColor(Color.RED);
        } else {
            if (checkLoginName(loginName).equals("ok")) {
                edLoginName.setTextColor(Color.BLUE);
            } else {
                if (checkLoginName(loginName).equals("notok")) {
                    edLoginName.setTextColor(Color.RED);
                } else {
                    Toast.makeText(RegisterActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String checkLoginName(String loginName) {
        ArrayList<Property> list = new ArrayList<>();
        String soap = "http://tempuri.org/checkAccount";
        String operation = "checkAccount";
        Property property = new Property("loginName", loginName);
        list.add(property);
        if (cn.processString(list, soap, operation).equals("true")) {
            return "notok";
        } else {
            if (cn.processString(list, soap, operation).equals("false")) {
                return "ok";
            } else return "er";
        }
    }

    private boolean register(String loginName, String fullName, String phone, String pass, String address, String email, String image) {
        final String soap = "http://tempuri.org/addAccount";
        final String operation = "addAccount";
        ArrayList<Property> list = new ArrayList<>();

        Property pr = new Property("loginName", loginName);
        list.add(pr);

        Property pr1 = new Property("fullName", fullName);
        list.add(pr1);

        Property pr2 = new Property("phone", phone);
        list.add(pr2);

        Property pr3 = new Property("pass", pass);
        list.add(pr3);

        Property pr4 = new Property("address", address);
        list.add(pr4);

        Property pr5 = new Property("email", email);
        list.add(pr5);

        Property pr6 = new Property("image", image);
        list.add(pr6);

        ConnectServer cn = new ConnectServer();
        int status = Integer.parseInt(cn.processString(list, soap, operation));
        if (status > 0) {
            return true;
        } else {
            return false;
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

}
