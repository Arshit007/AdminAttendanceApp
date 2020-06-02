package com.example.arshit.adminattendanceapp.AddStudentActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arshit.adminattendanceapp.CourseActivity.SemesterActivity;
import com.example.arshit.adminattendanceapp.CourseActivity.ShowAllCourseActivity;
import com.example.arshit.adminattendanceapp.Model.Category;
import com.example.arshit.adminattendanceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdmitYearActivity extends AppCompatActivity {

    Intent intent;
    String deptSpecName;
    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;
    String selected_user_id,deptName,deptField;
    TextView toolbar_title;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_admit_year);
        intialise();
        toolbar();

    }


    private void intialise() {

        uRecyclerView = findViewById(R.id.all_student_admit_year);

        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 1);
        uRecyclerView.setLayoutManager(glm);


        intent = getIntent();

        deptSpecName = intent.getStringExtra("DeptSpec");
        deptName = intent.getStringExtra("DeptName");
        deptField = intent.getStringExtra("DeptField");


        showDeptField();


    }


    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Choose Year of Admission");

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

    private void showDeptField() {

        database = FirebaseDatabase.getInstance().getReference("StudentManagement").child(deptName).child(deptField).child(deptSpecName);
        database.keepSynced(true);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(database, Category.class).build();

        adapter = new FirebaseRecyclerAdapter<Category, StudentAdmitYearActivity.UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final StudentAdmitYearActivity.UserViewHolder holder, int position, @NonNull final Category category) {



                selected_user_id = getRef(position).getKey();
                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        holder.uNameValue.setText("Year : ");
                        holder.uName.setText(dataSnapshot.getKey());

                    }





                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });



                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        Intent intent = new Intent(getApplicationContext(),StudentInfoActivity.class);
                        intent.putExtra("DeptSpec",deptSpecName);
                        intent.putExtra("DeptName",deptName);
                        intent.putExtra("DeptField",deptField);
                        intent.putExtra("Year",String.valueOf(holder.uName.getText().toString()));
                        startActivity(intent);



                    }
                });
            }





            @NonNull
            @Override
            public StudentAdmitYearActivity.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_value, parent, false);

                return new StudentAdmitYearActivity.UserViewHolder(view);

            }

        } ;


        uRecyclerView.setAdapter(adapter);
        adapter.startListening();




    }

    public static class UserViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView profileImg;

        TextView uName,uCode,uStatus,uNameValue;
        View  mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uName = mView.findViewById(R.id.dept_name_text_value);
            uNameValue = mView.findViewById(R.id.dept_name_text);
        }



    }

}
