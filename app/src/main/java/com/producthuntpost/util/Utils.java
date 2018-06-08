package com.producthuntpost.util;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.producthuntpost.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    //Método responsável por verificar se esta habilitado os serviços Wifi ou Dados Movél
    public static Boolean checkInternetConnection(Context context) {
        Boolean isConnected = false;

        try {
            //Inicializa verificação da conectividade
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            //Verifica se esta conectado
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isConnected;
    }

    public static String getDateTodayFormatter(){
        return simpleDateFormat().format(new Date());
    }

    public static String formatDate(int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);
        return simpleDateFormat().format(c.getTime());
    }

    public static SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static boolean isTable(Context ctx){
        return ctx.getResources().getBoolean(R.bool.isTablet);
    }

    public static boolean getRotatioScreen(Context context){
        if(context != null){
            return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        }else {
            return false;
        }
    }
}
