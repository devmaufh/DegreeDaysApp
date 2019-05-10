package com.devmaufh.degreedaysapp.Entities;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "dates")
public class DatesEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;
    @NonNull
    @ColumnInfo(name = "date")
    private String date;
    @NonNull
    @ColumnInfo(name = "Tmin")
    private Double Tmin;
    @NonNull
    @ColumnInfo(name = "Tmax")
    private Double Tmax;
    @NonNull
    public int getId() {
        return id;
    }
    public void setId(@NonNull int id) {
        this.id = id;
    }
    @NonNull
    public Double getTmin() {
        return Tmin;
    }
    public void setTmin(@NonNull Double tmin) {
        Tmin = tmin;
    }
    @NonNull
    public Double getTmax() {
        return Tmax;
    }
    public void setTmax(@NonNull Double tmax) {
        Tmax = tmax;
    }
    @NonNull
    public String getDate() {
        return date;
    }
    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
