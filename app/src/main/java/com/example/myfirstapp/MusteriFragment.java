package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MusteriFragment extends Fragment {
    EditText ad,numara,adres,alınan;

    Button btnListele,btnEkle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_musteri, container, false);
        ConSql con=new ConSql();
        Bundle bundle=getArguments();
        String kullanici=bundle.getString("key");
        Connection conn=con.conClas(kullanici);
        ad=v.findViewById(R.id.ad);
        ad.setText("Müşteri");
        numara=v.findViewById(R.id.numara);
        adres=v.findViewById(R.id.adres);
        alınan=v.findViewById(R.id.alınan);
        btnEkle=v.findViewById(R.id.btnEkle);
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Error",alınan.getText().toString());
                if(alınan.getText().toString().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "Alınan ekmek kısmını boş bırakmayın!!",Toast.LENGTH_LONG).show();
                }
                else{
                if(conn!=null){
                    try {
                        String query = "Insert Into musteri(musteriAd,musteriNumara,musteriAdres,musteriIstek) Values('"  + ad.getText().toString()+"','"+numara.getText().toString()+"','"+adres.getText().toString()+"','" + alınan.getText().toString() +"')";
                        Statement smt = conn.createStatement();
                        smt.executeUpdate(query);

                    }catch (Exception es) {
                        Log.e("can",es.getMessage());
                    }
                    }}
            }
        });
        btnListele=v.findViewById(R.id.btnListele);
        btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Listele.class);
                startActivity(intent);
            }
        });
        return v;
    }
}