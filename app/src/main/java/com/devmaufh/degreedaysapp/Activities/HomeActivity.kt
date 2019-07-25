package com.devmaufh.degreedaysapp.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.devmaufh.degreedaysapp.API.VolleySingleton
import com.devmaufh.degreedaysapp.Adapters.RecyclerInsectsAdapter
import com.devmaufh.degreedaysapp.Database_kt.InsectsRoomDatabase
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.Entities.Insect
import com.devmaufh.degreedaysapp.R
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods
import com.devmaufh.degreedaysapp.Utilities.BottomNavigationDrawerFragment
import com.devmaufh.degreedaysapp.Utilities.Utilities
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var insectViewModel: InsectsViewModel
    lateinit var sharedPref: SharedPreferences
    private var db: InsectsRoomDatabase? = null
    companion object {
        const val newInsectActivityRequestCode = 1
        const val deleteInsectActivityRequestCode=2
        const val updateInsectActivityRequestCode=3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_bottomBar)
        sharedPref=getSharedPreferences(AdditionalMethods.PREFERENCES_NAME, Context.MODE_PRIVATE)

        insectViewModel=ViewModelProviders.of(this).get(InsectsViewModel::class.java)
        val recyclerView=findViewById<RecyclerView>(R.id.home_recyclerView)
        val adapter=RecyclerInsectsAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        insectViewModel.allInsects.observe(this, Observer { insects->
            insects?.let {
                adapter.setInsectList(it)
                var insectsArray=JSONArray()
                var finalJson = JSONObject()
                it.forEach { insects->
                    Log.w("INSECTS :'v",insects.insect_id.toString())
                    insectsArray.put(Utilities.parseInsectToJson(insects))
                }
                finalJson.put("email",sharedPref.getString(AdditionalMethods.USER_NAME,""))
                finalJson.put("password",sharedPref.getString(AdditionalMethods.USER_PASS,""))
                finalJson.put("insects",insectsArray)
                Log.w("FINAL JSON: ",finalJson.toString())
                sendRequest("${AdditionalMethods.SERVER_NAME}register_insects.php",finalJson)
            }
        })
    }

    fun navigateToRegister(v:View){
        //startActivity(Intent(this,Register::class.java))
        startActivityForResult(Intent(this,Register::class.java), newInsectActivityRequestCode)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== newInsectActivityRequestCode && resultCode== Activity.RESULT_OK){
            data?.let {
                val insect=Insect(0,
                        it.getStringExtra(Register.EXTRA_NAME),
                        it.getDoubleExtra(Register.EXTRA_TU,0.0),
                        it.getDoubleExtra(Register.EXTRA_TL,0.0),
                        it.getStringExtra(Register.EXTRA_DATE),
                        sharedPref.getString(AdditionalMethods.USER_NAME,"")
                )
                insectViewModel.vModelInsects_insert(insect)
            }
        }
        if(requestCode== deleteInsectActivityRequestCode && resultCode==Activity.RESULT_OK){
            data?.let { intent->
                val id=intent.getIntExtra(InsectDetailsActivity.EXTRA_INSECT_ID,0)
                insectViewModel.vModelInsert_deleteById(id)
                var jsonObject=JSONObject()
                jsonObject.put("email",sharedPref.getString(AdditionalMethods.USER_NAME,""))
                jsonObject.put("password",sharedPref.getString(AdditionalMethods.USER_PASS,""))
                jsonObject.put("delete",1)
                jsonObject.put("insect_id",id)
                Log.w("BORRAR",jsonObject.toString())
                sendRequest("${AdditionalMethods.SERVER_NAME}delete.php",jsonObject)
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.menu_h,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.menu_settings->{
                val bottonNavigationDrawerFragment=BottomNavigationDrawerFragment()
                bottonNavigationDrawerFragment.show(supportFragmentManager,bottonNavigationDrawerFragment.tag)

            }
            else->{
            }
        }
        return true
    }

    fun sendRequest(url:String,jsonObject: JSONObject){
        Toast.makeText(this, getString(R.string.syn_in_cloud), Toast.LENGTH_SHORT).show();
        val jsonObjectRequest=JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                Response.Listener { response->
                    Log.w("Response service: ", response.toString())
                },
                Response.ErrorListener { error->
                    Log.w("ErrorListener", error.toString())
                    Log.w("ErrorListener",error.message.toString())
                }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}