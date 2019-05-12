package com.devmaufh.degreedaysapp.Utilities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdditionalMethods {
    public static String DATABASE_NAME="insectos";
    public static String PREFERENCES_THEME="theme";
    public static int INDIGO=1;
    public static int GREEN=2;
    public static int BLACK=3;
    public static int PINK=4;
    public static  int SEA=5;
    public static int FILTER_BLUE= Color.argb(203,63,81,181);
    public static int FILTER_GREEN= Color.argb(203,0,150,136);
    public static int FILTER_BLACK=Color.argb(203,33,33,33);
    public static int FILTER_PINK=Color.argb(203,197,17,98);
    public static  int FILTER_SEA=Color.argb(203,130,177,255);
    public static int getFilterImage(SharedPreferences preferences){
        int theme=Integer.parseInt(preferences.getString("theme",INDIGO+""));
        if(theme==INDIGO) return FILTER_BLUE;
        if(theme==GREEN) return FILTER_GREEN;
        if (theme==BLACK) return FILTER_BLACK;
        if(theme==PINK) return  FILTER_PINK;
        if(theme==SEA) return FILTER_SEA;
        return FILTER_SEA;
    }
    public static  int getTheme(SharedPreferences preferences){
        int theme=Integer.parseInt(preferences.getString("theme",INDIGO+""));
        if(theme==INDIGO) return R.style.AppTheme_indigo;
        if(theme==GREEN) return  R.style.AppTheme;
        if (theme==BLACK) return  R.style.AppTheme_dark;
        if(theme==PINK) return  R.style.AppTheme_red;
        if(theme==SEA) return  R.style.AppTheme_sea;
        return  R.style.AppTheme_indigo;
    }
    public static int getRandomImageDrawable(){
        int[] images=
                {
                        R.drawable.insect1,
                        R.drawable.insect2,
                        R.drawable.insect3,
                        R.drawable.hojita,
                        R.drawable.hojita2,
                        R.drawable.trigo,
                        R.drawable.planta
                };
        Random ran= new Random();
        int index=ran.nextInt((images.length-1)-0+1)+0;

        return images[index];
    }
    public static List<DatesEntity> testDatesList(){
        ArrayList<DatesEntity> dates= new ArrayList<DatesEntity>();
        for (int i = 1; i <= 30; i++) {
            DatesEntity entity= new DatesEntity();
            entity.setDate(i+"/4"+"/2019");
            double r1=Math.floor(Math.random()*(19-17+1)+17);
            double r2=Math.floor(Math.random()*(16-13+1)+13);
            entity.setTmax(r1);
            entity.setTmin(r2);
            dates.add(entity);
        }
        return dates;
    }
}
