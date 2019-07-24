package com.devmaufh.degreedaysapp.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devmaufh.degreedaysapp.Database_kt.InsectsRepository
import com.devmaufh.degreedaysapp.Database_kt.InsectsRoomDatabase
import com.devmaufh.degreedaysapp.Entities.Insect
import com.devmaufh.degreedaysapp.R
import kotlinx.android.synthetic.main.activity_registro.*

class Register: AppCompatActivity(){
    var name:String=""
    var date:String=""
    var tu:Double=0.0
    var tl:Double=0.0
    var id:Int=0
    companion object{
        val EXTRA_ID = "extra_id"
        val EXTRA_NAME="extra_name"
        val EXTRA_DATE="extra_date"
        val EXTRA_TL="extra_tl"
        val EXTRA_TU="extra_tu"
        val EXTRA_DEFAULT=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val intent=intent
        if( intent!=null&&
                intent.hasExtra(EXTRA_ID)&&
                intent.hasExtra(EXTRA_NAME) &&
                intent.hasExtra(EXTRA_DATE) &&
                intent.hasExtra(EXTRA_TL)&&
                intent.hasExtra(EXTRA_TU)
        ){
            id=intent.getIntExtra(EXTRA_ID,0)
            name=intent.getStringExtra(EXTRA_NAME)
            date=intent.getStringExtra(EXTRA_DATE)
            tu=intent.getDoubleExtra(EXTRA_TU,0.0)
            tl=intent.getDoubleExtra(EXTRA_TL,0.0)

            fr_edName.setText(name)
            fr_edDate.setText(date)
            fr_edUS.setText(tu.toString())
            fr_edUF.setText(tl.toString())

            Log.w("REGISTRO - EDITAR : ",id.toString())
        }else{
            listenersForEditTexts()
            Log.w("REGISTRO - NUEVO : ","NUEVO ")

        }
        fr_aceptar.setOnClickListener {
            if(validateFields()){
                val replyIntent= Intent()
                replyIntent.putExtra(EXTRA_ID,id)
                replyIntent.putExtra(EXTRA_NAME,fr_edName.text.toString())
                replyIntent.putExtra(EXTRA_DATE,fr_edDate.text.toString())
                replyIntent.putExtra(EXTRA_TU,fr_edUS.text.toString().toDouble())
                replyIntent.putExtra(EXTRA_TL,fr_edUF.text.toString().toDouble())
                setResult(Activity.RESULT_OK,replyIntent)
                finish()
            }
        }
    }

    fun validateFields():Boolean{
        if(!fr_edName.text.toString().isEmpty()){
            if(fr_edName.text.toString().length<40){
                if(!fr_edDate.text.toString().isEmpty()){
                    if(!fr_edUS.text.toString().isEmpty()) {
                        if (!fr_edUF.text.toString().isEmpty()) {
                            if(fr_edUS.text.toString().toDouble()>fr_edUF.text.toString().toDouble()) {
                                Toast.makeText(this, "Campos correctos", Toast.LENGTH_SHORT).show()
                                return true
                              //  insertInsect(Insect(0,fr_edName.text.toString(),fr_edUS.text.toString().toDouble(),fr_edUF.text.toString().toDouble(),fr_edDate.text.toString(),""))

                            }else{
                                Toast.makeText(this, getString(R.string.error_umbralmayor), Toast.LENGTH_SHORT).show();
                                fr_edUS.requestFocus()
                                fr_tiLayoutUS.error="Este umbral debe ser mayor que el umbral inferior"
                            }
                        }else{
                            fr_edUF.requestFocus()
                            fr_tiLayoutUF.error="Tienes que ingresar el umbral inferior"
                        }
                    }else{
                        fr_edUS.requestFocus()
                        fr_tiLayoutUS.error="Tienes que ingresar el umbral superior"
                    }
                }else{
                    fr_edDate.requestFocus()
                    fr_tiLayoutDate.error="No puedes dejar la fecha vacía"
                    //Abrir calendario
                }
            }else{
                fr_edName.requestFocus()
                fr_tiLayoutName.error="No puedes exceder los 40 caracteres"
            }
        }else{
            fr_edName.requestFocus()
            fr_tiLayoutName.error="Tienes que ingresar un nombre"
        }
        return false

    }
    //End function
    fun listenersForEditTexts(){
        fr_edName.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                fr_tiLayoutName.error=null
            }
        })
        fr_edDate.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                fr_tiLayoutDate.error=null
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        fr_edUS.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                fr_tiLayoutUS.error=null
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        fr_edUF.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                fr_tiLayoutUF.error=null
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }
    suspend fun insertInsect(insect:Insect){
       // val db=InsectsRoomDatabase.getDatabase(this)
        //val repo=InsectsRepository(db.insectDao())
        //repo.insert_Insect(insect)
        //Log.w("DATABASE ACTION : "," Inserted")
    }
}