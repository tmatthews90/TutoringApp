package team7.tutoringappv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TutorProfileActivity extends AppCompatActivity {

    SQLiteDatabase db;
    String selectedTutor;

    String name;
    String subject;

    TextView fieldName;
    TextView fieldEmail;
    TextView fieldPhone;
    TextView fieldSubject;
    TextView fieldRate;
    TextView fieldOverallRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        fieldName = (TextView) findViewById(R.id.fieldName);
        fieldEmail = (TextView) findViewById(R.id.fieldEmail);
        fieldPhone = (TextView) findViewById(R.id.fieldPhone);
        fieldSubject = (TextView) findViewById(R.id.fieldSubject);
        fieldRate = (TextView) findViewById(R.id.fieldRate);
        fieldOverallRating = (TextView) findViewById(R.id.fieldOverallRating);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedTutor = extras.getString("selectedTutor");
        }

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        Cursor dbEntry = db.rawQuery("SELECT firstName, lastName, rating, tutorRate, phonenumber, email, t_math, t_science, t_literature, t_history, t_musicInstrument, t_musicTheory FROM userst WHERE email = '" + selectedTutor + "';", null);
        dbEntry.moveToFirst();

        if (dbEntry.getCount() == 1) {

            System.out.println("Found user: " + selectedTutor);

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setPhoneNumber(dbEntry.getString(4));
            tempUser.setEmail(dbEntry.getString(5));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(11)));

            name = tempUser.getFirstName() + " " + tempUser.getLastName();

            if (tempUser.isT_math()) {
                subject = "Math";
            } else if (tempUser.isT_science()) {
                subject = "Science";
            } else if (tempUser.isT_literature()) {
                subject = "Literature";
            } else if (tempUser.isT_history()) {
                subject = "History";
            } else if (tempUser.isT_musicInstrument()) {
                subject = "Musical Instruments";
            } else if (tempUser.isT_musicTheory()) {
                subject = "Music Theory";
            } else {
                subject = "N/A";
            }

            fieldName.setText(name);
            fieldEmail.setText(tempUser.getEmail());
            fieldPhone.setText(tempUser.getPhoneNumber());
            fieldSubject.setText(subject);
            fieldRate.setText(Integer.toString(tempUser.getTutorRate()));
            fieldOverallRating.setText(Float.toString(tempUser.getReviewRate()));
        }

    }

}
