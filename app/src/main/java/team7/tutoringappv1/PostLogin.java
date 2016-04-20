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
                finish();
            }
        });
<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
>>>>>>> Stashed changes

        Button btnTutors = (Button) findViewById(R.id.btnBrowseTutors);
        btnTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(view.getContext(), TutorsActivity.class);
                startActivityForResult(registerIntent, 0);
                finish();
            }
        });

        Button btnMyAccount = (Button) findViewById(R.id.btnMyAccount);
        btnMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(view.getContext(), AccountActivity.class);
                startActivityForResult(loginIntent, 0);
                finish();
<<<<<<< Updated upstream
=======
=======
        Button displayTutors = (Button) findViewById(R.id.browseTutors);
        displayTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent displayTutorsIntent = new Intent(view.getContext(), DisplayTutors.class);
                startActivityForResult(displayTutorsIntent, 0);
            }
        });
        Button myAccount = (Button) findViewById(R.id.myAccountButton);
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAccountIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(myAccountIntent, 0);

>>>>>>> Stashed changes
>>>>>>> Stashed changes
            }
        });
    }


}
