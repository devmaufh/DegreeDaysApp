package com.devmaufh.degreedaysapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.devmaufh.degreedaysapp.Fragments.HomeFragment;
import com.devmaufh.degreedaysapp.Fragments.ListFragment;
import com.devmaufh.degreedaysapp.Fragments.PerfilFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment=null;
                switch (menuItem.getItemId()){
                    case R.id.homemenu_home:
                        fragment=new HomeFragment();break;
                    case R.id.homemenu_registros:
                        fragment= new ListFragment();break;
                    case R.id.homemenu_perfil:
                        fragment=new PerfilFragment();break;
                }
                setFragment(fragment);
                return true;
            }
        });
    }
    private void bindUI(){
        bottomNav=(BottomNavigationView)findViewById(R.id.home_BottomNav);
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home,fragment);
        fragmentTransaction.commit();
    }
}
