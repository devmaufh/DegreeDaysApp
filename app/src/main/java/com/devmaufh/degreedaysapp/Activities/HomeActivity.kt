package com.devmaufh.degreedaysapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.devmaufh.degreedaysapp.Database_kt.InsectsRoomDatabase
import com.devmaufh.degreedaysapp.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var db: InsectsRoomDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        home_bottomBar.replaceMenu(R.menu.menu_h)
        db= InsectsRoomDatabase.getDatabase(this)
    }

    fun navigateToRegister(v:View){
        startActivity(Intent(this,Register::class.java))
    }
}