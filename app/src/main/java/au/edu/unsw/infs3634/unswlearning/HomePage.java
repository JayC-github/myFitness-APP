package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

/**
 * class for home page of the application
 */
public class HomePage extends AppCompatActivity {
    //Strings to check intents and msgs
    private static final String TAG = "HomePage";
    private static final String INTENT_MESSAGE = "intent_message";

    private Button btnLaunchLesson;
    private Button btnLaunchQuiz;
    
    // for taking note API
    private Button btnLaunchNote;

    private String selectedGroup;
    private ImageView selectedGroupPic, hubLesson, hubQuiz, hubNote;

    private TextView user_name;

    /**
     * on create method for home page with user name
     * @param savedInstanceState    reference to bundle object passed into on create method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);


        // get the elements from the hub_page we need
        selectedGroupPic = findViewById(R.id.ivSelectedGroup);
        user_name = findViewById(R.id.user_name);
        hubLesson = findViewById(R.id.ivHubLesson);
        hubQuiz = findViewById(R.id.ivHubQuiz);
        hubNote = findViewById(R.id.ivHubNote);



        // setTitle
        setTitle("myUNSW Fitness");

        // set Group pictures and all the other pics
        selectedGroupPic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.homepage_symbol));
        user_name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        hubLesson.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lesson2));
        hubQuiz.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.quiz2));
        hubNote.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.note2));
    }

    /**
     * method to launch lesson homepage from hub
     * @param view      current view
     */
    public void startLessonHomePage(View view) {
        Intent intent = new Intent(HomePage.this, ExerciseGroupLauncher.class);
        startActivity(intent);
    }

    /**
     * method to launch quiz homepage from hub
     * @param view      current view
     */
    public void startQuizHomePage(View view) {
        Intent intent = new Intent(HomePage.this, QuizLauncher.class);
        startActivity(intent);
    }

    /**
     * method to launch notes homepage from hub
     * @param view      current view
     */
    public void startNoteHomePage(View view) {
        Intent intent = new Intent(HomePage.this, NoteLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, selectedGroup);
        startActivity(intent);
    }
}
