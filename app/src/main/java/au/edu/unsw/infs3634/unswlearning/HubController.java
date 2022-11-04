package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HubController extends AppCompatActivity {

    private static final String TAG = "HubController";
    public static final String INTENT_MESSAGE = "intent_message";

    private Button btnLaunchLesson;
    private Button btnLaunchQuiz;
    private Button btnLaunchNote;
    private TextView selectedGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_page);
        Intent intent = getIntent();
        String msg = intent.getStringExtra(INTENT_MESSAGE);
        selectedGroup = findViewById(R.id.tvHubSelectedExerciseGroup);
        selectedGroup.setText(msg);

    }

    public void startLessonHomePage(View view) {

        String group = selectedGroup.getText().toString();
        Intent intent = new Intent(HubController.this, LessonLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }

    public void startQuizHomePage(View view) {

        String group = selectedGroup.getText().toString();
        Intent intent = new Intent(HubController.this, MainActivity.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }

    public void startNoteHomePage(View view) {

        String group = selectedGroup.getText().toString();
        Intent intent = new Intent(HubController.this, NoteLauncher.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }





}
