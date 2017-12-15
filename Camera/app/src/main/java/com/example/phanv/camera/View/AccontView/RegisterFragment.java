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

import com.example.phanv.camera.Model.AccountModel.CheckLoginNameTask;
import com.example.phanv.camera.Model.AccountModel.RegisterInterface;
import com.example.phanv.camera.Model.DataLocalModel.DataLocalProcess;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.Other.MainActivity;
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


public class RegisterFragment extends Fragment implements View.OnClickListener, RegisterInterface {
    private Boolean status;
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
    private String linkImage = "";
    private Boolean setImg = false;
    private DataLocalProcess dataLocalProcess;
    private CheckLoginNameTask checkLoginNameTask;
    private AddAccountTask addTask;

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
        addTask = new AddAccountTask(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        dataLocalProcess = new DataLocalProcess(getActivity());
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
                    register(loginName, fullName, phone, pass, address, email, linkImage);
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

        if (loginName.length() > 4 & fullName.length() > 1
                & phone.length() > 6 & pass.length() > 5 & email.length() > 6 & address.length() > 10) {
            checkLoginName(loginName);
        } else {
            if (loginName.length() < 5) {
                Toast.makeText(getContext(), "Tên đăng nhập quá ngắn!", Toast.LENGTH_SHORT).show();
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

    private void checkLoginName(String loginName) {
        checkLoginNameTask = new CheckLoginNameTask(this);
        checkLoginNameTask.execute(loginName);
    }

    private void register(String loginName, String fullName, String phone, String pass, String address, String email, String image) {
        addTask.execute(loginName, fullName, phone, pass, address, email, image);
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

    @Override
    public void checkLogin(Boolean status) {
        if(status==false){
            if(setImg)
            {
                upLoadImage();
            }
            else {
                register(loginName, fullName, phone, pass, address, email, linkImage);
            }

        }
        else {
            Toast.makeText(getActivity(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void loginSucces(Integer result) {
        if (result > 0) {
            dataLocalProcess.write(result.toString(), loginName, fullName, linkImage);
            MainActivity.idAccount = result.toString();
            getActivity().finish();
            Intent intent = new Intent(getActivity(), ViewAccountActivity.class);
            startActivity(intent);
        }
    }
}
