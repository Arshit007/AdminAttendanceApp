package com.example.arshit.adminattendanceapp.SideNavigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arshit.adminattendanceapp.AddStudentActivity.AddStudent;
import com.example.arshit.adminattendanceapp.Fragment.AddCoursesFragment;
import com.example.arshit.adminattendanceapp.Fragment.AddDepartmentDetailsFragment;
import com.example.arshit.adminattendanceapp.Fragment.AddTeacherFragment;
import com.example.arshit.adminattendanceapp.Fragment.HomeFragment;
import com.example.arshit.adminattendanceapp.Fragment.PayFeesFragment;
import com.example.arshit.adminattendanceapp.Fragment.TimeTableFragment;
import com.example.arshit.adminattendanceapp.Login.SignIn;
import com.example.arshit.adminattendanceapp.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class HomeMainActivity extends AppCompatActivity {



    private DatabaseReference Rootref;
    FirebaseAuth mAuth;

    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";



    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;


    public static int navItemIndex = 0;

    private static final String ADD_STUDENT = "Add Student";
    private static final String Add_Courses = "Add Courses";
    private static final String Add_Teacher = "Add Teacher";
//    private static final String TAG_NOTIFICATIONS = "View Attendance";
    private static final String ADD_DEPARTMENT_DETAILS = "Add Department Details";
    private static final String ADD_TimeTable = "Add Time Table ";
    private static final String Add_FEES_Details = "Add Fees Detail";

    public static String CURRENT_TAG = ADD_DEPARTMENT_DETAILS;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


//        intialise();



        mAuth = FirebaseAuth.getInstance();
//        currentUserId = mAuth.getCurrentUser().getUid();

//        final DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference("Token");
//
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String deviceToken = instanceIdResult.getToken();
//                MediaSession.Token
//             Token data = new Token(deviceToken,true);
//                dbrf.child(currentUserId).setValue(data);
//
//            }
//        });







        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.addBtn);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);

        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeMainActivity.this,AddStudent.class));

            }
        });

        loadNavHeader();

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = ADD_DEPARTMENT_DETAILS;
            loadHomeFragment();
        }
    }


    private void loadNavHeader() {
        // name, website
        txtName.setText("Arshit Jain");

        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void loadHomeFragment() {
        selectNavMenu();

        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 2:

                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                AddCoursesFragment addCoursesFragment = new AddCoursesFragment();
                return addCoursesFragment;

            case 3:

                AddTeacherFragment addTeacherFragment = new AddTeacherFragment();
                return addTeacherFragment;
//            case 3:
//                // notifications fragment
//                ViewAttendanceFragment viewAttendanceFragment = new ViewAttendanceFragment();
//                return viewAttendanceFragment;
//
            case 0:
                // settings fragment
                AddDepartmentDetailsFragment addDepartmentDetailsFragment = new AddDepartmentDetailsFragment();
                return addDepartmentDetailsFragment;
            case 4:

                TimeTableFragment timeTableFragment = new TimeTableFragment();
                return timeTableFragment;
            case 5:

                PayFeesFragment payFeesFragment = new PayFeesFragment();
                return payFeesFragment;




            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    case R.id.nav_settings:
                        navItemIndex = 0;
                        CURRENT_TAG = ADD_DEPARTMENT_DETAILS;
                        break;
                    case R.id.nav_photos:
                        navItemIndex = 1;
                        CURRENT_TAG = Add_Courses;
                        break;

                    case R.id.nav_home:
                        navItemIndex = 2;
                        CURRENT_TAG = ADD_STUDENT;
                        break;
                    case R.id.nav_movies:
                        navItemIndex = 3;
                        CURRENT_TAG = Add_Teacher;
                        break;
//                    case R.id.nav_notifications:
//                        navItemIndex = 3;
//                        CURRENT_TAG = TAG_NOTIFICATIONS;
//                        break;

                    case R.id.nav_time_table:
                        navItemIndex = 4;
                        CURRENT_TAG = ADD_TimeTable;
                        break;

//                    case R.id.nav_pay_fees:
//                        navItemIndex = 5;
//                        CURRENT_TAG = Add_FEES_Details;
//                        break;






                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = ADD_STUDENT;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeMainActivity.this, SignIn.class));
            finish();

            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }



}
