package com.example.myfirstapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BakiyeFragment extends Fragment {
    private TextView textGünlükc,textGünlükm,textGünlükb,textAylıkc,textAylıkm,textAylıkb,textList;
    private EditText gunlukDate;
    float günlükc,günlükm,günlükb,aylıkc,aylıkm,aylıkb;
    private Button gunlukpdf;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year, month, dayOfMonth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_bakiye, container, false);
        ConSql conSql=new ConSql();
        Bundle bundle=getArguments();
        String kullanici=bundle.getString("key");
        Connection connection=conSql.conClas(kullanici);
        textGünlükc=v.findViewById(R.id.günlükc);
        textGünlükm=v.findViewById(R.id.günlükm);
        textGünlükb=v.findViewById(R.id.günlükb);
        textAylıkc=v.findViewById(R.id.aylıkc);
        textAylıkm=v.findViewById(R.id.aylıkm);
        textAylıkb=v.findViewById(R.id.aylıkb);
        gunlukpdf=v.findViewById(R.id.gunlukpdf);
        textList=v.findViewById(R.id.textView);
        gunlukDate=v.findViewById(R.id.date);
        Date tarih=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
        gunlukDate.setText(df.format(tarih));
        gunlukDate.setOnClickListener(new View.OnClickListener() {
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
                                gunlukDate.setText(year + "/" +"0"+ (month+1) + "/" + day);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        String query="Select*from stok";

        try {
            Statement smt=connection.createStatement();
            ResultSet set= smt.executeQuery(query);
            while (set.next()) {
                if (set.getString("stokTur").equals("alinan")) {

                    if (df.format(set.getDate("Gün")).equals(gunlukDate.getText().toString())) {
                        günlükm += set.getFloat("Fiyat");
                        aylıkm += set.getFloat("Fiyat");
                        günlükb -= set.getFloat("Fiyat");
                        aylıkb -= set.getFloat("Fiyat");
                    } else {
                        aylıkb -= set.getFloat("Fiyat");
                        aylıkm += set.getFloat("Fiyat");
                    }
                } else{
                    if (df.format(set.getDate("Gün")).equals(gunlukDate.getText().toString())){
                        günlükc+=set.getFloat("Fiyat")*set.getInt("Adet");
                        aylıkc+=set.getFloat("Fiyat")*set.getInt("Adet");
                        günlükb+=set.getFloat("Fiyat")*set.getInt("Adet");
                        aylıkb+=set.getFloat("Fiyat")*set.getInt("Adet");
                    }
                    else {
                        aylıkc+=set.getFloat("Fiyat")*set.getInt("Adet");
                        aylıkb+=set.getFloat("Fiyat")*set.getInt("Adet");
                    }

                }
            }
        }catch (Exception es){

        }
        textGünlükc.setText(String.valueOf(günlükc));
        textGünlükm.setText(String.valueOf(günlükm));
        textGünlükb.setText(String.valueOf(günlükb));
        textAylıkc.setText(String.valueOf(aylıkc));
        textAylıkm.setText(String.valueOf(aylıkm));
        textAylıkb.setText(String.valueOf(aylıkb));

        gunlukDate.addTextChangedListener(new TextWatcher() {
            String query="Select*from stok";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                günlükb=0;günlükc=0;günlükm=0;
                try {
                    Statement smt=connection.createStatement();
                    ResultSet set= smt.executeQuery(query);

                    while (set.next()) {
                        if (set.getString("stokTur").equals("alinan")) {
                            if (df.format(set.getDate("Gün")).equals(gunlukDate.getText().toString())) {
                                günlükm += set.getFloat("Fiyat");

                                günlükb -= set.getFloat("Fiyat");

                            }

                        }else {
                            if (df.format(set.getDate("Gün")).equals(gunlukDate.getText().toString())){
                                günlükc+=set.getFloat("Fiyat")*set.getInt("Adet");
                                günlükb+=set.getFloat("Fiyat")*set.getInt("Adet");
                            }

                        }
                    }
                }catch (Exception es){
                }
                textGünlükc.setText(String.valueOf(günlükc));
                textGünlükm.setText(String.valueOf(günlükm));
                textGünlükb.setText(String.valueOf(günlükb));
                textAylıkc.setText(String.valueOf(aylıkc));
                textAylıkm.setText(String.valueOf(aylıkm));
                textAylıkb.setText(String.valueOf(aylıkb));
            }
        });

        gunlukpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List alinanList=new List();
                alinanList.setListSymbol("");
                alinanList.add("                    ALINANLAR");
                List verilenList=new List();
                verilenList.setListSymbol("");
                verilenList.add("                   VERILENLER");
                alinanList.setListSymbol("-");
                verilenList.setListSymbol("-");
                List gunlukRapor=new List();
                Font fontMadde = new Font(Font.FontFamily.HELVETICA,15.0f, Font.NORMAL, BaseColor.GREEN);
                Chunk madde=new Chunk("$ ",fontMadde);
                gunlukRapor.setListSymbol(madde);

                gunlukRapor.add("Günlük Ciro :"+String.valueOf(günlükc));
                gunlukRapor.add("Günlük Masraf :"+String.valueOf(günlükm));
                gunlukRapor.add("Günlük Kazanç :"+String.valueOf(günlükb));
                try {
                    String query = "Select*From Alinan Where Gün='"+gunlukDate.getText().toString()+"' Order By Alınan";
                    Statement smt = connection.createStatement();
                    ResultSet rs = smt.executeQuery(query);

                    while (rs.next()) {
                        alinanList.add(rs.getString("Alınan") + "  " + rs.getString("Fiyat") + " TL");

                    }
                }
                catch(Exception es){
                 es.printStackTrace();
                    }
                    try {
                    String query="Select * From Verilen Where Gün='"+gunlukDate.getText().toString()+"' Order By Verilen";
                    Statement smt=connection.createStatement();
                    ResultSet rs=smt.executeQuery(query);
                    while(rs.next()){
                        verilenList.add(rs.getString("Verilen")+"  "+rs.getString("Adet")+" X "+rs.getString("Fiyat")+" TL");

                    }


                }catch (Exception es){
                es.printStackTrace();
                }

                PDFOlusturucu can=new PDFOlusturucu();
                try {
                    SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd");
                    String[] a=gunlukDate.getText().toString().split("/");
                    String b=a[2]+"."+a[1]+"."+a[0];

                    can.pdfOlustur(getActivity(),b, alinanList,verilenList,gunlukRapor);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }
}