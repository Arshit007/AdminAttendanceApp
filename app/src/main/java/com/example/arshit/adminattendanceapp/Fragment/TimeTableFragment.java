package com.example.arshit.adminattendanceapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.CourseActivity.AddCourses;
import com.example.arshit.adminattendanceapp.CourseActivity.ShowDeptFieldActivity;
import com.example.arshit.adminattendanceapp.Model.Category;
import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.SideNavigation.HomeMainActivity;
import com.example.arshit.adminattendanceapp.TimeTableActivity.TimeTabelActivity;
import com.example.arshit.adminattendanceapp.TimeTableActivity.TimeTableFieldActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class TimeTableFragment extends Fragment {

    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    private DatabaseReference Rootref,TeacherRef;
    String selected_user_id;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    private String Subjectvalue, ValueName, UserName, UserSubject;

    private ProgressDialog mProgressDialog;
    private StorageReference imgStorageReference;
    private FloatingActionButton add_time_table_float;

    View view;


    public TimeTableFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_time_table, container, false);
   intialise();
        return view;

    }

    private void intialise() {



        uRecyclerView = view.findViewById(R.id.all_time_rv_table);

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
        uRecyclerView.setLayoutManager(glm);

        showResult();

        add_time_table_float = view.findViewById(R.id.show_time_table_float);

        add_time_table_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(),TimeTabelActivity.class));

            }
        });



    }

    private void showResult() {




        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Time Table");
        database.keepSynced(true);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(database, Category.class).build();

        adapter = new FirebaseRecyclerAdapter<Category, AddCoursesFragment.UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final AddCoursesFragment.UserViewHolder holder, int position, @NonNull final Category category) {



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



                        Intent intent = new Intent(getContext(), TimeTableFieldActivity.class);
                        intent.putExtra("DeptName",String.valueOf(holder.uName.getText().toString()));
                        startActivity(intent);



                    }
                });
            }





            @NonNull
            @Override
            public AddCoursesFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_value, parent, false);

                return new AddCoursesFragment.UserViewHolder(view);

            }

        } ;


        uRecyclerView.setAdapter(adapter);
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


    @Override
    public void onResume() {
        super.onResume();
        showResult();
    }
}