package com.example.arshit.adminattendanceapp.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.Model.Category;
import com.example.arshit.adminattendanceapp.R;
import com.example.arshit.adminattendanceapp.ShowCourses;
import com.example.arshit.adminattendanceapp.ViewAttendanceActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewAttendanceFragment extends Fragment {

    View view;
    RecyclerView order_rv;
     LinearLayout cart_product_ll;
     DatabaseReference Rootref;
     RecyclerView recycler_category;

     Button btnStudent,btnCourse;
     ListView listView;

     ImageButton btnStart,btnEnd;
     TextView txtStart,txtEnd;
     CalendarView cStart,cEnd;
     String curDate;
    String categoryName;
    String UserId,PhoneNo,Subjectvalue,ValueName;


    RecyclerView uRecyclerView;
    FirebaseRecyclerAdapter adapter;

    public ViewAttendanceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_view_attendance, container, false);

        intialise();
        return view;


    }

    private void intialise() {

//        Rootref = FirebaseDatabase.getInstance().getReference();
//
//        recycler_category = view.findViewById(R.id.recycler_attendance);
//
//        GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
//
//        recycler_category.setLayoutManager(glm);



    }

//    @Override
//    public void onResume()      {
//        super.onResume();
//
//        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("StudentInfo");
//        database.keepSynced(true);
//
//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(database, Category.class).build();
//
//
//        adapter = new FirebaseRecyclerAdapter<Category, ViewAttendanceFragment.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final ViewAttendanceFragment.UserViewHolder holder, int position, @NonNull final Category category) {
//
//                final String selected_user_id = getRef(position).getKey();
//
//                database.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        categoryName = dataSnapshot.child("Name").getValue().toString();
//
//                        PhoneNo =  dataSnapshot.child("PhoneNumber").getValue().toString();
//
//                        ValueName = dataSnapshot.child("Name").getKey().toString();
//                        Subjectvalue = dataSnapshot.child("PhoneNumber").getKey().toString();
//
//
//                        holder.uStatus.setText(categoryName);
//                        holder.uName.setText(PhoneNo);
//
//                        UserId = dataSnapshot.child("Id").getValue().toString();
//                        holder.Uid.setText(UserId);
//                        holder.Uid.setVisibility(View.INVISIBLE);
//                        holder.uName.setVisibility(View.INVISIBLE);
//                        holder.uSubjectValue.setVisibility(View.INVISIBLE);
//                        holder.uNameValue.setText(ValueName);
//
//
//                    }
//
//
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) { }
//                });
//
//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent profileIntent = new Intent(getContext(),ViewAttendanceActivity.class);
//                        profileIntent.putExtra("selected_user_id",String.valueOf(holder.Uid.getText().toString()));
//                        profileIntent.putExtra("Name",String.valueOf(holder.uStatus.getText().toString()));
//                        startActivity(profileIntent);
//
//                    }
//                });
//
//            }
//
//
//
//
//            @NonNull
//            @Override
//            public ViewAttendanceFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_student, parent, false);
//
//                return new ViewAttendanceFragment.UserViewHolder(view);
//
//            }
//
//        } ;
//
//
//        recycler_category.setAdapter(adapter);
//        adapter.startListening();
//    }
//
//
//    public static class UserViewHolder extends RecyclerView.ViewHolder  {
//        public CircleImageView profileImg;
//        ListView listView;
//        TextView uName,uStatus,Uid,uNameValue,uSubjectValue;
//        View  mView;
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//
//            uName = mView.findViewById(R.id.category_name);
//            Uid = mView.findViewById(R.id.id_Course);
//            uStatus = mView.findViewById(R.id.course_code);
//            uNameValue = mView.findViewById(R.id.course_code_text);
//            uSubjectValue = mView.findViewById(R.id.course_name);
//        }
//
//
//    }
//

}




