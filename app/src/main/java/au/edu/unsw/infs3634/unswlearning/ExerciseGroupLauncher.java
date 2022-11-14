// we can call this HomeActivity or MainActivity
package au.edu.unsw.infs3634.unswlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    //implements the recyclerview object
    private RecyclerView recyclerViewHome;
    //implements the exercisegroup adapter class that handles conversion between data and recyclerview
    private ExerciseGroupAdapter exerciseGroupAdapter;
    //stores layoutManager
    private RecyclerView.LayoutManager layoutManager;
    // intent message
    private static final String INTENT_MESSAGE = "intent_message";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load the home_page.xml
        setContentView(R.layout.home_page);

        // get handle to correspoding recyclerview
        recyclerViewHome = findViewById(R.id.rvListHome);

        //initialise the recyclerview layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerViewHome.setLayoutManager(layoutManager);

        // Initialise the adapter with a list of exercise group data
        exerciseGroupAdapter = new ExerciseGroupAdapter(ExerciseGroup.getExerciseGroup(), this, this);

        //adding in divider to recyclerview
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerViewHome.addItemDecoration(dividerItemDecoration);

        //connect the adapter to the recyclerview
        recyclerViewHome.setAdapter(exerciseGroupAdapter);
    }

    //method to launch hub controller
    public void launchHub(String exerciseGroup) {
        // ExerciseGroupLauncher.this = this, (HubController = DetailActivity kinda)
        Intent intent = new Intent(ExerciseGroupLauncher.this, HubController.class);
        intent.putExtra(INTENT_MESSAGE, exerciseGroup);
        startActivity(intent);
    }

    //calls launchHub method when item in recyclerview is clicked
    @Override
    public void onItemClick(String exerciseGroup) {
        launchHub(exerciseGroup);
    }

    //instantiates the menu for exercisegroups
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

    //reacts to user interaction with the menu when sorting
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
