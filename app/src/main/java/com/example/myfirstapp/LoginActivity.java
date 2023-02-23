package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class
LoginActivity extends AppCompatActivity {
Button giris;
EditText kullanici,sifre;
Connection con;
ConSql conSql=new ConSql();
ResultSet set;
String  setKullanici="";
String setSifre="";
String setTur="";
Boolean basarili=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        internetCheck();
        kullanici=findViewById(R.id.kullanici);
        sifre=findViewById(R.id.sifre);
        giris=findViewById(R.id.sign_in_btn);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetCheck();
                try {
                    con = conSql.conClas("BePRG");
                    String query = "Select YetkiTur,YetkiKullanici,YetkiSifre From Yetki";
                    Statement smt = con.createStatement();
                    set = smt.executeQuery(query);
                    while (set.next()) {
                        setKullanici = set.getString("YetkiKullanici");
                        setSifre = set.getString("YetkiSifre");
                        setTur = set.getString("YetkiTur");

                        if (setKullanici.equals(kullanici.getText().toString())) {
                            if (setTur.equals("user") && setSifre.equals(sifre.getText().toString())) {


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("key",kullanici.getText().toString());
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                                basarili = true;
                            } else if (setTur.equals("admin") && setSifre.equals(sifre.getText().toString())){
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                          }
                        }
                    }


                } catch (Exception es) {

                    es.printStackTrace();
                }
                if (basarili == false)
                    Toast.makeText(LoginActivity.this, "Kullanıcı adı ya da şifrenizi kontrol edin", Toast.LENGTH_SHORT).show();


            }
        });
    }
    private void internetCheck(){
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting())
        {

        }
        else
        {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Hata");
            builder.setMessage("Lütfen internet bağlantınızı kontrol ediniz!");
            builder.setCancelable(true);
            builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("Çıkış", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();
                }
            });
            android.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
