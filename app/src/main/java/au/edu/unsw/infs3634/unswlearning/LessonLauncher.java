package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.unswlearning.API.ExerciseDBResponse;
import au.edu.unsw.infs3634.unswlearning.API.ExerciseDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonLauncher extends AppCompatActivity implements RecyclerViewInterface{
    private static final String TAG = "LessonLauncher";
    public static final String INTENT_MESSAGE = "intent_message";

    private RecyclerView recyclerViewLesson;
    private LessonAdapter lessonAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // a list of exercise/lesson
    private List<Lesson> lessonList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_home_page);

        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            // get the name of the exerciseGroup
            String message = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG, "Exercise Group name = " + message);


            ExerciseGroup exerciseGroup = ExerciseGroup.findGroup(message);
            if (exerciseGroup != null) {
                Log.d(TAG, "exerciseGroup exist");
                setTitle(exerciseGroup.getName());

                // this part is always similar
                recyclerViewLesson = findViewById(R.id.rvListLesson);
                layoutManager = new LinearLayoutManager(this);
                recyclerViewLesson.setLayoutManager(layoutManager);


                // create exercises' list of the exercise group
                // get all manually created lessons for now
                ArrayList<Lesson> allLesson = Lesson.getLesson();
                // tempLesson
                // ArrayList<Lesson> tempLesson = new ArrayList<>();

                for (Lesson lesson: allLesson) {
                    if (lesson.getMuscle().toLowerCase().contains(message.toLowerCase())) {
                        lessonList.add(lesson);
                    }
                }

                /** execute an API call to get all lessons of that body part(muscle)*/
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://exercisedb.p.rapidapi.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ExerciseDBService service = retrofit.create(ExerciseDBService.class);
                Call<ExerciseDBResponse> exerciseCall =service.getExercisebyBody(message);

                exerciseCall.enqueue(new Callback<ExerciseDBResponse>() {
                    @Override
                    public void onResponse(Call<ExerciseDBResponse> call, Response<ExerciseDBResponse> response) {
                        Log.d(TAG, "API success");
                        // List<Lesson> lessons = response.body().getData();
                    }

                    @Override
                    public void onFailure(Call<ExerciseDBResponse> call, Throwable t) {
                        Log.d(TAG, "API failure");
                    }
                });


                lessonAdapter = new LessonAdapter(this, lessonList, this);

                recyclerViewLesson.setAdapter(lessonAdapter);
            }
        }







    }


    public void launchLesson(String msg) {
        Intent intent = new Intent(LessonLauncher.this, ExerciseDetail.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String lesson) {
        launchLesson(lesson);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lesson_home_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.lessonHomeSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lessonAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lessonAdapter.getFilter().filter(newText);
                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lessonSortName:
                lessonAdapter.sort(LessonAdapter.SORT_METHOD_NAME);
                return true;
            case R.id.lessonSortDifficulty:
                lessonAdapter.sort(LessonAdapter.SORT_METHOD_DIFFICULTY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}