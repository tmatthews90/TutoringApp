package team7.tutoringappv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    SQLiteDatabase db;
    String selectedTutor;

    String name;

    TextView fieldName;
    TextView fieldEmail;
    TextView fieldPhone;
    TextView fieldSubject;
    TextView fieldRate;
    TextView fieldOverallRating;

    Users tempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        setTextFields();

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

    }

    public void setTextFields(){
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

        Cursor dbEntry = db.rawQuery("SELECT firstName, lastName, phonenumber, email FROM userst WHERE loggedIn = '1';", null);
        dbEntry.moveToFirst();

        if (dbEntry.getCount() == 1) {

            System.out.println("Found user: " + selectedTutor);

            tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setPhoneNumber(dbEntry.getString(2));
            tempUser.setEmail(dbEntry.getString(3));

            name = tempUser.getFirstName() + " " + tempUser.getLastName();


            fieldName.setText(name);
            fieldEmail.setText(tempUser.getEmail());
            fieldPhone.setText(tempUser.getPhoneNumber());
        }
    }


}
