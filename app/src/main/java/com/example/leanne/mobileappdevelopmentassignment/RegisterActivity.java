package com.example.leanne.mobileappdevelopmentassignment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Leanne on 15. 11. 29..
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    EditText editTextId, editTextPassword, editTextConfirmPassword, editTextName, editTextEmail, editTextPhone;
    static boolean isFemaleClicked;
    static boolean isMaleClicked;
    Button buttonMale;
    Button buttonFemale;
    Button buttonRegister;

    private static final String TAG = "RegisterActivity";
    private DatabaseOpener databaseOpener;
    private DatabaseOpener_Study databaseStudy;
    private Cursor cursor;
    private UserClass userClass;
    private StudyClass studyClass;
    private ArrayList<UserClass> userArrayList;
    private ArrayList<StudyClass> studyClasses;

    private int year;
    private int month;
    private int day;

    TextView textViewBirthday;
    Button buttonBirthday;

    static final int DATE_DIALOG_ID = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        databaseOpener = new DatabaseOpener(this);
        databaseOpener.open();
        databaseStudy = new DatabaseOpener_Study(this);
        databaseStudy.open();

//        databaseOpener.insertRecord("jiho111", "1111", "우지호", "male", "1992.9.4", "01011111111", "jiho111@gmail.com");
//        databaseOpener.insertRecord("jihoon222", "2222", "표지훈", "male", "1993.2.2", "01022222222", "jihoon222@gmail.com");
//        databaseOpener.insertRecord("kyungpark333", "3333", "박경", "male", "1992.7.8", "01033333333", "kyungpark333@gmail.com");
//        databaseOpener.insertRecord("taeil444", "4444", "이태일", "male", "1990.9.24", "01044444444", "taeil444@gmail.com");
//        databaseOpener.insertRecord("u-kwon555", "5555", "유권", "male", "1992.4.9", "01055555555", "kwon555@gmail.com");
//        databaseOpener.insertRecord("bbomb666", "6666", "이민혁", "male", "1990.12.14", "01066666666", "bbomb666@gmail.com");
//        databaseOpener.insertRecord("orange777", "7777", "안재효", "male", "1990.12.23", "01077777777", "orange777@gmail.com");

        userArrayList = new ArrayList<UserClass>();
        studyClasses = new ArrayList<StudyClass>();

        importDBToArray();
        importDBToStudyArray();
//        displayDB();
//        displayStudies();
    }

    private void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        editTextName = (EditText) findViewById(R.id.register_name);
        editTextId = (EditText) findViewById(R.id.register_id);
        editTextPassword = (EditText) findViewById(R.id.register_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        editTextEmail = (EditText) findViewById(R.id.register_email);
        editTextPhone = (EditText) findViewById(R.id.register_phone);
        buttonMale = (Button) findViewById(R.id.button_male);
        buttonFemale = (Button) findViewById(R.id.button_female);
        isMaleClicked = false;
        isFemaleClicked = false;
        buttonRegister = (Button) findViewById(R.id.button_register);
        textViewBirthday = (TextView) findViewById(R.id.text_birthday);
        buttonBirthday = (Button) findViewById(R.id.button_choose_birthday);

        buttonBirthday.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();
    }

    private void importDBToArray() {
        cursor = null;
        cursor = databaseOpener.getAllRecords();
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
            userArrayList.add(userClass);
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

    private void displayDB() {
        for (UserClass u : userArrayList) {
            Log.d(TAG, "ID = " + u._id);
            Log.d(TAG, "userID = " + u.userID);
            Log.d(TAG, "password = " + u.password);
            Log.d(TAG, "name = " + u.name);
            Log.d(TAG, "gender = " + u.gender);
            Log.d(TAG, "birthday = " + u.birthday);
            Log.d(TAG, "phone = " + u.phone);
            Log.d(TAG, "email = " + u.email);
        }
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

    private void updateDisplay() {
        this.textViewBirthday.setText(
                new StringBuilder().append(year).append(".").append(month + 1).append(".").append(day));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int setYear, int setMonth, int setDay) {
                    year = setYear;
                    month = setMonth;
                    day = setDay;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, year, month, day);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_male:
                if (buttonMale.getBackground() == buttonFemale.getBackground()) {
                    buttonMale.setBackgroundResource(R.drawable.button_border_after);
                    buttonMale.setTextColor(getResources().getColor(R.color.register_background));
                    isMaleClicked = true;
                } else if (buttonMale.getBackground() != buttonFemale.getBackground()) {
                    buttonMale.setBackgroundResource(R.drawable.button_border_after);
                    buttonMale.setTextColor(getResources().getColor(R.color.register_background));
                    isMaleClicked = true;
                    buttonFemale.setBackgroundResource(R.drawable.button_boarder_before);
                    buttonFemale.setTextColor(getResources().getColor(R.color.hint_color));
                    isFemaleClicked = false;
                }
                break;
            case R.id.button_female:
                if (buttonMale.getBackground() == buttonFemale.getBackground()) {
                    buttonFemale.setBackgroundResource(R.drawable.button_border_after);
                    buttonFemale.setTextColor(getResources().getColor(R.color.register_background));
                    isFemaleClicked = true;
                } else if (buttonMale.getBackground() != buttonFemale.getBackground()) {
                    buttonFemale.setBackgroundResource(R.drawable.button_border_after);
                    buttonFemale.setTextColor(getResources().getColor(R.color.register_background));
                    isFemaleClicked = true;
                    buttonMale.setBackgroundResource(R.drawable.button_boarder_before);
                    buttonMale.setTextColor(getResources().getColor(R.color.hint_color));
                    isMaleClicked = false;
                }
                break;
            case R.id.button_choose_birthday:
                buttonBirthday.setBackgroundResource(R.drawable.birthday_button_clicked);
                buttonBirthday.setTextColor(Color.WHITE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonBirthday.setBackgroundResource(R.drawable.birthday_button_normal);
                        buttonBirthday.setTextColor(getResources().getColor(R.color.register_background));
                        showDialog(DATE_DIALOG_ID);
                    }
                }, 100);
                break;
            case R.id.button_register:
                String userID = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                String userName = editTextName.getText().toString();
                String gender = "INIT";
                String birthday = textViewBirthday.getText().toString();
                String email = editTextEmail.getText().toString();
                String phoneNumber = editTextPhone.getText().toString();

                if (isFemaleClicked)
                    gender = "female";
                if (isMaleClicked)
                    gender = "male";

                if (userID.equals("") || password.equals("") || confirmPassword.equals("")
                        || userName.equals("") || email.equals("") || phoneNumber.equals("")) {
                    Toast.makeText(getApplicationContext(), "입력되지 않은 부분이 있습니다.", Toast.LENGTH_LONG).show();
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                } else {
                    databaseOpener.insertRecord(
                            userID, password, userName, gender, birthday, phoneNumber, email
                    );
                    databaseStudy.insertRecord("", "", "", "", "", "", "", "");
                    userArrayList.clear();
                    studyClasses.clear();
                    importDBToArray();
                    importDBToStudyArray();
//                    displayDB();
                    displayStudies();
                    cursor.close();
                    Toast.makeText(getApplicationContext(), "계정만들기 성공!", Toast.LENGTH_LONG).show();
//                    databaseOpener.close();
                    this.finish();
                    overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        userArrayList.clear();
        studyClasses.clear();
        cursor.close();
//        databaseOpener.close();
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }

//    @Override
//    protected void onDestroy() {
//        databaseOpener.close();
//        super.onDestroy();
//    }
}
