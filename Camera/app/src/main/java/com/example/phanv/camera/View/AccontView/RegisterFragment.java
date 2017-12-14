package com.example.phanv.camera.View.AccontView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.Model.ServerModel.ConnectServer;
import com.example.phanv.camera.Model.ServerModel.Property;
import com.example.phanv.camera.Presenter.SeverPresenter;
import com.example.phanv.camera.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import me.echodev.resizer.Resizer;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    private String loginName;
    private String fullName;
    private String phone;
    private String pass;
    private String email;
    private String address;
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
    private SeverPresenter cn = new SeverPresenter();
    private String linkImage = "";
    private Boolean setImg = false;
    private DataLocalProcess dataLocalProcess;

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        mStorage = FirebaseStorage.getInstance().getReference();

        img = v.findViewById(R.id.imgEditAccount);
        btRegister = v.findViewById(R.id.btRegister);
        img.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        progressDialog = new ProgressDialog(getContext());

        edLoginName = v.findViewById(R.id.edLGName);
        edFullName = v.findViewById(R.id.edFullName);
        edPhone = v.findViewById(R.id.edPhone);
        edPass = v.findViewById(R.id.edPass);
        edAddress = v.findViewById(R.id.edAddress);
        edEmail = v.findViewById(R.id.edEmail);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if (view == img) {
            if (checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                        123);
            }
        }
        if (view == btRegister) {
            //an ban phim
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            validData();
        }
    }

    private boolean upLoadImage() {
        progressDialog.setMessage("Đăng đăng kí...");
        progressDialog.show();
        try {
            Bitmap bitmap = resizeBitmap();
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
                    linkImage = taskSnapshot.getDownloadUrl().toString();
                    progressDialog.dismiss();
                    if (register(loginName, fullName, phone, pass, address, email, linkImage)) {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), ViewAccountActivity.class);
                        startActivity(intent);
                    }
                }
            });
            return true;
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Upload ảnh bị lỗi ", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void openGallery() {
        Config config = new Config();
        config.setSelectionMin(1);
        config.setSelectionLimit(1);

        ImagePickerActivity.setConfig(config);

        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        startActivityForResult(intent, 1234);
    }

    private void validData() {
        loginName = edLoginName.getText().toString();
        fullName = edFullName.getText().toString();
        phone = edPhone.getText().toString();
        pass = edPass.getText().toString();
        email = edEmail.getText().toString();
        address = edAddress.getText().toString();

        if (loginName.length() > 4 & checkLoginName(loginName).equals("ok") & fullName.length() > 1
                & phone.length() > 6 & pass.length() > 5 & email.length() > 6 & address.length() > 10) {
            if (setImg == true) {
                if (upLoadImage()) {
                } else {
                    Toast.makeText(getContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (register(loginName, fullName, phone, pass, address, email, "default")) {
                    Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            if (loginName.length() < 5) {
                Toast.makeText(getContext(), "Tên đăng nhập quá ngắn!", Toast.LENGTH_SHORT).show();
            } else {
                if (checkLoginName(loginName).equals("notok")) {
                    Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    if (fullName.length() < 2) {
                        Toast.makeText(getContext(), "Tên quá ngắn", Toast.LENGTH_SHORT).show();
                    } else {
                        if (phone.length() < 7) {
                            Toast.makeText(getContext(), "SĐT quá ngắn", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pass.length() < 6) {
                                if (phone.length() < 7) {
                                    Toast.makeText(getContext(), "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (email.length() < 6) {
                                        if (phone.length() < 7) {
                                            Toast.makeText(getContext(), "Email quá ngắn", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (address.length() < 11) {
                                                if (phone.length() < 7) {
                                                    Toast.makeText(getContext(), "Địa chỉ quá ngắn", Toast.LENGTH_SHORT).show();
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

    private String checkLoginName(String loginName) {
        progressDialog.setMessage("Đang kiểm tra dữ liệu");
        progressDialog.show();
        ArrayList<Property> list = new ArrayList<>();
        String soap = "http://tempuri.org/checkAccount";
        String operation = "checkAccount";
        Property property = new Property("loginName", loginName);
        list.add(property);
        String result = cn.processString(list, soap, operation);
        progressDialog.dismiss();
        if (result.equals("true")) {
            return "notok";
        } else {
            if (result.equals("false")) {
                return "ok";
            } else return "er";
        }

    }

    private boolean register(String loginName, String fullName, String phone, String pass, String address, String email, String image) {
        final String soap = "http://tempuri.org/addAccount";
        final String operation = "addAccount";
        ArrayList<Property> list = new ArrayList<>();
        Property pr = new Property("login_name", loginName);
        list.add(pr);
        Property pr1 = new Property("full_name", fullName);
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
            dataLocalProcess = new DataLocalProcess(getActivity());
            dataLocalProcess.writeId(status + "");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)
                openGallery();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> list = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                    uriFile = list.get(0);
                    img.setImageURI(uriFile);
                    setImg = true;
                } else {
                    setImg = false;
                }
        }
    }

    ;

    private Bitmap resizeBitmap() {
        Bitmap result = null;
        try {
            result = new Resizer(getContext())
                    .setTargetLength(480)
                    .setSourceImage(new File(uriFile.getPath()))
                    .getResizedBitmap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
