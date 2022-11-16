package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.apache.commons.lang3.StringUtils;

public class HubController extends AppCompatActivity {
    //Strings to check intents and msgs
    private static final String TAG = "HubController";
    private static final String INTENT_MESSAGE = "intent_message";

    private Button btnLaunchLesson;
    private Button btnLaunchQuiz;
    
    // for taking note API
    private Button btnLaunchNote;
    //private TextView selectedGroup;
    
    // selectedGroup -> Image, name
    private String selectedGroup;
    private ImageView selectedGroupPic, hubLesson, hubQuiz, hubNote;

    private TextView selectedGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_page);


        // get the elements from the hub_page we need
        selectedGroupPic = findViewById(R.id.ivSelectedGroup);
        hubLesson = findViewById(R.id.ivHubLesson);
        hubQuiz = findViewById(R.id.ivHubQuiz);
        hubNote = findViewById(R.id.ivHubNote);


        // get the name of the exercise group, set selectGroup, image and name
        Intent intent = getIntent();
        selectedGroup = intent.getStringExtra(INTENT_MESSAGE);

        // setTitle
        setTitle(StringUtils.capitalize(selectedGroup));

        // set Group pictures and all the other pics
        selectedGroupPic.setImageResource(getResources().getIdentifier(selectedGroup,
                "drawable", "au.edu.unsw.infs3634.unswlearning"));
        hubLesson.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lesson2));
        hubQuiz.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.quiz2));
        hubNote.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.note2));



    }

    //method to launch lesson homepage from hub
    public void startLessonHomePage(View view) {
        Intent intent = new Intent(HubController.this, LessonLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, selectedGroup);
        startActivity(intent);
    }

    //method to launch quiz homepage from hub
    public void startQuizHomePage(View view) {
        Intent intent = new Intent(HubController.this, QuizLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, selectedGroup);
        startActivity(intent);
    }

    //method to launch note homepage from hub
    public void startNoteHomePage(View view) {
        Intent intent = new Intent(HubController.this, NoteLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, selectedGroup);
        startActivity(intent);
    }
}
