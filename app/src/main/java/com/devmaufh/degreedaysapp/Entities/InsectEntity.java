package com.devmaufh.degreedaysapp.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "insects", foreignKeys =@ForeignKey(entity = DatesEntity.class, parentColumns = "id",childColumns = "initialDate"))
public class InsectEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "umbralS")
    private double umbralS;
    @NonNull
    @ColumnInfo(name = "umbralI")
    private double umbralI;
    @NonNull
    @ColumnInfo(name = "initialDate")
    private int initialDate;
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
    public int getInitialDate() {
        return initialDate;
    }
    public void setInitialDate(@NonNull int initialDate) {
        this.initialDate = initialDate;
    }
}
