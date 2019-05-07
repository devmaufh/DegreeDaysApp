package com.devmaufh.degreedaysapp.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dates")
public class DatesEntity {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;
    @NonNull
    private String tMax;
    @NonNull
    private String tMin;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String gettMax() {
        return tMax;
    }

    public void settMax(@NonNull String tMax) {
        this.tMax = tMax;
    }

    @NonNull
    public String gettMin() {
        return tMin;
    }

    public void settMin(@NonNull String tMin) {
        this.tMin = tMin;
    }
}
