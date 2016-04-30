package team7.tutoringappv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    TextView fieldZipCode;

    Users tempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        setTextFields();

        Button btnCall = (Button) findViewById(R.id.btnCall);
        assert btnCall != null;
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tempUser.getPhoneNumber()));
                startActivity(callIntent);
            }
        });

        Button btnText = (Button) findViewById(R.id.btnText);
        assert btnText != null;
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + tempUser.getPhoneNumber()));
                sendIntent.putExtra("sms_body", "Hi, I Found you on the tutoring app and would like to meet up for some tutoring.\n\n");
                startActivity(sendIntent);
            }
        });

        Button btnEmail = (Button) findViewById(R.id.btnEmail);
        assert btnEmail != null;
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { tempUser.getEmail() });
                intent.putExtra(Intent.EXTRA_SUBJECT, "TUTORING NEEDED");
                intent.putExtra(Intent.EXTRA_TEXT, "Hi, I Found you on the tutoring app and would like to meet up for some tutoring.\n\n");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        Button btnMap = (Button) findViewById(R.id.btnMap);
        assert btnMap != null;
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mapping logic here
            }
        });

    }

    public void setTextFields(){
        fieldName = (TextView) findViewById(R.id.fieldName);
        fieldEmail = (TextView) findViewById(R.id.fieldEmail);
        fieldPhone = (TextView) findViewById(R.id.fieldPhone);
        fieldSubject = (TextView) findViewById(R.id.fieldSubject);
        fieldRate = (TextView) findViewById(R.id.fieldRate);
        fieldOverallRating = (TextView) findViewById(R.id.fieldOverallRating);
        fieldZipCode = (TextView) findViewById(R.id.fieldZipCode);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedTutor = extras.getString("selectedTutor");
        }

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        Cursor dbEntry = db.rawQuery("SELECT firstName, lastName, rating, tutorRate, phonenumber, email, t_math, t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, zipcode FROM userst WHERE email = '" + selectedTutor + "';", null);
        dbEntry.moveToFirst();

        if (dbEntry.getCount() == 1) {

            System.out.println("Found user: " + selectedTutor);

            tempUser = new Users();

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
            tempUser.setZipCode(dbEntry.getString(12));

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
            fieldZipCode.setText(tempUser.getZipCode());
        }
    }


}
