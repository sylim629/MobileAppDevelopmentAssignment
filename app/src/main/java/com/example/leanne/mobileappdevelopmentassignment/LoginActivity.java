package com.example.leanne.mobileappdevelopmentassignment;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends Fragment implements View.OnClickListener {
    Button login;
    TextView register, findPW;
    EditText editTextID, editTextPW;

    private static final String TAG = "LoginActivity";
    private DatabaseOpener databaseOpener;
    private Cursor cursor;
    private UserClass userClass;
    private ArrayList<UserClass> userArrayList;

    private View mainView;

    public static int _ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_login, container, false);
        initView(mainView);

        databaseOpener = new DatabaseOpener(getActivity());
        databaseOpener.open();

        userArrayList = new ArrayList<UserClass>();

        return mainView;
    }

    private void initView(View v) {
        login = (Button) v.findViewById(R.id.button_login);
        register = (TextView) v.findViewById(R.id.register);
        findPW = (TextView) v.findViewById(R.id.find_password);
        editTextID = (EditText) v.findViewById(R.id.login_id);
        editTextPW = (EditText) v.findViewById(R.id.login_password);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        findPW.setOnClickListener(this);

        _ID = 0;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                importDBToArray();
                displayDB();

                String id = editTextID.getText().toString();
                String pw = editTextPW.getText().toString();
                String storedPassword = "";
                cursor = databaseOpener.getMatchingRecordByID(id);
                while (cursor.moveToNext()) {
                    _ID = cursor.getInt(0);
                    storedPassword = cursor.getString(cursor.getColumnIndex("password"));
                }
//                String temp = Integer.toString(_ID);
//                Log.d(TAG, "ID = " + temp);

                if (id.equals("") || storedPassword.equals("")) {
                    Toast.makeText(getActivity(), "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    editTextID.setText("");
                    editTextPW.setText("");
                    break;
                }

                if (pw.equals(storedPassword)) {
                    Toast.makeText(getActivity(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                    userArrayList.clear();
                    cursor.close();
                    Intent intentHome = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intentHome);
                    getActivity().overridePendingTransition(R.anim.animation_enter_right2left,
                            R.anim.animation_leave_right2left);
                } else {
                    Toast.makeText(getActivity(), "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    editTextID.setText("");
                    editTextPW.setText("");
                }
                break;
            case R.id.register:
                Intent intentRegister = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intentRegister);
                getActivity().overridePendingTransition(R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                break;
            case R.id.find_password:
                Intent intentFindPW = new Intent(getActivity(), FindPasswordActivity.class);
                startActivity(intentFindPW);
                getActivity().overridePendingTransition(R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                break;
        }
    }
}
