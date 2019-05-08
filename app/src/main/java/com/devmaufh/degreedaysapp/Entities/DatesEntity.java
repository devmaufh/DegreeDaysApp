package com.devmaufh.degreedaysapp.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "dates")
public class DatesEntity {
    @PrimaryKey()
    @NonNull
    private String id;
    @NonNull
    private String Tmin;
    @NonNull
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
