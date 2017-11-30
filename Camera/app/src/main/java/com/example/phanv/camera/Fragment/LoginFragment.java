package com.example.phanv.camera.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phanv.camera.Activity.AccountActivity;
import com.example.phanv.camera.Model.ConnectServer;
import com.example.phanv.camera.Model.Local;
import com.example.phanv.camera.Model.Property;
import com.example.phanv.camera.Process.LocalData;
import com.example.phanv.camera.R;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {
    EditText edLoginName;
    EditText edPassword;
    Button btLogin;
    ConnectServer connect = new ConnectServer();
    LocalData local;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        edLoginName = v.findViewById(R.id.edUserName);
        edPassword = v.findViewById(R.id.edPass);
        btLogin = v.findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edLoginName.getText().toString();
                String pass = edPassword.getText().toString();
                if (name.length() > 3 & pass.length() > 5) {
                    if (checkLogin(name, pass) < 0) {
                        Toast.makeText(getContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    } else {
                        int id = checkLogin(name, pass);
                        if (id== 0) {
                            Toast.makeText(getContext(), "Thông tin tài khoản không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            local = new LocalData(getActivity());
                            local.writeId(id+"");
                            Intent intent = new Intent(getActivity(), AccountActivity.class);
                            getActivity().finish();
                            startActivity(intent);
                        }
                    }
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

    private int checkLogin(String name, String pass) {
        List<Property> list = new ArrayList<>();
        Property loginName = new Property("loginName", name);
        Property password = new Property("pass", pass);
        list.add(loginName);
        list.add(password);
        String address = "http://tempuri.org/checkLogin";
        String action = "checkLogin";
        String id = connect.processString(list, address, action);
        if (id.equals("er")) {
            return -1;
        } else {
            if (id.equals("0")) {
                return 0;
            } else {
                local = new LocalData(getActivity());
                local.writeId(id);
                return Integer.parseInt(id);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
