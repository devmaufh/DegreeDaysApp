package com.devmaufh.degreedaysapp.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.devmaufh.degreedaysapp.API.VolleySingleton
import com.devmaufh.degreedaysapp.R
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUp : AppCompatActivity() {
    var Uemail:String=""
    var Uname:String=""
    var password:String=""
    var cPassword:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }
    fun getFields(){
        Uemail=su_edEmail.text.toString()
        Uname=su_edName.text.toString()
        password=su_edPass.text.toString()
        cPassword=su_edPassC.text.toString()
    }
    fun register(v:View){
        getFields()
        if(Uemail.isEmpty() || Uname.isEmpty() || password.isEmpty() || cPassword.isEmpty()){
            Snackbar.make(v,"Incorrect information",Snackbar.LENGTH_LONG).show()
        }else{
            var jsonObject=JSONObject()
            jsonObject.put("email",Uemail)
            jsonObject.put("name",Uname)
            jsonObject.put("password",password)
            sendRequest(jsonObject)
        }
    }
    fun sendRequest(jsonObject: JSONObject){
        val url="${AdditionalMethods.SERVER_NAME}register.php"

        val jsonObjectRequest=JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                Response.Listener { response->
                    if(response["status"]==1){
                        Toast.makeText(this, "Registro cool", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, ""+response["user"], Toast.LENGTH_SHORT).show();
                        saveOnPreferences(true,response["user"].toString())
                        startActivity(Intent(this,HomeActivity::class.java))
                    }
                },
                Response.ErrorListener { error->
                    Log.w("Send request: ","${error.message}")
                    Toast.makeText(this, "Error: try it later", Toast.LENGTH_SHORT).show();
                }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun saveOnPreferences(value:Boolean,user:String){
        val sharedPref: SharedPreferences =getSharedPreferences(AdditionalMethods.PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putBoolean(AdditionalMethods.SESSION_STATUS,value)
        editor.putString(AdditionalMethods.USER_NAME,user)
        editor.commit()
    }
}