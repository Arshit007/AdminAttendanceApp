package com.example.arshit.adminattendanceapp.CourseActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.SideNavigation.HomeMainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCourses extends AppCompatActivity {

    EditText course_name,course_code,course_Credit;
    String courseName,courseCode,courseCredit,DeptNameItem,DeptFieldItem,DeptSpecItem,SemItem;
    private Button btn_submit,btn_dob;
    Spinner DeptFieldspinner,DeptNamespinner,DeptSpecspinner,SemesterSpinner;
    TextView toolbar_title;
    DatabaseReference mRootRef,Dbref,databaseReference;
    List<String> ManageField,DeptName,DeptField,DeptSpec,SemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

            intialise();
        toolbar();

    }

    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);
        ImageView imageView = findViewById(R.id.toolbar_img);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Courses");

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

        Dbref = FirebaseDatabase.getInstance().getReference("Management System");

        DeptName = new ArrayList<String>();
        DeptField = new ArrayList<String>();
        DeptSpec = new ArrayList<String>();
        SemList = new ArrayList<>();


        DeptFieldspinner = findViewById(R.id.DeptNamespinner);
        DeptNamespinner= findViewById(R.id.DeptFieldspinner);
        DeptSpecspinner = findViewById(R.id.DeptSpecspinner);
        SemesterSpinner  = findViewById(R.id.Semesterspinner);

        Dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                    String DeptNameValue = dataSnapshot1.getKey().toString();
                    DeptName.add(DeptNameValue);

                    ArrayAdapter<String> DeptNameAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, DeptName);
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


        DeptNamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                DeptNameItem = adapterView.getItemAtPosition(i).toString();

                DeptFieldspinner.setVisibility(View.INVISIBLE);
                DeptSpecspinner.setVisibility(View.INVISIBLE);
                SemesterSpinner.setVisibility(View.INVISIBLE);
                if (DeptNameItem.equals("ENGINEERING")){

                    databaseReference = FirebaseDatabase.getInstance().getReference("Departments");
                    SemesterSpinner.setVisibility(View.VISIBLE);
                    databaseReference.child(DeptNameItem).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            List<String> SemList1 = new ArrayList<>();
                            SemList1.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                            {


                                String sem = dataSnapshot1.getKey().toString();
                                SemList1.add(sem);
                            }

                            ArrayAdapter<String> SemesterAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,SemList1);
                            SemesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            SemesterSpinner.setAdapter(SemesterAdapter);

                            SemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    SemItem = adapterView.getItemAtPosition(i).toString();

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



                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference("Management System").child(DeptNameItem);

                            Db.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    DeptFieldspinner.setVisibility(View.VISIBLE);

                                    final List<String> FieldList = new ArrayList<>();
                                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                      String Field = dataSnapshot1.getKey();
                                        FieldList.add(Field);

                                    }

                                    ArrayAdapter<String> DeptFieldAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,FieldList);
                                    DeptFieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    DeptFieldspinner.setAdapter(DeptFieldAdapter);

                                    DeptFieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                            DeptFieldItem = adapterView.getItemAtPosition(i).toString();
                                            DatabaseReference Db = FirebaseDatabase.getInstance().getReference("Management System").child(DeptNameItem);







                                            DeptSpecspinner.setVisibility(View.VISIBLE);

                                            Db.child(DeptFieldItem).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    List<String> SpecList = new ArrayList<>();
                                                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                                     String Spec =   dataSnapshot1.getKey().toString();
                                                       SpecList.add(Spec);



                                                    }

                                                    ArrayAdapter<String> DeptSpecAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,SpecList);
                                                    DeptSpecAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    DeptSpecspinner.setAdapter(DeptSpecAdpater);

                                                    DeptSpecspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                            DeptSpecItem = adapterView.getItemAtPosition(i).toString();

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

                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference("Management System").child(DeptNameItem);

                  DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference("Departments");

                    databaseReference.child(DeptNameItem).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            List<String> SemList1 = new ArrayList<>();
                            SemList1.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                            {

                                String sem = dataSnapshot1.getKey().toString();
                                SemList1.add(sem);
                            }

                            ArrayAdapter<String> SemesterAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,SemList1);
                            SemesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            SemesterSpinner.setAdapter(SemesterAdapter);


                            SemesterSpinner.setVisibility(View.VISIBLE);
                            SemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    SemItem = adapterView.getItemAtPosition(i).toString();

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



                    Db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            DeptFieldspinner.setVisibility(View.VISIBLE);

                            List<String> FieldList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                String Field = dataSnapshot1.getKey();
                                FieldList.add(Field);

                            }

                            ArrayAdapter<String> DeptFieldAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,FieldList);
                            DeptFieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            DeptFieldspinner.setAdapter(DeptFieldAdapter);

                            DeptFieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    DeptFieldItem = adapterView.getItemAtPosition(i).toString();

                                    DatabaseReference Db = FirebaseDatabase.getInstance().getReference("Management System").child(DeptNameItem);





                                    DeptSpecspinner.setVisibility(View.VISIBLE);

                                    Db.child(DeptFieldItem).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            List<String> SpecList = new ArrayList<>();
                                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                                String Spec =   dataSnapshot1.getKey().toString();
                                                SpecList.add(Spec);



                                            }

                                            ArrayAdapter<String> DeptSpecAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,SpecList);
                                            DeptSpecAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            DeptSpecspinner.setAdapter(DeptSpecAdpater);

                                            DeptSpecspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                    DeptSpecItem = adapterView.getItemAtPosition(i).toString();

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


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_submit = findViewById(R.id.btn_add_courses);

        mRootRef = FirebaseDatabase.getInstance().getReference();

         course_name = findViewById(R.id.edit_text_course_name);
         course_Credit = (EditText) findViewById(R.id.edit_text_course_credit);




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                courseName = course_name.getText().toString();

                courseCredit = course_Credit.getText().toString();


                if (TextUtils.isEmpty(courseCredit) || TextUtils.isEmpty(courseName)){

                    Toast.makeText(AddCourses.this, "Please Enter Given  Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {


                    HashMap<String, String> hashMap = new HashMap<>();

                    String SubstrDeptName = String.valueOf(DeptNameItem).substring(0, 2);
                    String SubstrDeptField = String.valueOf(DeptFieldItem).substring(0, 2);
                    String SubstrDeptSpec = String.valueOf(DeptSpecItem).substring(0, 2);
                    String CourseName = String.valueOf(courseName).substring(0, 2);


                    hashMap.put("DeptName", DeptNameItem);
                    hashMap.put("DeptField", DeptFieldItem);
                    hashMap.put("DeptSpec", DeptSpecItem);
                    hashMap.put("Semester", SemItem);
                    hashMap.put("CourseCode", (SubstrDeptName + SubstrDeptField + SubstrDeptSpec + CourseName).toUpperCase());
                    hashMap.put("CourseName", courseName);
                    hashMap.put("CourseCredit", courseCredit);



                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


                    databaseReference.child(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem).child(SemItem).child((SubstrDeptName + SubstrDeptField + SubstrDeptSpec + CourseName).toUpperCase()).setValue(hashMap);



                    mRootRef.child("Courses").child((SubstrDeptName + SubstrDeptField + SubstrDeptSpec + CourseName).toUpperCase()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(AddCourses.this, "Course Added", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddCourses.this, HomeMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            finish();


                        }
                    });
                }


            }
        });

    }



}
