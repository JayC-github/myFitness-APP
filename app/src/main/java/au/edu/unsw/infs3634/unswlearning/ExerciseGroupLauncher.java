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

// RecyclerViewInterface will be used by all Launcher need RecyclerView
public class ExerciseGroupLauncher extends AppCompatActivity implements RecyclerViewInterface {
    // actually allows to implement view, the view object
    private RecyclerView recyclerViewHome;
    // the adapter class that handles conversion between data and recycleView
    private ExerciseGroupAdapter exerciseGroupAdapter;
    // can store layoutManager here or just build below
    private RecyclerView.LayoutManager layoutManager;
    // intent message
    private static final String INTENT_MESSAGE = "intent_message";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load the home_page.xml
        setContentView(R.layout.home_page);

        // Initialise the Recycler view, this part is always similar
        recyclerViewHome = findViewById(R.id.rvListHome);
        // Not sure why use this instead of getApplicationContext()?
        // In general it's better to use "this", but careful about memory leak
        layoutManager = new LinearLayoutManager(this);
        recyclerViewHome.setLayoutManager(layoutManager);

        // Initialise the adapter with a list of exercise group data
        exerciseGroupAdapter = new ExerciseGroupAdapter(ExerciseGroup.getExerciseGroup(), this, this);
        recyclerViewHome.setAdapter(exerciseGroupAdapter);
    }

    // can just put in the onItemClick but ok to leave it here
    public void launchHub(String exerciseGroup) {
        // ExerciseGroupLauncher.this = this, (HubController = DetailActivity kinda)
        Intent intent = new Intent(ExerciseGroupLauncher.this, HubController.class);
        intent.putExtra(INTENT_MESSAGE, exerciseGroup);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String exerciseGroup) {
        // click each exercise group -> load to exercise group hub
        launchHub(exerciseGroup);
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
                exerciseGroupAdapter.sort(ExerciseGroupAdapter.SORT_METHOD_NAME);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
