package com.example.arshit.adminattendanceapp.TimeTableActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.adminattendanceapp.R;
import com.google.firebase.database.DatabaseReference;

public class TimeTableDetail extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    Intent intent;
    String deptSpecName;
    RecyclerView uRecyclerView0,uRecyclerView,uRecyclerView1,uRecyclerView2,uRecyclerView3,uRecyclerView4;
    String deptName,deptField,Semester;
    TextView toolbar_title;
    DatabaseReference database;
    TabLayout tabLayout;
    MyPagerAdapter adapterViewPager;
    ViewPager vpPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_detail);



        intialise();
        toolbar();
    }

    private void intialise() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Monday"));

        tabLayout.addTab(tabLayout.newTab().setText("Tuesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Wednesday"));
        tabLayout.addTab(tabLayout.newTab().setText("Thursday"));
        tabLayout.addTab(tabLayout.newTab().setText("Friday"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

         vpPager = (ViewPager) findViewById(R.id.vpPager);
        tabLayout.setOnTabSelectedListener(TimeTableDetail.this);

        intent = getIntent();
        deptSpecName = intent.getStringExtra("DeptSpec");
        deptName = intent.getStringExtra("DeptName");
        deptField = intent.getStringExtra("DeptField");
        Semester =  intent.getStringExtra("Semester");

        SharedPreferences myprefs= getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        myprefs.edit().putString("DeptName",deptName).commit();
        myprefs.edit().putString("DeptSpec",deptSpecName).commit();
        myprefs.edit().putString("DeptField",deptField).commit();
        myprefs.edit().putString("Semester",Semester).commit();




        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
//        showDay();

        vpPager.setAdapter(adapterViewPager);




        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                vpPager.setCurrentItem(position);

                tabLayout.getTabAt(position).select();


            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }


            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        vpPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }





    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        int tabCount;

        public MyPagerAdapter(FragmentManager fragmentManager,int tabCount) {
            super(fragmentManager);
             this.tabCount= tabCount;
        }

        @Override
        public int getCount() {
            return tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment


                    MondayFragment mondayFragment = new MondayFragment();
                    return mondayFragment;

                case 1 : // Fragment # 0 - This will show FirstFragment different title
                    TuesdayFragment tuesdayFragment = new TuesdayFragment();
                    return tuesdayFragment;

                case 2: // Fragment # 0 - This will show FirstFragment

                    WednesdayFragment wednesdayFragment = new WednesdayFragment();
                    return wednesdayFragment;

                case 3: // Fragment # 0 - This will show FirstFragment different title

                    ThursdayFragment thursdayFragment =  new ThursdayFragment();
                    return  thursdayFragment;
                case 4: // Fragment # 0 - This will show FirstFragment

                  FridayFragment fridayFragment = new FridayFragment();
                  return  fridayFragment;

                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }



    }


    private void toolbar() {


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Time Table of "+deptSpecName);

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

//    private void intialise() {
//
//
//
//
//
//        uRecyclerView1 = findViewById(R.id.all_rv_tue);
//
//        uRecyclerView2 = findViewById(R.id.all_rv_wed);
//
//        uRecyclerView3 = findViewById(R.id.all_rv_thurs);
//
//        uRecyclerView4 = findViewById(R.id.all_rv_fri);
//
//
//        monTxt = findViewById(R.id.txt_mon);
//        tueTxt = findViewById(R.id.txt_tue);
//        wedTxt = findViewById(R.id.txt_wed);
//        ThurTxt = findViewById(R.id.txt_Thurs);
//        friTxt = findViewById(R.id.txt_fri);
//
//
//        uRecyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        uRecyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        uRecyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        uRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//
//
//
////        showDeptField();
//
//
//
//        showDay();
//
//
//    }






//    private void showTuesDay(String areaName)
//    {
//
//        final DatabaseReference  TuesdayRef = FirebaseDatabase.getInstance().getReference("Time Table").child(deptName).child(deptField).child(deptSpecName).child(Semester).child(areaName);
//        TuesdayRef.keepSynced(true);
//
//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(TuesdayRef, Category.class).build();
//
//        FirebaseRecyclerAdapter  adapter1 = new FirebaseRecyclerAdapter<Category,TimeTableDetail.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final TimeTableDetail.UserViewHolder holder, int position, @NonNull final Category category) {
//
//                String   selected_user_id = getRef(position).getKey();
//                TuesdayRef.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        holder.uName.setText(String.valueOf(dataSnapshot.child("CourseName").getValue().toString()));
//
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) { }
//                });
//
//
//
//
//            }
//
//
//
//
//
//            @NonNull
//            @Override
//            public TimeTableDetail.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
//
//                return new TimeTableDetail.UserViewHolder(view);
//
//            }
//
//        } ;
//
//
//        uRecyclerView1.setAdapter(adapter1);
//        adapter1.startListening();
//
//    }
//
//
//    private void showWednesday(String areaName)
//    {
//
//        final DatabaseReference  WedRef = FirebaseDatabase.getInstance().getReference("Time Table").child(deptName).child(deptField).child(deptSpecName).child(Semester).child(areaName);
//        WedRef.keepSynced(true);
//
//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(WedRef, Category.class).build();
//
//        FirebaseRecyclerAdapter  adapter1 = new FirebaseRecyclerAdapter<Category,TimeTableDetail.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final TimeTableDetail.UserViewHolder holder, int position, @NonNull final Category category) {
//
//                String selected_user_id = getRef(position).getKey();
//                WedRef.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        holder.uName.setText(String.valueOf(dataSnapshot.child("CourseName").getValue().toString()));
//
//
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) { }
//                });
//
//
//
//
//            }
//
//
//
//
//
//            @NonNull
//            @Override
//            public TimeTableDetail.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
//
//                return new TimeTableDetail.UserViewHolder(view);
//
//            }
//
//        } ;
//
//
//        uRecyclerView2.setAdapter(adapter1);
//        adapter1.startListening();
//
//    }
//
//    private void showThurday(String areaName)
//    {
//
//        final DatabaseReference  ThursRef = FirebaseDatabase.getInstance().getReference("Time Table").child(deptName).child(deptField).child(deptSpecName).child(Semester).child(areaName);
//        ThursRef.keepSynced(true);
//
//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(ThursRef, Category.class).build();
//
//        FirebaseRecyclerAdapter  adapter1 = new FirebaseRecyclerAdapter<Category,TimeTableDetail.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final TimeTableDetail.UserViewHolder holder, int position, @NonNull final Category category) {
//
//                String       selected_user_id = getRef(position).getKey();
//                ThursRef.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        holder.uName.setText(String.valueOf(dataSnapshot.child("CourseName").getValue().toString()));
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) { }
//                });
//
//
//
//
//            }
//
//
//
//
//
//            @NonNull
//            @Override
//            public TimeTableDetail.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
//
//                return new TimeTableDetail.UserViewHolder(view);
//
//            }
//
//        } ;
//
//
//        uRecyclerView3.setAdapter(adapter1);
//        adapter1.startListening();
//
//    }
//
//    private void showFriday(String areaName)
//    {
//
//        final DatabaseReference FriRef = FirebaseDatabase.getInstance().getReference("Time Table").child(deptName).child(deptField).child(deptSpecName).child(Semester).child(areaName);
//        FriRef.keepSynced(true);
//
//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(FriRef, Category.class).build();
//
//        FirebaseRecyclerAdapter  adapter1 = new FirebaseRecyclerAdapter<Category,TimeTableDetail.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final TimeTableDetail.UserViewHolder holder, int position, @NonNull final Category category) {
//
//                String        selected_user_id = getRef(position).getKey();
//                FriRef.child(selected_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        holder.uName.setText(String.valueOf(dataSnapshot.child("CourseName").getValue().toString()));
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) { }
//                });
//
//
//
//
//            }
//
//
//
//
//
//            @NonNull
//            @Override
//            public TimeTableDetail.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
//
//                return new TimeTableDetail.UserViewHolder(view);
//
//            }
//
//        } ;
//
//
//        uRecyclerView4.setAdapter(adapter1);
//        adapter1.startListening();
//
//    }
//


//    private void showDay() {
//
//        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Time Table").child(deptName).child(deptField).child(deptSpecName).child(Semester);
//
//        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(database, Category.class).build();
//
//        FirebaseRecyclerAdapter       adapter1 = new FirebaseRecyclerAdapter<Category,TimeTableDetail.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final TimeTableDetail.UserViewHolder holder, int position, @NonNull final Category category) {
//
//
//                String selected_user_id1 = getRef(position).getKey();
//
//                database.child(selected_user_id1).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        String areaName = dataSnapshot.getKey();
//
//
//                        switch (areaName)
//
//                        {
//                            case "Monday" :
////                                holder.uName.setText(areaName);
////                                monTxt.setText(areaName);
////                                showMon(areaName);
//                                break;
//                            case "Tuesday" :
////                                holder.uName.setText(areaName);
//                                tabLayout.addTab(tabLayout.newTab().setText(areaName));
//
//                                //                                tueTxt.setText(areaName);
////                                showTuesDay(areaName);
//                                break;
//
//                            case "Wednesday" :
////                                holder.uName.setText(areaName);
//                                tabLayout.addTab(tabLayout.newTab().setText(areaName));
//                                //                                wedTxt.setText(areaName);
////                                showWednesday(areaName);
//                                break;
//                            case "Thursday" :
////                                holder.uName.setText(areaName);
//                                tabLayout.addTab(tabLayout.newTab().setText(areaName));
////                                ThurTxt.setText(areaName);
////                                showThurday(areaName);
//                                break;
//                            case "Friday" :
////                                holder.uName.setText(areaName);
//                                tabLayout.addTab(tabLayout.newTab().setText(areaName));
////                                friTxt.setText(areaName);
////                                showFriday(areaName);
//                                break;
//                            default:
//                                break;
//                        }
//
//
//
////                                    if (areaName.equals("Monday"))
////
////                                    else if (areaName.equals("Tuesday"))
////                                                holder.uName.setText("Monday");
////                                         showTuesDay(areaName);
////
////                                    else  if (areaName.equals("Wednesday"))
////
////                                        showWednesday(areaName);
////
////                                    else  if (areaName.equals("Thursday"))
////
////                                        showThurday(areaName);
////                                    else
////
////                                        showFriday(areaName);
//
//
//
//
//
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//
//
//
//
//
//            }
//
//            @NonNull
//            @Override
//            public TimeTableDetail.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
//
//                if (i==0){
//
//
//                }
//
//
//                return new TimeTableDetail.UserViewHolder(view);
//
//            }
//
//        } ;
//
//
//        uRecyclerView0.setAdapter(adapter1);
//        adapter1.startListening();
//
//
//    }

//    public static class UserViewHolder extends RecyclerView.ViewHolder  {
//        public CircleImageView profileImg;
//
//        TextView uName, uNameValue,uDeptField,uDeptFieldValue,uDept,uDeptValue,
//                uTotal,uPresent,uPresentValue,uTotalValue,uPercentage,uPercentageValue;
//        View  mView;
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//
//
//            uName = mView.findViewById(R.id.name_item);
//
//        }
//
//
//
//    }


}
