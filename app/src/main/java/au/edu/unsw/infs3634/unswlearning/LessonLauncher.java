package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import au.edu.unsw.infs3634.unswlearning.API.ExerciseDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to manage launching of lesson recyclerview
 */
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
    // selected exercise group
    private String selectedGroup;
    // a list of exercise/lesson
    private List<Lesson> lessonList = new ArrayList<>();
    // a lesson table in database
    private MainDatabase lessonDb;

    /**
     * on create method for this class, sets up adapters for recyclerview and initialises it with
     * lesson data, and connects it to the recyclerview, using API calls and storing them in the a
     * database
     * @param savedInstanceState    reference to bundle object passed into on create method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view with lesson_home_page.xml
        setContentView(R.layout.exercise_page);

        //get intent that started this activity and extract string
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            // get the name of the exerciseGroup
            selectedGroup = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG, "Exercise Group name = " + selectedGroup);
            setTitle(StringUtils.capitalize(selectedGroup) + " Exercise");

            recyclerViewLesson = findViewById(R.id.rvListLesson);
            layoutManager = new LinearLayoutManager(this);
            recyclerViewLesson.setLayoutManager(layoutManager);

            // create a new empty adapter
            lessonAdapter = new LessonAdapter(this, lessonList, this);

            // create a database here
            lessonDb = Room.databaseBuilder(getApplicationContext(), MainDatabase.class, "main-database").fallbackToDestructiveMigration().build();

            /** execute an API call to get all lessons of that body part(selected muscle group)*/
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://exercises-by-api-ninjas.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ExerciseDBService service = retrofit.create(ExerciseDBService.class);
            Call<ArrayList<Lesson>> exerciseCall = service.getExerciseByBody(selectedGroup);

            exerciseCall.enqueue(new Callback<ArrayList<Lesson>>() {
                @Override
                public void onResponse(Call<ArrayList<Lesson>> call, Response<ArrayList<Lesson>> response) {
                    Log.d(TAG, "ExerciseDB API success");
                    Log.d(TAG, response.toString());
                    Log.d(TAG, String.valueOf(response.body()));
                    // get the lesson list (a list of lesson object)

                    List<Lesson> lessons = response.body();

                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            // if lessonList exist already, it means already load into database, no need to call API
                            // else need to get the youtube url of the exercise and store into LessonList too
                            if (lessonDb.lessonDao().getLessonGroupNum(selectedGroup) > 0) {
                                Log.d(TAG, "Data exist in the database, no need to load it again");
                            // need to load data into database
                            } else {
                                Log.d(TAG, "Data does not exist in the database, need to load it with group and image");
                                for (Lesson lesson: lessons) {
                                    // set group for each lesson at least
                                    lesson.setGroup(selectedGroup);
                                    Log.d(TAG, lesson.toString());
                                    lessonDb.lessonDao().insertLessons(lesson);
                                }
                            }

                            lessonList = lessonDb.lessonDao().getLessonGroup(selectedGroup);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // update the lesson list in the adapter
                                    lessonAdapter.setData(lessonList);
                                    lessonAdapter.sort(LessonAdapter.SORT_METHOD_NAME);
                                }
                            });

                        }
                    });
                }

                @Override
                public void onFailure(Call<ArrayList<Lesson>> call, Throwable t) {
                    Log.d(TAG, "API failure");
                    Log.d(TAG, t.toString());
                }
            });

            // connect the adapter to the recyclerview
            recyclerViewLesson.setAdapter(lessonAdapter);
        }
    }


    /**
     * method to launch exercise detail
     * @param msg     passed lesson name that was clicked
     */
    public void launchLesson(String msg) {
        Intent intent = new Intent(LessonLauncher.this, ExerciseDetail.class);
        intent.putExtra(INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    /**
     * method that calls launchlesson when item in recyclerview is clicked
     * @param lesson     string lesson that was clicked
     */
    @Override
    public void onItemClick(String lesson) {
        launchLesson(lesson);
    }

    /**
     * instantiates the menu for lessons
     * @param       menu for lesson recyclerview
     * @return      boolean of if menu was successfully created or if there were changes made
     */
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

    /**
     * reacts to user interaction with the menu when sorting
     * @param item      selected menu item
     * @return          boolean of if the sort was successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lessonSortName:
                lessonAdapter.sort(LessonAdapter.SORT_METHOD_NAME);
                return true;
            case R.id.lessonSortLevel:
                lessonAdapter.sort(LessonAdapter.SORT_METHOD_LEVEL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * method to get a proper search symbol
     * @param menu      menu for exercise group recyclerview
     * @return          boolean of if the search was successful
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem searchViewMenuItem = menu.findItem(R.id.lessonHomeSearch);
        SearchView mSearchView = (SearchView) searchViewMenuItem.getActionView();
        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.search_small);
        return super.onPrepareOptionsMenu(menu);
    }


}