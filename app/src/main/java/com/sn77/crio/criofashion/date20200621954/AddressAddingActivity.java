package com.sn77.crio.criofashion.date20200621954;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressAddingActivity extends AppCompatActivity {


    String message="";
    String area;
    EditText pinCode,addressLine1,addressLine2,city,state,landmark,name,ph_number;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_adding);

        addressLine1=findViewById(R.id.addressLine1);
        addressLine2=findViewById(R.id.addressLine2);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        landmark=findViewById(R.id.landMark);
        name=findViewById(R.id.name);
        ph_number=findViewById(R.id.phoneNumber);

        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        mRootRef=FirebaseDatabase.getInstance().getReference();

       pinCode= findViewById(R.id.postalCode);




       pinCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){

                    if (pinCode.getText().length()!=6){
                        pinCode.setError("Pin code must be 6 character");
                    }

                    else {

                        String zipcode=pinCode.getText().toString();
                        findArea(zipcode);
                    }
                 //   Toast.makeText(AddressAddingActivity.this,"done", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void findArea(String zip)
    {
        final Geocoder geocoder = new Geocoder(getApplicationContext());
        String zipcode=zip;
        try {
            List<Address> addresses = geocoder.getFromLocationName(zipcode, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                address.getLocality();//one use full
                area= address.getSubAdminArea();//two usefull
                message = String.format("Latitude: %f, Longitude: %f", address.getLatitude(), address.getLongitude());
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                state.setText(address.getAdminArea());
                city.setText(address.getLocality());

                Log.i("Lat & Long",message);


            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(getApplicationContext(), "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            // handle exception
        }



    }

    public void addAddressToDatabase(View view) {

        if (!addressLine1.getText().toString().equals("")|! addressLine2.getText().toString().equals("") | !city.getText().toString().equals("") | !state.getText().toString().equals("")|
                !landmark.getText().toString().equals("")| !pinCode.getText().toString().equals("")| !name.getText().toString().equals("")| !ph_number.getText().toString().equals(""))
        {

            DatabaseReference pushRef=mRootRef.child("users").child(mCurrentUser.getUid()).child("addresses").push();
            String pushKey=pushRef.getKey();

            String pathRef="users/"+mCurrentUser.getUid()+"addresses";



            Map addressHashMap=new HashMap();
            addressHashMap.put("addressLine1",addressLine1.getText().toString());
            addressHashMap.put("addressLine2",addressLine2.getText().toString());
            addressHashMap.put("city",city.getText().toString());
            addressHashMap.put("state",state.getText().toString());
            addressHashMap.put("pin_code",pinCode.getText().toString());
            addressHashMap.put("landmark",landmark.getText().toString());
            addressHashMap.put("persion_name",name.getText().toString());
            addressHashMap.put("phone_number",ph_number.getText().toString());

           // Map pathRefMap = new HashMap();
          //  pathRefMap.put(pathRef+"/"+pushKey,pathRefMap);

            mRootRef.child("users").child(mCurrentUser.getUid()).child("addresses").child(pushKey).updateChildren(addressHashMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError==null){

                        Toast.makeText(AddressAddingActivity.this, " Recorded Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActionPage.class);
                        startActivity(intent);
                    }
                }
            });



           // Toast.makeText(this, addressLine1.getText().toString(), Toast.LENGTH_SHORT).show();

        }
        else {
            if (addressLine1.getText().toString().equals("")){
            addressLine1.setError("Can not be empty");}

            if (addressLine2.getText().toString().equals("")){
            addressLine2.setError("Can not be empty");}

            if (city.getText().toString().equals("")){
            city.setError("Can not be empty");}

            if (state.getText().toString().equals("")){
          state.setError("Can not be empty");}

            if (pinCode.getText().toString().equals("")){
            pinCode.setError("Can not be empty");}

            if (landmark.getText().toString().equals("")){
            landmark.setError("Can not be empty");}

            if (name.getText().toString().equals("")){
            name.setError("Can not be empty");}

            if (ph_number.getText().toString().equals("")){
            ph_number.setError("Can not be empty");}

        }




    }
}


/*
1.getLocality() -->Kharagpur
2.getAdminArea() --> West Bengal
3.getSubAdminArea()--> West Midnapore
make sure to check null :)*/