package com.sn77.crio.criofashion.date20200621954;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductDetailsPage extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_page);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Banners");
        list=new ArrayList<>();
    }

    public void buttonClicked(View view) {

   /*    databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                  //  list.add(childSnapshot.child("url").getValue().toString());
                    Toast.makeText(getApplicationContext(),dataSnapshot.child("url").getValue().toString(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (dataSnapshot.exists()){

            list.clear();
        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {



            String urls=childSnapshot.getValue(String.class);
            list.add(urls);


        }
        }
        Toast.makeText(ProductDetailsPage.this, list.get(1), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});


    }
}