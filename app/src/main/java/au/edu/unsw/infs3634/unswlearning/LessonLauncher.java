package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.unswlearning.API.ExerciseDBService;
import au.edu.unsw.infs3634.unswlearning.API.VideoItem;
import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataResponse;
import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonLauncher extends AppCompatActivity implements RecyclerViewInterface {
    //Strings to check intents and msgs
    private static final String TAG = "LessonLauncher";
    public static final String INTENT_MESSAGE = "intent_message";

    // implements the recyclerview object
    private RecyclerView recyclerViewLesson;

    // implements the lessonadapter class that handles conversion between data and recyclerView
    private LessonAdapter lessonAdapter;

    // stores layoutManager
    private RecyclerView.LayoutManager layoutManager;

    // an empty list of lessons
    private List<Lesson> lessonList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view with lesson_home_page.xml
        setContentView(R.layout.lesson_home_page);

        //get intent that started this activity and extract string
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            // get the name of the exerciseGroup
            String message = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG, "Exercise Group name = " + message);

            //find exercise group of the selected exercise from previous launcher
            ExerciseGroup exerciseGroup = ExerciseGroup.findGroup(message);
            if (exerciseGroup != null) {
                Log.d(TAG, "exerciseGroup exist");
                //update activity title with extracted string
                setTitle(exerciseGroup.getName());

                //get handle to corresponding recyclerview
                recyclerViewLesson = findViewById(R.id.rvListLesson);

                //initialise the recyclerview layout manager
                layoutManager = new LinearLayoutManager(this);
                recyclerViewLesson.setLayoutManager(layoutManager);


                //initialise the adapter with an empty list
                lessonAdapter = new LessonAdapter(this, lessonList, this);

                /** execute an API call to get all lessons of that body part(muscle)*/
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://exercises-by-api-ninjas.p.rapidapi.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ExerciseDBService service = retrofit.create(ExerciseDBService.class);
                Call<ArrayList<Lesson>> exerciseCall = service.getExerciseByBody(message);

                exerciseCall.enqueue(new Callback<ArrayList<Lesson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Lesson>> call, Response<ArrayList<Lesson>> response) {
                        Log.d(TAG, "API success");
                        Log.d(TAG, response.toString());
                        Log.d(TAG, String.valueOf(response.body()));
                        // get the lessonlist here
                        lessonList = response.body();

                        // update the lesson list in the adapter
                        lessonAdapter.setData(lessonList);
                        lessonAdapter.sort(LessonAdapter.SORT_METHOD_NAME);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Lesson>> call, Throwable t) {
                        Log.d(TAG, "API failure");
                        Log.d(TAG, t.toString());
                    }
                });

                //adding in divider to recyclerview
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
                recyclerViewLesson.addItemDecoration(dividerItemDecoration);

                //connect the adapter to the recyclerview
                recyclerViewLesson.setAdapter(lessonAdapter);
            }
        }
    }

    //method to launch exercise detail
    public void launchLesson(String msg) {
        Intent intent = new Intent(LessonLauncher.this, ExerciseDetail.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    //calls launchLesson method when item in recyclerview is clicked
    @Override
    public void onItemClick(String lesson) {
        launchLesson(lesson);
    }

    //instantiates the menu for lessons
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

    //reacts to user interaction with the menu when sorting
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lessonSortName:
                lessonAdapter.sort(LessonAdapter.SORT_METHOD_NAME);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}