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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmaufh.degreedaysapp.Adapters.RecyclerInsectsAdapter
import com.devmaufh.degreedaysapp.Database_kt.InsectsRoomDatabase
import com.devmaufh.degreedaysapp.Database_kt.InsectsViewModel
import com.devmaufh.degreedaysapp.Entities.Insect
import com.devmaufh.degreedaysapp.R
import com.devmaufh.degreedaysapp.Utilities.BottomNavigationDrawerFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var insectViewModel: InsectsViewModel
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
        insectViewModel=ViewModelProviders.of(this).get(InsectsViewModel::class.java)
        val recyclerView=findViewById<RecyclerView>(R.id.home_recyclerView)
        val adapter=RecyclerInsectsAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        insectViewModel.allInsects.observe(this, Observer { insects->
            insects?.let {
                adapter.setInsectList(it)
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
                        ""
                )
                insectViewModel.vModelInsects_insert(insect)
            }
        }
        if(requestCode== deleteInsectActivityRequestCode && resultCode==Activity.RESULT_OK){
            data?.let { intent->
                val id=intent.getIntExtra(InsectDetailsActivity.EXTRA_INSECT_ID,0)
                insectViewModel.vModelInsert_deleteById(id)
            }
        }
        if(requestCode== updateInsectActivityRequestCode && resultCode==Activity.RESULT_OK){
            data?.let{
                val insect=Insect(it.getIntExtra(Register.EXTRA_ID,0),
                        it.getStringExtra(Register.EXTRA_NAME),
                        it.getDoubleExtra(Register.EXTRA_TU,0.0),
                        it.getDoubleExtra(Register.EXTRA_TL,0.0),
                        it.getStringExtra(Register.EXTRA_DATE),
                        ""
                )
                Log.w("HOME ACTIVITY: ", insect.insect_id.toString())
                //insectViewModel.vModelInsect_updateInsect(insect)
            }
        }
    }
    fun testList():List<Insect>{
        var lista= ArrayList<Insect>()
        lista.add(Insect(1,"Juan daniel",25.0,25.0,"ASies",""))
        lista.add(Insect(2,"Jose didire",25.0,25.0,"ASies",""))
        lista.add(Insect(3,"Marco antonio",25.0,25.0,"ASies",""))
        lista.add(Insect(4,"Alan renato",25.0,25.0,"ASies",""))
        lista.add(Insect(5,"El porfi loco",25.0,25.0,"ASies",""))
        lista.add(Insect(1,"Juan daniel",25.0,25.0,"ASies",""))
        lista.add(Insect(2,"Jose didire",25.0,25.0,"ASies",""))
        lista.add(Insect(3,"Marco antonio",25.0,25.0,"ASies",""))
        lista.add(Insect(4,"Alan renato",25.0,25.0,"ASies",""))
        lista.add(Insect(5,"El porfi loco",25.0,25.0,"ASies",""))
        return lista
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

}