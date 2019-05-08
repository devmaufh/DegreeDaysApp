package com.devmaufh.degreedaysapp.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "insects")
public class InsectEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String name;
    @NonNull
    private double umbralS;
    @NonNull
    private double umbralI;
    @NonNull
    private String initialDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUmbralS() {
        return umbralS;
    }

    public void setUmbralS(double umbralS) {
        this.umbralS = umbralS;
    }

    public double getUmbralI() {
        return umbralI;
    }

    public void setUmbralI(double umbralI) {
        this.umbralI = umbralI;
    }
    @NonNull
    public String getInitialDate() {
        return initialDate;
    }
    public void setInitialDate(@NonNull String initialDate) {
        this.initialDate = initialDate;
    }
}
