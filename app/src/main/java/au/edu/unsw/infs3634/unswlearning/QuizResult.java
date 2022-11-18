package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * class to manage quiz results
 */
public class QuizResult extends AppCompatActivity {

    // get the home btn and all the other elements
    private Button home_btn;
    private RatingBar ratingBar;
    private TextView final_result;

    /**
     * on create method to set up the quiz with the questions, options, and score
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_results_page);

        setTitle("Result page");

        home_btn = findViewById(R.id.btnResult);
        final_result = findViewById(R.id.tvResult);
        ratingBar = findViewById(R.id.ratingBar);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("final_result");
        final_result.setText(msg);
        // get total question
        int total_question = intent.getIntExtra("total_question", 0);
        ratingBar.setNumStars(total_question);
        // get the total score
        int score = intent.getIntExtra("total_score", 0);
        ratingBar.setRating(score);

        /**
         * method to return user to home page after theyve finished viewing their results
         */
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizResult.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });



    }
}