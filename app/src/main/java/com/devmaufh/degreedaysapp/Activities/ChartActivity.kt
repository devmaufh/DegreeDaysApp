package com.devmaufh.degreedaysapp.Activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.R
import com.devmaufh.degreedaysapp.Utilities.ITRDegreeDays
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.Series
import java.text.DateFormat
import java.text.SimpleDateFormat

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
        setupPermissions()


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

    private fun setupPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }
            requestPermissions(arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION);

            return;
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}
