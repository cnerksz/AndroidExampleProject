package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConSql {
    Connection con;
    @SuppressLint("NewApi")
    public Connection conClas(String database){
    String ip="database-1.cga5aqz9gawt.us-east-2.rds.amazonaws.com",port="1433",username="admin",parola="Ailem123*";
    StrictMode.ThreadPolicy a=new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(a);
    String connectURL=null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://"+ip+":"+port+";databasename="+database+";user="+username+";"+"password="+parola+";";
            con = DriverManager.getConnection(connectURL);

        } catch (Exception e) {
            Log.e("Errsdor :", e.getMessage());
        }
        return con;
    }
    }



