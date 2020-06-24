package com.sn77.crio.criofashion.date20200621954;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActionPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action_page);


        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
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

               case R.id.nav_feedback:
                   selectedFragment=new FeedBack_Fragment();
                   break;

           }
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   selectedFragment).commit();
           return true;

       }
   };
}
