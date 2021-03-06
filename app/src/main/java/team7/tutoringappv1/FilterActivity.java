// FilterActivity.java

package team7.tutoringappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {

    String distanceFilter;
    String priceFilter;
    String maxDistance;
    String maxPrice;
    String minRating;

    RatingBar ratingBar;
    TextView txtRatingValue;
    TextView txtDistanceValue;
    TextView txtPriceValue;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        txtPriceValue = (TextView) findViewById(R.id.txtPriceValue);

        Button btn$ = (Button) findViewById(R.id.btn$);
        btn$.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply btn logic here
                priceFilter = "$";
                maxPrice = "1";
                txtPriceValue.setText("≤ " + priceFilter);            }
        });

        Button btn$$ = (Button) findViewById(R.id.btn$$);
        btn$$.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply btn logic here
                priceFilter = "$$";
                maxPrice = "2";
                txtPriceValue.setText("≤ " + priceFilter);            }
        });

        Button btn$$$ = (Button) findViewById(R.id.btn$$$);
        btn$$$.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply btn logic here
                priceFilter = "$$$";
                maxPrice = "3";
                txtPriceValue.setText("≤ " + priceFilter);
            }
        });


        txtDistanceValue = (TextView) findViewById(R.id.txtDistanceValue);

        Button btn5Miles = (Button) findViewById(R.id.btn5Miles);
        btn5Miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply filter logic here
                distanceFilter = "5 Miles";
                maxDistance = "5";
                txtDistanceValue.setText("≤ " + distanceFilter);
            }
        });

        Button btn15Miles = (Button) findViewById(R.id.btn15Miles);
        btn15Miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply filter logic here
                distanceFilter = "15 Miles";
                maxDistance = "15";
                txtDistanceValue.setText("≤ " + distanceFilter);
            }
        });

        Button btn25Miles = (Button) findViewById(R.id.btn25Miles);
        btn25Miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply filter logic here
                distanceFilter = "25 Miles";
                maxDistance = "25";
                txtDistanceValue.setText("≤ " + distanceFilter);
            }
        });

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                minRating = String.valueOf(rating);
                txtRatingValue.setText("≥ " + String.valueOf(rating));

            }
        });


        Button btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply filter logic here
                // adding the variables to pass onto the TutorsListActivity to check for the filter
                bundle = new Bundle();
                if (maxPrice != null)
                    bundle.putString("maxPrice", maxPrice);
                if (maxDistance != null)
                    bundle.putString("maxDistance", maxDistance);
                if (minRating != null)
                    bundle.putString("minRating", minRating);
                TutorListActivity.listActivity.finish();
                Intent intent = new Intent(view.getContext(), TutorListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

}
