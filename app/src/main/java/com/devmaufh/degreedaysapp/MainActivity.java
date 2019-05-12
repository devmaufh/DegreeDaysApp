package com.devmaufh.degreedaysapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.devmaufh.degreedaysapp.Fragments.HomeFragment;
import com.devmaufh.degreedaysapp.Fragments.ListFragment;
import com.devmaufh.degreedaysapp.Fragments.PerfilFragment;
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=getSharedPreferences(AdditionalMethods.PREFERENCES_THEME,MODE_PRIVATE);
        testPreferencesTheme(AdditionalMethods.BLACK+"");
        setTheme(AdditionalMethods.getTheme(preferences));
        if(TextUtils.isEmpty(preferences.getString("theme",""))){
            Toast.makeText(this, "TEMA VACIO", Toast.LENGTH_SHORT).show();
            testPreferencesTheme(AdditionalMethods.SEA+"");
        }else{
            Toast.makeText(this, "EL TEMA ES: "+preferences.getString("theme",""), Toast.LENGTH_SHORT).show();
        }
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
    private void testPreferencesTheme(String theme){
        SharedPreferences.Editor edit=preferences.edit();
        edit.putString("theme",theme);
        edit.commit();
        Toast.makeText(this, "Cambiando tema en preferences", Toast.LENGTH_SHORT).show();
    }
}
