package com.sn77.crio.criofashion.date20200621954;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class Home_Fragment extends Fragment {

    private View mView;
    private ImageButton cartButton;
    private ImageButton notificationButton;
    private ImageButton menuButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.home_fragment,container,false);




        //for fragment we have to use ((AppCompatActivity)getActivity()) this for set & get support ActionBar
        Toolbar mToolBar = mView.findViewById(R.id.homeAppBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Crio Fashion");

       LayoutInflater inflater1= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view=inflater1.inflate(R.layout.home_custom_appbar,null);
        actionBar.setCustomView(action_bar_view);

        cartButton=mView.findViewById(R.id.cartButton);
        notificationButton=mView.findViewById(R.id.notificationButton);
        menuButton=mView.findViewById(R.id.menuButton);


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             intentSettings();

            }
        });


        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "No items!", Toast.LENGTH_SHORT).show();
            }
        });


         return mView;

    }

    private void intentSettings() {

        Intent intent=new Intent(getContext(),SettingsMenuActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_in,R.anim.hold);
    }
}


/*

For random image choosing


*DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
DatabaseReference imageUrlsRef = rootRef.child("imageUrls");
ValueEventListener valueEventListener = new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<String> urlList = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            String url = ds.getKey();
            urlList.add(url);
        }

        int urlCount = urlList.size();
        int randomNumber = new Random().nextInt(urlCount);
        List<String> randomUrlList = new ArrayList<>();
        for (int i=1; i<=5; i++) {
            randomUrlList.add(urlList.get(randomNumber));
            //Set image to ImageView
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {}
};
imageUrlsRef.addListenerForSingleValueEvent(valueEventListener);
*/