package com.example.arshit.adminattendanceapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.SideNavigation.HomeMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddDepartmentDetailsFragment extends Fragment {

    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    private DatabaseReference Rootref,Dbref;
    String selected_user_id;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    private String DeptName,DeptField,DeptSpecialisation,DeptSemester;

    Spinner  ETxtDeptSpecialisation,ETxtDeptNameSpinner,ETxtDeptFieldSpinner,ETxtDeptFieldSpecSpinner;
    HashMap<String, String> hashMap;

    private ProgressDialog mProgressDialog;

    private StorageReference imgStorageReference;
    private FloatingActionButton add_teacher_float;
     Button btnSubmit;
     String selectedItem,DeptFieldItem,DeptFieldSpecItem;
    List<String> semester = new ArrayList<String>();
    List<String> Deptname = new ArrayList<String>();

    List<String> EngField = new ArrayList<String>();
    List<String> ManageField = new ArrayList<String>();


    List<String> CseSpec,MeSpec,BbaSpec,BcomSpec;

    View view;

    public AddDepartmentDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_department_details, container, false);

        intialsise();
        return view;

    }

    private void intialsise() {


        Rootref = FirebaseDatabase.getInstance().getReference();

        Dbref = FirebaseDatabase.getInstance().getReference();

        ETxtDeptNameSpinner = (Spinner) view.findViewById(R.id.spinner_text_department_name);
        ETxtDeptFieldSpinner = (Spinner) view.findViewById(R.id.spinner_text_department_field);

        ETxtDeptFieldSpecSpinner = (Spinner) view.findViewById(R.id.spinner_text_department_field_Specialization);

        btnSubmit = (Button) view.findViewById(R.id.btn_add_deapartment_details);



        Deptname.add("ENGINEERING");
        Deptname.add("MANAGEMENT");


        CseSpec = new ArrayList<String>();
        MeSpec   = new ArrayList<String>();
        BbaSpec = new ArrayList<String>();
        BcomSpec  = new ArrayList<String>();

        CseSpec.add("AI (Aritifical Intelligence)");
        CseSpec.add("DS (Data Science) ");

        MeSpec.add("ET (Energy Technology)");
        MeSpec.add("CAD (Computer Aided design)");


        BbaSpec.add("HR (Human Resources)");
        BbaSpec.add("FT (Foreign Trade)    ");

        BcomSpec.add("CA (Computer Application)");
        BcomSpec.add("AT ( Accounting And Taxation)");

        EngField.add("CSE(Computer Science & TECHNOLOGY Engineering)");
        EngField.add("ME (Mechanical Engineering)");

        ManageField.add("BBA");
        ManageField.add("BCOM");




        ArrayAdapter<String> deptNameSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,Deptname);
        deptNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ETxtDeptNameSpinner.setAdapter(deptNameSpinnerAdapter);


        ETxtDeptFieldSpinner.setVisibility(View.INVISIBLE);


        ETxtDeptNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedItem = parent.getItemAtPosition(position).toString();


                if (selectedItem.equals("ENGINEERING")){

                    ETxtDeptFieldSpinner.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> deptFieldSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,EngField);
                    deptFieldSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ETxtDeptFieldSpinner.setAdapter(deptFieldSpinnerAdapter);

                    ETxtDeptFieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            DeptFieldItem = adapterView.getItemAtPosition(i).toString();


                            if (DeptFieldItem.equals("CSE(Computer Science & TECHNOLOGY Engineering)")){


                                ETxtDeptFieldSpecSpinner.setVisibility(View.VISIBLE);

                                ArrayAdapter<String> deptFieldSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,CseSpec);
                                deptFieldSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ETxtDeptFieldSpecSpinner.setAdapter(deptFieldSpinnerAdapter);


                                ETxtDeptFieldSpecSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                        DeptFieldSpecItem = adapterView.getItemAtPosition(i).toString();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }

                            else {


                                ETxtDeptFieldSpecSpinner.setVisibility(View.VISIBLE);

                                ArrayAdapter<String> deptFieldSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,MeSpec);
                                deptFieldSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ETxtDeptFieldSpecSpinner.setAdapter(deptFieldSpinnerAdapter);

                                ETxtDeptFieldSpecSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                        DeptFieldSpecItem = adapterView.getItemAtPosition(i).toString();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }




                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }

                else {


                    ETxtDeptFieldSpinner.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> deptFieldSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,ManageField);
                    deptFieldSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ETxtDeptFieldSpinner.setAdapter(deptFieldSpinnerAdapter);

                    ETxtDeptFieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            DeptFieldItem = adapterView.getItemAtPosition(i).toString();


                            if (DeptFieldItem.equals("BBA")){


                                ETxtDeptFieldSpecSpinner.setVisibility(View.VISIBLE);

                                ArrayAdapter<String> deptFieldSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,BbaSpec);
                                deptFieldSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ETxtDeptFieldSpecSpinner.setAdapter(deptFieldSpinnerAdapter);


                            }

                            else {


                                ETxtDeptFieldSpecSpinner.setVisibility(View.VISIBLE);

                                ArrayAdapter<String> deptFieldSpinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,BcomSpec);
                                deptFieldSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ETxtDeptFieldSpecSpinner.setAdapter(deptFieldSpinnerAdapter);

                            }



                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }




            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });





        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



    hashMap = new HashMap<>();
    hashMap.put("DeptName", selectedItem);

    hashMap.put("DeptSpecialisation",DeptFieldSpecItem);
    hashMap.put("Semester",selectedItem);

    hashMap.put("DeptField",DeptFieldItem);

    String SubstrDeptName = String.valueOf(selectedItem).substring(0, 2);
    String SubstrDeptField = String.valueOf(DeptFieldItem).substring(0, 2);
    String SubstrDeptSpec = String.valueOf(DeptFieldSpecItem).substring(0, 2);


            if (selectedItem.equals("MANAGEMENT")) {
                for (int i = 1; i <= 6; i++) {
                    Dbref.child("Departments").child("MANAGEMENT").child(i +" "+"Semester").child("Year").setValue("2019");
                }
            }

            else {
               for (int i = 1;i<=8;i++)
                {
                    Dbref.child("Departments").child("ENGINEERING").child(i+" "+"Semester").child("Year").setValue("2019");
                }
            }




    Rootref.child("Management System").child(String.valueOf(selectedItem)).child(String.valueOf(DeptFieldItem)).child(String.valueOf(DeptFieldSpecItem)).child(SubstrDeptName + SubstrDeptField + SubstrDeptSpec).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()) {

                Toast.makeText(getContext(), "Details Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), HomeMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }

        }
    });

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}

