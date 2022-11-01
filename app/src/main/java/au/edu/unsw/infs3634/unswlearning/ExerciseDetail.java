package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class ExerciseDetail extends YouTubeBaseActivity {

    String api_key = "AIzaSyCvTBA8V3W0z7v8m9aTMj_U0EoEEpKIFEk";

    public static final String INTENT_MESSAGE = "intent_message";
    private static final String TAG = "ExerciseDetail";

    private TextView mName;
    private TextView mType;
    private TextView mMuscle;
    private TextView mEquipment;
    private TextView mDifficulty;
    private TextView mInstructions;
    private Button mNoteLaunch;
    //private YouTubePlayerView mPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail);

        mName = findViewById(R.id.tvExerciseName);
        mType = findViewById(R.id.tvExerciseType);
        mMuscle = findViewById(R.id.tvExerciseMuscle);
        mEquipment = findViewById(R.id.tvExerciseEquipment);
        mDifficulty = findViewById(R.id.tvExerciseDifficulty);
        mInstructions = findViewById(R.id.tvExerciseInstruction);
        mNoteLaunch = findViewById(R.id.btnTakeNote);
        //mPlayer = findViewById(R.id.ytPlayer);



        // Get reference to the view of Video player
        YouTubePlayerView ytPlayer = (YouTubePlayerView)findViewById(R.id.ytPlayer);



        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            String message = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG, "Intent Message = " + message);
            Lesson lesson = Lesson.findLesson(message);
            if(lesson != null) {
                setTitle(lesson.getName());
                mName.setText(lesson.getName());
                mType.setText(lesson.getType());
                mMuscle.setText(lesson.getMuscle());
                mEquipment.setText(lesson.getEquipment());
                mDifficulty.setText(lesson.getDifficulty());
                mInstructions.setText(lesson.getInstructions());

                ytPlayer.initialize(
                        api_key,
                        new YouTubePlayer.OnInitializedListener() {
                            // Implement two methods by clicking on red
                            // error bulb inside onInitializationSuccess
                            // method add the video link or the playlist
                            // link that you want to play In here we
                            // also handle the play and pause
                            // functionality
                            @Override
                            public void onInitializationSuccess(
                                    YouTubePlayer.Provider provider,
                                    YouTubePlayer youTubePlayer, boolean b)
                            {
                                youTubePlayer.loadVideo(lesson.getVideoLink());
                                youTubePlayer.play();
                            }

                            // Inside onInitializationFailure
                            // implement the failure functionality
                            // Here we will show toast
                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                YouTubeInitializationResult
                                                                        youTubeInitializationResult)
                            {
                                Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            }

        }

    }

    public void startNoteDetail(View view) {
        String group = mMuscle.getText().toString();
        Intent intent = new Intent(ExerciseDetail.this, NoteDetail.class);//not sure about where this leads tbh
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }
}