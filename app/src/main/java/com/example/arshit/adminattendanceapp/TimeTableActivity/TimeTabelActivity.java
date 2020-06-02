package com.example.arshit.adminattendanceapp.TimeTableActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.AddStudentActivity.AddStudent;
import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.SideNavigation.HomeMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class TimeTabelActivity extends AppCompatActivity {


    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    private DatabaseReference Rootref,TeacherRef;
    String userid,Tuid,Tid;
    FirebaseAuth auth;
 HashMap<String, String> hashMap;


    String courseName, courseCode, courseCredit, DeptNameItem, DeptFieldItem, DeptSpecItem, SemItem, TeacherItem, DayItem, TimeItem, CourseItem;
    private Button btn_submit;
    Spinner DeptFieldspinner, DeptNamespinner, DeptSpecspinner, SemesterSpinner, TeacherSpinner, DaySpinner, TimeSpinner, CourseSpinner;
    DatabaseReference mRootRef, Dbref, databaseReference1,databaseReference,TeacherDb;
    List<String> ManageField, DeptName, DeptField, DeptSpec, SemList, TeacherList, DayList, TimeList, CourseList;
    EditText editText;
    TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tabel);
        intialise();
        toolbar();
    }

    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);
        ImageView imageView = findViewById(R.id.toolbar_img);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Teacher");

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

    private void intialise() {

        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        DeptName = new ArrayList<String>();
        DeptField = new ArrayList<String>();
        DeptSpec = new ArrayList<String>();
        SemList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        TimeList = new ArrayList<String>();
        DayList = new ArrayList<String>();
        TeacherList = new ArrayList<>();

        CourseList = new ArrayList<>();


        DeptFieldspinner = findViewById(R.id.DeptNamespinner);
        DeptNamespinner = findViewById(R.id.DeptFieldspinner);
        DeptSpecspinner = findViewById(R.id.DeptSpecspinner);
        SemesterSpinner = findViewById(R.id.Semesterspinner);

        editText  = findViewById(R.id.RoomNumber);
        TimeSpinner = findViewById(R.id.Timespinner);
        DaySpinner = findViewById(R.id.Dayspinner);
        TeacherSpinner = findViewById(R.id.TeacherSpinner);

        CourseSpinner = findViewById(R.id.CourseSpinner);

        TimeList.clear();
        DayList.clear();




        TimeList.add("8:30-9:30");
        TimeList.add("9:35-10:30");
        TimeList.add("10:35-11:30");
        TimeList.add("11:25-12:15");

        TimeList.add("13:15-14:00");

        TimeList.add("14:05-15:00");
        TimeList.add("15:05-16:00");
        TimeList.add("16:05-17:00");

        DayList.add("Monday");
        DayList.add("Tuesday");
        DayList.add("Wednesday");
        DayList.add("Thursday");
        DayList.add("Friday");


//        DatabaseReference  timeRef = FirebaseDatabase.getInstance().getReference("TimeSlot");
//        for (int i = 0; i <TimeList.size(); i++) {
//            timeRef.child(TimeList.get(i)).child("TimeSlot").setValue(String.valueOf(i));
//        }
//
//        DatabaseReference  DayRef = FirebaseDatabase.getInstance().getReference("DaySLot");
//        for (int i = 0; i <DayList.size(); i++) {
//            DayRef.child(DayList.get(i)).child("DayList").setValue(String.valueOf(i));
//        }



        showSpinner();
    }

    private void showSpinner() {


        ArrayAdapter<String> TimeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, TimeList);
        TimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TimeSpinner.setAdapter(TimeAdapter);


        ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, DayList);
        DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DaySpinner.setAdapter(DayAdapter);


        DaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                DayItem =   adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TimeItem =adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Dbref = FirebaseDatabase.getInstance().getReference("Management System");

        TeacherRef = FirebaseDatabase.getInstance().getReference("Teachers");

        TeacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TeacherList.clear();
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    String TeacherName = dataSnapshot1.child("Name").getValue().toString();

                    TeacherList.add(TeacherName);

                     }
                ArrayAdapter<String> TeacherAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, TeacherList);
                TeacherAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                TeacherSpinner.setAdapter(TeacherAdpater);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String DeptNameValue = dataSnapshot1.getKey().toString();
                    DeptName.add(DeptNameValue);

                    ArrayAdapter<String> DeptNameAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, DeptName);
                    DeptNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    DeptNamespinner.setAdapter(DeptNameAdapter);


                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {


                        String DeptFieldValue = dataSnapshot2.getKey();
                        DeptField.add(DeptFieldValue);


                        ArrayAdapter<String> DeptFieldAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, DeptField);
                        DeptFieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        DeptFieldspinner.setAdapter(DeptFieldAdapter);


//
                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {


                            String DeptSpecialisationValue = dataSnapshot3.getKey().toString();
                            DeptSpec.add(DeptSpecialisationValue);

                            ArrayAdapter<String> DeptSpecAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, DeptSpec);
                            DeptSpecAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            DeptSpecspinner.setAdapter(DeptSpecAdpater);






                        }


                    }


                }
            }


//


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Rootref = FirebaseDatabase.getInstance().getReference("Courses");

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CourseList.clear();
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    String DeptCourse = dataSnapshot1.child("CourseName").getValue().toString();

                    CourseList.add(DeptCourse);
                    ArrayAdapter<String> CourseAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, CourseList);
                    CourseAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    CourseSpinner.setAdapter(CourseAdpater);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DeptFieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                DeptFieldItem = adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        DeptSpecspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                DeptSpecItem = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TeacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TeacherItem = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                CourseItem = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        DeptNamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                DeptNameItem = adapterView.getItemAtPosition(i).toString();

                TeacherSpinner.setVisibility(View.INVISIBLE);
                CourseSpinner.setVisibility(View.INVISIBLE);
                DeptFieldspinner.setVisibility(View.INVISIBLE);
                DeptSpecspinner.setVisibility(View.INVISIBLE);
                SemesterSpinner.setVisibility(View.INVISIBLE);

                if (DeptNameItem.equals("ENGINEERING")) {


//                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference("Management System").child(DeptNameItem);
                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference().child(DeptNameItem);

                    Db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DeptFieldspinner.setVisibility(View.VISIBLE);

                            final List<String> FieldList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                String Field = dataSnapshot1.getKey();
                                FieldList.add(Field);

                            }

                            ArrayAdapter<String> DeptFieldAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, FieldList);
                            DeptFieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            DeptFieldspinner.setAdapter(DeptFieldAdapter);

                            DeptFieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    DeptFieldItem = adapterView.getItemAtPosition(i).toString();
                                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference().child(DeptNameItem);


                                    DeptSpecspinner.setVisibility(View.VISIBLE);

                                    Db.child(DeptFieldItem).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            List<String> SpecList = new ArrayList<>();
                                            SpecList.clear();
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                                String Spec = dataSnapshot1.getKey().toString();
                                                SpecList.add(Spec);


                                            }


                                            TeacherRef = FirebaseDatabase.getInstance().getReference("Teacher Management").child(DeptNameItem).child(DeptFieldItem);

                                            TeacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    TeacherList.clear();

                                                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
//                                                        String DeptName = dataSnapshot1.child("DeptName").getValue().toString();
//
//                                                        String DeptField = dataSnapshot1.child("DeptField").getValue().toString();
//
                                                        String TName = dataSnapshot1.child("Name").getValue().toString();
//                                                        if (DeptName.equals(DeptNameItem) && DeptField.equals(DeptFieldItem)) {
                                                            TeacherSpinner.setVisibility(View.VISIBLE);
                                                            TeacherList.add(TName);


                                                            ArrayAdapter<String> TeacherAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, TeacherList);
                                                            TeacherAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            TeacherSpinner.setAdapter(TeacherAdpater);

//                                                        }

                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });




                                            ArrayAdapter<String> DeptSpecAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, SpecList);
                                            DeptSpecAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            DeptSpecspinner.setAdapter(DeptSpecAdpater);

                                            DeptSpecspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                    DeptSpecItem = adapterView.getItemAtPosition(i).toString();

                                                    databaseReference = FirebaseDatabase.getInstance().getReference().child(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem);
                                                    SemesterSpinner.setVisibility(View.VISIBLE);
                                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            List<String> SemList1 = new ArrayList<>();
                                                            SemList1.clear();
                                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())

                                                            {


                                                                String sem = dataSnapshot1.getKey().toString();
                                                                SemList1.add(sem);
                                                            }

                                                            ArrayAdapter<String> SemesterAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, SemList1);
                                                            SemesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            SemesterSpinner.setAdapter(SemesterAdapter);

                                                            SemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                                    SemItem = adapterView.getItemAtPosition(i).toString();
                                                                    DatabaseReference  Rootref1 = FirebaseDatabase.getInstance().getReference(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem).child(SemItem);


                                                                    Rootref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            CourseList.clear();
                                                                            for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                                                                String DeptName = dataSnapshot1.child("DeptName").getValue().toString();

                                                                                String DeptField1 = dataSnapshot1.child("DeptField").getValue().toString();


                                                                                String DeptSpec1 = dataSnapshot1.child("DeptSpec").getValue().toString();



//                                                        if (DeptName.equals(DeptNameItem) && DeptField1.equals(DeptFieldItem)  && DeptSpec1.equals(DeptSpecItem)) {


                                                                                String DeptCourse = dataSnapshot1.child("CourseName").getValue().toString();

                                                                                CourseSpinner.setVisibility(View.VISIBLE);
                                                                                CourseList.add(DeptCourse);


                                                                                ArrayAdapter<String> CourseAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, CourseList);
                                                                                CourseAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                                CourseSpinner.setAdapter(CourseAdpater);

//                                                        }

                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });



                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                                }
                                                            });






                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });

                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                                }
                                                            });

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });




                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }



                else {


                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference().child(DeptNameItem);

                    Db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DeptFieldspinner.setVisibility(View.VISIBLE);

                            final List<String> FieldList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                String Field = dataSnapshot1.getKey();
                                FieldList.add(Field);

                            }

                            ArrayAdapter<String> DeptFieldAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, FieldList);
                            DeptFieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            DeptFieldspinner.setAdapter(DeptFieldAdapter);

                            DeptFieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    DeptFieldItem = adapterView.getItemAtPosition(i).toString();
                                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference().child(DeptNameItem);


                                    DeptSpecspinner.setVisibility(View.VISIBLE);

                                    Db.child(DeptFieldItem).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            List<String> SpecList = new ArrayList<>();
                                            SpecList.clear();
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                                String Spec = dataSnapshot1.getKey().toString();
                                                SpecList.add(Spec);


                                            }


                                            TeacherRef = FirebaseDatabase.getInstance().getReference("Teacher Management").child(DeptNameItem).child(DeptFieldItem);

                                            TeacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    TeacherList.clear();

                                                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
//                                                        String DeptName = dataSnapshot1.child("DeptName").getValue().toString();
//
//                                                        String DeptField = dataSnapshot1.child("DeptField").getValue().toString();
//
                                                        String TName = dataSnapshot1.child("Name").getValue().toString();
//                                                        if (DeptName.equals(DeptNameItem) && DeptField.equals(DeptFieldItem)) {
                                                        TeacherSpinner.setVisibility(View.VISIBLE);
                                                        TeacherList.add(TName);


                                                        ArrayAdapter<String> TeacherAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, TeacherList);
                                                        TeacherAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        TeacherSpinner.setAdapter(TeacherAdpater);

//                                                        }

                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });




                                            ArrayAdapter<String> DeptSpecAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, SpecList);
                                            DeptSpecAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            DeptSpecspinner.setAdapter(DeptSpecAdpater);

                                            DeptSpecspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                    DeptSpecItem = adapterView.getItemAtPosition(i).toString();

                                                    databaseReference = FirebaseDatabase.getInstance().getReference().child(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem);
                                                    SemesterSpinner.setVisibility(View.VISIBLE);
                                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            List<String> SemList1 = new ArrayList<>();
                                                            SemList1.clear();
                                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())

                                                            {


                                                                String sem = dataSnapshot1.getKey().toString();
                                                                SemList1.add(sem);
                                                            }

                                                            ArrayAdapter<String> SemesterAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, SemList1);
                                                            SemesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            SemesterSpinner.setAdapter(SemesterAdapter);

                                                            SemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                                    SemItem = adapterView.getItemAtPosition(i).toString();
                                                                    DatabaseReference  Rootref1 = FirebaseDatabase.getInstance().getReference(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem).child(SemItem);


                                                                    Rootref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            CourseList.clear();
                                                                            for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                                                                String DeptName = dataSnapshot1.child("DeptName").getValue().toString();

                                                                                String DeptField1 = dataSnapshot1.child("DeptField").getValue().toString();


                                                                                String DeptSpec1 = dataSnapshot1.child("DeptSpec").getValue().toString();




//                                                        if (DeptName.equals(DeptNameItem) && DeptField1.equals(DeptFieldItem)  && DeptSpec1.equals(DeptSpecItem)) {


                                                                                String DeptCourse = dataSnapshot1.child("CourseName").getValue().toString();

                                                                                CourseSpinner.setVisibility(View.VISIBLE);
                                                                                CourseList.add(DeptCourse);


                                                                                ArrayAdapter<String> CourseAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, CourseList);
                                                                                CourseAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                                CourseSpinner.setAdapter(CourseAdpater);

//                                                        }

                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });



                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                                }
                                                            });






                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                }
                                            });

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });




                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


//                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference("Management System").child(DeptNameItem);
//
//                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Departments");
//
//                    databaseReference.child(DeptNameItem).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            List<String> SemList1 = new ArrayList<>();
//                            SemList1.clear();
//                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
//
//                            {
//
//                                String sem = dataSnapshot1.getKey().toString();
//                                SemList1.add(sem);
//                            }
//
//                            ArrayAdapter<String> SemesterAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, SemList1);
//                            SemesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            SemesterSpinner.setAdapter(SemesterAdapter);
//
//
//                            SemesterSpinner.setVisibility(View.VISIBLE);
//                            SemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                    SemItem = adapterView.getItemAtPosition(i).toString();
//
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                }
//                            });
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//                    Db.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            DeptFieldspinner.setVisibility(View.VISIBLE);
//
//                            List<String> FieldList = new ArrayList<>();
//                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                                String Field = dataSnapshot1.getKey();
//                                FieldList.add(Field);
//
//                            }
//
//                            ArrayAdapter<String> DeptFieldAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, FieldList);
//                            DeptFieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            DeptFieldspinner.setAdapter(DeptFieldAdapter);
//
//                            DeptFieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                    DeptFieldItem = adapterView.getItemAtPosition(i).toString();
//
//                                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference("Management System").child(DeptNameItem);
//
//
//                                    DeptSpecspinner.setVisibility(View.VISIBLE);
//
//                                    Db.child(DeptFieldItem).addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            List<String> SpecList = new ArrayList<>();
//                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                                                String Spec = dataSnapshot1.getKey().toString();
//                                                SpecList.add(Spec);
//
//
//                                            }
//
//                                            TeacherRef = FirebaseDatabase.getInstance().getReference("Teachers");
//
//                                            TeacherRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                    TeacherList.clear();
//
//                                                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
//                                                        String DeptName = dataSnapshot1.child("DeptName").getValue().toString();
//
//                                                        String DeptField = dataSnapshot1.child("DeptField").getValue().toString();
//                                                        String TName = dataSnapshot1.child("Name").getValue().toString();
//                                                        if (DeptName.equals(DeptNameItem) && DeptField.equals(DeptFieldItem)) {
//                                                            TeacherSpinner.setVisibility(View.VISIBLE);
//                                                            TeacherList.add(TName);
//
//
//                                                            ArrayAdapter<String> TeacherAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, TeacherList);
//                                                            TeacherAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                                            TeacherSpinner.setAdapter(TeacherAdpater);
//
//                                                        }
//
//                                                    }
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//
//                                            Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    CourseList.clear();
//                                                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
//                                                        String DeptName = dataSnapshot1.child("DeptName").getValue().toString();
//                                                        String DeptField = dataSnapshot1.child("DeptField").getValue().toString();
//
//                                                        String DeptSpec1 = dataSnapshot1.child("DeptSpec").getValue().toString();
//
//                                                        String DeptCourse = dataSnapshot1.child("CourseName").getValue().toString();
//
//                                                        if (DeptName.equals(DeptNameItem) && DeptField.equals(DeptFieldItem) && DeptSpec1.equals(DeptSpecItem) ) {
//                                                            CourseSpinner.setVisibility(View.VISIBLE);
//                                                            CourseList.add(DeptCourse);
//
//                                                            ArrayAdapter<String> CourseAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, CourseList);
//                                                            CourseAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                                            CourseSpinner.setAdapter(CourseAdpater);
//
//                                                        }
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//                                            ArrayAdapter<String> DeptSpecAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, SpecList);
//                                            DeptSpecAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                            DeptSpecspinner.setAdapter(DeptSpecAdpater);
//
//                                            DeptSpecspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                                @Override
//                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                                    DeptSpecItem = adapterView.getItemAtPosition(i).toString();
//
//                                                }
//
//                                                @Override
//                                                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                                }
//                                            });
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                }
//                            });
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_submit = findViewById(R.id.btn_add_courses);
        mRootRef = FirebaseDatabase.getInstance().getReference();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(TimeTabelActivity.this, "Assign Room to given Course", Toast.LENGTH_SHORT).show();
                   return;
                } else {



                     hashMap = new HashMap<>();

                    String SubstrDeptName = String.valueOf(DeptNameItem).substring(0, 2);
                    String SubstrDeptField = String.valueOf(DeptFieldItem).substring(0, 2);
                    String SubstrDeptSpec = String.valueOf(DeptSpecItem).substring(0, 2);
                    final String CourseName = String.valueOf(CourseItem.substring(0, 2));



                    hashMap.put("DeptName", DeptNameItem);
                    hashMap.put("DeptField", DeptFieldItem);
                    hashMap.put("DeptSpec", DeptSpecItem);
                    hashMap.put("Semester", SemItem);
                hashMap.put("CourseCode", (SubstrDeptName + SubstrDeptField + SubstrDeptSpec + CourseName).toUpperCase());
                    hashMap.put("CourseName", CourseItem);
                    hashMap.put("TeacherName", TeacherItem);
                    hashMap.put("Day", DayItem);
                    hashMap.put("Time", TimeItem);
                    hashMap.put("Room", editText.getText().toString());

                    TeacherDb = FirebaseDatabase.getInstance().getReference("TeacherClass");

                    databaseReference1.child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                                if (TeacherItem.equals(dataSnapshot1.child("Name").getValue().toString())){

                                    Tuid = dataSnapshot1.child("TeacherId").getValue().toString();
                                    Tid = dataSnapshot1.child("Id").getValue().toString();
//                                    Toast.makeText(TimeTabelActivity.this, dataSnapshot1.child("TeacherId").getValue().toString(), Toast.LENGTH_SHORT).show();
                                    hashMap.put("TUid",Tuid);
                                    hashMap.put("Tid",Tid);




                                    TeacherDb.child(DeptNameItem).child(DeptFieldItem).child(Tuid).child("DailyTimeTable").child(DayItem).child(TimeItem).setValue(hashMap);
                                    TeacherDb.child(DeptNameItem).child(DeptFieldItem).child(Tuid).child("CourseTimeTable").child(SemItem).child(DayItem).child(TimeItem).setValue(hashMap);

                                    TeacherDb.child(DeptNameItem).child(DeptFieldItem).child(Tuid).child("Course").child(DeptSpecItem).child(SemItem).child(CourseItem).setValue(hashMap);


                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    mRootRef.child("IndividualTimeTable").child(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem)
                            .child(SemItem).child(DayItem).child(TimeItem).child(CourseItem)
                            .setValue(hashMap);

                    mRootRef.child("Time Table").child(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem)
                            .child(SemItem).child(DayItem).child(TimeItem)
                            .setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {


                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(getApplicationContext(), "Course Added To Time Table", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);


                                }
                            });
                }
            }

        });

    }

}
