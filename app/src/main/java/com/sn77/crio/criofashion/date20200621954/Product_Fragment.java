package com.sn77.crio.criofashion.date20200621954;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpfreetech.neumorphism.Neumorphism;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Product_Fragment extends Fragment {




    private DatabaseReference productRef;
    private DatabaseReference rootRef;
    private RecyclerView productlistRecyclerView;
    private RecyclerView horizontalRecyclerView;
    private View mView;
    private ImageButton searchButtonAppbar;

    private EditText searchItemText;
    private ImageButton searchItemButton;

    private RelativeLayout relativeSearchLayout;
    private RelativeLayout relativeSearchLayoutInner;
    private ImageButton crossButton;

    private TextView searchResultTextView;

    Animation animation,searchButtonAnimation;

    SliderView sliderView;
    private SliderAdapterExample adapter;
    ArrayList<String> listItem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.product_fragment,container,false);
        productRef= FirebaseDatabase.getInstance().getReference().child("products");
        rootRef=FirebaseDatabase.getInstance().getReference();
        listItem=new ArrayList<String>();

        productlistRecyclerView=mView.findViewById(R.id.productList);
        horizontalRecyclerView=mView.findViewById(R.id.horizontalLayout);

        relativeSearchLayout=mView.findViewById(R.id.layoutForSearch);
        relativeSearchLayoutInner=mView.findViewById(R.id.layoutForSearchInner);
        productlistRecyclerView.hasFixedSize();
        productlistRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        horizontalRecyclerView.hasFixedSize();
        horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        searchItemText=mView.findViewById(R.id.searchItem);
        searchItemButton=mView.findViewById(R.id.searchItemButton);


        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        searchButtonAnimation=AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in);

//     this portion is for sliding image..... here i use this lib -->> https://github.com/smarteist/Android-Image-Slider  an adapter and a getter setter class also created for the sliding   -->>
        sliderView=mView.findViewById(R.id.imageSlider);
        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION
        );
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        rootRef.child("Banners").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    listItem.clear();
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                        String urls=childSnapshot.getValue(String.class);
                        listItem.add(urls);
                    }
                }

                List<SliderItem> sliderItemList = new ArrayList<>();
                for (int i = 0; i < listItem.size(); i++) {
                    SliderItem sliderItem = new SliderItem();
                    sliderItem.setDescription("Slider Item " + i);
                    sliderItem.setImageUrl(listItem.get(i));
                    sliderItemList.add(sliderItem);
                }
                adapter.renewItems(sliderItemList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

// >> upto this for sliding

        //for fragment we have to use ((AppCompatActivity)getActivity()) this for set & get support ActionBar
        Toolbar mToolBar = mView.findViewById(R.id.productAppBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);

        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
     //   ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Crio Fashion");
        LayoutInflater inflater1= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view=inflater1.inflate(R.layout.product_custom_appbar,null);
        actionBar.setCustomView(action_bar_view);
      searchButtonAppbar=mView.findViewById(R.id.searchButtonAppbar);
      crossButton=mView.findViewById(R.id.crossButtonAppbar);
      searchResultTextView=mView.findViewById(R.id.serchItemTextView);

        displayItems("true");

      searchButtonAppbar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              relativeSearchLayout.setVisibility(View.VISIBLE);
              relativeSearchLayoutInner.setVisibility(View.VISIBLE);
              relativeSearchLayout.clearAnimation();
              relativeSearchLayout.setAnimation(animation);
              relativeSearchLayout.getAnimation().start();
              //horizontalRecyclerView.setVisibility(View.GONE);
              searchButtonAppbar.setVisibility(View.GONE);
              crossButton.setVisibility(View.VISIBLE);
          }
      });

      searchItemButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


             horizontalRecyclerView.setVisibility(View.GONE);

              String searchItem = searchItemText.getText().toString();
              {

                  if (!searchItem.equals("")) {

                      displayItems(searchItem);
                     // horizontalRecyclerView.setVisibility(View.GONE);
                      relativeSearchLayout.setVisibility(View.GONE);
                      searchResultTextView.setVisibility(View.VISIBLE);
                      searchResultTextView.setText("Search result for "+searchItem);
                  } else {
                      Toast.makeText(getContext(),"Please enter some Thing", Toast.LENGTH_SHORT).show();
                      relativeSearchLayout.setVisibility(View.VISIBLE);
                     // displayItems("true");
                     /* crossButton.setVisibility(View.GONE);
                      searchButtonAppbar.setVisibility(View.VISIBLE);
                      searchResultTextView.setVisibility(View.GONE);
                      relativeSearchLayout.setVisibility(View.GONE);
                      horizontalRecyclerView.setVisibility(View.VISIBLE);*/


                  }

              }

          }
      });

      crossButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //relativeSearchLayout.setVisibility(View.VISIBLE);
              horizontalRecyclerView.setVisibility(View.VISIBLE);
              crossButton.clearAnimation();
              crossButton.setAnimation(searchButtonAnimation);
              crossButton.getAnimation().start();
              relativeSearchLayout.setVisibility(View.GONE);
              searchItemText.setText("");
              searchItemText.setVisibility(View.INVISIBLE);
              crossButton.setVisibility(View.GONE);
              searchButtonAppbar.setVisibility(View.VISIBLE);
              relativeSearchLayoutInner.setVisibility(View.GONE);
              displayItems("true");


          }
      });


        return  mView;
    }

    private void displayItems(String item) {

        FirebaseRecyclerOptions<Products> options;


        if (item.equals("true")){

            options=new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(productRef,Products.class)
                    .build();

        }

        else {

            Query firebaseSearchQuery = productRef.orderByChild("name").startAt(item).endAt(item + "\uf8ff");

            options=new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(firebaseSearchQuery,Products.class)
                    .build();

        }





        FirebaseRecyclerAdapter<Products,ProductViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Products products) {

                productViewHolder.productName.setText(products.getName());
                productViewHolder.productPrice.setText(products.getPrice());


            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product_layout_design,parent,false);
                ProductViewHolder viewHolder=new ProductViewHolder(view);
                return viewHolder;



            }
        };
        productlistRecyclerView.setAdapter(firebaseRecyclerAdapter);                                                 //Recycler Aapter
        firebaseRecyclerAdapter.startListening();

//adapter for top elements.......................................................................................................................................

        FirebaseRecyclerOptions<Products> options1=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productRef,Products.class)
                .build();

        FirebaseRecyclerAdapter<Products,ProductViewHolderSmall> firebaseRecyclerAdapterSmall=new FirebaseRecyclerAdapter<Products, ProductViewHolderSmall>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolderSmall productViewHolderSmall, int i, @NonNull Products products) {

                //productViewHolderSmall.productNameSmall.setText(products.getName());

            }

            @NonNull
            @Override
            public ProductViewHolderSmall onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_single_product_layout,parent,false);
                ProductViewHolderSmall viewHolder=new ProductViewHolderSmall(view);
                return viewHolder;

            }
        };
        horizontalRecyclerView.setAdapter(firebaseRecyclerAdapterSmall);
        firebaseRecyclerAdapterSmall.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();



    }



    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice;
        ImageView itemImageView;
        View mview;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            productName=itemView.findViewById(R.id.productName);
            productPrice=itemView.findViewById(R.id.productPrice);
            itemImageView=itemView.findViewById(R.id.itemImageView);




        }
    }

    public static class ProductViewHolderSmall extends RecyclerView.ViewHolder {

        //TextView productNameSmall;
        ImageView itemImageViewSmall;
        View mview;

        public ProductViewHolderSmall(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            //productNameSmall=itemView.findViewById(R.id.itemNameSmall);
            itemImageViewSmall=itemView.findViewById(R.id.itemImageSmall);




        }
    }

}
