package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage launching of quiz recyclerview
 */
public class QuizLauncher extends AppCompatActivity implements RecyclerViewInterface{
    private static final String TAG = "QuizLauncher";
    public static final String INTENT_MESSAGE = "intent_message";

    private RecyclerView recyclerViewQuiz;
    private QuizAdapter quizAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // a list of quizzes will generated manually
    private List<Quiz> quizList = new ArrayList<>();

    /**
     * on create method for this class, sets up adapters for recyclerview and initialises it with
     * quiz data, and connects it to the recyclerview, using locally stored arrays
     * @param savedInstanceState    reference to bundle object passed into on create method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page);

        // this message will get used for quiz database too
        setTitle("Quiz Page");

        recyclerViewQuiz = findViewById(R.id.rvListQuiz);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewQuiz.setLayoutManager(layoutManager);

        // generate a quiz list based on the muscle module;
        quizList = Quiz.getQuizzes();
        quizAdapter = new QuizAdapter(quizList, this);


        recyclerViewQuiz.setAdapter(quizAdapter);


    }

    /**
     * method that launches quiz when item in recyclerview is clicked
     * @param group     string quiz that was clicked
     */
    @Override
    public void onItemClick(String group) {
        Intent intent = new Intent(this, QuizDetail.class);
        intent.putExtra(INTENT_MESSAGE, group);
        startActivity(intent);
    }
}