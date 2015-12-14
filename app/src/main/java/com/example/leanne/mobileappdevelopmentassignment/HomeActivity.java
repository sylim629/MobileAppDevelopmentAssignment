package com.example.leanne.mobileappdevelopmentassignment;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Leanne on 15. 11. 29..
 */
public class HomeActivity extends TabActivity implements View.OnClickListener {
    ImageButton picture;
    Button save;
    EditText studyRegion, pay, period, days, hours, numParticipants, companyInfo, infoDetail;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private ListView listViewStudy, listViewTeacher;
    private ArrayAdapter<String> arrayAdapterStudy, arrayAdapterTeacher;

    private static final String TAG = "HomeActivity";
    private DatabaseOpener databaseUsers;
    private UserClass userClass;
    private ArrayList<UserClass> listUsers;
    private DatabaseOpener_Study databaseStudy;
    private Cursor cursor;
    private StudyClass studyClass;
    private ArrayList<StudyClass> studyClasses;
    private ArrayList<String> arrayListTitles;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("info").setIndicator("내 정보").setContent(R.id.my_info));
        tabHost.addTab(tabHost.newTabSpec("study").setIndicator("스터디모임").setContent(R.id.study_list));
        tabHost.addTab(tabHost.newTabSpec("teacher").setIndicator("강사리스트").setContent(R.id.teacher_list));

//        tabHost.setCurrentTab(0);

        databaseUsers = new DatabaseOpener(this);
        databaseUsers.open();
        databaseStudy = new DatabaseOpener_Study(this);
        databaseStudy.open();

        initView();

        displayStudiesOnScreen(LoginActivity._ID);
    }

    private void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        picture = (ImageButton) findViewById(R.id.picture);
        save = (Button) findViewById(R.id.save);

        studyRegion = (EditText) findViewById(R.id.study_region);
        pay = (EditText) findViewById(R.id.amount_pay);
        period = (EditText) findViewById(R.id.study_period);
        days = (EditText) findViewById(R.id.study_days);
        hours = (EditText) findViewById(R.id.study_hours);
        numParticipants = (EditText) findViewById(R.id.study_n_people);
        companyInfo = (EditText) findViewById(R.id.company_info);
        infoDetail = (EditText) findViewById(R.id.info_specific);

        listUsers = new ArrayList<UserClass>();
        studyClasses = new ArrayList<StudyClass>();
        arrayListTitles = new ArrayList<String>();

        importDBToUserArray();
        importDBToStudyArray();
        displayStudies();

        for (StudyClass s : studyClasses) {
            arrayListTitles.add(s.company);
            Log.d(TAG, "TITLE = " + arrayListTitles);
        }

        arrayAdapterStudy = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.simple_list_item_text_black, arrayListTitles);
        arrayAdapterTeacher = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.simple_list_item_text_black);
        listViewStudy = (ListView) findViewById(R.id.study_list);
        listViewTeacher = (ListView) findViewById(R.id.teacher_list);

        listViewStudy.setAdapter(arrayAdapterStudy);
        listViewTeacher.setAdapter(arrayAdapterTeacher);
//        arrayAdapterStudy.notifyDataSetChanged();

        listViewStudy.setOnItemClickListener(onClickListItem);
        listViewTeacher.setOnItemClickListener(onClickListItem);

        picture.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void displayStudiesOnScreen(int id) {
        studyRegion.setText(studyClasses.get(id - 1).region);
        pay.setText(studyClasses.get(id - 1).pay);
        period.setText(studyClasses.get(id - 1).period);
        days.setText(studyClasses.get(id - 1).days);
        hours.setText(studyClasses.get(id - 1).hours);
        numParticipants.setText(studyClasses.get(id - 1).nPeople);
        companyInfo.setText(studyClasses.get(id - 1).company);
        infoDetail.setText(studyClasses.get(id - 1).infoDetail);
    }

    private void importDBToUserArray() {
        cursor = null;
        cursor = databaseUsers.getAllRecords();
        Log.i(TAG, "COUNT = " + cursor.getCount());

        while (cursor.moveToNext()) {
            userClass = new UserClass(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("userID")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("gender")),
                    cursor.getString(cursor.getColumnIndex("birthday")),
                    cursor.getString(cursor.getColumnIndex("phone")),
                    cursor.getString(cursor.getColumnIndex("email"))
            );
            listUsers.add(userClass);
        }
        cursor.close();
    }

    private void importDBToStudyArray() {
        cursor = null;
        cursor = databaseStudy.getAllRecords();
        Log.i(TAG, "COUNT = " + cursor.getCount());

        while (cursor.moveToNext()) {
            studyClass = new StudyClass(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("region")),
                    cursor.getString(cursor.getColumnIndex("pay")),
                    cursor.getString(cursor.getColumnIndex("period")),
                    cursor.getString(cursor.getColumnIndex("days")),
                    cursor.getString(cursor.getColumnIndex("hours")),
                    cursor.getString(cursor.getColumnIndex("num_people")),
                    cursor.getString(cursor.getColumnIndex("company_info")),
                    cursor.getString(cursor.getColumnIndex("info_detail"))
            );
            studyClasses.add(studyClass);
        }
        cursor.close();
    }

    private void displayStudies() {
        for (StudyClass s : studyClasses) {
            Log.d(TAG, "ID = " + s._id);
            Log.d(TAG, "REGION = " + s.region);
            Log.d(TAG, "PAY = " + s.pay);
            Log.d(TAG, "PERIOD = " + s.period);
            Log.d(TAG, "DAYS = " + s.days);
            Log.d(TAG, "HOURS = " + s.hours);
            Log.d(TAG, "NUM OF PEOPLE = " + s.nPeople);
            Log.d(TAG, "COMPANY INFO = " + s.company);
            Log.d(TAG, "INFO DETAIL = " + s.infoDetail);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture:
                loadImagefromGallery(v);
                break;
            case R.id.save:
                String region = studyRegion.getText().toString();
                String amountPay = pay.getText().toString();
                String studyPeriod = period.getText().toString();
                String studyDays = days.getText().toString();
                String studyHours = hours.getText().toString();
                String studyPeople = numParticipants.getText().toString();
                String company = companyInfo.getText().toString();
                String detailInfo = infoDetail.getText().toString();

                save.setBackgroundColor(getResources().getColor(R.color.button_change_color));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        save.setBackgroundColor(getResources().getColor(R.color.login_background));
                    }
                }, 100);
                databaseStudy.updateRecord(LoginActivity._ID, region, amountPay, studyPeriod, studyDays,
                        studyHours, studyPeople, company, detailInfo);
                Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show();
                studyClasses.clear();
                importDBToStudyArray();
                displayStudies();
                break;
        }
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//            Log.d(TAG, "POSITION = " + position);
//            for (UserClass u : listUsers) {
//                if (u._id - 1 == position) {
//                    Intent intent = new Intent(v.getContext(), DisplayListItemActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.animation_enter_right2left,
//                            R.anim.animation_leave_right2left);
//                }
//            }
        }
    };

    public void loadImagefromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageButton after decoding the String
                picture.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        listUsers.clear();
        studyClasses.clear();
        cursor.close();
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }
}
