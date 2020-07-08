package com.sn77.crio.criofashion.date20200621954;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class User_Account_Fragment extends Fragment {

    FirebaseAuth mAuth;
    private View mView;
    private FirebaseUser mCurrentUser;

    private RelativeLayout createOrLoginLayout;

    private RelativeLayout afterLoginView;

    private Button loginButton;

    private Button createAccountButon;

    private CardView logoutCardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.user_account_fragment,container,false);

        mAuth=FirebaseAuth.getInstance();

        loginButton=mView.findViewById(R.id.loginPageIntent);

        logoutCardView=mView.findViewById(R.id.logout_user);

        createAccountButon=mView.findViewById(R.id.signupPageIntent);

        mCurrentUser=mAuth.getCurrentUser();

        createOrLoginLayout=mView.findViewById(R.id.createOrLoginLayout);
        afterLoginView=mView.findViewById(R.id.afterLoginView);


        if (mCurrentUser==null){

        createOrLoginLayout.setVisibility(View.VISIBLE);
        afterLoginView.setVisibility(View.GONE);

        }

        else {

            createOrLoginLayout.setVisibility(View.GONE);
            afterLoginView.setVisibility(View.VISIBLE);

        }




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),UserLoginActivity.class);
                startActivity(intent);
            }
        });

        createAccountButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(getContext(),CreateAnAccountActivity.class);
              startActivity(intent);
            }
        });

        logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                mCurrentUser=null;

            }
        });

        return  mView;
    }


}
