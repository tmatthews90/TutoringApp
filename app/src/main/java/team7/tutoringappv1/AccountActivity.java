package team7.tutoringappv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

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

    TextView fieldName;
    TextView fieldEmail;
    TextView fieldPhone;
    TextView fieldSubject;
    TextView fieldRate;
    TextView fieldOverallRating;
    TextView fieldZipCode;

    CheckBox cbMath;
    CheckBox cbScience;
    CheckBox cbLiterature;
    CheckBox cbHistory;
    CheckBox cbMusicTheory;
    CheckBox cbMusicIns;
    CheckBox cbTutor;

    Users tempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

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
                insertIntoDB();
                Intent saveIntent = new Intent(view.getContext(), PostLogin.class);
                startActivityForResult(saveIntent, 0);
                finish();
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

            cbMath.setChecked(tempUser.isMath());
            cbScience.setChecked(tempUser.isScience());
            cbLiterature.setChecked(tempUser.isLiterature());
            cbHistory.setChecked(tempUser.isHistory());
            cbMusicIns.setChecked(tempUser.isMusicInstrument());
            cbMusicTheory.setChecked(tempUser.isMusicTheory());
        }
    }

    private void insertIntoDB(){

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        String query = "UPDATE userst SET phonenumber = '" + fieldPhone.getText() + "', zipcode = '" + fieldZipCode.getText() + "', math = '" + cbMath.isChecked() + "', science = '" + cbScience.isChecked() + "', literature = '" + cbLiterature.isChecked() + "', history = '" + cbHistory.isChecked() + "', musicTheory = '" + cbMusicTheory.isChecked() + "', musicInstrument = '" + cbMusicIns.isChecked() + "' WHERE loggedIn = '1';";

        db.execSQL(query);
    }

}
