package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Alinan extends AppCompatActivity  {
    public MyAdapterr myAdapter;
    private ArrayList<AlinanList> alinanNesne;
    private RecyclerView recyclerView;
    public   boolean success =false;
    private RecyclerView.LayoutManager layoutManager;
    private ConSql c;
    String fr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fr="2022/08/24";
        setContentView(R.layout.activity_alinan);
        recyclerView = findViewById(R.id.alinanTablo);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        c = new ConSql();
        alinanNesne = new ArrayList<AlinanList>();
        SyncData orderData = new SyncData();
        orderData.execute("");
        Bundle alinan=getIntent().getExtras();
        fr=alinan.getString("veri");


    }



    @SuppressLint("StaticFieldLeak")
        public class SyncData extends AsyncTask<String,String,String> {
            String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error,See Android Monitor in the bottom for detals!!";
            ProgressDialog progressDialog;

            @Override
            public void onPreExecute() {
                progressDialog = ProgressDialog.show(Alinan.this, "Yükleniyor", "Lütfen Bekleyiniz", true);
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    Connection con = c.conClas("BiEfsane");


                    if (con == null) {
                        success = false;
                    } else {



                        String query = "Select *From stok Where stokTur='alinan' and stokGun='"+ fr+"'"+"Order By stokUrun";

                        Statement smt = con.createStatement();
                        ResultSet rs = smt.executeQuery(query);
                        Log.e("ERror",fr);
                        if (rs != null) {
                            while (rs.next()) {

                                try {
                                    alinanNesne.add(new AlinanList(rs.getString("stokUrun"), rs.getFloat("stokFiyat"), rs.getDate("stokGun"),rs.getInt("stokID")));


                                } catch (Exception es) {
                                    es.printStackTrace();
                                }
                            }

                            msg = "Alınanlar";
                            success = true;
                        } else {
                            msg = "Eşys Bilgisine Ulaşılamadı";
                            success = false;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    msg = writer.toString();
                    success = false;
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                progressDialog.dismiss();
                Toast.makeText(Alinan.this, msg + "", Toast.LENGTH_LONG).show();
                if (success = false) {

                } else {
                    try {


                        myAdapter = new com.example.myfirstapp.MyAdapterr(alinanNesne, Alinan.this);
                        recyclerView.setAdapter(myAdapter);


                    }catch (Exception ex){

                    }
                }
            }
        }
    }
    class MyAdapterr extends RecyclerView.Adapter<com.example.myfirstapp.MyAdapterr.ViewHolder> {
        public List<AlinanList> values;
        public Context context;
        ConSql con=new ConSql();
        Connection connection=con.conClas("BiEfsane");


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textAlınan, textFiyat,textGun,textId;
            public Button sil;
            public ViewHolder(View v) {
                super(v);
                textAlınan = v.findViewById(R.id.alınan);
                textFiyat = v.findViewById(R.id.fiyat);
                textGun = v.findViewById(R.id.gün);
                textId=v.findViewById(R.id.ID);
                sil=v.findViewById(R.id.sil);




            }


        }

        public MyAdapterr(List<AlinanList> myDataset, Context context) {
            values = myDataset;
            this.context = context;
        }


        @Override
        public com.example.myfirstapp.MyAdapterr.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_alinan, parent, false);
            com.example.myfirstapp.MyAdapterr.ViewHolder vh = new com.example.myfirstapp.MyAdapterr.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(com.example.myfirstapp.MyAdapterr.ViewHolder holder, final int position) {
            final AlinanList alinanList = values.get(position);

            holder.textAlınan.setText(alinanList.getAlınan());

            holder.textFiyat.setText(alinanList.getFiyat().toString());

            holder.textGun.setText(alinanList.getGün().toString());

            holder.textId.setText(String.valueOf(alinanList.getAlınanId()));

            holder.sil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    values.remove(holder.getAbsoluteAdapterPosition());
                    notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                    try {


                        String query = "Delete From stok Where stokId='" + alinanList.alınanId+"'";
                        Statement smt = connection.createStatement();
                        smt.executeUpdate(query);
                    }catch (Exception es){
                        Log.e("Error",es.getMessage());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return values.size();
        }
    }


