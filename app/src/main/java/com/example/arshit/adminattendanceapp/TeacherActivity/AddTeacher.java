package com.example.arshit.adminattendanceapp.TeacherActivity;

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

import com.example.arshit.adminattendanceapp.AddStudentActivity.AddStudent;
import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.SideNavigation.HomeMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AddTeacher extends AppCompatActivity {

    EditText teacher_name,teacher_email,teacher_number;
    String teacherName,teacherNumber,id;
    private Button btn_submit;
    TextView toolbar_title;
    AuthCredential credential;
    Spinner spin;
    String selectedItem;
     DatabaseReference database;
    HashMap<String, String> hashMap;
    DatabaseReference mRootRef;
    Spinner DeptFieldspinner,DeptNamespinner,DeptSpecspinner;
    DatabaseReference Dbref,databaseReference;
    String userid;
    String courseName,courseCode,courseCredit,DeptNameItem,DeptFieldItem,DeptSpecItem,SemItem;
    List<String> ManageField,DeptName,DeptField,DeptSpec,SemList;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

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
        auth = FirebaseAuth.getInstance();

        btn_submit = findViewById(R.id.btn_add_teacher);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        teacher_name = findViewById(R.id.edit_text_teacher_name);

        teacher_number = findViewById(R.id.edit_text_teacher_number);


        hashMap = new HashMap<>();


        Dbref = FirebaseDatabase.getInstance().getReference("Management System");

        DeptName = new ArrayList<String>();
        DeptField = new ArrayList<String>();
        DeptSpec = new ArrayList<String>();
        SemList = new ArrayList<>();


        DeptFieldspinner = findViewById(R.id.DeptNamespinner1);
        DeptNamespinner= findViewById(R.id.DeptFieldspinner1);
        DeptSpecspinner = findViewById(R.id.DeptSpecspinner1);


        showSpinner();
        submitInfo();









    }

    private  void  showSpinner(){

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
                if (DeptNameItem.equals("ENGINEERING")){




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









    }
    private void submitInfo() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                teacherName = teacher_name.getText().toString();
                teacherNumber = teacher_number.getText().toString();


                if (TextUtils.isEmpty(teacherName) || TextUtils.isEmpty(teacherNumber)  ){

                    Toast.makeText(AddTeacher.this, "Please Enter Given  Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(String.valueOf(teacherNumber).substring(0, 1))>=1 && Integer.parseInt(String.valueOf(teacherNumber).substring(0, 1))<=5 ) {
                    Toast.makeText(AddTeacher.this, "Number should start either 6,7,8,9", Toast.LENGTH_SHORT).show();
                    return;
                }


                else if (teacherNumber.length() != 10) {

                    Toast.makeText(AddTeacher.this, "Please Enter validate 10 digit  Number", Toast.LENGTH_SHORT).show();
                    return;
                }


                else {

                    auth.createUserWithEmailAndPassword(teacherName+"@gmail.com", teacherNumber)
                            .addOnCompleteListener(AddTeacher.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {



                                    id = UUID.randomUUID().toString();

                                    userid = FirebaseAuth.getInstance().getCurrentUser().getUid();



                                    String SubstrDeptName = String.valueOf(DeptNameItem).substring(0, 2);
                                    String SubstrDeptField = String.valueOf(DeptFieldItem).substring(0, 2);
                                    String SubstrDeptSpec = String.valueOf(DeptSpecItem).substring(0, 2);

                                    hashMap.put("Email",teacherName+"@gmail.com");
                                    hashMap.put("PhoneNumber", teacherNumber);
                                    hashMap.put("Password",teacherNumber);
                                    hashMap.put("Name", teacherName);
                                    hashMap.put("Id", id);
                                    hashMap.put("TeacherId",userid);

                                    hashMap.put("imageURL", "");

                                    hashMap.put("DeptName", DeptNameItem);
                                    hashMap.put("DeptField", DeptFieldItem);
                                    hashMap.put("DeptSpec", DeptSpecItem);
                                    hashMap.put("CourseCode", (SubstrDeptName + SubstrDeptField + SubstrDeptSpec).toUpperCase());

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Teacher Management");
                                    databaseReference.child(DeptNameItem).child(DeptFieldItem).child(userid).setValue(hashMap);

                                    mRootRef.child("Teachers").child(userid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(AddTeacher.this, "TEACHER Added", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(AddTeacher.this, HomeMainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                            finish();


                                        }
                                    });
                                }
                            });
                }



            }
        });
    }

}
