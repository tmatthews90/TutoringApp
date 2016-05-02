package team7.tutoringappv1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    SQLiteDatabase db;

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
    String musicInstrument = "0";
    String isTutor = "0";
    String loggedIn = "1";

    EditText fieldFName;
    EditText fieldLName;
    EditText fieldEmail;
    EditText fieldPhone;
    EditText fieldZipCode;
    EditText fieldPassword;
    EditText fieldConfirmPassword;

    Boolean login = false;


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
                phoneNumber = fieldPhone.getText().toString();
                zipCode = isValidZipCode(fieldZipCode.getText().toString());
                password = fieldPassword.getText().toString();
                confirmPassword = fieldConfirmPassword.getText().toString();

                checkFields();

                if (login){
                    Intent doneIntent = new Intent(view.getContext(), PostLogin.class);
                    startActivityForResult(doneIntent, 0);
                    landingPage.landingActivity.finish();
                    finish();
                }

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
                    musicInstrument = "1";
                }
                else{
                    musicInstrument = "0";
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
                    isTutor = "1";
                }
                else{
                    isTutor = "0";
                }
            }
        });

    }

    private boolean isEmailValid() {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid() {
        //TODO: Replace this with your own logic
        return phoneNumber.length() >= 10;
    }

    private boolean passwordMatch() {
        //TODO: Replace this with your own logic

        return (password.equals(confirmPassword));
    }

    private int isValidZipCode(String zipCode){
        if (zipCode.equals("")){
            return 76201;
        }
        else{
            return Integer.parseInt(zipCode);
        }
    }

    private void checkFields(){
        // Reset errors.
        fieldEmail.setError(null);
        fieldPassword.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a first name.
        if (TextUtils.isEmpty(fName)) {
            fieldFName.setError(getString(R.string.error_field_required));
            focusView = fieldFName;
            cancel = true;
        }

        // Check for a last name.
        if (TextUtils.isEmpty(lName)) {
            fieldLName.setError(getString(R.string.error_field_required));
            focusView = fieldLName;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            fieldEmail.setError(getString(R.string.error_field_required));
            focusView = fieldEmail;
            cancel = true;
        } else if (!isEmailValid()) {
            fieldEmail.setError(getString(R.string.error_invalid_email));
            focusView = fieldEmail;
            cancel = true;
        }

        // Check for a phone number.
        if (TextUtils.isEmpty(phoneNumber)) {
            fieldPhone.setError(getString(R.string.error_field_required));
            focusView = fieldPhone;
            cancel = true;
        } else if(!isPhoneValid()){
            fieldPhone.setError("Invalid phone number");
        }

        // Check for a zip code.
        if (TextUtils.isEmpty(fieldZipCode.getText().toString())) {
            fieldZipCode.setError(getString(R.string.error_field_required));
            focusView = fieldZipCode;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            fieldPassword.setError(getString(R.string.error_invalid_password));
            focusView = fieldPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            fieldPassword.setError(getString(R.string.error_field_required));
            focusView = fieldPassword;
            cancel = true;
        }

        // Check for a valid confirm password, if the user entered one.
        if (!TextUtils.isEmpty(confirmPassword) && !passwordMatch()) {
            fieldConfirmPassword.setError("Password does not match");
            focusView = fieldConfirmPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            fieldConfirmPassword.setError(getString(R.string.error_field_required));
            focusView = fieldConfirmPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            insertIntoDB();
            login = true;

        }
    }

    private void insertIntoDB(){

        String insertIntoTableString = fName + "', '" + lName + "', '" + zipCode + "', '" + phoneNumber + "', '" + isTutor + "', '" + email + "', '" + password +
                "', '" + loggedIn + "', '" + math + "', '" + science + "', '" + literature + "', '" + history + "', '" + musicInstrument + "', '" + musicTheory +
                "', '" + "false" + "', '" + "false" + "', '" + "false" + "', '" + "false" + "', '" + "false" + "', '" + "false" + "', '" + "0" + "', '" +  "0" + "', '" +  "0";

        System.out.println(insertIntoTableString);

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        String query = "INSERT INTO userst VALUES('" + insertIntoTableString + "');";

        db.execSQL(query);
    }

}
