package com.devmaufh.degreedaysapp.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.devmaufh.degreedaysapp.Activities.HomeActivity.Companion.updateInsectActivityRequestCode
import com.devmaufh.degreedaysapp.Activities.Register.Companion.EXTRA_DATE
import com.devmaufh.degreedaysapp.Activities.Register.Companion.EXTRA_ID
import com.devmaufh.degreedaysapp.Activities.Register.Companion.EXTRA_NAME
import com.devmaufh.degreedaysapp.Activities.Register.Companion.EXTRA_TL
import com.devmaufh.degreedaysapp.Activities.Register.Companion.EXTRA_TU
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.Entities.Insect
import com.devmaufh.degreedaysapp.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_insect_details.*

class InsectDetailsActivity : AppCompatActivity() {
    private lateinit var insectViewModel: InsectsViewModel

    var id: Int=0
    var iName:String= ""
    var tu: Double = 0.0
    var tl: Double = 0.0
    var rd: String = ""
    var flag: Boolean = false

    companion object{
        val EXTRA_INSECT_ID:   String = "INSECT_ID"
        val EXTRA_INSECT_NAME:String = "INSECT_NAME"
        val EXTRA_INSECT_TU:  String = "INSECT_TU"
        val EXTRA_INSECT_TL:  String = "INSECT_TL"
        val EXTRA_INSECT_RD:  String = "INSECT_REGISTRATION_DATE"
        val STRING_DEFAULT:   String = "Data not founded"
        val DOUBLE_DEFAULT:   Double = 0.0
        val INT_DEFAULT:      Int    = 0

       // val UpdateInsectRequestCode:Int = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insect_details)
        insectViewModel= ViewModelProviders.of(this).get(InsectsViewModel::class.java)

        val intent=intent
        if(     intent!=null &&
                intent.hasExtra(EXTRA_INSECT_ID)&&
                intent.hasExtra(EXTRA_INSECT_NAME) &&
                intent.hasExtra(EXTRA_INSECT_TU)&&
                intent.hasExtra(EXTRA_INSECT_TL)&&
                intent.hasExtra(EXTRA_INSECT_RD)
            ){
            id    = intent.getIntExtra(EXTRA_INSECT_ID,0)
            iName = intent.getStringExtra(EXTRA_INSECT_NAME)
            tu    = intent.getDoubleExtra(EXTRA_INSECT_TU,0.0)
            tl    = intent.getDoubleExtra(EXTRA_INSECT_TL,0.0)
            rd    = intent.getStringExtra(EXTRA_INSECT_RD)
            flag=true

            Log.w("DEGREE ID:  ",id.toString())
            Log.w("DEGREE TL",tl.toString())
            Log.w("DETAILS INSECTS",iName)
        }else{
            iName = STRING_DEFAULT
            tu = DOUBLE_DEFAULT
            tl = DOUBLE_DEFAULT
            rd = STRING_DEFAULT
            flag=false
        }
        setTitle(getString(R.string.details_insect))
        setData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==updateInsectActivityRequestCode && resultCode==Activity.RESULT_OK){
            data?.let {
               val insect=Insect(it.getIntExtra(Register.EXTRA_ID,0),
                        it.getStringExtra(Register.EXTRA_NAME),
                        it.getDoubleExtra(Register.EXTRA_TU,0.0),
                        it.getDoubleExtra(Register.EXTRA_TL,0.0),
                        it.getStringExtra(Register.EXTRA_DATE),
                        "")
                Log.w("INSECT TO EDIT: ",insect.insect_id.toString())
                insectViewModel.vModelInsect_updateInsect(insect)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.menu_details_delete -> {
                Toast.makeText(this, "DELETE INSECt", Toast.LENGTH_SHORT).show();
                showDialog()
            }
            R.id.menu_details_edit -> {
                var replyIntent=Intent(this,Register::class.java)
                replyIntent.putExtra(EXTRA_ID,id)
                replyIntent.putExtra(EXTRA_NAME,iName)
                replyIntent.putExtra(EXTRA_TL,tl)
                replyIntent.putExtra(EXTRA_TU,tu)
                replyIntent.putExtra(EXTRA_DATE,rd)
                startActivityForResult(replyIntent,updateInsectActivityRequestCode)
                Toast.makeText(this, "EDIT INSECT", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setData(){
        id_ai_tvInsectName.setText(iName)
        id_ai_tvUInf.setText(tl.toString()+" "+getString(R.string.grados_centigrados))
        id_ai_tvUsup.setText(tu.toString()+" "+getString(R.string.grados_centigrados))
        id_ai_tvFechaReg.setText(rd)
    }
    fun navigateToGraphic(v:View){
        var intent=Intent(this,ChartActivity::class.java)
        intent.putExtra(ChartActivity.EXTRA_REGISTRATION_DATE,rd)
        intent.putExtra(ChartActivity.EXTRA_TL,tl)
        intent.putExtra(ChartActivity.EXTRA_TU,tu)
        startActivity(intent)
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(this@InsectDetailsActivity)

        builder.setTitle(getString(R.string.confirm_yourAction))
        builder.setMessage(getString(R.string.message_of_deleteConfirm))
        builder.setPositiveButton(getString(R.string.yes)){ dialog, wich->
            var replyIntent=Intent()
            replyIntent.putExtra(EXTRA_INSECT_ID,id)
            setResult(Activity.RESULT_OK,replyIntent)
            finish()
        }
        builder.setNegativeButton(getString(R.string.no)){ dialog, wich->

        }
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }
}
