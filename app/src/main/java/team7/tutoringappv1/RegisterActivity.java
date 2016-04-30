package team7.tutoringappv1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    String fName;
    String lName;
    String email;
    String phoneNumber;
    int zipCode;
    String password;
    String confirmPassword;
    String math = "0";
    String science = "0";
    String literature = "0";
    String history = "0";
    String musicTheory = "0";
    String musicInstument = "0";
    String tutor = "0";


    EditText fieldFName;
    EditText fieldLName;
    EditText fieldEmail;
    EditText fieldPhone;
    EditText fieldZipCode;
    EditText fieldPassword;
    EditText fieldConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fieldFName = (EditText)findViewById(R.id.fieldFName);
        fieldLName = (EditText)findViewById(R.id.fieldLName);
        fieldEmail = (EditText)findViewById(R.id.fieldEmail);
        fieldPhone = (EditText)findViewById(R.id.fieldPhoneNumber);
        fieldZipCode = (EditText)findViewById(R.id.fieldZipCode);
        fieldPassword = (EditText)findViewById(R.id.fieldPassword);
        fieldConfirmPassword = (EditText)findViewById(R.id.fieldConfirmPassword);


        Button registerButton = (Button) findViewById(R.id.btnSubmit);
        assert registerButton != null;
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = fieldFName.getText().toString();
                lName = fieldLName.getText().toString();
                email = fieldEmail.getText().toString();

                isEmailValid(email);

                phoneNumber = fieldPhone.getText().toString();
                zipCode = isValidZipCode(fieldZipCode.getText().toString());

                password = fieldPassword.getText().toString();

                isPasswordValid(password);

                confirmPassword = fieldConfirmPassword.getText().toString();

                System.out.println(fName + " " + lName + " | " + email + " | " + phoneNumber + " | " + zipCode + " | " + password + " | " + confirmPassword);

//                Intent doneIntent = new Intent(view.getContext(), LoginActivity.class);
//                startActivityForResult(doneIntent, 0);
//                landingPage.landingActivity.finish();
//                finish(); //to stop back button from going back to main menu
            }
        });


        CheckBox cbMath = (CheckBox) findViewById(R.id.checkBoxMath);
        assert cbMath != null;
        cbMath.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    math = "1";
                }
                else{
                    math = "0";
                }
            }
        });

        CheckBox cbScience = (CheckBox) findViewById(R.id.checkBoxScience);
        assert cbScience != null;
        cbScience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    science = "1";
                }
                else{
                    science = "0";
                }
            }
        });

        CheckBox cbLiterature = (CheckBox) findViewById(R.id.checkBoxLiterature);
        assert cbLiterature != null;
        cbLiterature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    literature = "1";
                }
                else{
                    literature = "0";
                }
            }
        });

        CheckBox cbHistory = (CheckBox) findViewById(R.id.checkBoxHistory);
        assert cbHistory != null;
        cbHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    history = "1";
                }
                else{
                    history = "0";
                }
            }
        });

        CheckBox cbMusicTheory = (CheckBox) findViewById(R.id.checkBoxMusicTheory);
        assert cbMusicTheory != null;
        cbMusicTheory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    musicTheory = "1";
                }
                else{
                    musicTheory = "0";
                }
            }
        });

        CheckBox cbMusicIns = (CheckBox) findViewById(R.id.checkBoxMusicInstrument);
        assert cbMusicIns != null;
        cbMusicIns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    tutor = "1";
                }
                else{
                    tutor = "0";
                }
            }
        });

        CheckBox cbTutor = (CheckBox) findViewById(R.id.checkBoxTutor);
        assert cbTutor != null;
        cbTutor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    musicInstument = "1";
                }
                else{
                    musicInstument = "0";
                }
            }
        });

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private int isValidZipCode(String zipCode){
        if (zipCode.equals("")){
            return 76201;
        }
        else{
            return Integer.parseInt(zipCode);
        }
    }


}
