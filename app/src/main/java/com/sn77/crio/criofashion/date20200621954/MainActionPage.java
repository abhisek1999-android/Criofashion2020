package com.sn77.crio.criofashion.date20200621954;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActionPage extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action_page);







        bottomNavigationView  =(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.post(new Runnable() {
            @Override
            public void run() {
                int height = (int) bottomNavigationView.getMeasuredHeight();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener( navListener);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home_Fragment()).commit();

        }




    }

   private  BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
       @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


           Fragment selectedFragment = null;
           switch (menuItem.getItemId()) {
               case R.id.nav_home:
                   selectedFragment=new Home_Fragment();

                   break;

               case R.id.nav_products:
                   selectedFragment=new Product_Fragment();
                   break;
               case R.id.nav_growth:
                   selectedFragment=new Growth_Fragment();
                   break;

               case R.id.nav_account:
                   selectedFragment=new User_Account_Fragment();
                   break;

           }
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   selectedFragment).commit();
           return true;

       }
   };
}
