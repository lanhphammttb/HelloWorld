package com.example.helloworld;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsername, txtPassword;
    private Button btnLogin, btnCallphone;
    private ActivityResultLauncher<Intent> launcher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //code here
        //gan giao dien voi code ma nguon
        setContentView(R.layout.login_activity);
        init();
        processEvents();
    }
    public void init(){
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCallphone = findViewById(R.id.btnCallphone);

    }
    void processEvents(){
        try{
            launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                //code here sau khi dong 56,57 duoc goi ben MainActivity
                if(result!=null && result.getResultCode()==RESULT_OK){
                    String value = result.getData().getStringExtra("time");
                    //file cau hinh

                }

            });
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
//                //code here
//            });
            btnLogin.setOnClickListener(view -> {
                //code here
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
//                launcher.launch(intent);
                //CALLBACK
            });
            btnCallphone.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "0365131536"));
                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    startActivity(intent);
                }
            });
        }catch (Exception ex){
            Log.e("Process Events",ex.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","onStart duoc goi");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
