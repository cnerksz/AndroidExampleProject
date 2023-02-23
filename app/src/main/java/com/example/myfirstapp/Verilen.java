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
import java.util.TimeZone;

public class Verilen extends AppCompatActivity {
    public MyAdapteer myAdapter;
    private ArrayList<VerilenList> verilenNesne;
    private RecyclerView recyclerView;
    public   boolean success =false;
    private RecyclerView.LayoutManager layoutManager;
    private ConSql c;
    String fr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verilen);
        recyclerView = findViewById(R.id.verilenTablo);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        c = new ConSql();
        verilenNesne = new ArrayList<VerilenList>();
        SyncData orderData=new SyncData();
        orderData.execute("");
        Bundle alınan=getIntent().getExtras();
        fr=alınan.getString("veri");
    }
    @SuppressLint("StaticFieldLeak")
    public class SyncData extends AsyncTask<String,String,String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error,See Android Monitor in the bottom for detals!!";
        ProgressDialog progressDialog;

        @Override
        public void onPreExecute() {
            progressDialog = ProgressDialog.show(Verilen.this, "Yükleniyor", "Lütfen Bekleyiniz", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection con = c.conClas("BiEfsane");

                if (con == null) {
                    success = false;
                } else {

                    String query = "Select *From stok Where stokTur='verilen' and stokGun='"+fr+"'"+"Order By stokUrun";
                    Statement smt = con.createStatement();
                    ResultSet rs = smt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {

                            try {
                                verilenNesne.add(new VerilenList(rs.getString("stokUrun"), rs.getInt("stokAdet"),rs.getFloat("stokFiyat") ,rs.getDate("stokGun"),rs.getLong("stokID")));


                            } catch (Exception es) {
                                es.printStackTrace();
                            }
                        }

                        msg = "Verilenler";
                        success = true;
                    } else {
                        msg = "Eşya Bilgisine Ulaşılamadı";
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
            Toast.makeText(Verilen.this, msg + "", Toast.LENGTH_LONG).show();
            if (success = false) {

            } else {
                try {


                    myAdapter = new com.example.myfirstapp.MyAdapteer(verilenNesne, Verilen.this);
                    recyclerView.setAdapter(myAdapter);


                }catch (Exception ex){

                }
            }
        }
    }
}
class MyAdapteer extends RecyclerView.Adapter<com.example.myfirstapp.MyAdapteer.ViewHolder> {
    public List<VerilenList> values;
    public Context context;
    ConSql con=new ConSql();
    Connection connection=con.conClas("BiEfsane");


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textVerilen,textAdet,textFiyat,textGun,textId;
        public Button sil;
        public ViewHolder(View v) {
            super(v);
            textVerilen = v.findViewById(R.id.verilen);
            textAdet=v.findViewById(R.id.adet);
            textFiyat = v.findViewById(R.id.fiyat);
            textGun = v.findViewById(R.id.gün);
            textId=v.findViewById(R.id.ID);
            sil=v.findViewById(R.id.sil);




        }


    }

    public MyAdapteer(List<VerilenList> myDataset, Context context) {
        values = myDataset;
        this.context = context;
    }


    @Override
    public com.example.myfirstapp.MyAdapteer.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_verilen, parent, false);
        com.example.myfirstapp.MyAdapteer.ViewHolder vh = new com.example.myfirstapp.MyAdapteer.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(com.example.myfirstapp.MyAdapteer.ViewHolder holder, final int position) {
        final VerilenList verilenList = values.get(position);

        holder.textVerilen.setText(verilenList.getVerilen());
        holder.textAdet.setText(String.valueOf(verilenList.adet));
        holder.textFiyat.setText(String.valueOf(verilenList.getFiyat()));
        holder.textGun.setText(verilenList.getGün().toString());
        holder.textId.setText(String.valueOf(verilenList.getID()));

        holder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                values.remove(holder.getAbsoluteAdapterPosition());
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                try {


                    String query = "Delete From stok Where stokId='" + verilenList.ID+"'";
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

