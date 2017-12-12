package com.example.phanv.camera.View.AccontView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.AccountModel.UpdateAccountTask;
import com.example.phanv.camera.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EditAccountActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog;
    UpdateAccountTask task;
    Account account = null;
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
    ImageView img;
    Boolean changeImage = false;
    Uri linkImage;
    String linkDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        edAddress = findViewById(R.id.edAddress);
        edEmail = findViewById(R.id.edEmail);
        edFullName = findViewById(R.id.edFullName);
        edPhone = findViewById(R.id.edPhone);
        edPass = findViewById(R.id.edPass);
        edLoginName = findViewById(R.id.edLGName);
        edLoginName.setFocusable(false);
        edPass.setFocusable(false);
        btUpdate = findViewById(R.id.btUpdateAccount);
        img = findViewById(R.id.imgEditAccount);
        img.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        task = new UpdateAccountTask(this);
        loadInformation();
        dialog = new ProgressDialog(this);
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
                account = new Account("1", "d", fullName, phone, address, email, "default", "1", "1");
                task.execute(account);
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
        dialog.setMessage("Đang up ảnh");
        dialog.show();
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
                        dialog.dismiss();
                        linkDown = taskSnapshot.getDownloadUrl().toString();
                        account = new Account("1", "d", fullName, phone, address, email, linkDown, "1", "1");
                        task.execute(account);
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

    private void loadInformation() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("data");
            edLoginName.setText(bundle.getString("loginName"));
            edPhone.setText(bundle.getString("phone"));
            edEmail.setText(bundle.getString("email"));
            edAddress.setText(bundle.getString("address"));
            edFullName.setText(bundle.getString("fullName"));
            String linkImg = bundle.getString("img");
            try {
                if (linkImg.length() > 40) {
                    Picasso.with(this).load(linkImg).into(img);
                } else {
                    img.setImageResource(R.drawable.img_account);
                }
            } catch (Exception e) {
                img.setImageResource(R.drawable.img_account);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Đã có lỗi xảy ra", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> list = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                    linkImage = list.get(0);
                    img.setImageURI(linkImage);
                    changeImage = true;
                } else {
                    changeImage = false;
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view == btUpdate) {
            //an ban phim
            InputMethodManager imm = (InputMethodManager) this.getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            validData();
        }
        if (view == img) {
            try {
                if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                            1234);
                }
            } catch (Exception e) {
                Log.e("Err : ", e.toString());
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
        Config config = new Config();
        config.setSelectionMin(1);
        config.setSelectionLimit(1);

        ImagePickerActivity.setConfig(config);

        Intent intent = new Intent(this, ImagePickerActivity.class);
        startActivityForResult(intent, 1234);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        finish();
    }

    public void updatSuccess() {
        Intent intent = new Intent(this, AccountActivity.class);
        finish();
        startActivity(intent);
    }
}
