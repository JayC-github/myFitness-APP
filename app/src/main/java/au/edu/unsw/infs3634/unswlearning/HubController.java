package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HubController extends AppCompatActivity {

    private static final String TAG = "HubController";
    public static final String INTENT_MESSAGE = "intent_message";

    private Button btnLaunchLesson;
    private Button btnLaunchQuiz;
    
    // for taking note API
    private Button btnLaunchNote;
    //private TextView selectedGroup;
    
    // selectedGroup -> Image, name
    private String selectedGroup;
    private ImageView selectedGroupPic;
    // instead of calling selectedGroup, call it selectedGroupName
    private TextView selectedGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_page);

        // get the elements from the hub_page we need
        selectedGroupPic = findViewById(R.id.ivSelectedGroup);
        selectedGroupName = findViewById(R.id.tvSelectedGroup);


        // get the name of the exercise group, set selectGroup, image and name
        Intent intent = getIntent();
        // String msg = intent.getStringExtra(INTENT_MESSAGE);
        selectedGroup = intent.getStringExtra(INTENT_MESSAGE);
        // set pic and name
        selectedGroupPic.setImageResource(getResources().getIdentifier(selectedGroup,
                "drawable", "au.edu.unsw.infs3634.unswlearning"));
        selectedGroupName.setText(selectedGroup);
    }

    public void startLessonHomePage(View view) {
        String group = selectedGroupName.getText().toString();
        Intent intent = new Intent(HubController.this, LessonLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }

    public void startQuizHomePage(View view) {
        String group = selectedGroupName.getText().toString();
        Intent intent = new Intent(HubController.this, QuizLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }

    public void startNoteHomePage(View view) {
        String group = selectedGroupName.getText().toString();
        Intent intent = new Intent(HubController.this, NoteLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }





}
