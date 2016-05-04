// AccountActivity.java

package team7.tutoringappv1;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements  OnItemSelectedListener{

    SQLiteDatabase db;
    String selectedTutor;

    String name;
    String math;
    String science;
    String literature;
    String history;
    String musicTheory;
    String musicInstrument;
    String isTutor;

    String tMath;
    String tScience;
    String tLiterature;
    String tHistory;
    String tMusicTheory;
    String tMusicInstrument;

    TextView fieldName;
    TextView fieldEmail;
    TextView fieldPhone;
    TextView fieldSubject;
    TextView fieldRate;
    TextView fieldOverallRating;
    TextView fieldZipCode;
    TextView fieldPassword;
    TextView fieldConfirmPassword;

    CheckBox cbMath;
    CheckBox cbScience;
    CheckBox cbLiterature;
    CheckBox cbHistory;
    CheckBox cbMusicTheory;
    CheckBox cbMusicIns;
    CheckBox cbTutor;

    Users tempUser;

    Boolean submit = false;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

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

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        assert btnLogout != null;
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("UPDATE userst SET loggedIn = '0' where loggedIn = '1';");
                Intent logoutIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(logoutIntent, 0);
                PostLogin.postLoginInstance.finish();
                finish();
            }
        });

        Button btnSave = (Button) findViewById(R.id.btnSave);
        assert btnSave != null;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
                if (submit) {
                    insertIntoDB();
                    Intent saveIntent = new Intent(view.getContext(), PostLogin.class);
                    startActivityForResult(saveIntent, 0);
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

        setTextFields();

    }

    public void setTextFields(){
        fieldName = (TextView) findViewById(R.id.fieldName);
        fieldEmail = (TextView) findViewById(R.id.fieldEmail);
        fieldPhone = (TextView) findViewById(R.id.fieldPhone);
        fieldSubject = (TextView) findViewById(R.id.fieldSubject);
        fieldRate = (TextView) findViewById(R.id.fieldRate);
        fieldOverallRating = (TextView) findViewById(R.id.fieldOverallRating);
        fieldZipCode = (TextView) findViewById(R.id.fieldZipCode);
        fieldPassword = (TextView) findViewById(R.id.fieldPassword);
        fieldConfirmPassword = (TextView) findViewById(R.id.fieldConfirmPassword);

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        Cursor dbEntry = db.rawQuery("SELECT firstName, lastName, zipcode, phonenumber, isTutor, email, password, loggedIn, math, science, literature, history, musicInstrument, musicTheory, t_math, t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, tutorRate, rating FROM userst WHERE loggedIn = '1';", null);
        dbEntry.moveToFirst();

        if (dbEntry.getCount() == 1) {

            tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setZipCode(dbEntry.getString(2));
            tempUser.setPhoneNumber(dbEntry.getString(3));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setEmail(dbEntry.getString(5));
            tempUser.setPassword(dbEntry.getString(6));
            tempUser.setLoggedIn(dbEntry.getString(7));
            tempUser.setMath(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setScience(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setLiterature(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setHistory(Boolean.parseBoolean(dbEntry.getString(11)));
            tempUser.setMusicInstrument(Boolean.parseBoolean(dbEntry.getString(12)));
            tempUser.setMusicTheory(Boolean.parseBoolean(dbEntry.getString(13)));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(14)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(15)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(16)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(17)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(18)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(19)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(20)));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(21)));


            name = tempUser.getFirstName() + " " + tempUser.getLastName();


            fieldName.setText(name);
            fieldEmail.setText(tempUser.getEmail());
            fieldPhone.setText(tempUser.getPhoneNumber());
            fieldZipCode.setText(tempUser.getZipCode());
            fieldPassword.setText(tempUser.getPassword());
            fieldConfirmPassword.setText(tempUser.getPassword());

            cbMath.setChecked(tempUser.isMath());
            cbScience.setChecked(tempUser.isScience());
            cbLiterature.setChecked(tempUser.isLiterature());
            cbHistory.setChecked(tempUser.isHistory());
            cbMusicIns.setChecked(tempUser.isMusicInstrument());
            cbMusicTheory.setChecked(tempUser.isMusicTheory());

            setSelection();
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid() {
        //TODO: Replace this with your own logic
        return fieldPhone.getText().length() >= 10;
    }

    private boolean passwordMatch() {
        //TODO: Replace this with your own logic

        return (fieldPassword.getText().toString().equals(fieldConfirmPassword.getText().toString()));
    }


    private void checkFields(){

        // Reset errors.
        fieldEmail.setError(null);
        fieldPassword.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a phone number.
        if (TextUtils.isEmpty(fieldPhone.getText())) {
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
        if (!TextUtils.isEmpty(fieldPassword.getText()) && !isPasswordValid(fieldPassword.getText().toString())) {
            fieldPassword.setError(getString(R.string.error_invalid_password));
            focusView = fieldPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(fieldPassword.getText())) {
            fieldPassword.setError(getString(R.string.error_field_required));
            focusView = fieldPassword;
            cancel = true;
        }

        // Check for a valid confirm password, if the user entered one.
        if (!TextUtils.isEmpty(fieldConfirmPassword.getText()) && !passwordMatch()) {
            fieldConfirmPassword.setError("Password does not match");
            focusView = fieldConfirmPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(fieldConfirmPassword.getText())) {
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
            submit = true;
        }
    }

    private void insertIntoDB(){

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        String query = "UPDATE userst SET phonenumber = '" + fieldPhone.getText() +
                "', zipcode = '" + fieldZipCode.getText() + "', math = '" + cbMath.isChecked() +
                "', science = '" + cbScience.isChecked() + "', literature = '" + cbLiterature.isChecked() +
                "', history = '" + cbHistory.isChecked() + "', musicTheory = '" + cbMusicTheory.isChecked() +
                "', musicInstrument = '" + cbMusicIns.isChecked() + "', t_math = '" +  tMath +
                "', t_science = '" +  tScience + "', t_literature = '" +  tLiterature +
                "', t_history = '" +  tHistory + "', t_musicInstrument = '" +  tMusicInstrument +
                "', t_musicTheory = '" + tMusicTheory + "', password = '" + fieldPassword.getText() + "' WHERE loggedIn = '1';";

        db.execSQL(query);
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

    private void setSelection(){
        if (tempUser.isT_math()){
            spinner.setSelection(1);
        } else if (tempUser.isT_science()){
            spinner.setSelection(2);
        } else if (tempUser.isT_literature()){
            spinner.setSelection(3);
        } else if (tempUser.isT_history()){
            spinner.setSelection(4);
        } else if (tempUser.isT_musicInstrument()){
            spinner.setSelection(5);
        } else if (tempUser.isT_musicTheory()){
            spinner.setSelection(6);
        }
    }
}
