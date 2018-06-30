package com.example.abirhasan.finaltest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abirhasan.finaltest.utils.Constants;
import com.example.abirhasan.finaltest.R;
import com.example.abirhasan.finaltest.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.etName)
    EditText etName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        checkIfUserLoggedIn();
    }

    private void checkIfUserLoggedIn() {
        /*SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        String userName = prefs.getString(Constants.USER_NAME_KEY, null);*/
        String userName = AppUtils.getUser(this);
        if (userName != null) {
            goToDashBoard();
            finish();
        }
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String user = etName.getText().toString();
        if (user.isEmpty()) {
            Toast.makeText(this, "Enter user name", Toast.LENGTH_SHORT).show();
        } else {
            saveUserName(user);
            goToDashBoard();
            finish();
        }
    }

    private void saveUserName(String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE)
                .edit();
        editor.putString(Constants.USER_NAME_KEY, userName);
        editor.apply();
    }

    private void goToDashBoard() {
        startActivity(new Intent(this, DashBoardActivity.class));
    }
}
