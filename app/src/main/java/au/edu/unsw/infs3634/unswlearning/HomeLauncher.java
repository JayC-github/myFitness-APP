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

public class HomeLauncher extends AppCompatActivity implements RecyclerViewInterface{
    private RecyclerView recyclerViewHome;
    private HomeExerciseGroupAdapter exerciseGroupAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        recyclerViewHome = findViewById(R.id.rvListHome);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewHome.setLayoutManager(layoutManager);


        exerciseGroupAdapter = new HomeExerciseGroupAdapter(this, ExerciseGroup.getExerciseGroup(), this);

        recyclerViewHome.setAdapter(exerciseGroupAdapter);
    }


    public void launchYoutube(String msg) {
        Intent intent = new Intent(HomeLauncher.this, YoutubeAPI.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String group) {
        launchYoutube(group);
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
