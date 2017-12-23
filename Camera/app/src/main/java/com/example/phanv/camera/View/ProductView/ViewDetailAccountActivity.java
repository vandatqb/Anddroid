package com.example.phanv.camera.View.ProductView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanv.camera.Model.AccountModel.Account;
import com.example.phanv.camera.Model.AccountModel.AccountInforTask;
import com.example.phanv.camera.Model.AccountModel.AccountInformationInterface;
import com.example.phanv.camera.Model.CommentModel.AddCommentTask;
import com.example.phanv.camera.Model.CommentModel.Comment;
import com.example.phanv.camera.Model.CommentModel.CommentInterface;
import com.example.phanv.camera.Model.CommentModel.CommnetAdapter;
import com.example.phanv.camera.Model.CommentModel.GetListCommentTask;
import com.example.phanv.camera.Model.CommentModel.GetStartCommentTask;
import com.example.phanv.camera.Model.ProductModel.ListProductTask;
import com.example.phanv.camera.Model.ProductModel.Product;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.ChatView.ChatActivity;
import com.example.phanv.camera.View.Other.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewDetailAccountActivity extends AppCompatActivity implements AccountInformationInterface, View.OnClickListener, GetListProductInterface, CommentInterface {
    private ProgressDialog dialog;
    private ListProductAdapter adapter;
    private String idAccount;
    private AccountInforTask taskAccount;
    private ListProductTask taskProduct;
    private ImageView img;
    private TextView tvLoginName;
    private TextView tvFullName;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvAddres;
    private TextView tvViewLGName;
    private String phone;
    private ImageView imgCall;
    private ImageView imgMessage;
    private RecyclerView rcvList;
    private RecyclerView rcvListComment;
    private RatingBar rtb;
    private TextView tvStart;
    private GetStartCommentTask taskStart;
    private CommnetAdapter adapterComment;
    private GetListCommentTask taskComment;
    private EditText edComment;
    private RatingBar rtComment;
    private ImageView imgComment;
    private AddCommentTask taskAddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_account);
        getIntentData();
        dialog = new ProgressDialog(this);
        taskAccount = new AccountInforTask(this, this);
        taskAccount.execute(idAccount);
        taskProduct = new ListProductTask(this, this);
        taskProduct.execute(idAccount);
        imgCall = findViewById(R.id.icCall);
        imgMessage = findViewById(R.id.icMessage);
        imgMessage.setOnClickListener(this);
        imgCall.setOnClickListener(this);
        rcvList = findViewById(R.id.rcvViewProductAccount);
        rcvListComment = findViewById(R.id.rcvComment);
        rtb = findViewById(R.id.rtbComment);
        tvStart = findViewById(R.id.viewInformationComment);
        getAvgStart();
        getListComment();
        edComment = findViewById(R.id.edComment);
        rtComment = findViewById(R.id.rtbAddComment);
        imgComment = findViewById(R.id.icAddComment);
        imgComment.setOnClickListener(this);
    }

    private void getAvgStart() {
        taskStart = new GetStartCommentTask(this);
        taskStart.execute(idAccount);
    }

    private void getListComment() {
        taskComment = new GetListCommentTask(this);
        taskComment.execute(idAccount);
    }

    private void addComment(String content, Float start) {
        dialog.setMessage("Đang thêm đánh giá");
        dialog.show();
        taskAddComment = new AddCommentTask(this);
        taskAddComment.execute(idAccount, MainActivity.idAccount, content, start.toString());

    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id");
        idAccount = bundle.getString("id");
    }

    @Override
    public void onClick(View view) {
        if (view == imgCall) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 2222);
                return;
            } else {
                startActivity(intent);
            }
        }
        if (view == imgMessage) {
            if (MainActivity.loged) {
                if (!idAccount.equals(MainActivity.idAccount)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", idAccount);
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("id", bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Bạn không thể nhắn tin cho chính bạn", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }

        }
        if (view == imgComment)
            if (MainActivity.loged) {
                String content = edComment.getText().toString();
                Float start = rtComment.getRating();
                if (content.length() > 2 & start > 0) {
                    addComment(content, start);
                } else {
                    if (content.length() < 3) {
                        Toast.makeText(ViewDetailAccountActivity.this, "Nội dung đánh giá quá ngắn ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ViewDetailAccountActivity.this, "Bạn chưa chọn sao", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void loadSuccess(Account account) {
        if (account != null) {
            img = findViewById(R.id.imgViewAccount);
            tvLoginName = findViewById(R.id.tvLGName);
            tvFullName = findViewById(R.id.tvFullName);
            tvPhone = findViewById(R.id.tvPhone);
            tvEmail = findViewById(R.id.tvEmail);
            tvAddres = findViewById(R.id.tvAddress);
            tvAddres.setText(account.getAddress());
            tvEmail.setText(account.getEmail());
            tvViewLGName = findViewById(R.id.tvViewLGName);
            tvViewLGName.setVisibility(View.INVISIBLE);
            tvLoginName.setVisibility(View.INVISIBLE);
            phone = account.getPhone();
            tvPhone.setText(account.getPhone());
            tvFullName.setText(account.getFullName());
            tvLoginName.setText(account.getLoginName());
            try {
                if (account.getImage() != null) {
                    if (account.getImage().length() > 40) {
                        Picasso.with(this).load(account.getImage()).into(img);
                    } else {
                        img.setImageResource(R.drawable.img_account);
                    }
                }
            } catch (Exception e) {
                img.setImageResource(R.drawable.img_account);
            }
        }
    }

    @Override
    public void loadSuccess(List<Product> list) {
        adapter = new ListProductAdapter(list, this, 0, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvList.setLayoutManager(layoutManager);
        rcvList.setAdapter(adapter);
    }

    @Override
    public void addCommentSucces(int result) {
        dialog.dismiss();
        edComment.setText("");
        getAvgStart();
        getListComment();
    }

    @Override
    public void getListCommentSuccess(List<Comment> list) {
        adapterComment = new CommnetAdapter(list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListComment.setLayoutManager(layoutManager);
        rcvListComment.setAdapter(adapterComment);
    }

    @Override
    public void getStartComment(Float count, Float avg) {
        if (avg >= 0) {
            rtb.setRating(avg);
            tvStart.setText("( " + count + " đánh giá )");
        } else {
            rtb.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2222) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        }
    }
}
