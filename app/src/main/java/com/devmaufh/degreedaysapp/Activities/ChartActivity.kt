package com.devmaufh.degreedaysapp.Activities

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.R
import com.devmaufh.degreedaysapp.Utilities.ITRDegreeDays
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ChartActivity : AppCompatActivity() {
    lateinit var  graph:GraphView
    var gtu:Double=0.0
    var gtl:Double=0.0
    private lateinit var insectViewModel: InsectsViewModel

    companion object{
        val REQUEST_PERMISSION=99
        val EXTRA_REGISTRATION_DATE=""
        val EXTRA_TU = "INSECT_TL"
        val EXTRA_TL = "INSECT_TU"
        val EXTRA_DEFAULT="01/01/2019"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        graph=findViewById(R.id.graphic)


        val intent=intent
        if(intent!=null && intent.hasExtra(EXTRA_TU) && intent.hasExtra(EXTRA_TL)){
            gtu=intent.getDoubleExtra(EXTRA_TU,0.0)
            gtl=intent.getDoubleExtra(EXTRA_TL,0.0)
            Log.w("UMBRALES: ","TU $gtu")
            Log.w("UMBRALES: ","TL $gtl")


        }else{

        }

        insectViewModel= ViewModelProviders.of(this).get(InsectsViewModel::class.java)




        graph.getViewport().setScalable(true)

        graph.getViewport().setScalableY(true)


        insectViewModel.allDates.observe(this, Observer {dates->
            dates?.let {
                var dataArray= arrayOfNulls<DataPoint>(it.size)//<DataPoint>()
                var counter:Int=0
                it.forEach { date->
                    Log.w("LIVE DATA DATES", "${date.date_id} - ${date.max_temp} - ${date.min_temp}")
                    val units=ITRDegreeDays(gtu,gtl,date.min_temp,date.max_temp).solve()
                    dataArray[counter]= DataPoint(counter.toDouble(),units)
                    counter++
                }
                val series = LineGraphSeries(dataArray)
                graph.addSeries(series)
                graph.gridLabelRenderer.setGridColor(R.color.white)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.share_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.menushared_friends -> {
                askPermission(){
                    graph.takeSnapshotAndShare(this, "graphic", "Gráfica de líneas");
                }.onDeclined { error->
                }

            }
            R.id.menushared_storage -> {
                askPermission(){
                    val bitmap=graph.takeSnapshot()
                    val uri=saveImage(bitmap,"grafic")
                    Toast.makeText(this, "Image saved to $uri", Toast.LENGTH_SHORT).show();
                }.onDeclined { error->
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun saveImage(bitmap: Bitmap, title:String): Uri {

        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                title,
                "Image of $title"
        )
        return Uri.parse(savedImageURL)
    }
}
