package com.devmaufh.degreedaysapp.Utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.devmaufh.degreedaysapp.API.VolleySingleton
import com.devmaufh.degreedaysapp.Activities.InsectDetailsActivity
import com.devmaufh.degreedaysapp.Activities.Login
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottomsheet.*
import org.json.JSONObject

class BottomNavigationDrawerFragment : BottomSheetDialogFragment(){
    private lateinit var insectViewModel: InsectsViewModel
    lateinit var sharedPref: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        insectViewModel= ViewModelProviders.of(this).get(InsectsViewModel::class.java)
        sharedPref=inflater.context.getSharedPreferences(AdditionalMethods.PREFERENCES_NAME, Context.MODE_PRIVATE)

        return inflater.inflate(R.layout.fragment_bottomsheet,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigation_view.setNavigationItemSelectedListener {menuItem ->
            when(menuItem.itemId){

                R.id.homemenu_home->{
                    context!!.toast("CLICK remove all")
                    val builder = AlertDialog.Builder(context!!)
                    builder.setTitle("Confirm your action")
                    builder.setMessage("Select where you want to erase the data")
                    builder.setPositiveButton("Local"){ dialog, wich->
                        insectViewModel.vModel_deleteAll()
                    }
                    builder.setNegativeButton("Cloud"){ dialog, wich->
                        var jsonObject= JSONObject()

                        jsonObject.put("email",sharedPref.getString(AdditionalMethods.USER_NAME,""))
                        jsonObject.put("password",sharedPref.getString(AdditionalMethods.USER_PASS,""))
                        jsonObject.put("delete",4)
                        Log.w("JSON DIALOG: ",jsonObject.toString())
                        val jsonObjectRequest= JsonObjectRequest(
                                Request.Method.POST,
                                "${AdditionalMethods.SERVER_NAME}delete.php",
                                jsonObject,
                                Response.Listener { response->
                                    Log.w("Response service: ", response.toString())
                                },
                                Response.ErrorListener { error->
                                    Log.w("ErrorListener", error.toString())
                                    Log.w("ErrorListener",error.message.toString())
                                }
                        )
                        VolleySingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)
                    }
                    builder.setNeutralButton(getString(R.string.cancelacion)){dialog,wich->
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    true
                }
                R.id.homemenu_perfil->{
                    val sharedPref: SharedPreferences =context!!.
                            getSharedPreferences(AdditionalMethods.PREFERENCES_NAME,
                                    Context.MODE_PRIVATE)
                    val editor=sharedPref.edit()
                    editor.clear().commit()
                    insectViewModel.vModelDates_deleteAll()
                    insectViewModel.vModel_deleteAll()
                    startActivity(Intent(context,Login::class.java))
                    context!!.toast("CLICK profile")

                    true
                }
                else -> true
            }
        }
    }
    fun Context.toast(message: CharSequence) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 600)
        toast.show()
    }

}