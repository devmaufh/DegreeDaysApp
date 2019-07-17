package com.devmaufh.degreedaysapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener

import com.android.volley.toolbox.JsonObjectRequest
import com.devmaufh.degreedaysapp.API.VolleySingleton
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods.SERVER_NAME
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import android.os.StrictMode
import android.view.WindowManager
import com.devmaufh.degreedaysapp.R


class Login : AppCompatActivity() {
    val url="${SERVER_NAME}login.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide()
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        startActivity(Intent(this,HomeActivity::class.java))

    }

    fun signIn(v: View){
        if(login_ed_username.text.toString().isEmpty()||login_ed_pass.text.toString().isEmpty()){
            Snackbar.make(v,getString(R.string.check_credentials),Snackbar.LENGTH_SHORT).show()
            login_ed_username.requestFocus()
        }else{
            val params=HashMap<String,String>()
            params["email"]=login_ed_username.text.toString()
            params["password"]=login_ed_pass.text.toString()
            val jsonObject=JSONObject(params)
            sendRequest(jsonObject)
        }
    }
    fun signUp(v:View){

    }
    fun sendRequest(jsonObject: JSONObject){
        val request=JsonObjectRequest(Request.Method.POST,url, jsonObject,
                Listener { response->
                    if(response["status"]==0){
                        Toast.makeText(this, "${response["msg"]}", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Usuario correcto", Toast.LENGTH_SHORT).show()
                      //  PreferencesApp.prefs.name="Nombre en sharede"
                    }
                },
                Response.ErrorListener {error->
                    Log.w("RESPONSE: ",error.message)
                    Toast.makeText(this, "Este es un perro error ):${error}", Toast.LENGTH_SHORT).show()
        })
        request.retryPolicy=DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0,
                1f
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }
}
