package com.example.arshit.adminattendanceapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arshit.adminattendanceapp.AddStudentActivity.AddStudent;
import com.example.arshit.adminattendanceapp.AddStudentActivity.StudentFieldActivity;
import com.example.arshit.adminattendanceapp.CourseActivity.AddCourses;

import com.example.arshit.adminattendanceapp.Model.Category;
import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.ShowCourses;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    private DatabaseReference Rootref;
    FirebaseAuth mAuth;
    FirebaseRecyclerAdapter adapter;
    RecyclerView recycler_category;
    String categoryName;
    String UserId,PhoneNo;
    private String Subjectvalue,ValueName,UserName,UserSubject;

    public static final String UPDATE = "UPDATE";

    public static final String DELETE = "DELETE";
    View view;
    FloatingActionButton addStudent;

    String selected_user_id;

    private ArrayList<String> keys = new ArrayList<String>();
    LayoutInflater inflater;
    public HomeFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.fragment_home, container, false);

//        toolbar();

        intialise();

        return view;

    }




    private void intialise() {


        mAuth = FirebaseAuth.getInstance();
        addStudent = view.findViewById(R.id.addStudentBtn);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddStudent.class));

            }
        });

        Rootref = FirebaseDatabase.getInstance().getReference();

        recycler_category = view.findViewById(R.id.recycler_category);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 1);

        recycler_category.setLayoutManager(glm);

        showDepartments();
    }

    private  void showDepartments(){

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("StudentManagement");
        database.keepSynced(true);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(database, Category.class).build();

        adapter = new FirebaseRecyclerAdapter<Category, HomeFragment.UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final HomeFragment.UserViewHolder holder, int position, @NonNull final Category category) {



                selected_user_id = getRef(position).getKey();
                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                        holder.uName.setText(dataSnapshot.getKey());

                    }





                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });



                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        Intent intent = new Intent(getContext(),StudentFieldActivity.class);
                        intent.putExtra("DeptName",String.valueOf(holder.uName.getText().toString()));
                        startActivity(intent);



                    }
                });
            }





            @NonNull
            @Override
            public HomeFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_value, parent, false);

                return new HomeFragment.UserViewHolder(view);

            }

        } ;


        recycler_category.setAdapter(adapter);
        adapter.startListening();



    }



    public static class UserViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView profileImg;

        TextView uName,uCode,uStatus;
        View  mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uName = mView.findViewById(R.id.dept_name_text_value);

        }



    }





}
