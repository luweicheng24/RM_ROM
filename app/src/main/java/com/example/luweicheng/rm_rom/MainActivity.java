package com.example.luweicheng.rm_rom;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_key;
    private CheckBox checkBox;
    private CheckBox rember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.Account);
        et_key = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.cb);
        rember = (CheckBox) findViewById(R.id.rem);
        readAccount();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    et_key.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else
                    et_key.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        rember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rember.isChecked()){
                    //创建存储文件
                    String name = et_name.getText().toString();
                    String key = et_key.getText().toString();
                    File file = new File(getFilesDir(), "info.txt");
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write((name + "&&" + key).getBytes());
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
}

    public void readAccount() {

        String name = et_name.getText().toString();
        String key = et_key.getText().toString();
        File file = new File(getFilesDir(), "info.txt");
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            String text = bf.readLine().toString();
            String[] str = text.split("&&");
            if (str.length > 0) {
                et_name.setText(str[str.length-2]);
                et_key.setText(str[str.length-1]);
            } else {
                Toast.makeText(this, "您第一次登陆", Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    //登录监听
    public void login(View v) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MainActivity.this, SecActivity.class));
        startActivity(intent);
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }




}