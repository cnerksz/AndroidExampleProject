package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class AdminConSql {
    Connection con;
    @SuppressLint("NewApi")
    public Connection conClas(){
        String ip="database-1.cga5aqz9gawt.us-east-2.rds.amazonaws.com",port="1433",username="admin",parola="Ailem123*";
        StrictMode.ThreadPolicy a=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+":"+port,username,parola);

        } catch (Exception e) {
            Log.e("Errsdor :", e.getMessage());
        }
        return con;
    }
}
