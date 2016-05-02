package team7.tutoringappv1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

public class TutorListActivity extends ListActivity {

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
    String wholeStar = "★";
    String partialStar = "✩";
    int ratingCount;
    String ratingString = "";
    String distance;
    Bundle filters;
    Intent myIntent;

    SQLiteDatabase mydatabase;
    Users tutor;

    public static Activity listActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors);

        myIntent = getIntent();
        filters = myIntent.getExtras();

        listActivity = this;

        if (filters == null || (filters.containsKey("maxDistance") == false && filters.containsKey("maxPrice") == false && filters.containsKey("minRating") == false)) {
            createDefaultList();
        } else if ((filters.containsKey("maxDistance") == true && filters.containsKey("maxPrice") == false && filters.containsKey("minRating") == false) ||
                (filters.containsKey("maxDistance") == false && filters.containsKey("maxPrice") == true && filters.containsKey("minRating") == false) ||
                (filters.containsKey("maxDistance") == false && filters.containsKey("maxPrice") == false && filters.containsKey("minRating") == true)){

            if (filters.containsKey("maxDistance") == true) {
                String maxDistance = filters.getString("maxDistance");
//                createList("maxDistance", maxDistance);
            }
            if (filters.containsKey("maxPrice") == true) {
                String tutorRate = filters.getString("maxPrice");
                createListWithPriceFilter("tutorRate", tutorRate);
            }
            if (filters.containsKey("minRating") == true) {
                String rating = filters.getString("minRating");
                createListWithRatingFilter("rating", rating);
            } // Commented out the cases where we need the distance from the tutor and user
        } else if (/*(filters.containsKey("maxDistance") == true && filters.containsKey("maxPrice") == true && filters.containsKey("minRating") == false)*/
                /*(filters.containsKey("maxDistance") == true && filters.containsKey("maxPrice") == false && filters.containsKey("minRating") == true)*/
                (filters.containsKey("maxDistance") == false && filters.containsKey("maxPrice") == true && filters.containsKey("minRating") == true)) {

            String tutorRate = filters.getString("maxPrice");
            String rating = filters.getString("minRating");
            createListWithMaxAndMinFilter("tutorRate", tutorRate, "rating", rating);
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

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("Item Clicked: " + position);
                System.out.println("Selected user: " + tutorList.get(position).getFirstName() + " " + tutorList.get(position).getLastName());
                Intent tutorDisplayIntent = new Intent(view.getContext(), TutorProfileActivity.class);
                tutorDisplayIntent.putExtra("selectedTutor", tutorList.get(position).getEmail());
                startActivityForResult(tutorDisplayIntent, 0);

            }
        });

    }

    // this will create a list based on having 2 filters where we have a max restriction (maxPrice/maxDistance)
    // and a minimum restriction (minRating)
    public void createListWithMaxAndMinFilter(String maxFilter, String maxFilterValue, String minFilter, String minFilterValue) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email FROM userst WHERE isTutor = '1' AND " + maxFilter + " <= " + maxFilterValue
                +
                " AND "
                +
                minFilter + " >= " + minFilterValue
                + " ORDER BY " + minFilter + " ASC, " + maxFilter + " ASC", null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));

            tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();

            if (tutor.isT_math()) {
                subject = "Math";
            } else if (tutor.isT_science()) {
                subject = "Science";
            } else if (tutor.isT_literature()) {
                subject = "Literature";
            } else if (tutor.isT_history()) {
                subject = "History";
            } else if (tutor.isT_musicInstrument()) {
                subject = "Musical Instruments";
            } else if (tutor.isT_musicTheory()) {
                subject = "Music Theory";
            } else {
                subject = "N/A";
            }

            switch (rate) {
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
                    rateMoneySign = "-";
                    break;
            }

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign;

            tutorNames.add(entry);
        }
    }

    public void createListWithPriceFilter(String filter, String filterValue) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email FROM userst WHERE isTutor = '1' AND " + filter + " <= " + filterValue
                + " ORDER BY " + filter, null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));

            tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();

            if (tutor.isT_math()) {
                subject = "Math";
            } else if (tutor.isT_science()) {
                subject = "Science";
            } else if (tutor.isT_literature()) {
                subject = "Literature";
            } else if (tutor.isT_history()) {
                subject = "History";
            } else if (tutor.isT_musicInstrument()) {
                subject = "Musical Instruments";
            } else if (tutor.isT_musicTheory()) {
                subject = "Music Theory";
            } else {
                subject = "N/A";
            }

            switch (rate) {
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
                    rateMoneySign = "-";
                    break;
            }

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign + "\n\nDistance    " + distance;

            tutorNames.add(entry);
        }
    }

    public void createListWithRatingFilter(String filter, String filterValue) {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email FROM userst WHERE isTutor = '1' AND " + filter + " >= " + filterValue
                + " ORDER BY " + filter, null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));

            tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();

            if (tutor.isT_math()) {
                subject = "Math";
            } else if (tutor.isT_science()) {
                subject = "Science";
            } else if (tutor.isT_literature()) {
                subject = "Literature";
            } else if (tutor.isT_history()) {
                subject = "History";
            } else if (tutor.isT_musicInstrument()) {
                subject = "Musical Instruments";
            } else if (tutor.isT_musicTheory()) {
                subject = "Music Theory";
            } else {
                subject = "N/A";
            }

            switch (rate) {
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
                    rateMoneySign = "-";
                    break;
            }

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign;

            tutorNames.add(entry);
        }
    }

    public void createDefaultList() {
        mydatabase = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            mydatabase.rawQuery("DROP TABLE userst;", null);
        Cursor dbEntry = mydatabase.rawQuery("SELECT firstName, lastName, rating, tutorRate, isTutor, t_math, " +
                "t_science, t_literature, t_history, t_musicInstrument, t_musicTheory, email FROM userst WHERE isTutor = '1' ORDER BY lastName", null);
        dbEntry.moveToFirst();

        for (int i = 0; i < dbEntry.getCount(); i++) {

            Users tempUser = new Users();

            tempUser.setFirstName(dbEntry.getString(0));
            tempUser.setLastName(dbEntry.getString(1));
            tempUser.setReviewRate(Float.parseFloat(dbEntry.getString(2)));
            tempUser.setTutorRate(Integer.parseInt(dbEntry.getString(3)));
            tempUser.setIsTutor(dbEntry.getString(4));
            tempUser.setT_math(Boolean.parseBoolean(dbEntry.getString(5)));
            tempUser.setT_science(Boolean.parseBoolean(dbEntry.getString(6)));
            tempUser.setT_literature(Boolean.parseBoolean(dbEntry.getString(7)));
            tempUser.setT_history(Boolean.parseBoolean(dbEntry.getString(8)));
            tempUser.setT_musicInstrument(Boolean.parseBoolean(dbEntry.getString(9)));
            tempUser.setT_musicTheory(Boolean.parseBoolean(dbEntry.getString(10)));
            tempUser.setEmail(dbEntry.getString(11));

            tutorList.add(tempUser);

            dbEntry.move(1);
        }


        for (int i = 0; i < tutorList.size(); i++) {

            tutor = tutorList.get(i);

            fName = tutor.getFirstName();
            lName = tutor.getLastName();
            name = fName + " " + lName;
            rating = tutor.getReviewRate();
            rate = tutor.getTutorRate();

            if (tutor.isT_math()) {
                subject = "Math";
            } else if (tutor.isT_science()) {
                subject = "Science";
            } else if (tutor.isT_literature()) {
                subject = "Literature";
            } else if (tutor.isT_history()) {
                subject = "History";
            } else if (tutor.isT_musicInstrument()) {
                subject = "Musical Instruments";
            } else if (tutor.isT_musicTheory()) {
                subject = "Music Theory";
            } else {
                subject = "N/A";
            }

            switch (rate) {
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
                    rateMoneySign = "-";
                    break;
            }

            ratingCount = Math.round(rating);
            ratingString = "";
            for (int j = 0; j < ratingCount; j++) {
                ratingString += wholeStar;
            }

            if (ratingCount != 5) {
                for (int j = 0; j < 5 - ratingCount; j++) {
                    ratingString += partialStar;
                }
            }
            entry = name + "\nSubject:  " + subject + "\nRating:    " + ratingString + "\nRate:       " + rateMoneySign;

            tutorNames.add(entry);
        }
    }

}
