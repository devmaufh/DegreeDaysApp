package com.devmaufh.degreedaysapp.Entities;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "dates")
public class DatesEntity {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @NonNull
    private String id;
    @NonNull
    @ColumnInfo(name = "Tmin")
    private String Tmin;
    @NonNull
    @ColumnInfo(name = "Tmax")
    private String Tmax;
    @NonNull
    public String getId() {
        return id;
    }
    public void setId(@NonNull String id) {
        this.id = id;
    }
    @NonNull
    public String getTmin() {
        return Tmin;
    }
    public void setTmin(@NonNull String tmin) {
        Tmin = tmin;
    }
    @NonNull
    public String getTmax() {
        return Tmax;
    }
    public void setTmax(@NonNull String tmax) {
        Tmax = tmax;
    }
}
