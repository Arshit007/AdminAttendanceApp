package com.example.arshit.adminattendanceapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.arshit.adminattendanceapp.CourseActivity.ShowDeptFieldActivity;
import com.example.arshit.adminattendanceapp.Model.Category;
import com.example.arshit.adminattendanceapp.TeacherActivity.AddTeacher;
import com.example.arshit.adminattendanceapp.Model.User;
import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.TeacherActivity.TeacherDeptFieldActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddTeacherFragment extends Fragment {

    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    private DatabaseReference Rootref;
    String selected_user_id;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    private String Subjectvalue,ValueName,UserName,UserSubject;

    private ProgressDialog mProgressDialog;
    private StorageReference imgStorageReference;
    private FloatingActionButton add_teacher_float,floatingActionButton;

    View view;
    public AddTeacherFragment() {
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_teacher, container, false);

    intialise();
     return view;

    }

    private  void intialise(){


        add_teacher_float = view.findViewById(R.id.add_teacher_float);
        uRecyclerView = view.findViewById(R.id.all_user_rv1);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
        uRecyclerView.setLayoutManager(glm);

        add_teacher_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(),AddTeacher.class));

            }
        });

        showDepartments();

    }

    private  void showDepartments(){

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Teacher Management");
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



                        Intent intent = new Intent(getContext(),TeacherDeptFieldActivity.class);
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
        showDepartments();
    }
}
