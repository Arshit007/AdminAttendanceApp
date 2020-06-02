package com.example.arshit.adminattendanceapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.arshit.adminattendanceapp.Model.CourseModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAttendanceActivity extends AppCompatActivity {


    Intent intent;
    String  UserId,UserName;
    TextView toolbar_title;
    android.support.v7.widget.Toolbar toolbar;
    DatabaseReference RootRef;
    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        initialise();
        toolbar();

    }


    private void initialise() {

        intent= getIntent();
        UserId = intent.getStringExtra("selected_user_id");
        UserName = intent.getStringExtra("Name");
        uRecyclerView = findViewById(R.id.Show_Attendance_Recycler);
        uRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));



        final DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("Attendance");


        RootRef = FirebaseDatabase.getInstance().getReference().child("CoursesRegister");



        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("IndividualAttendance").child(UserId);


        FirebaseRecyclerOptions<CourseModel> options = new FirebaseRecyclerOptions.Builder<CourseModel>()
                .setQuery(db,CourseModel.class).build();


        adapter = new FirebaseRecyclerAdapter<CourseModel, UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final ViewAttendanceActivity.UserViewHolder holder, int position, @NonNull final CourseModel courseModel) {

                final String selected_user_id = getRef(position).getKey();
                db.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int Psum= 0,Absum =0;

                        holder.uNameValue.setText(dataSnapshot.getKey());
                        holder.uName.setText("Course Name :");


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                            for (DataSnapshot snapshot1 :snapshot.getChildren()){

                                if (snapshot1.getValue().equals("Present"))
                                {
                                    Psum = Psum + 1;
                                }

                                if (snapshot1.getValue().equals("Absent"))
                                {
                                    Absum = Absum + 1;
                                }



                                holder.uPresent.setText("Class Attended");
                                holder.uPercentage.setText("Perecentage =");
                                holder.uTotal.setText("Total Classes =");

                                holder.uPresentValue.setText(String.valueOf(Psum));

                                holder.uTotalValue.setText(String.valueOf(Integer.parseInt(String.valueOf(Psum))+ (Integer.parseInt(String.valueOf(Absum)))));
                                holder.uPercentageValue.setText(String.valueOf(Float.parseFloat(holder.uPresentValue.getText().toString())/Float.parseFloat(holder.uTotalValue.getText().toString())*100)+"%");


                            }


                        }

                    }




                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });



            }




            @NonNull
            @Override
            public ViewAttendanceActivity.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_all_attendance,parent, false);

                return new ViewAttendanceActivity.UserViewHolder(view);

            }
        };


        uRecyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profileImg;
        ListView listView;
        TextView uName, uStatus, Uid, uNameValue, uSubjectValue,uTotal,uPresent,uPresentValue,uTotalValue,uPercentage,uPercentageValue;
        View mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uName = mView.findViewById(R.id.course_code_text);
            uNameValue = mView.findViewById(R.id.course_code);
            uTotal = mView.findViewById(R.id.total_attend);
            uTotalValue = mView.findViewById(R.id.total_attend_value);
            uPresent = mView.findViewById(R.id.class_attend);
            uPresentValue = mView.findViewById(R.id.class_attend_value);
            uPercentage = mView.findViewById(R.id.percentage);
            uPercentageValue = mView.findViewById(R.id.percentage_value);


        }

//    private void intialise(){
//
////        currentUserId = getArguments().getString("UserId");
//        SharedPreferences myprefs= getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
//        String session_id= myprefs.getString("session_id", null);
//
//        Toast.makeText(getContext(), "id = "+session_id, Toast.LENGTH_SHORT).show();
//
//        btnStudent = view.findViewById(R.id.btn_student_attendance);
//        btnCourse = view.findViewById(R.id.btn_course_attendance);
////
//         btnStart = (ImageButton) view.findViewById(R.id.btn_Startdate);
//
//         btnEnd   = (ImageButton) view.findViewById(R.id.btn_endDate);
//
//         txtStart= (TextView) view.findViewById(R.id.txt_Start_Date);
//
//         txtEnd = view.findViewById(R.id.txt_end_Date);
//
//
//
//
//         btnStart.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//                 showStartCalendar();
//             }
//         });
//
//
//
//
//
//        }

//    private void showStartCalendar() {
//
//
////        cStart.setVisibility(View.VISIBLE);
//
//        //        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
////
////        cStart.setDate(Long.parseLong(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())));
////      Long date =   cStart.getDate();
////        Toast.makeText(getContext(), "date = "+date, Toast.LENGTH_SHORT).show();
//
//
//
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Please Select Date ");
//
//            LayoutInflater inflater = this.getLayoutInflater();
//            View custom_layout = inflater.inflate(R.layout.calendar_dialog,null);
//
//        cStart =  (CalendarView) custom_layout.findViewById(R.id.CalendarStartDate);
////        cEnd =     (CalendarView) custom_layout.findViewById(R.id.CalendarEndDate);
//
//
//        cStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onSelectedDayChange(CalendarView arg0, int year, int month,
//                                            int date) {
//
//                curDate =String.valueOf(date+"/"+month+"/"+year);
//
//            }
//        });
//
//
//        builder.setView(custom_layout);
//
//            builder.setIcon(R.drawable.ic_date_range_black_24dp);
//
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(getContext(), "working ++ Datwe ="+curDate, Toast.LENGTH_SHORT).show();
//                        txtStart.setText(curDate);
//                }
//            });
//
//
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    dialogInterface.cancel();
//                }
//            });
//
//
//
//            builder.show();
//
//
//
//
//    }


    }

    private void toolbar() {


//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(false);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                finish();
//            }
//        });


    }

}
