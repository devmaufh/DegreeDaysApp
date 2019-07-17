package com.devmaufh.degreedaysapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmaufh.degreedaysapp.Adapters.RecyclerInsectsAdapter
import com.devmaufh.degreedaysapp.Database_kt.InsectsRoomDatabase
import com.devmaufh.degreedaysapp.Entities.Insect
import com.devmaufh.degreedaysapp.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var db: InsectsRoomDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        home_bottomBar.replaceMenu(R.menu.menu_h)

        val recyclerView=findViewById<RecyclerView>(R.id.home_recyclerView)
        val adapter=RecyclerInsectsAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
        adapter.setInsectList(testList())
    }

    fun navigateToRegister(v:View){
        startActivity(Intent(this,Register::class.java))
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
}