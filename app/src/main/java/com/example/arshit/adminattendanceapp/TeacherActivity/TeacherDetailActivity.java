package com.example.arshit.adminattendanceapp.TeacherActivity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.arshit.adminattendanceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.arshit.adminattendanceapp.Model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherDetailActivity extends AppCompatActivity {


    Intent intent;
    String deptSpecName,deptFieldName;

    private String Subjectvalue,ValueName,UserName,UserSubject;

    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;
    String deptName;
    TextView toolbar_title;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        intialise();
        toolbar();
    }


    private void intialise() {

        uRecyclerView = findViewById(R.id.all_teacher_detail);

        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 1);
        uRecyclerView.setLayoutManager(glm);


        intent = getIntent();

        deptFieldName = intent.getStringExtra("DeptFieldName");
        deptName = intent.getStringExtra("DeptName");


        showTeacher();
    }


    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Teacher Detail");

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

    private void showTeacher(){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Teacher Management").child(deptName).child(deptFieldName);
        database.keepSynced(true);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(database, User.class).build();

        adapter = new FirebaseRecyclerAdapter<User,TeacherDetailActivity.UsersViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final TeacherDetailActivity.UsersViewHolder holder, int position, @NonNull final User user) {

         final String  selected_user_id = getRef(position).getKey();



                database.child(selected_user_id).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                         UserName = dataSnapshot.child("Name").getValue().toString();
                        UserSubject = dataSnapshot.child("PhoneNumber").getValue().toString();

                            holder.uName.setText(UserName);
                        holder.uSubject.setText(UserSubject);
                        holder.uSubjectValue.setText("Phone Number :");
                        holder.uNameValue.setText("Name :");





                        }
//                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }




            @NonNull
            @Override
            public TeacherDetailActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_student, parent, false);

                return new TeacherDetailActivity.UsersViewHolder(view);

            }
        } ;


        uRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder   {
        public CircleImageView profileImg;
        TextView uName,uSubject,uNameValue,uSubjectValue;
        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            uName = mView.findViewById(R.id.course_code);
            uSubject = mView.findViewById(R.id.category_name);
            uNameValue = mView.findViewById(R.id.course_code_text);
            uSubjectValue = mView.findViewById(R.id.course_name);

        }

    }


}
