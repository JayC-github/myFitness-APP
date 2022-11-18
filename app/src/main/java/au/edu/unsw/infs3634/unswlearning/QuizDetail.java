package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * The QuizDetail class is used to update the corresponding .XML file
 * with the correct quiz details
 */
public class QuizDetail extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "QuizDetail";
    public static final String INTENT_MESSAGE = "intent_message";

    // get all the quiz element
    private TextView question, progress, score;
    private ImageView tick_cross;
    private Button choice1, choice2, choice3, choice4;
    private Button submitBtn;
    // a progress bar
    private ProgressBar progressBar;

    // most of the info need for quiz
    // generated quiz from the Quiz class
    private Quiz quiz;
    // number of question in the quiz, for checking if the quiz reach the end
    private int totalQuestion;
    // flag is the current question index, start from 0
    private int flag = 0;
    // current answer ID
    private int currentAnswerID;
    // selectedOption and selectedOptionID to check and compare with the answer
    private String selectedOption = "";
    private int selectedOptionID;
    // total score get from the quiz
    private int total_score = 0;
    // And the selection history of the quiz
    // ideally it would be: selectedOption, correctOption
    private List<Integer> answer_history = new ArrayList<Integer>();

    /**
     * oncreate method to assign views and update them with corresponding quiz questions using intent
     * from QuizLauncher and quizzes stored and created locally and load progress bar and score
     * @param savedInstanceState    reference to bundle object passed into on create method
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_question_page);

        Intent intent = getIntent();
        String quizName = intent.getStringExtra(INTENT_MESSAGE);
        setTitle(quizName);

        // start getting a list of data
        // get the quiz and the lenght of quiz
        quiz = Quiz.getQuiz(quizName);
        totalQuestion = quiz.getQuestions().length;

        // get the text view n button
        question = findViewById(R.id.questionText);
        progress = findViewById(R.id.question_index);
        score = findViewById(R.id.total_score);
        // get the progress Bar, set initial
        progressBar = findViewById(R.id.quizProgBar);
        progressBar.setMax(totalQuestion);
        progressBar.setProgress(0);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        tick_cross = findViewById(R.id.ivTickCross);
        submitBtn = findViewById(R.id.quiz_submit);


        // make if default color
        // set all color default color
        // set tickCross to empty
        choice1.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice2.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice3.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice4.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

        // make the text of choice not all capital letter
        choice1.setTransformationMethod(null);
        choice2.setTransformationMethod(null);
        choice3.setTransformationMethod(null);
        choice4.setTransformationMethod(null);

        // important part
        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        submitBtn.setOnClickListener(this);


        // load question
        loadNewQuestion();
    }

    /**
     * method to show correct and incorrect options to user after they click submit,
     * and move onto next question or finish the quiz
     */
    private void showSolution() {
        choice1.setClickable(false);
        choice2.setClickable(false);
        choice3.setClickable(false);
        choice4.setClickable(false);

        choice1.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice2.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice3.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice4.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

        switch (quiz.getAnswers()[flag]) {
            case 0:
                choice1.setBackground(ContextCompat.getDrawable(this, R.drawable.border_correct));
                break;
            case 1:
                choice2.setBackground(ContextCompat.getDrawable(this, R.drawable.border_correct));
                break;
            case 2:
                choice3.setBackground(ContextCompat.getDrawable(this, R.drawable.border_correct));
                break;
            case 3:
                choice4.setBackground(ContextCompat.getDrawable(this, R.drawable.border_correct));
                break;
        }

        if (selectedOptionID != quiz.getAnswers()[flag]) {
            switch (selectedOptionID) {
                case 0:
                    choice1.setBackground(ContextCompat.getDrawable(this, R.drawable.border_wrong));
                    break;
                case 1:
                    choice2.setBackground(ContextCompat.getDrawable(this, R.drawable.border_wrong));
                    break;
                case 2:
                    choice3.setBackground(ContextCompat.getDrawable(this, R.drawable.border_wrong));
                    break;
                case 3:
                    choice4.setBackground(ContextCompat.getDrawable(this, R.drawable.border_wrong));
                    break;
            }
        }

    }

    /**
     * method to laod next question or finish the quiz if there are no questions left
     */
    private void loadNewQuestion() {
        if (flag == totalQuestion) {
            Log.d(TAG, answer_history.toString());
            finishQuiz();
            return;
        }

        progress.setText("Question " + String.valueOf(flag + 1) + " of " + String.valueOf(totalQuestion));
        score.setText("Total score: " + String.valueOf(total_score) + "/" + String.valueOf(totalQuestion));
        question.setText(quiz.getQuestions()[flag]);
        choice1.setText(quiz.getOptions()[flag * 4]);
        choice2.setText(quiz.getOptions()[flag * 4 + 1]);
        choice3.setText(quiz.getOptions()[flag * 4 + 2]);
        choice4.setText(quiz.getOptions()[flag * 4 + 3]);

        choice1.setClickable(true);
        choice2.setClickable(true);
        choice3.setClickable(true);
        choice4.setClickable(true);
        submitBtn.setText("SUBMIT");
        tick_cross.setImageDrawable(null);

    }

    /**
     * method to end quiz once end is reached
     */
    private void finishQuiz() {
        // new AlertDialog.Builder(this).setTitle("Game done").show();
        Intent intent = new Intent(this, QuizResult.class);
        intent.putExtra("final_result", "Congratulations! Your Score is " + total_score + " out of " + totalQuestion);
        intent.putExtra("total_score", total_score);
        intent.putExtra("total_question", totalQuestion);
        startActivity(intent);
    }

    /**
     * on click methods for user interaction with quiz
     * @param view      current view
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View view) {
        // set all color default color
        choice1.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice2.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice3.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        choice4.setBackground(ContextCompat.getDrawable(this, R.drawable.border));


        Button clickBtn = (Button) view;
        // user clicked the submit btn
        // need to check if it's submit or next
        if (clickBtn.getId() == R.id.quiz_submit) {
            Log.d(TAG, "Click submit/next btn");
            Log.d(TAG, clickBtn.getText().toString());
            // if the btn is submit
            if (clickBtn.getText().toString().equals("SUBMIT")) {
                // make sure user select an option
                if (selectedOption == "") {
                    Toast.makeText(QuizDetail.this, "Please choose an option!", Toast.LENGTH_SHORT).show();
                } else {
                    // COMPARE the answer
                    Log.d(TAG, selectedOption);
                    Log.d(TAG, String.valueOf(selectedOptionID));
                    Log.d(TAG, String.valueOf(quiz.getAnswers()[flag]));

                    currentAnswerID = quiz.getAnswers()[flag];

                    // store the selected answer and correct answer into the history for result page
                    answer_history.add(selectedOptionID);
                    answer_history.add(currentAnswerID);

                    // compare the selected option with correct answer
                    if (selectedOptionID == currentAnswerID) {
                        total_score++;
                        Log.d(TAG, "Answer correct ++" + total_score);
                        tick_cross.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tick));
                        score.setText("Total score: " + String.valueOf(total_score) + "/" + String.valueOf(totalQuestion));

                    } else {
                        Log.d(TAG, "Answer not correct");
                        tick_cross.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cross));
                    }

                    // And also update the progress
                    progressBar.setProgress(flag + 1);

                    // check if this question is the last question
                    // if Yes, after click submit should show Finish instead of next
                    if (flag == totalQuestion - 1) {
                        clickBtn.setText("FINISH");
                    } else {
                        clickBtn.setText("NEXT");
                    }

                    // show the correct solution and wrong selection
                    showSolution();
                }
            // if not submit, it means either final or next
            } else {
                // go to next question
                // need to clean up the selectedOption?
                // need to reset lots of stuff?
                flag++;
                // clean up the selectedOption and OptionID (tho not really necessary for OptionID)
                selectedOption = "";
                selectedOptionID = -1;
                // clickBtn.setText("SUBMIT");
                loadNewQuestion();
            }
        //  the clicked btn is not submit but choices' btn
        } else {
            // just store the selected Option here, it's actually useful for checking
            // if the user selected an option or not
            selectedOption = clickBtn.getText().toString();
            // update the selectedOptionID from
            selectedOptionID = Integer.parseInt(clickBtn.getTag().toString());
            Log.d(TAG, String.valueOf(selectedOptionID));
            // also change the color from grey to purple
            clickBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.border3));
        }
    }
}