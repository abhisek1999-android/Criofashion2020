package com.sn77.crio.criofashion.date20200621954;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.iid.FirebaseInstanceId;
import com.lamudi.phonefield.PhoneEditText;

import java.util.HashMap;

public class UserDetailsActivity extends AppCompatActivity {

    PhoneEditText phoneEditText;
    Button button;
    private EditText userName;
    boolean valid;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference userDataBaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        phoneEditText=findViewById(R.id.userPhoneNumber);
        userName=findViewById(R.id.userName);
        button=findViewById(R.id.addButtonClicked);
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        userDataBaseRef=FirebaseDatabase.getInstance().getReference().child("users");
        phoneEditText.setDefaultCountry("IN");
        phoneEditText.setBackgroundColor(Color.parseColor("#00000000"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               valid = true;

                if (phoneEditText.isValid()) {
                    phoneEditText.setError(null);
                } else {
                    phoneEditText.setError("Invalid");
                    valid = false;
                }
                if (valid) {
                    //Toast.makeText(UserDetailsActivity.this, "valid", Toast.LENGTH_LONG).show();
                    final String deviceToken= FirebaseInstanceId.getInstance().getToken();
                    HashMap<String,String> userHashMap=new HashMap<>();
                    userHashMap.put("user_name",userName.getText().toString());
                    userHashMap.put("user_email",mCurrentUser.getEmail());
                    userHashMap.put("phone_number",phoneEditText.getPhoneNumber());
                    userHashMap.put("device_token",deviceToken);
                    userHashMap.put("created_at", String.valueOf(ServerValue.TIMESTAMP));
                    userDataBaseRef.child(mCurrentUser.getUid()).setValue(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                            Intent intent=new Intent(getApplicationContext(),AddressAddingActivity.class);
                            startActivity(intent);
                            }

                            else {

                                Toast.makeText(UserDetailsActivity.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




                } else {
                    Toast.makeText(UserDetailsActivity.this, "In-Valid phone number!", Toast.LENGTH_LONG).show();
                }

              // Toast.makeText(UserDetailsActivity.this, phoneEditText.getPhoneNumber(), Toast.LENGTH_SHORT).show();



            }
        });
    }
}