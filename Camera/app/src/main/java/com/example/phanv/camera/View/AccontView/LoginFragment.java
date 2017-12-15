package com.example.phanv.camera.View.AccontView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phanv.camera.Model.AccountModel.LoginTask;
import com.example.phanv.camera.R;
import com.example.phanv.camera.View.Other.MainActivity;

public class LoginFragment extends Fragment {
    private EditText edLoginName;
    private EditText edPassword;
    private Button btLogin;
    private LoginTask task;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = new LoginTask(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        edLoginName = v.findViewById(R.id.edUserName);
        edPassword = v.findViewById(R.id.edPass);
        btLogin = v.findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //an ban phim
                InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String name = edLoginName.getText().toString();
                String pass = edPassword.getText().toString();
                if (name.length() > 3 & pass.length() > 5) {
                    task.execute(name, pass);
                } else {
                    if (name.length() < 4) {
                        Toast.makeText(getContext(), "Tên đăng nhập quá ngắn", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pass.length() < 6) {
                            Toast.makeText(getContext(), "Mật khẩu quá ngắn nhập quá ngắn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return v;
    }

    public void checkLogin(int i) {
        if (i > 0) {
            Intent intent = new Intent(getActivity(), ViewAccountActivity.class);
            getActivity().finish();
            MainActivity.idAccount = i + "";
            startActivity(intent);

        } else {
            if (i == 0) {
                Toast.makeText(this.getContext(), "Thông tin tài khoản không đúng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
