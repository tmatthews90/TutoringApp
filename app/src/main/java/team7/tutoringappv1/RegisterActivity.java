package team7.tutoringappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class RegisterActivity extends AppCompatActivity {

    String fName;
    String lName;
    String email;
    String phoneNumber;
    String address;
    String password;
    String confirmPassword;
    String math = "0";
    String science = "0";
    String literature = "0";
    String history = "0";
    String musicTheory = "0";
    String musicInstument = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = (Button) findViewById(R.id.btnNext);
        assert registerButton != null;
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doneIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(doneIntent, 0);
                landingPage.landingActivity.finish();
                finish(); //to stop back button from going back to main menu
            }
        });

        CheckBox cbMath = (CheckBox) findViewById(R.id.checkBoxMath);
        assert cbMath != null;
        cbMath.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    math = "1";
                }
                else{
                    math = "0";
                }
            }
        });

        CheckBox cbScience = (CheckBox) findViewById(R.id.checkBoxScience);
        assert cbScience != null;
        cbScience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    science = "1";
                }
                else{
                    science = "0";
                }
            }
        });

        CheckBox cbLiterature = (CheckBox) findViewById(R.id.checkBoxLiterature);
        assert cbLiterature != null;
        cbLiterature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    literature = "1";
                }
                else{
                    literature = "0";
                }
            }
        });

        CheckBox cbHistory = (CheckBox) findViewById(R.id.checkBoxHistory);
        assert cbHistory != null;
        cbHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    history = "1";
                }
                else{
                    history = "0";
                }
            }
        });

        CheckBox cbMusicTheory = (CheckBox) findViewById(R.id.checkBoxMusicTheory);
        assert cbMusicTheory != null;
        cbMusicTheory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    musicTheory = "1";
                }
                else{
                    musicTheory = "0";
                }
            }
        });

        CheckBox cbMusicIns = (CheckBox) findViewById(R.id.checkBoxMusicInstrument);
        assert cbMusicIns != null;
        cbMusicIns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Checkbox checked: " + isChecked);
                if (isChecked){
                    musicInstument = "1";
                }
                else{
                    musicInstument = "0";
                }
            }
        });

    }


}
