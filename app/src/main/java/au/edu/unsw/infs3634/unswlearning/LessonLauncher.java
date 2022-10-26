package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LessonLauncher extends AppCompatActivity implements RecyclerViewInterface{
    private static final String TAG = "LessonLauncher";
    private RecyclerView recyclerViewLesson;
    private LessonAdapter lessonAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_home_page);
        recyclerViewLesson = findViewById(R.id.rvListLesson);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewLesson.setLayoutManager(layoutManager);

        lessonAdapter = new LessonAdapter(this, Lesson.getLesson(), this);

        recyclerViewLesson.setAdapter(lessonAdapter);
    }


    public void launchLesson(String msg) {
        Intent intent = new Intent(LessonLauncher.this, YoutubeAPI.class);
        intent.putExtra(YoutubeAPI.INTENT_MESSAGE, msg);
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