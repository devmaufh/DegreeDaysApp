package com.devmaufh.degreedaysapp;

import android.os.Bundle;
import android.view.MenuItem;

import com.devmaufh.degreedaysapp.Fragments.HomeFragment;
import com.devmaufh.degreedaysapp.Fragments.ListFragment;
import com.devmaufh.degreedaysapp.Fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        setFragment(new HomeFragment());
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment=null;
                switch (menuItem.getItemId()){
                    case R.id.homemenu_home:
                        fragment=new HomeFragment();break;
                    case R.id.homemenu_search:
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
