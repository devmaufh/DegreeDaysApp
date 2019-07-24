package com.devmaufh.degreedaysapp.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "insects_table")
data class Insect (
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "insect_id") val insect_id:Int,
        @ColumnInfo(name = "name")  val name:String,
        @ColumnInfo(name = "tu")  val tu:Double,
        @ColumnInfo(name = "tl")  val tl:Double,
        @ColumnInfo(name = "registration_date")  val registration_date:String,
        @ColumnInfo(name = "email")  val email:String)

@Entity(tableName="dates_table" )
data class IDate(
        @PrimaryKey @ColumnInfo(name = "date_id")    val date_id:String,
        @ColumnInfo(name = "min_temp")   val min_temp:Double,
        @ColumnInfo(name = "max_temp")       val max_temp:Double)

