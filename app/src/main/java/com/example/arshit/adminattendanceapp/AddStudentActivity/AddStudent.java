package com.example.arshit.adminattendanceapp.AddStudentActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AddStudent extends AppCompatActivity {

    private EditText txt_name, txt_contact, txt_email, txt_password;
    private String UserName, Contact,PhoneNo;
    private Button btn_submit;
    DatabaseReference mRootRef;

    FirebaseRecyclerAdapter adapter;
    private DatabaseReference database,Ref;
    HashMap<String, String> hashMap1;
    DatabaseReference db1;
    private FirebaseAuth auth;
    TextView toolbar_title;
    RecyclerView recyclerViewCheckBox;
    HashMap<String, Object> hashmap3;
    int length;
    String userid;
    private int mYear, mMonth, mDay, mHour, mMinute;
Button btn_dob;
TextView txt_dob;
    EditText course_name,course_code,course_Credit;
    String courseName,courseCode,courseCredit,DeptNameItem,DeptFieldItem,DeptSpecItem,AdmissionItem;
    String Dob, SubstrDeptName,SubstrDeptField,SubstrDeptSpec;
    Spinner DeptFieldspinner,DeptNamespinner,DeptSpecspinner,AdmissionSpinner;
    DatabaseReference Dbref,databaseReference;
    List<String> ManageField;
    List<String> DeptName;
    List<String> DeptField;
    List<String> DeptSpec;
    List<String> AdmissionYear;
    String DateOfBirth;


    HashMap<String, String> hashMap;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intialise();
        toolbar();

    }

    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Student");

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
        final String id;


        Ref = FirebaseDatabase.getInstance().getReference("StudentInfo");
        btn_dob = findViewById(R.id.btn_DOB);

        hashMap = new HashMap<>();

//        mRootRef = FirebaseDatabase.getInstance().getReference("StudentRegistration");
        hashMap1 = new HashMap<>();
        id = UUID.randomUUID().toString();

        txt_name = findViewById(R.id.edit_text_cat);
        txt_contact = findViewById(R.id.edit_text_cat_number);

        txt_dob = findViewById(R.id.edit_text_cat_dob);

        btn_submit = findViewById(R.id.btn_submit);




        Dbref = FirebaseDatabase.getInstance().getReference("Management System");

        DeptName = new ArrayList<String>();
        DeptField = new ArrayList<String>();
        DeptSpec = new ArrayList<String>();
        AdmissionYear = new ArrayList<>();

        AdmissionYear.add("2019");
        AdmissionYear.add("2020");
        AdmissionYear.add("2021");
        AdmissionYear.add("2022");
        AdmissionYear.add("2023");
        AdmissionYear.add("2024");




        DeptFieldspinner = findViewById(R.id.DeptNamespinner3);
        DeptNamespinner= findViewById(R.id.DeptFieldspinner3);
        DeptSpecspinner = findViewById(R.id.DeptSpecspinner3);
        AdmissionSpinner = findViewById(R.id.AdmissionSpinner3);

        showSpinner();


        submitInfo();



    }

    private void submitInfo() {

        btn_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddStudent.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                     txt_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }


        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                UserName = txt_name.getText().toString();
                Contact = txt_contact.getText().toString();

                DateOfBirth = txt_dob.getText().toString();
//int Digit1= Integer.parseInt(String.valueOf(Contact).substring(0, 1));
                Log.e("Valid","Valid");

     if (valid())

                    PhoneNo = String.valueOf(Contact).substring(0, 3);

                    auth.createUserWithEmailAndPassword(UserName + PhoneNo + "@gmail.com", Contact)
                            .addOnCompleteListener(AddStudent.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    userid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                                   String Year = String.valueOf(AdmissionItem).substring(2, 4);
                                    SubstrDeptName = String.valueOf(DeptNameItem).substring(0, 2);
                                    SubstrDeptField = String.valueOf(DeptFieldItem).substring(0, 2);
                                    SubstrDeptSpec = String.valueOf(DeptSpecItem).substring(0, 2);

                                    String id = (SubstrDeptName + Year + SubstrDeptField + SubstrDeptSpec + PhoneNo + String.valueOf(UserName).substring(0, 2)).toUpperCase();

                                    hashMap.put("Email", UserName + PhoneNo + "@gmail.com");
                                    hashMap.put("PhoneNumber", Contact);

                                    hashMap.put("Password", Contact);

                                    hashMap.put("Name", UserName);
                                    hashMap.put("UserId", userid);
                                    hashMap.put("imageURL", "default");

                                    hashMap.put("DeptName", DeptNameItem);
                                    hashMap.put("DeptField", DeptFieldItem);
                                    hashMap.put("DeptSpec", DeptSpecItem);
                                    hashMap.put("AdmissionYear", AdmissionItem);
                                    hashMap.put("Id", id);
                                    hashMap.put("DOB", DateOfBirth);



                                    Log.e("Not Valid","Not Valid"+String.valueOf(hashMap));



                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("StudentManagement");


                                    Ref.child(id).setValue(hashMap);


                                    databaseReference.child(DeptNameItem).child(DeptFieldItem).child(DeptSpecItem).child(AdmissionItem).child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                            Toast.makeText(AddStudent.this, "Student Added", Toast.LENGTH_SHORT).show();
//                        addStudent(id);
                                            Intent intent = new Intent(AddStudent.this, HomeMainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                            startActivity(intent);

                                            finish();


                                        }
                                    });


                                }
                            });

                }

        });


    }
    private  boolean valid(){
        if (UserName.length() == 2 || UserName.length() == 1 ){
            Toast.makeText(AddStudent.this, "Name should greater than 2", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(UserName) || TextUtils.isEmpty(Contact)  || TextUtils.isEmpty(DateOfBirth) ) {

            Toast.makeText(AddStudent.this, "Please Enter Given  Details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(String.valueOf(Contact).substring(0, 1))>=1 && Integer.parseInt(String.valueOf(Contact).substring(0, 1))<=5 ) {
            Toast.makeText(AddStudent.this, "Number should start either 6,7,8,9", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (Contact.length() != 10) {

            Toast.makeText(AddStudent.this, "Please Enter validate 10 digit  Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }

    private void showSpinner() {



        ArrayAdapter<String> AdmAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,AdmissionYear);
        AdmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AdmissionSpinner.setAdapter(AdmAdapter);


        AdmissionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AdmissionItem =  adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



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


//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        database = FirebaseDatabase.getInstance().getReference().child("Courses");
//        database.keepSynced(true);
//
//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(database, Category.class).build();
//
//        adapter = new FirebaseRecyclerAdapter<Category, AddStudent.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final AddStudent.UserViewHolder holder, int position, @NonNull final Category category) {
//
//                final String selected_user_id = getRef(position).getKey();
//
//                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        String CCode = dataSnapshot.child("CourseCode").getValue().toString();
//                        String  CName = dataSnapshot.child("CourseName").getValue().toString();
//
//                        holder.uCode.setText(CCode);
//                        holder.uName.setText(CName);
//
//
//
//        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(final CompoundButton compoundButton, boolean isChecked) {
//
//
//          if (compoundButton.isChecked()) {
//
//
//              hashMap1.put(holder.uCode.getText().toString(), holder.uName.getText().toString());
//
//
//
//          }
//
//
//                else
//                {
//
//                    hashMap1.remove(holder.uCode.getText().toString());
//
//
//
////                    db1 = FirebaseDatabase.getInstance().getReference("CoursesRegister");
////
////
////                    db1.child(id).child(holder.uCode.getText().toString()).removeValue();
////                    adapter.notifyDataSetChanged();
////                    compoundButton.setChecked(false);
//
//
//                }
//
//
//    }
//});
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
////                Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);
//
//
//
//
//
//
//            }
//
//            @NonNull
//            @Override
//            public AddStudent.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_checkbox, parent, false);
//
//                return new AddStudent.UserViewHolder(view);
//
//            }
//
//        } ;
//
//
////        all_user_rv = findViewById(R.id.all_user_recyclerview);
//        recyclerViewCheckBox.setAdapter(adapter);
//        adapter.startListening();
//    }
//
//    public static class UserViewHolder extends RecyclerView.ViewHolder {
//        ImageView profileImg;
//    private     TextView uName,uCode;
//    CheckBox cbSelect;
//        View  mView;
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//
//            uName =  mView.findViewById(R.id.name_course);
//            uCode =  mView.findViewById(R.id.name_code);
//
//            cbSelect = mView.findViewById(R.id.cbSelect);
//
//
//
//
//        }
//
//    }

}
