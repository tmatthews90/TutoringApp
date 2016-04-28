package team7.tutoringappv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PostLogin extends AppCompatActivity {

    TextView txtTitle;
    String usrName;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        db = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        txtTitle = (TextView) findViewById(R.id.title);

        txtTitle.setText("Welcome, " + getUserName() + "!");

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        assert btnLogout != null;
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("UPDATE userst SET loggedIn = '0' where loggedIn = '1';");
                Intent logoutIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(logoutIntent, 0);
                finish();
            }
        });

        Button btnTutors = (Button) findViewById(R.id.btnBrowseTutors);
        assert btnTutors != null;
        btnTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(view.getContext(), TutorListActivity.class);
                startActivityForResult(registerIntent, 0);
            }
        });

        Button btnMyAccount = (Button) findViewById(R.id.btnMyAccount);
        assert btnMyAccount != null;
        btnMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(view.getContext(), AccountActivity.class);
                startActivityForResult(loginIntent, 0);
            }
        });
    }

    public String getUserName(){
        Cursor fName = db.rawQuery("Select firstName from userst where loggedIn = '1'",null);
        Cursor lName = db.rawQuery("Select lastName from userst where loggedIn = '1'",null);
        fName.moveToFirst();
        lName.moveToFirst();
        usrName = fName.getString(0) + " " + lName.getString(0);
        return usrName;
    }


}
