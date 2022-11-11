package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class QuizResult extends AppCompatActivity {

    // get the home btn and all the other elements
    private Button home_btn;
    private RatingBar ratingBar;
    private TextView final_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_results_page);

        home_btn = findViewById(R.id.btnResult);
        final_result = findViewById(R.id.tvResult);
        ratingBar = findViewById(R.id.ratingBar);

        // get the final message, and that's all.
        Intent intent = getIntent();
        String msg = intent.getStringExtra("final_result");
        final_result.setText(msg);
        // get total question
        int total_question = intent.getIntExtra("total_question", 0);
        Log.d("QuizResult", "Is it chaing the rating bar?" + total_question);
        ratingBar.setNumStars(total_question);
        // get the total score
        int score = intent.getIntExtra("total_score", 0);
        ratingBar.setRating(score);


        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizResult.this, HomeLauncher.class);
                startActivity(intent);
                finish();
            }
        });

        // set it not changeable
        //ratingBar.setIsIndicator(true);


//        home_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(QuizResult.this, HomeLauncher.class);
//                startActivity(intent);
//            }
//        });

    }
}