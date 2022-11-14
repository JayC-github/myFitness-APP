package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizLauncher extends AppCompatActivity implements RecyclerViewInterface{
    private static final String TAG = "QuizLauncher";
    public static final String INTENT_MESSAGE = "intent_message";

    private RecyclerView recyclerViewQuiz;
    private QuizAdapter quizAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // a list of quizzes will generated manually
    private List<Quiz> quizList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_home_page);

        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            // this message will get used for quiz database too
            String message = intent.getStringExtra(INTENT_MESSAGE);
            setTitle(message + " Quiz");
            Log.d(TAG, "Exercise Group name = " + message);

            // this part is always similar
            recyclerViewQuiz = findViewById(R.id.rvListQuiz);
            // or getApplicationContext() instead of this, but same thing
            layoutManager = new LinearLayoutManager(this);
            recyclerViewQuiz.setLayoutManager(layoutManager);

            // generate a quiz list based on the muscle module;
            quizList = Quiz.getQuizzes_on_module(message);
            quizAdapter = new QuizAdapter(quizList, this);

            // create a database here for storing all the generated data later
            // also update all the data in quizAdapter

            //adding in divider to recyclerview
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
            recyclerViewQuiz.addItemDecoration(dividerItemDecoration);

            recyclerViewQuiz.setAdapter(quizAdapter);
        }

    }

    @Override
    public void onItemClick(String group) {
        Intent intent = new Intent(this, QuizDetail.class);
        intent.putExtra(INTENT_MESSAGE, group);
        startActivity(intent);
    }
}