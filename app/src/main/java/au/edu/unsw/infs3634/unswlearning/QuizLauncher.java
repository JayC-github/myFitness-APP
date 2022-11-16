package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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
        setContentView(R.layout.quiz_page);

        // this message will get used for quiz database too
        setTitle("Quiz Page");

        // this part is always similar
        recyclerViewQuiz = findViewById(R.id.rvListQuiz);
        // or getApplicationContext() instead of this, but same thing
        layoutManager = new LinearLayoutManager(this);
        recyclerViewQuiz.setLayoutManager(layoutManager);

        // generate a quiz list based on the muscle module;
        quizList = Quiz.getQuizzes();
        quizAdapter = new QuizAdapter(quizList, this);

        // create a database here for storing all the generated data later
        // also update all the data in quizAdapter


        recyclerViewQuiz.setAdapter(quizAdapter);


    }

    @Override
    public void onItemClick(String group) {
        Intent intent = new Intent(this, QuizDetail.class);
        intent.putExtra(INTENT_MESSAGE, group);
        startActivity(intent);
    }
}