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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

public class Listele extends AppCompatActivity {
public MyAdapter myAdapter;
private ArrayList<MusteriList> musteriNesne;
private RecyclerView recyclerView;
public   boolean success =false;
private RecyclerView.LayoutManager layoutManager;
private ConSql c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listele);
        recyclerView=findViewById(R.id.müsteriTablo);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        c=new ConSql();
        musteriNesne=new ArrayList<MusteriList>();
        SyncData orderData=new SyncData();
        orderData.execute("");
    }
    @SuppressLint("StaticFieldLeak")
    public class SyncData extends AsyncTask<String,String,String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error,See Android Monitor in the bottom for detals!!";
        ProgressDialog progressDialog;

        @Override
        public void onPreExecute() {
            progressDialog = ProgressDialog.show(Listele.this, "Yükleniyor", "Lütfen Bekleyiniz", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection con = c.conClas("BiEfsane");

                if (con == null) {
                    success = false;
                } else {
                    String query = "Select *From Musteriler";
                    Statement smt = con.createStatement();
                    ResultSet rs = smt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {

                            try {
                                musteriNesne.add(new MusteriList(rs.getString("Ad"), rs.getString("Numara"), rs.getString("Adres"), rs.getString("Alınan"),rs.getLong("musteriId")));


                            } catch (Exception es) {
                                es.printStackTrace();
                            }
                        }

                        msg = "Bulunan Müşteriler";
                        success = true;
                    } else {
                        msg = "Müşteri Bilgisine Ulaşılamadı";
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
            Toast.makeText(Listele.this, msg + "", Toast.LENGTH_LONG).show();
            if (success = false) {

            } else {
                try {


                    myAdapter = new MyAdapter(musteriNesne, Listele.this);
                    recyclerView.setAdapter(myAdapter);

                }catch (Exception ex){

                }
            }
        }
    }
}
 class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public List<MusteriList> values;
    public Context context;
    ConSql con=new ConSql();
    Connection connection=con.conClas("BiEfsane");


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textAd, textNo, textAdres, textAlınan,textId;
        public Button sil;
        public ViewHolder(View v) {
            super(v);
            textAd = v.findViewById(R.id.ad);
            textNo = v.findViewById(R.id.numara);
            textAdres = v.findViewById(R.id.adres);
            textAlınan = v.findViewById(R.id.Alınan);
            textId=v.findViewById(R.id.mustId);
            sil=v.findViewById(R.id.sil);




        }


    }

    public MyAdapter(List<MusteriList> myDataset, Context context) {
        values = myDataset;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_musteri, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MusteriList musteriList = values.get(position);
        holder.textAd.setText(musteriList.getAd());
        holder.textNo.setText(musteriList.getNo());
        holder.textAdres.setText(musteriList.getAdres());
        holder.textAlınan.setText(musteriList.getAlınan());
        holder.textId.setText(String.valueOf(musteriList.getId()));
        holder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                values.remove(holder.getAbsoluteAdapterPosition());
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                try {


                    String query = "Delete From Musteriler Where musteriId='" + musteriList.id+"'";
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


