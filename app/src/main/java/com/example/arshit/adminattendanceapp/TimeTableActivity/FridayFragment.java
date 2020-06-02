package com.example.arshit.adminattendanceapp.TimeTableActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.Model.Category;
import com.example.arshit.adminattendanceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class FridayFragment extends Fragment {

    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    private DatabaseReference Rootref;
    String selected_user_id;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    private String Subjectvalue, ValueName, UserName, UserSubject;

    private ProgressDialog mProgressDialog;
    private StorageReference imgStorageReference;
    private FloatingActionButton add_teacher_float;
    RelativeLayout relativeLayout;
    String deptName,deptField,Semester,deptSpecName;


    View view;

    public FridayFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_friday, container, false);

        intialise();

        return view;
    }

    private void intialise() {



        relativeLayout = view.findViewById(R.id.txt_fri);


        uRecyclerView = view.findViewById(R.id.rv_fri);
        uRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        showTue();
    }



    private void showTue() {

        SharedPreferences myprefs= getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        deptName = myprefs.getString("DeptName", null);

        deptSpecName = myprefs.getString("DeptSpec", null);
        deptField = myprefs.getString("DeptField", null);
        Semester = myprefs.getString("Semester", null);
        relativeLayout.setVisibility(View.VISIBLE);

        final DatabaseReference   database1 = FirebaseDatabase.getInstance().getReference("Time Table").child(deptName).child(deptField).child(deptSpecName).child(Semester).child("Friday");
        database1.keepSynced(true);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(database1, Category.class).build();

        FirebaseRecyclerAdapter       adapter1 = new FirebaseRecyclerAdapter<Category,FridayFragment.UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final FridayFragment.UserViewHolder holder, int position, @NonNull final Category category) {


                String selected_user_id1 = getRef(position).getKey();

                database1.child(selected_user_id1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        relativeLayout.setVisibility(View.INVISIBLE);

                        holder.uSubjectName.setText(String.valueOf(dataSnapshot.child("CourseName").getValue().toString()));
                        holder.uTeacherName.setText(String.valueOf(dataSnapshot.child("TeacherName").getValue().toString()));
                        holder.uTimeSlot.setText(String.valueOf(dataSnapshot.child("Time").getValue().toString()));
                        holder.uRoomNumber.setText(String.valueOf(dataSnapshot.child("Room").getValue().toString()));


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });




            }

            @NonNull
            @Override
            public FridayFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
                return new FridayFragment.UserViewHolder(view);

            }

        } ;


        uRecyclerView.setAdapter(adapter1);
        adapter1.startListening();

    }




    public static class UserViewHolder extends RecyclerView.ViewHolder  {

        TextView uSubjectName,uTeacherName,uTimeSlot,uRoomNumber;
        View  mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            uSubjectName = mView.findViewById(R.id.subjectexams);
            uTeacherName = mView.findViewById(R.id.teacherexams);
            uTimeSlot = mView.findViewById(R.id.timeexams);
            uRoomNumber = mView.findViewById(R.id.roomexams);



        }



    }
    @Override
    public void onResume() {
        super.onResume();
        showTue();
    }
}