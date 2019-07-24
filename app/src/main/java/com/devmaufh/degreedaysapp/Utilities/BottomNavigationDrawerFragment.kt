package com.devmaufh.degreedaysapp.Utilities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.devmaufh.degreedaysapp.Activities.Login
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottomsheet.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment(){
    private lateinit var insectViewModel: InsectsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        insectViewModel= ViewModelProviders.of(this).get(InsectsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_bottomsheet,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigation_view.setNavigationItemSelectedListener {menuItem ->
            when(menuItem.itemId){
                R.id.homemenu_search-> {
                   context!!.toast("CLICK NUBE")
                    true
                }
                R.id.homemenu_home->{
                    insectViewModel.vModel_deleteAll()
                    context!!.toast("CLICK remove all")
                    true
                }
                R.id.homemenu_perfil->{
                    val sharedPref: SharedPreferences =context!!.
                            getSharedPreferences(AdditionalMethods.PREFERENCES_NAME,
                                    Context.MODE_PRIVATE)
                    val editor=sharedPref.edit()
                    editor.clear().commit()
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