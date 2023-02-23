package com.example.myfirstapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class StokFragment extends Fragment {
private EditText alinangn,verilengn,adet,fiyat,alinan,afiyat;
public EditText date;
private Spinner spinnerEkmekici;
private Spinner spinnerEkmekler;
private ArrayAdapter<String> getDataAdaptorForekmekici;
private ArrayAdapter<String>dataAdaptorForekmek;
private Button btnvEkle,btnEkle,btnListele,btnvListele;
private DatePickerDialog datePickerDialog;
private Calendar calendar;
private int year, month, dayOfMonth;
Bundle bnd;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_alver, container, false);
       ConSql conSql=new ConSql();
       Bundle bundle=getArguments();
        String kullanici=bundle.getString("key");
        Connection connection=conSql.conClas(kullanici);
       adet=v.findViewById(R.id.vadet);
       alinan=v.findViewById(R.id.alinan);
       afiyat=v.findViewById(R.id.fiyat);
       fiyat=v.findViewById(R.id.vfiyat);
       alinangn=v.findViewById(R.id.tarih);
        verilengn=v.findViewById(R.id.vtarih);
        date=v.findViewById(R.id.selectDate);
        Date tarih=new Date();

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        date.setText(df.format(tarih));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(year + "/" +"0"+ (month+1) + "/" + day);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });




        alinangn.setText(df.format(tarih));
        verilengn.setText(df.format(tarih));
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
        spinnerEkmekler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerEkmekler.getSelectedItem().toString()=="Mayalı"||spinnerEkmekler.getSelectedItem().toString()=="Lavaş"||spinnerEkmekler.getSelectedItem().toString()=="Şebit") {
                    spinnerEkmekici.setEnabled(false);
                }
                else {
                    spinnerEkmekici.setEnabled(true);
                }
                String sorgu="";
                switch(spinnerEkmekler.getSelectedItem().toString()) {
                    case "Mayalı":
                        sorgu = "Select * from ucret where ucretID=1";

                        break;
                    case "Lavaş":
                        sorgu = "Select * from ucret where ucretID=2";

                        break;
                    case "Şebit":
                        sorgu = "Select * from ucret where ucretID=3";

                        break;
                    case "Sıkma":
                        sorgu = "Select * from ucret where ucretID=4";
                            break;
                    case "Gözleme":
                        sorgu = "Select * from ucret where ucretID=8";
                        break;
                    case "K.Börek":
                        sorgu = "Select * from ucret where ucretID=12";


                }
                try {
                    Statement smt=connection.createStatement();
                    ResultSet set=smt.executeQuery(sorgu);
                    set.next();
                    fiyat.setText(String.valueOf(set.getFloat(3)));
                }catch (Exception es){

                }
                spinnerEkmekici.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String sorgu="";
                        switch(spinnerEkmekler.getSelectedItem().toString()) {

                            case "Sıkma":

                                switch (spinnerEkmekici.getSelectedItem().toString()) {

                                    case "Peynirli":

                                        sorgu = "Select * from ucret where ucretID=4";
                                        break;
                                    case"Kıymalı":
                                        sorgu = "Select * from ucret where ucretID=5";
                                        break;
                                    case "Patatesli":
                                        sorgu = "Select * from ucret where ucretID=6";
                                        break;
                                    case"Karışık":
                                        sorgu = "Select * from ucret where ucretID=7";
                                        break;
                                }
                                break;
                            case "Gözleme":

                                switch (spinnerEkmekici.getSelectedItem().toString()) {
                                    case "Peynirli":
                                        sorgu = "Select * from ucret where ucretID=8";
                                        break;
                                    case"Kıymalı":
                                        sorgu = "Select * from ucret where ucretID=9";
                                        break;
                                    case "Patatesli":
                                        sorgu = "Select * from ucret where ucretID=10";
                                        break;
                                    case"Karışık":
                                        sorgu = "Select * from ucret where ucretID=11";
                                        break;
                                }

                                break;
                            case "K.Börek":

                                switch (spinnerEkmekici.getSelectedItem().toString()) {
                                    case "Peynirli":
                                        sorgu = "Select * from ucret where ucretID=12";
                                        break;
                                    case"Kıymalı":
                                        sorgu = "Select * from ucret where ucretID=13";
                                        break;
                                    case "Patatesli":
                                        sorgu = "Select * from ucret where ucretID=14";
                                        break;
                                    case"Karışık":
                                        sorgu = "Select * from ucret where ucretID=15";
                                        break;
                                }
                                break;
                        }
                        try {
                            Statement smt=connection.createStatement();
                            ResultSet set=smt.executeQuery(sorgu);
                            set.next();
                            fiyat.setText(String.valueOf(set.getFloat(3)));
                        }catch (Exception es){

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnEkle=v.findViewById(R.id.btnEkle);
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alinan.getText().toString().equals("") || afiyat.getText().toString().equals("")||alinangn.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Eksik alanları doldurun!!", Toast.LENGTH_LONG).show();
                } else {


                    String query = "Insert into stok(stokUrun,stokFiyat,stokGun,stokTur)Values('" + alinan.getText().toString() + "','" + afiyat.getText().toString() + "','" + alinangn.getText().toString() + "','alinan')";
                    try {
                        Statement smt = connection.createStatement();
                        smt.executeUpdate(query);
                        Toast.makeText(getActivity().getApplicationContext(),"Veri Eklendi",Toast.LENGTH_LONG).show();

                    } catch (Exception es) {
                        Toast.makeText(getActivity().getApplicationContext(), "bağlantınızı kontrol edin",Toast.LENGTH_LONG).show();

                    }

                }
            }
        });
        btnvEkle=v.findViewById(R.id.btnvEkle);
        btnvEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adet.getText().toString().equals("") || fiyat.getText().toString().equals("") || verilengn.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Eksik alanları doldurun!!", Toast.LENGTH_LONG).show();
                } else {
                    if (spinnerEkmekler.getSelectedItem().toString() == "Mayalı" || spinnerEkmekler.getSelectedItem().toString() == "Lavaş" || spinnerEkmekler.getSelectedItem().toString() == "Şebit") {
                        String query = "Insert into stok(stokUrun,stokFiyat,stokAdet,stokGun,stokTur)Values('" + spinnerEkmekler.getSelectedItem().toString() + "','" + fiyat.getText().toString() + "','" + adet.getText().toString() + "','" + verilengn.getText().toString() + "','verilen')";
                        try {
                            Statement smt = connection.createStatement();
                            smt.executeUpdate(query);
                        } catch (Exception es) {
                            Toast.makeText(getActivity().getApplicationContext(), "bağlantınızı kontrol edin",Toast.LENGTH_LONG).show();

                        }
                    } else {
                        String query = "Insert into stok(stokUrun,stokFiyat,stokAdet,stokGun,stokTur)Values('" + spinnerEkmekici.getSelectedItem().toString() + " " + spinnerEkmekler.getSelectedItem().toString() + "','" + fiyat.getText().toString() + "','" + adet.getText().toString() + "','" + verilengn.getText().toString() + "',verilen)";
                        try {
                            Statement smt = connection.createStatement();
                            smt.executeUpdate(query);
                            Toast.makeText(getActivity().getApplicationContext(),"Veri Eklendi",Toast.LENGTH_LONG).show();
                        } catch (Exception es) {
                            Toast.makeText(getActivity().getApplicationContext(), "bağlantınızı kontrol edin",Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }

        });
btnListele=v.findViewById(R.id.btnListele);

btnListele.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent=new Intent(getActivity(),Alinan.class);
        bnd=new Bundle();
        String gün=date.getText().toString();
        bnd.putString("veri",gün);
        intent.putExtras(bnd);
        startActivity(intent);

    }
});
btnvListele=v.findViewById(R.id.btnvListele);
btnvListele.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent=new Intent(getActivity(),Verilen.class);
        bnd=new Bundle();
        String gün=date.getText().toString();
        bnd.putString("veri",gün);
        intent.putExtras(bnd);
        startActivity(intent);

    }
});
        return v;
    }

}