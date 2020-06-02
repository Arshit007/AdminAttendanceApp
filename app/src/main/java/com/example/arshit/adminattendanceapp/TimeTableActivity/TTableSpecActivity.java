package com.example.arshit.adminattendanceapp.TimeTableActivity;

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
import com.example.arshit.adminattendanceapp.CourseActivity.ShowDeptSpecActivity;
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

public class TTableSpecActivity extends AppCompatActivity {

    Intent intent;
    String deptSpecName;
    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;
    String selected_user_id,deptName;
    TextView toolbar_title;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttable_spec);
        intialise();
        toolbar();
    }


    private void intialise() {

        uRecyclerView = findViewById(R.id.all_time_table_spec);

        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 1);
        uRecyclerView.setLayoutManager(glm);


        intent = getIntent();

        deptSpecName = intent.getStringExtra("DeptSpecName");
        deptName = intent.getStringExtra("DeptName");



        showDeptField();


    }


    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Department of"+deptSpecName);

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

        database = FirebaseDatabase.getInstance().getReference("Time Table").child(deptName).child(deptSpecName);
        database.keepSynced(true);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(database, Category.class).build();

        adapter = new FirebaseRecyclerAdapter<Category, TTableSpecActivity.UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final TTableSpecActivity.UserViewHolder holder, int position, @NonNull final Category category) {



                selected_user_id = getRef(position).getKey();
                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        holder.uNameValue.setText("Department Specialisation");
                        holder.uName.setText(dataSnapshot.getKey());

                    }





                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });



                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        Intent intent = new Intent(getApplicationContext(),TimeTableSemesterActivity.class);
                        intent.putExtra("DeptSpec",String.valueOf(holder.uName.getText().toString()));
                        intent.putExtra("DeptName",deptName);
                        intent.putExtra("DeptField",deptSpecName);
                        startActivity(intent);



                    }
                });
            }





            @NonNull
            @Override
            public TTableSpecActivity.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_value, parent, false);

                return new TTableSpecActivity.UserViewHolder(view);

            }

        } ;


        uRecyclerView.setAdapter(adapter);
        adapter.startListening();




    }

    public static class UserViewHolder extends RecyclerView.ViewHolder  {
        public CircleImageView profileImg;

        TextView uName,uNameValue;
        View  mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uName = mView.findViewById(R.id.dept_name_text_value);
            uNameValue = mView.findViewById(R.id.dept_name_text);


        }



    }

}
