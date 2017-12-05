package com.example.phanv.camera.View.ProductView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phanv.camera.Model.DataLocalModel.Local;
import com.example.phanv.camera.Model.DataLocalModel.LocalData;
import com.example.phanv.camera.Model.ProductModel.Maker;
import com.example.phanv.camera.Model.ProductModel.MakerSpinerAdapter;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.Model.ProductModel.ProductProcess;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.echodev.resizer.Resizer;

public class NewCamera extends AppCompatActivity implements View.OnClickListener {
    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private Spinner spnMaker;
    private Spinner spnVideo;
    private Spinner spnStatus;
    private ProductProcess process = new ProductProcess();
    private String idMaker;
    private String idVideo;
    private String idStatus;
    private String nameCamera;
    private String mega;
    private String price;
    private String access;
    private String description;
    private EditText edName;
    private EditText edMega;
    private EditText edPrice;
    private EditText edAdd;
    private EditText edDesscriptin;
    private Button btPost;
    private ImageView img;
    private ImageView img1;
    private ImageView img2;
    private final int SELECT_FILE = 222;
    private int number = 0;
    private int numberOk = 0;
    private String linkImage;
    private String linkImage1;
    private String linkImage2;
    private Uri uri;
    private Uri uri1;
    private Uri uri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera);
        mStorage = FirebaseStorage.getInstance().getReference();
        spnMaker = findViewById(R.id.spnMaker);
        spnVideo = findViewById(R.id.spnVideo);
        spnStatus = findViewById(R.id.spnStatus);
        edName = findViewById(R.id.edCameraName);
        edMega = findViewById(R.id.edMega);
        edAdd = findViewById(R.id.edAccessories);
        edDesscriptin = findViewById(R.id.edDescription);
        btPost = findViewById(R.id.btPost);
        img = findViewById(R.id.imgAddCamera);
        img1 = findViewById(R.id.imgAddCamera1);
        img2 = findViewById(R.id.imgAddCamera2);
        edPrice = findViewById(R.id.edPrice);
        progressDialog = new ProgressDialog(this);
        loadMakerSpinner();
        loadVideoSpinner();
        loadStatusSpinner();
        btPost.setOnClickListener(this);
        img.setOnClickListener(this);
    }

    private void loadStatusSpinner() {
        List<Maker> listStatus = new ArrayList<>();
        Maker old = new Maker("0", "Cũ");
        Maker newS = new Maker("1", "Mới");
        listStatus.add(newS);
        listStatus.add(old);
        final MakerSpinerAdapter adapterStatus = new MakerSpinerAdapter(this, android.R.layout.simple_spinner_item, listStatus);
        spnStatus.setAdapter(adapterStatus);
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Maker maker = adapterStatus.getItem(i);
                idStatus = maker.getIdMaker();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idStatus = "1";
            }
        });
    }

    private void loadVideoSpinner() {
        List<Maker> listVideo = process.getListVideo();
        final MakerSpinerAdapter adapterVideo = new MakerSpinerAdapter(this, android.R.layout.simple_spinner_item, listVideo);
        spnVideo.setAdapter(adapterVideo);
        spnVideo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Maker maker = adapterVideo.getItem(i);
                idVideo = maker.getIdMaker();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idVideo = "1";
            }
        });
    }

    private void loadMakerSpinner() {
        List<Maker> listMaker = process.getListMaker();
        final MakerSpinerAdapter adapter = new MakerSpinerAdapter(this, android.R.layout.simple_spinner_item, listMaker);
        spnMaker.setAdapter(adapter);
        spnMaker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Maker maker = adapter.getItem(i);
                idMaker = maker.getIdMaker();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idMaker = "1";
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        if (view == img) {
            Config config = new Config();
            config.setSelectionMin(1);
            config.setSelectionLimit(3);

            ImagePickerActivity.setConfig(config);

            Intent intent = new Intent(this, ImagePickerActivity.class);
            startActivityForResult(intent, SELECT_FILE);
        }
        if (view == btPost) {
            nameCamera = edName.getText().toString();
            mega = edMega.getText().toString();
            price = edPrice.getText().toString();
            access = edAdd.getText().toString();
            description = edDesscriptin.getText().toString();
            if (nameCamera.length() > 5 & mega.length() > 0 & price.length() > 0 & access.length() > 0 & description.length() > 0) {
                upImage();
            } else {
                Toast.makeText(this, "Chưa điền hết thông tin", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == this.RESULT_OK) {

            ArrayList<Uri> list = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            for (int i = 0; i < list.size(); i++) {
                number++;
                Uri uriFile = list.get(i);
                if (i == 0) {
                    uri = uriFile;
                    img.setImageURI(uri);
                } else {
                    if (i == 1) {
                        uri1 = uriFile;
                        img1.setVisibility(View.VISIBLE);
                        img1.setImageURI(uriFile);
                    } else {
                        if (i == 2) {
                            uri2 = uriFile;
                            img2.setVisibility(View.VISIBLE);
                            img2.setImageURI(uriFile);
                        }
                    }
                }
            }
        }
    }

    private void upImage() {
        progressDialog.setMessage("Đang tải ảnh lên ");
        progressDialog.show();
        try {
            for (int i = 1; i <= number; i++) {
                if (i == 1) {
                    Bitmap bitmap = resizeBitmap(uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference filePat = mStorage.child("camera").child("image").child("product").child(uri.getLastPathSegment());
                    UploadTask uploadTask = filePat.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(NewCamera.this, "Đã có lỗi hay thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            linkImage = taskSnapshot.getDownloadUrl().toString();
                            numberOk++;
                            if (numberOk == number) {
                                addProduct();
                            }
                        }
                    });
                }
                if (i == 2) {
                    Bitmap bitmap = resizeBitmap(uri1);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference filePat = mStorage.child("camera").child("image").child("product").child(uri1.getLastPathSegment());
                    UploadTask uploadTask = filePat.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(NewCamera.this, "Đã có lỗi hay thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            linkImage1 = taskSnapshot.getDownloadUrl().toString();
                            numberOk++;
                            if (numberOk == number) {
                                addProduct();
                            }
                        }
                    });
                }
                if (i == 3) {
                    Bitmap bitmap = resizeBitmap(uri2);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference filePat = mStorage.child("camera").child("image").child("product").child(uri2.getLastPathSegment());
                    UploadTask uploadTask = filePat.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(NewCamera.this, "Đã có lỗi hay thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            linkImage2 = taskSnapshot.getDownloadUrl().toString();
                            numberOk++;
                            if (numberOk == number) {
                                addProduct();
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(this, "Upload ảnh bị lỗi ", Toast.LENGTH_LONG).show();
        }
    }

    private void addProduct() {
        progressDialog.dismiss();
        LocalData data = new LocalData(this);
        Local local = data.read();
        Product product = new Product("1", local.getId(), nameCamera, idMaker, "aaa",
                idStatus, mega, idVideo, "nnnn", access, price, "aa", "0", linkImage, linkImage1, linkImage2, description);
        if (process.addProduct(product, local.getId())) {
            Intent intent = new Intent(this,ProductActivity.class);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Đã có lỗi xảy ra", Toast.LENGTH_LONG).show();
        }
    }
    private Bitmap resizeBitmap(Uri u) {
        Bitmap result = null;
        try {
            result = new Resizer(this)
                    .setTargetLength(480)
                    .setSourceImage(new File(u.getPath()))
                    .getResizedBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
