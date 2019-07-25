package com.devmaufh.degreedaysapp.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import android.os.StrictMode
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.Entities.IDate
import com.devmaufh.degreedaysapp.Entities.Insect
import com.devmaufh.degreedaysapp.R
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods.*
import org.json.JSONArray


class Login : AppCompatActivity() {
    val url="${SERVER_NAME}login.php"
    private lateinit var insectViewModel: InsectsViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPref:SharedPreferences=getSharedPreferences(AdditionalMethods.PREFERENCES_NAME, Context.MODE_PRIVATE)
        if(sharedPref.getBoolean(SESSION_STATUS,false)){
            startActivity(Intent(this,HomeActivity::class.java))
        }

        insectViewModel= ViewModelProviders.of(this).get(InsectsViewModel::class.java)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide()
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

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
        startActivity(Intent(this,SignUp::class.java))
    }
    fun sendRequest(jsonObject: JSONObject){
        Log.w("URL-REQUEST",url)
        val request=JsonObjectRequest(Request.Method.POST,url, jsonObject,
                Listener { response->
                    if(response["status"]==0){
                        Toast.makeText(this, "${response["msg"]}", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Usuario correcto", Toast.LENGTH_SHORT).show()
                        val array=response.getJSONArray("data")
                        for(i in 0 until array.length()){
                            val insect=Insect(
                                    array.getJSONObject(i)["id_insect"].toString().toInt(),
                                    array.getJSONObject(i)["name"].toString(),
                                    array.getJSONObject(i)["tu"].toString().toDouble(),
                                    array.getJSONObject(i)["tl"].toString().toDouble(),
                                    array.getJSONObject(i)["registration_date"].toString(),
                                    ""
                                    )
                            insectViewModel.vModelInsects_insert(insect)
                        }
                        saveOnPreferences(true,login_ed_username.text.toString(),login_ed_pass.text.toString())
                        populateDates()
                        startActivity(Intent(this,HomeActivity::class.java))
                    }
                },
                Response.ErrorListener {error->
                    Log.w("SEND REQUEST:  ","${error.toString()}")
                    Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_SHORT).show()
        })
        request.retryPolicy=DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0,
                1f
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }
    fun saveOnPreferences(value:Boolean,user:String,pass:String){
        val sharedPref:SharedPreferences=getSharedPreferences(AdditionalMethods.PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor=sharedPref.edit()
        editor.putBoolean(SESSION_STATUS,value)
        editor.putString(USER_NAME,user)
        editor.putString(USER_PASS,pass)
        editor.commit()
    }
    fun populateDates(){
        insectViewModel.vModelDates_deleteAll()
        for(i in 0 until 30){
            Log.w("POPULATE DATABASE: ","$i")
            val rnd1=( 30 .. 40 ).random()
            val rnd2=( 5 .. 29 ).random()
            val iDate= IDate("$i-07-2019",rnd1.toDouble(),rnd2.toDouble())
            insectViewModel.vModelDates_insert(iDate)
        }
    }

}
