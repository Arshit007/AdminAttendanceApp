package com.example.arshit.adminattendanceapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.Fragment.HomeFragment;
import com.example.arshit.adminattendanceapp.Model.Category;
import com.example.arshit.adminattendanceapp.Model.CourseModel;
import com.example.arshit.adminattendanceapp.Model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.arshit.adminattendanceapp.Fragment.HomeFragment.DELETE;
import static com.example.arshit.adminattendanceapp.Fragment.HomeFragment.UPDATE;

public class ShowCourses extends AppCompatActivity {

    TextView toolbar_title;
    RecyclerView recyclerView;
  private DatabaseReference Rootref;
    Intent intent;
    String UserName,UserId;
  private   FirebaseRecyclerAdapter adapter;
    String CName,CCode;
    FirebaseRecyclerOptions<Order> options;
    String selected_user_id;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_courses);

        initialise();
        toolbar();
    }

    private void initialise() {

        intent= getIntent();
        UserId = intent.getStringExtra("selected_user_id");
        UserName = intent.getStringExtra("Name");

         listView  = findViewById(R.id.list_all_courses);

    }

    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(UserName);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }




    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("CoursesRegister");

        database.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    String areaName = dataSnapshot1.getValue(String.class);
                    areas.add(areaName);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowCourses.this,android.R.layout.simple_list_item_1,areas);
                listView.setAdapter(adapter) ;




            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }



}
