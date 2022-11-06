// we can call this HomeActivity or MainActivity
package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

// RecyclerViewInterface can be used by all recyleView aye
public class HomeLauncher extends AppCompatActivity implements RecyclerViewInterface {
    // actually allows to implement view, the view object
    private RecyclerView recyclerViewHome;
    // the adapter class that handles conversion between data and recycleView
    private HomeExerciseGroupAdapter exerciseGroupAdapter;
    // can store layoutManager here or just build below
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is the place to load the home_page.xml
        setContentView(R.layout.home_page);


        // Initialise the Recycler view, this part is always similar
        recyclerViewHome = findViewById(R.id.rvListHome);
        // Not sure why use this instead of getApplicationContext()?
        // In general it's better to use this, but careful about memory leak
        layoutManager = new LinearLayoutManager(this);
        recyclerViewHome.setLayoutManager(layoutManager);

        // Initialise the adapter with a list of exercise group data
        // need to check how does it actually work
        exerciseGroupAdapter = new HomeExerciseGroupAdapter(this, ExerciseGroup.getExerciseGroup(), this);
        recyclerViewHome.setAdapter(exerciseGroupAdapter);
    }

    // can just put in the onItemClick but ok to leave it here
    public void launchLesson(String msg) {
        // HomeLauncher.this = this, (HubController = DetailActivity kinda)
        Intent intent = new Intent(HomeLauncher.this, HubController.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String group) {
        // need to change the function name from launch lesson to launch hub
        launchLesson(group);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.homeSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                exerciseGroupAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                exerciseGroupAdapter.getFilter().filter(newText);
                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeSortName:
                exerciseGroupAdapter.sort(HomeExerciseGroupAdapter.SORT_METHOD_NAME);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
