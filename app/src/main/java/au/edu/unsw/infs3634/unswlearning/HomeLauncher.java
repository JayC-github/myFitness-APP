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


public class HomeLauncher extends AppCompatActivity implements RecyclerViewInterface {
    // implements the recyclerview object
    private RecyclerView recyclerViewHome;
    // implements the exercisegroup adapter class that handles conversion between data and recyclerView
    private HomeExerciseGroupAdapter exerciseGroupAdapter;
    // stores layoutManager
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view with home_page.xml
        setContentView(R.layout.home_page);

        // get handle to corresponding recyclerview
        recyclerViewHome = findViewById(R.id.rvListHome);

        // Initialise the Recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerViewHome.setLayoutManager(layoutManager);

        // Initialise the adapter with a list of exercise group data
        exerciseGroupAdapter = new HomeExerciseGroupAdapter(this, ExerciseGroup.getExerciseGroup(), this);

        //Connect the adapter to the recyclerview
        recyclerViewHome.setAdapter(exerciseGroupAdapter);
    }

    //method to launch hub controller
    public void launchHub(String msg) {
        Intent intent = new Intent(HomeLauncher.this, HubController.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    //calls launchHub method when item in recyclerview is clicked
    @Override
    public void onItemClick(String group) {
        launchHub(group);
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
                exerciseGroupAdapter.sort(HomeExerciseGroupAdapter.SORT_METHOD_NAME);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
