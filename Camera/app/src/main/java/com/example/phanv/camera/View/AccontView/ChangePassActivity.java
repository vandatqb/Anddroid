package com.example.phanv.camera.View.AccontView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phanv.camera.Model.AccountModel.ChangePassTask;
import com.example.phanv.camera.R;

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edOldPass;
    private EditText edNewPass;
    private EditText edRePass;
    private Button btChange;
    private ChangePassTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        edOldPass = findViewById(R.id.edOldPass);
        edNewPass = findViewById(R.id.edNewPass);
        edRePass = findViewById(R.id.edRePass);
        btChange = findViewById(R.id.btChangePass);
        task = new ChangePassTask(this);
        btChange.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == btChange) {
            //an ban phim
            InputMethodManager imm = (InputMethodManager) this.getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            String old = edOldPass.getText().toString();
            String newPass = edNewPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (old.length() > 5 & newPass.length() > 5 & rePass.length() > 5 & newPass.equals(rePass)) {
                task.execute(old, newPass);
            } else {
                if (old.length() < 5) {
                    Toast.makeText(ChangePassActivity.this, "Mật khẩu cũ quá ngắn", Toast.LENGTH_SHORT).show();
                } else {
                    if (newPass.length() < 5) {
                        Toast.makeText(ChangePassActivity.this, "Mật khẩu mới quá ngắn", Toast.LENGTH_SHORT).show();
                    } else {
                        if (newPass.equals(old)) {
                            Toast.makeText(ChangePassActivity.this, "Mật khẩu mới không trùng nhau", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    public void result(int result) {
        if (result > 0) {
            Toast.makeText(this, "Đổi thành công", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Đổi không thành công", Toast.LENGTH_LONG).show();
        }
    }
}