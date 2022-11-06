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
import java.util.ArrayList;

import au.edu.unsw.infs3634.unswlearning.API.ExerciseDBService;
import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataResponse;
import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        Log.d(TAG, "WHY IT'S NOT SHOWING IN THE logcat");

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
            // Lesson lesson = Lesson.findLesson(message);
            /** Execute an API call to get exercise detail with the exercise name */
            /** execute an API call to get all lessons of that body part(muscle)*/
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://exercises-by-api-ninjas.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ExerciseDBService service = retrofit.create(ExerciseDBService.class);
            Call<ArrayList<Lesson>> exerciseCall = service.getExerciseByName(message);

            exerciseCall.enqueue(new Callback<ArrayList<Lesson>>() {
                @Override
                public void onResponse(Call<ArrayList<Lesson>> call, Response<ArrayList<Lesson>> response) {
                    Log.d(TAG, "API success");
                    Log.d(TAG, response.toString());
                    Log.d(TAG, String.valueOf(response.body()));
                    // get the lesson here
                    //lessonList = response.body();
                    Lesson lesson = response.body().get(0);
                    // get the lesson's info, set it
                    setTitle(lesson.getName());
                    mName.setText(lesson.getName());
                    mType.setText(lesson.getType());
                    mMuscle.setText(lesson.getMuscle());
                    mEquipment.setText(lesson.getEquipment());
                    mDifficulty.setText(lesson.getDifficulty());
                    mInstructions.setText(lesson.getInstructions());

                    // Use another API to get the URL link of the name
                    /** execute an API call to get the youtube video id based on searched (muscle)*/
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl("https://www.googleapis.com/youtube/v3/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    YoutubeDataService service2 = retrofit2.create(YoutubeDataService.class);
                    // add an exercise keyword to get the actual exercise video haha
                    Call<YoutubeDataResponse> youtubeCall = service2.getVideoByName(lesson.getName() + " Exercise");

                    youtubeCall.enqueue(new Callback<YoutubeDataResponse>() {
                        @Override
                        public void onResponse(Call<YoutubeDataResponse> call, Response<YoutubeDataResponse> response) {
                            Log.d(TAG, "Second API success");
                            Log.d(TAG, response.toString());
                            Log.d(TAG, String.valueOf(response.body()));
                            String videoId = " ";

                            // again handle the Youtube exceed quota
                            if (response.code() == 200) {
                                videoId = response.body().getItems().get(0).getId().getVideoId();
                                Log.d(TAG, videoId);
                            }

                            String finalVideoId = videoId;
                            ytPlayer.initialize(api_key, new YouTubePlayer.OnInitializedListener() {
                                        // Implement two methods by clicking on red
                                        // error bulb inside onInitializationSuccess
                                        // method add the video link or the playlist
                                        // link that you want to play In here we
                                        // also handle the play and pause
                                        // functionality
                                        @Override
                                        public void onInitializationSuccess(
                                                YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b
                                        ) {
                                            youTubePlayer.loadVideo(finalVideoId);
                                            youTubePlayer.play();
                                        }

                                        // Inside onInitializationFailure
                                        // implement the failure functionality
                                        // Here we will show toast
                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                            YouTubeInitializationResult
                                                                                    youTubeInitializationResult
                                        ) {
                                            Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        @Override
                        public void onFailure(Call<YoutubeDataResponse> call, Throwable t) {
                            Log.d(TAG, "API failure");
                            Log.d(TAG, t.toString());
                        }
                    });

                }

                @Override
                public void onFailure(Call<ArrayList<Lesson>> call, Throwable t) {
                    Log.d(TAG, "API failure");
                    Log.d(TAG, t.toString());
                }
            });

        }

    }

    public void startNoteDetail(View view) {
        String group = mMuscle.getText().toString();
        Intent intent = new Intent(ExerciseDetail.this, NoteDetail.class);//not sure about where this leads tbh
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, group);
        startActivity(intent);
    }
}