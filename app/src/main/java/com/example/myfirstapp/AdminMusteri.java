package com.example.myfirstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.Statement;

public class AdminMusteri extends Fragment {
EditText userName,userPw;
Button btnEkle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_musteri, container, false);
        userName=v.findViewById(R.id.userName);
        userPw=v.findViewById(R.id.pw);
        btnEkle=v.findViewById(R.id.btnEkle);
AdminConSql con=new AdminConSql();
ConSql connect=new ConSql();
        Connection connection=con.conClas();


       btnEkle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try{
                   Statement smt=connection.createStatement();
                   smt.executeQuery("Create DATABASE "+userName.getText().toString());

               }catch (Exception es){
es.printStackTrace();
               }
               try {


                   Connection connection1 = connect.conClas(userName.getText().toString());
                   Statement smt1 = connection1.createStatement();
                   String tables = "use " + userName.getText().toString() + " CREATE TABLE stok(stokID int PRIMARY KEY,stokTur varchar(20),stokEsya varchar(255),stokFiyat float,stokGun datetime,stokAdet float ); CREATE TABLE musteri(musteriID int PRIMARY KEY,musteriAd varchar(255),musteriNumara varchar(255),musteriAdres varchar(255),musteriIstek varchar(255) ); CREATE TABLE ucret(ucretID int PRIMARY KEY,ucretTur varchar(255),ucretMiktar float );";
                   smt1.executeQuery(tables);
               }catch (Exception es){
                   es.printStackTrace();
               }

           }
       });

    return v;
    }
}