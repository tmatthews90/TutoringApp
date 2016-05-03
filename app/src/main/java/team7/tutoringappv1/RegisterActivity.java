package team7.tutoringappv1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements OnItemSelectedListener {

    SQLiteDatabase db;

    String fName;
    String lName;
    String email;
    String phoneNumber;
    int zipCode;
    String password;
    String confirmPassword;
    String math;
    String science;
    String literature;
    String history;
    String musicTheory;
    String musicInstrument;
    String isTutor = "0";
    String loggedIn = "1";
    String tMath;
    String tScience;
    String tLiterature;
    String tHistory;
    String tMusicTheory;
    String tMusicInstrument;


    EditText fieldFName;
    EditText fieldLName;
    EditText fieldEmail;
    EditText fieldPhone;
    EditText fieldZipCode;
    EditText fieldPassword;
    EditText fieldConfirmPassword;

    Boolean login = false;

    CheckBox cbMath;
    CheckBox cbScience;
    CheckBox cbLiterature;
    CheckBox cbHistory;
    CheckBox cbMusicTheory;
    CheckBox cbMusicIns;

    Spinner spinner;

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

        // Spinner element
        spinner = (Spinner) findViewById(R.id.tutorSpinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> subjects = new ArrayList<String>();
        subjects.add("Select a subject if you would like to tutor.");
        subjects.add("Math");
        subjects.add("Science");
        subjects.add("Literature");
        subjects.add("History");
        subjects.add("Music Instrument");
        subjects.add("Music Theory");
        subjects.add("No thank you");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjects);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


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


        cbMath = (CheckBox) findViewById(R.id.checkBoxMath);
        assert cbMath != null;
        cbMath.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    math = "true";
                }
                else{
                    math = "false";
                }
            }
        });

        cbScience = (CheckBox) findViewById(R.id.checkBoxScience);
        assert cbScience != null;
        cbScience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    science = "true";
                }
                else{
                    science = "false";
                }
            }
        });

        cbLiterature = (CheckBox) findViewById(R.id.checkBoxLiterature);
        assert cbLiterature != null;
        cbLiterature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    literature = "true";
                }
                else{
                    literature = "false";
                }
            }
        });

        cbHistory = (CheckBox) findViewById(R.id.checkBoxHistory);
        assert cbHistory != null;
        cbHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    history = "true";
                }
                else{
                    history = "false";
                }
            }
        });

        cbMusicTheory = (CheckBox) findViewById(R.id.checkBoxMusicTheory);
        assert cbMusicTheory != null;
        cbMusicTheory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    musicTheory = "true";
                }
                else{
                    musicTheory = "false";
                }
            }
        });

        cbMusicIns = (CheckBox) findViewById(R.id.checkBoxMusicInstrument);
        assert cbMusicIns != null;
        cbMusicIns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    musicInstrument = "true";
                }
                else{
                    musicInstrument = "false";
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch(item){
            case "Math":
                tMath = "true";
                tScience = "false";
                tLiterature = "false";
                tHistory = "false";
                tMusicTheory = "false";
                tMusicInstrument = "false";
                isTutor = "1";
                break;
            case "Science":
                tMath = "false";
                tScience = "true";
                tLiterature = "false";
                tHistory = "false";
                tMusicTheory = "false";
                tMusicInstrument = "false";
                isTutor = "1";
                break;
            case "Literature":
                tMath = "false";
                tScience = "false";
                tLiterature = "true";
                tHistory = "false";
                tMusicTheory = "false";
                tMusicInstrument = "false";
                isTutor = "1";
                break;
            case "History":
                tMath = "false";
                tScience = "false";
                tLiterature = "false";
                tHistory = "true";
                tMusicTheory = "false";
                tMusicInstrument = "false";
                isTutor = "1";
                break;
            case "Music Instrument":
                tMath = "false";
                tScience = "false";
                tLiterature = "false";
                tHistory = "false";
                tMusicTheory = "false";
                tMusicInstrument = "true";
                isTutor = "1";
                break;
            case "Music Theory":
                tMath = "false";
                tScience = "false";
                tLiterature = "false";
                tHistory = "false";
                tMusicTheory = "true";
                tMusicInstrument = "false";
                isTutor = "1";
                break;
            case "default":
                tMath = "false";
                tScience = "false";
                tLiterature = "false";
                tHistory = "false";
                tMusicTheory = "false";
                tMusicInstrument = "false";
                isTutor = "0";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        tMath = "false";
        tScience = "false";
        tLiterature = "false";
        tHistory = "false";
        tMusicTheory = "false";
        tMusicInstrument = "false";
        isTutor = "0";
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
                "', '" + tMath + "', '" + tScience + "', '" + tLiterature + "', '" + tHistory + "', '" + tMusicInstrument + "', '" + tMusicTheory + "', '" + "0" + "', '" +  "0" + "', '" +  "0";

        System.out.println(insertIntoTableString);

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        String query = "INSERT INTO userst VALUES('" + insertIntoTableString + "');";

        db.execSQL(query);
    }

}
