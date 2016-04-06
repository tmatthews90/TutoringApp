package team7.tutoringappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = (Button) findViewById(R.id.doneButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doneIntent = new Intent(view.getContext(), PostLogin.class);
                startActivityForResult(doneIntent, 0);
                finish(); //to stop back button from going back to main menu
            }
        });
    }
}
