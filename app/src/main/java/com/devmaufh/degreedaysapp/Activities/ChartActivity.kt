package com.devmaufh.degreedaysapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmaufh.degreedaysapp.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_chart.*

class ChartActivity : AppCompatActivity() {
    companion object{
        val EXTRA_REGISTRATION_DATE=""
        val EXTRA_DEFAULT="01/01/2019"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)


        val graph=findViewById(R.id.graphic) as GraphView
        val series = LineGraphSeries(
                arrayOf(
                        DataPoint(0.0, 1.0),
                        DataPoint(1.0, 5.0),
                        DataPoint(2.0, 3.0),
                        DataPoint(4.0, 8.0),
                        DataPoint(5.0, 10.0),
                        DataPoint(22.0, 38.0)
                        ))
        graph.getViewport().setScalable(true)
        graph.getViewport().setScalableY(true)
        graph.setTitle("Unidades calor registradas")
        series.setColor(getColor(R.color.colorPrimaryDark))
        graph.addSeries(series)

    }
}
