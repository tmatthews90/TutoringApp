package team7.tutoringappv1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TutorsActivity extends ListActivity {

    List<String> tutorNames = new ArrayList<>();
    private ArrayList<Users> tutorList = new ArrayList<>();
    String fName;
    String lName;
    String name;
    String subject;
    Float rating;
    int rate;
    String rateMoneySign;
    String entry;
    SQLiteDatabase mydatabase;
    Users tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors);

        mydatabase = openOrCreateDatabase("Users",MODE_PRIVATE,null);

        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory FROM userst WHERE isTutor = '1' ORDER BY lastName",null);
        dbEntry.moveToFirst();

        for(int i=0 ; i < dbEntry.getCount(); i++){

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.getBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.getBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.getBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.getBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.getBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.getBoolean(dbEntry.getString(10)));

            System.out.println(tempUser.getFirstName() + " " + tempUser.getLastName() + " | " + tempUser.isT_math());

            tutorList.add(tempUser);

            dbEntry.move(1);
        }



        for (int i = 0; i < tutorList.size(); i++){

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();

            if (tutor.isT_math()){
                subject = "Math";
            }
            else if (tutor.isT_science()){
                subject = "Science";
            }
            else if (tutor.isT_literature()){
                subject = "Literature";
            }
            else if (tutor.isT_history()){
                subject = "History";
            }
            else if (tutor.isT_musicInstrument()){
                subject = "Musical Instruments";
            }else if (tutor.isT_musicTheory()) {
                subject = "Music Theory";
            }
            else {
                subject = "N/A";
            }

            switch(rate) {
                case 1:
                    rateMoneySign = "$";
                    break;
                case 2:
                    rateMoneySign = "$$";
                    break;
                case 3:
                    rateMoneySign = "$$$";
                    break;
                default:
                    rateMoneySign = "$$$";
                    break;
            }

            entry = name + "\nSubject:  " + subject + "\nRating:    "+ rating + "\nRate:       " + rateMoneySign;

            tutorNames.add(entry);
        }

        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.actvity_listview, R.id.itemName,tutorNames));

        Button btnFilter = (Button) findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterIntent = new Intent(view.getContext(), FilterActivity.class);
                startActivityForResult(filterIntent, 0);
            }
        });

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("Item Clicked: " + position);
                System.out.println("Selected user: " + tutorList.get(position).getFirstName() + " " + tutorList.get(position).getLastName());

            }
        });

    }

}
