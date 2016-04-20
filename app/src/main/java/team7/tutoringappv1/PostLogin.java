package team7.tutoringappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PostLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        Button logout = (Button) findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(logoutIntent, 0);
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
