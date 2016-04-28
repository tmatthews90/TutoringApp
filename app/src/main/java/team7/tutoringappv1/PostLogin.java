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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        txtTitle = (TextView) findViewById(R.id.title);

        final SQLiteDatabase mydatabase = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        Cursor fName = mydatabase.rawQuery("Select firstName from userst where loggedIn = '1'",null);
        Cursor lName = mydatabase.rawQuery("Select lastName from userst where loggedIn = '1'",null);
        fName.moveToFirst();
        lName.moveToFirst();
        usrName = fName.getString(0) + " " + lName.getString(0);

        txtTitle.setText("Welcome, " + usrName + "!");

        Button logout = (Button) findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydatabase.execSQL("UPDATE userst SET loggedIn = '0' where loggedIn = '1';");
                Intent logoutIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(logoutIntent, 0);
                finish();
            }
        });

        Button btnTutors = (Button) findViewById(R.id.btnBrowseTutors);
        btnTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(view.getContext(), TutorsActivity.class);
                startActivityForResult(registerIntent, 0);
            }
        });

        Button btnMyAccount = (Button) findViewById(R.id.btnMyAccount);
        btnMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(view.getContext(), AccountActivity.class);
                startActivityForResult(loginIntent, 0);
            }
        });
    }


}
