package com.sn77.crio.criofashion.date20200621954;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.borutsky.neumorphism.NeumorphicFrameLayout;
import com.gpfreetech.neumorphism.Neumorphism;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



    NeumorphicFrameLayout button =findViewById(R.id.button);

    button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
       /* new Neumorphism(this)
                //set views you want to style in comma seperated format list
                .setViews(button,editText)
        	   .parentColor(Color.parseColor("#ffffff")) // madnetory color in int
                .controlColor(Color.parseColor("#bdbdbd")) // madnetory color in int
                .sharpEdges(false) // optional
                .withCurvedSurface() // optional
                 // optional
                //optional
                .withRoundedCorners(20)
                .build();*/

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent =new Intent(getApplicationContext(),MainActionPage.class);
        startActivity(intent);
      }
    });

  }



}