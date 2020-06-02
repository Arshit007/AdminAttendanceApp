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

public class StudentInfoActivity extends AppCompatActivity {


    Intent intent;
    String deptSpecName;
    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;
    String selected_user_id,deptName,deptField,Year;
    TextView toolbar_title;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        intialise();
        toolbar();
    }

    private void intialise() {


        uRecyclerView = findViewById(R.id.all_student_info);

        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 1);
        uRecyclerView.setLayoutManager(glm);


        intent = getIntent();

        deptSpecName = intent.getStringExtra("DeptSpec");
        deptName = intent.getStringExtra("DeptName");
        deptField = intent.getStringExtra("DeptField");
        Year =  intent.getStringExtra("Year");

        showDeptField();


    }






    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(deptSpecName+" " +Year);

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

        database = FirebaseDatabase.getInstance().getReference("StudentManagement").child(deptName).child(deptField).child(deptSpecName).child(Year);
        database.keepSynced(true);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(database, Category.class).build();

        adapter = new FirebaseRecyclerAdapter<Category,StudentInfoActivity.UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final StudentInfoActivity.UserViewHolder holder, int position, @NonNull final Category category) {



                selected_user_id = getRef(position).getKey();
                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        String Number = dataSnapshot.child("PhoneNumber").getValue().toString();
                        String Name = dataSnapshot.child("Name").getValue().toString();
                        String Email = dataSnapshot.child("Email").getValue().toString();
                        String DOB = dataSnapshot.child("DOB").getValue().toString();



                        holder.uNameValue.setText(Name);
                        holder.uName.setText("Name : ");


                        holder.uTotal.setText("Enrollment Id");
                        holder.uTotalValue.setText(dataSnapshot.getKey());


                        holder.uPresent.setText("Phone Number : ");
                        holder.uPresentValue.setText(Number);


                        holder.uDeptField.setText("Date Of Birth ");
                        holder.uDeptFieldValue.setText(DOB);


                        holder.uPercentage.setText("Deparment :");
                        holder.uPercentageValue.setText(deptField);





                        holder.uDept.setText("Email : ");
                        holder.uDeptValue.setText(Email);

                    }





                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });




            }





            @NonNull
            @Override
            public StudentInfoActivity.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_detail, parent, false);

                return new StudentInfoActivity.UserViewHolder(view);

            }

        } ;


        uRecyclerView.setAdapter(adapter);
        adapter.startListening();




    }

    public static class UserViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView profileImg;

        TextView uName, uNameValue,uDeptField,uDeptFieldValue,uDept,uDeptValue,
                uTotal,uPresent,uPresentValue,uTotalValue,uPercentage,uPercentageValue;
        View  mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uName = mView.findViewById(R.id.course_code_text1);
            uNameValue = mView.findViewById(R.id.course_code1);

            uTotal = mView.findViewById(R.id.total_attend1);
            uTotalValue = mView.findViewById(R.id.total_attend_value1);

            uPresent = mView.findViewById(R.id.class_attend1);
            uPresentValue = mView.findViewById(R.id.class_attend_value1);

            uPercentage = mView.findViewById(R.id.percentage1);
            uPercentageValue = mView.findViewById(R.id.percentage_value1);

            uDeptField = mView.findViewById(R.id.deptName1);
            uDeptFieldValue = mView.findViewById(R.id.deptNamevalue1);

            uDept = mView.findViewById(R.id.deptNameField1);
            uDeptValue = mView.findViewById(R.id.deptNameFieldValue1);
        }



    }

}
