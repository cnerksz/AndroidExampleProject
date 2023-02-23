package com.example.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class HesapFragment extends Fragment {
    Connection connection;
    private Spinner spinnerEkmekici;
    private Spinner spinnerEkmekler;
    private  TextView sonuc;
    private EditText editAdet;
    private EditText editDuzenle;
    private ArrayAdapter<String> getDataAdaptorForekmekici;
    private ArrayAdapter<String>dataAdaptorForekmek;
    private Button guncelle;
    ResultSet rs;
    boolean bos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v=inflater.inflate(R.layout.fragment_hesap, container, false);
        guncelle=(Button)v.findViewById(R.id.guncelle);
        editDuzenle=(EditText) v.findViewById(R.id.editDuzenle);
        ArrayList<String> ekmekler=new ArrayList<>();
        ekmekler.add("Mayalı");ekmekler.add("Lavaş");ekmekler.add("Şebit");ekmekler.add("Sıkma");ekmekler.add("Gözleme");ekmekler.add("K.Börek");
        ArrayList<String> ekmekici=new ArrayList<>();
        ekmekici.add("Peynirli");ekmekici.add("Kıymalı");ekmekici.add("Patatesli");ekmekici.add("Karışık");

        spinnerEkmekler=v.findViewById(R.id.spinnerEkmekler);
        spinnerEkmekici=v.findViewById(R.id.spinnerEkmekici);
        dataAdaptorForekmek=new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_item,ekmekler);
        dataAdaptorForekmek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEkmekler.setAdapter(dataAdaptorForekmek);
        getDataAdaptorForekmekici=new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_item,ekmekici);
        getDataAdaptorForekmekici.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEkmekici.setAdapter(getDataAdaptorForekmekici);
        ConSql c=new ConSql();
        Bundle bundle=getArguments();
        String kullanici=bundle.getString("key");

        connection=c.conClas(kullanici);
        String sorgu="Select *From ucret";


        ArrayList<Float> fiyat=new ArrayList<Float>();
        try {
            Statement smt=connection.createStatement();
            rs=smt.executeQuery(sorgu);
            while (rs.next()){
                fiyat.add(rs.getFloat("ucretMiktar"));
            }
        }catch (Exception es){

        }
        Log.e(fiyat.get(2).toString(),"can");

        sonuc=(TextView)v.findViewById(R.id.sonuc);
        spinnerEkmekler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerEkmekler.getSelectedItem().toString()=="Mayalı"||spinnerEkmekler.getSelectedItem().toString()=="Lavaş"||spinnerEkmekler.getSelectedItem().toString()=="Şebit") {
                    spinnerEkmekici.setEnabled(false);

                    guncelle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sorgu="Update ucret Set ucretMiktar="+editDuzenle.getText().toString()+"Where ucretTur ='"+spinnerEkmekler.getSelectedItem().toString()+"'";
                            try {
                                Statement smt = connection.createStatement();
                                smt.executeUpdate(sorgu);




                            }catch(Exception ex){ Log.e("Error",ex.getMessage());}
                        }
                    });
                }
                else {
                    spinnerEkmekici.setEnabled(true);
                    guncelle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sorgu="Update ucret Set ucretMiktar="+editDuzenle.getText().toString()+"Where ucretTur ='"+spinnerEkmekici.getSelectedItem().toString()+" "+spinnerEkmekler.getSelectedItem().toString()+"'";
                            try {
                                Statement smt = connection.createStatement();
                                smt.executeUpdate(sorgu);




                            }catch(Exception ex){ Log.e("Error",ex.getMessage());}
                        }

                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        editAdet=(EditText) v.findViewById(R.id.editAdet);

        editAdet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            sonuc.setText("0.0TL");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float ucret = 0;
                String sonucc="";

                switch (spinnerEkmekler.getSelectedItem().toString()) {
                    case "Mayalı":
                        ucret=fiyat.get(0);

                        break;
                    case "Lavaş":
                        ucret=fiyat.get(0);
                        break;
                    case "Şebit":
                        ucret=fiyat.get(2);
                        break;
                    case "Sıkma":

                        switch (spinnerEkmekici.getSelectedItem().toString()) {
                            case "Peynirli":
                                ucret=fiyat.get(3);
                                break;
                            case "Kıymalı":
                                ucret=fiyat.get(4);
                                break;
                            case "Patatesli":
                                ucret=fiyat.get(5);
                                break;
                            case "Karışık":
                                ucret=fiyat.get(6);
                                break;
                        }
                        break;
                    case "Gözleme":
                        switch (spinnerEkmekici.getSelectedItem().toString()) {
                            case "Peynirli":
                                ucret=fiyat.get(7);
                                break;
                            case "Kıymalı":
                                ucret=fiyat.get(8);
                                break;
                            case "Patatesli":
                                ucret=fiyat.get(9);
                                break;
                            case "Karışık":
                                ucret=fiyat.get(10);
                                break;
                        }

                        break;
                    case "K.Börek":
                        switch (spinnerEkmekici.getSelectedItem().toString()) {
                            case "Peynirli":
                                ucret=fiyat.get(11);
                                break;
                            case "Kıymalı":
                                ucret=fiyat.get(12);
                                break;
                            case "Patatesli":
                                ucret=fiyat.get(13);
                                break;
                            case "Karışık":
                                ucret=fiyat.get(14);
                                break;
                        }
                        break;
                }
bos=editAdet.getText().toString().isEmpty();
                    if(bos==false) {
                        Float carpim = ucret * Float.valueOf(editAdet.getText().toString());
                        sonucc += carpim.toString();
                        sonuc.setText(sonucc + "TL");
                    }

        }
        });

        return v;
    }


}