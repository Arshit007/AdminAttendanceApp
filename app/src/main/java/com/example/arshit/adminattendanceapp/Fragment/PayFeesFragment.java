package com.example.arshit.adminattendanceapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.SideNavigation.HomeMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PayFeesFragment extends Fragment {



    String DeptNameItem, DeptFieldItem, DeptSpecItem, SemItem,YearItem,EditTextAmt;
    Spinner YearSpinner,DeptFieldspinner, DeptNamespinner, DeptSpecspinner, SemesterSpinner, TeacherSpinner, DaySpinner, TimeSpinner, CourseSpinner;
    DatabaseReference mRootRef, Dbref, databaseReference1;
    List<String> YearList, DeptName, DeptField, DeptSpec, SemList, TeacherList, DayList, TimeList, CourseList;
    EditText editTextAmt;
    View view;
    Button btnSubmit;
    ValueEventListener valueEventListener;
    HashMap<String, String> hashMap;

    public PayFeesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pay_fees, container, false);
intialise();
        return view;

    }

    private void intialise() {


         hashMap = new HashMap<>();
        DeptName = new ArrayList<String>();
        DeptField = new ArrayList<String>();
        DeptSpec = new ArrayList<String>();
        SemList = new ArrayList<>();
        YearList = new ArrayList<>();


        YearList.add("2019");
        YearList.add("2020");
        YearList.add("2021");
        YearList.add("2022");
        YearList.add("2023");

 mRootRef = FirebaseDatabase.getInstance().getReference("StudentInfo");
        DeptFieldspinner = view.findViewById(R.id.DeptNamespinner2);

        DeptNamespinner = view.findViewById(R.id.DeptFieldspinner2);

        DeptSpecspinner = view.findViewById(R.id.DeptSpecspinner2);

        SemesterSpinner = view.findViewById(R.id.Semesterspinner2);

        YearSpinner = view.findViewById(R.id.Yearspinner2);

        editTextAmt = view.findViewById(R.id.edit_txt_fees);


        btnSubmit = view.findViewById(R.id.btn_add_fees);


        Dbref = FirebaseDatabase.getInstance().getReference();

        showSpinner();

        submit();

    }

    private void submit() {


         btnSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 EditTextAmt = editTextAmt.getText().toString();

                 if (TextUtils.isEmpty(EditTextAmt) ) {

                     Toast.makeText(getContext(), "Please Enter Given  Amount", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 else {






                     hashMap.put("Price",EditTextAmt);
                     hashMap.put("DeptName", DeptNameItem);
                     hashMap.put("DeptField", DeptFieldItem);
                     hashMap.put("DeptSpec", DeptSpecItem);
                     hashMap.put("AdmissionYear", YearItem);
                     hashMap.put("Semester", SemItem);

                     DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                             .getReference("FeesManagement");


                     databaseReference.child(DeptNameItem)
                             .child(DeptFieldItem)
                             .child(DeptSpecItem)
                             .child(YearItem)
                             .child(SemItem)
                             .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {

                             Toast.makeText(getContext(), "Fees Added", Toast.LENGTH_SHORT).show();

                             Intent intent = new Intent(getContext(), HomeMainActivity.class);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                             startActivity(intent);



                         }
                     });


                 }
             }
         });
    }

    private void showSpinner() {

        ArrayAdapter<String> AdmAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,YearList);
        AdmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearSpinner.setAdapter(AdmAdapter);

        YearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                YearItem = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Dbref.child("Management System").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                DeptName.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String DeptNameValue = dataSnapshot1.getKey().toString();
                    DeptName.add(DeptNameValue);

                    ArrayAdapter<String> DeptNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, DeptName);
                    DeptNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    DeptNamespinner.setAdapter(DeptNameAdapter);

                }


                DeptNamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        DeptNameItem = adapterView.getItemAtPosition(i).toString();


                                Dbref.child("Departments").child(DeptNameItem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SemList.clear();

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                    String DeptNameValue = dataSnapshot1.getKey().toString();
                    SemList.add(DeptNameValue);

                    ArrayAdapter<String> DeptNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, SemList);
                    DeptNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SemesterSpinner.setAdapter(DeptNameAdapter);


                }

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

                        Dbref.child("Management System").child(DeptNameItem).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                DeptField.clear();

                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                                    String DeptNameValue = dataSnapshot1.getKey().toString();
                                    DeptField.add(DeptNameValue);

                                    ArrayAdapter<String> DeptNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, DeptField);
                                    DeptNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    DeptFieldspinner.setAdapter(DeptNameAdapter);


                                }


                                DeptFieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        DeptFieldItem = adapterView.getItemAtPosition(i).toString();

                                        Dbref.child("Management System").child(DeptNameItem)
                                                .child(DeptFieldItem)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        DeptSpec.clear();

                                                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                                                            String DeptNameValue = dataSnapshot1.getKey().toString();
                                                            DeptSpec.add(DeptNameValue);

                                                            ArrayAdapter<String> DeptNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, DeptSpec);
                                                            DeptNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            DeptSpecspinner.setAdapter(DeptNameAdapter);


                                                        }

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