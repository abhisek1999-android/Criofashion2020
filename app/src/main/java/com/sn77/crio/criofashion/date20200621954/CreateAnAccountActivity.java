package com.sn77.crio.criofashion.date20200621954;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class CreateAnAccountActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =77 ;
    private TextInputLayout userId_Email;
    private TextInputLayout userPassword;
    private TextInputLayout userPasswordConf;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button sign_inButton;

    private Button next_Button,signInWithGoogle,signin_withEmail;
    String userId,user_Password,passwordConf;

    GoogleSignInClient mGoogleSignInClient;

    RelativeLayout emailLoginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_an_account);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        userId_Email=findViewById(R.id.user_id);
        userPassword=findViewById(R.id.user_password);
        userPasswordConf=findViewById(R.id.user_password_conf);

        sign_inButton=findViewById(R.id.signin_button);

        signInWithGoogle=findViewById(R.id.signIn_withGoogle);

        signin_withEmail=findViewById(R.id.signIn_withEmail);

        emailLoginLayout=findViewById(R.id.emailLoginLayout);

        // Build a GoogleSignInClient with the options specified by gso.

// google sign in setp 1: ,all are copied from firebase google sign in docs.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //step 2
        mGoogleSignInClient  = GoogleSignIn.getClient(this, gso);


     /*   button.setOnTouchListener(new View.OnTouchListener()           expremental..
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {



                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    v.setBackgroundColor(Color.parseColor("#000000"));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    v.setBackgroundColor(Color.parseColor("#ffffff"));
                }


                return false;
            }
        });*///expremental

        signInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });




    }
//step3
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);//rc_sign_in ta define korte hobe
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriatelyLog.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
//step 4
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's informationLog.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            updateUI(null);//ata create korte hobe
                        }

                        // ...
                    }
                });
    }
//last step
    private void updateUI(FirebaseUser user) {
        Intent intent=new Intent(getApplicationContext(),UserDetailsActivity.class);
        startActivity(intent);

    }

// sign  up with email process ........................................
    public  void signUpClicked(View view){


        userId=userId_Email.getEditText().getText().toString();

        user_Password=userPassword.getEditText().getText().toString();

        passwordConf=userPasswordConf.getEditText().getText().toString();

        sign_inButton=findViewById(R.id.signin_button);

        next_Button=findViewById(R.id.next_button);


        if (user_Password.equals(passwordConf)) {

            mAuth.createUserWithEmailAndPassword(userId,user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        currentUser=mAuth.getCurrentUser();
                        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateAnAccountActivity.this, "Verification Email sent!", Toast.LENGTH_SHORT).show();
                                    userId_Email.setEnabled(false);
                                    userPassword.setEnabled(false);
                                    userPasswordConf.setEnabled(false);
                                    sign_inButton.setVisibility(View.GONE);
                                    next_Button.setVisibility(View.VISIBLE);

                                }
                                else {

                                    userId_Email.setEnabled(true);
                                    userPassword.setEnabled(true);
                                    userPasswordConf.setEnabled(true);
                                    sign_inButton.setVisibility(View.VISIBLE);
                                    next_Button.setVisibility(View.GONE);
                                    Toast.makeText(CreateAnAccountActivity.this, "Some Error Occoured!", Toast.LENGTH_SHORT).show();


                                }

                            }
                        });

                    }


                }
            });
        }

        else {

            Toast.makeText(this, "PassWord does not match", Toast.LENGTH_SHORT).show();
        }

    }

    public void nextClicked(View view) {


        userId = userId_Email.getEditText().getText().toString();
        user_Password = userPassword.getEditText().getText().toString();
        currentUser = mAuth.getCurrentUser();



        mAuth.signInWithEmailAndPassword(userId,user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    if (currentUser.isEmailVerified()){

                        Intent intent=new Intent(getApplicationContext(),UserDetailsActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(CreateAnAccountActivity.this, "Not Verified", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


    }

    public void signInEmailButtonClicked(View view) {

        signin_withEmail.setVisibility(View.GONE);
        signInWithGoogle.setVisibility(View.GONE);
        emailLoginLayout.setVisibility(View.VISIBLE);

    }
}