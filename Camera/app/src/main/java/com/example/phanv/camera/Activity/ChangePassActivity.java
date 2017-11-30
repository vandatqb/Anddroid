package com.example.phanv.camera.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phanv.camera.Model.Local;
import com.example.phanv.camera.Process.AccountProcess;
import com.example.phanv.camera.Process.LocalData;
import com.example.phanv.camera.R;

public class ChangePassActivity extends AppCompatActivity {
    EditText edOldPass;
    EditText edNewPass;
    EditText edRePass;
    Button btChange;
    AccountProcess process;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        edOldPass = findViewById(R.id.edOldPass);
        edNewPass = findViewById(R.id.edNewPass);
        edRePass = findViewById(R.id.edRePass);
        btChange = findViewById(R.id.btChangePass);
        activity = this;
        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old = edOldPass.getText().toString();
                String newPass = edNewPass.getText().toString();
                String rePass = edRePass.getText().toString();
                if (old.length() > 5 & newPass.length() > 5 & rePass.length() > 5 & newPass.equals(rePass)) {
                    LocalData data = new LocalData(activity);
                    Local local = data.read();
                    int result = process.upPass(local.getId(), old, newPass);
                    if (result < 0) {
                        Toast.makeText(activity, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                    if (result == 0) {
                        Toast.makeText(activity, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                    }
                    if (result > 0) {
                        Toast.makeText(activity, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
