package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NoteLauncher extends AppCompatActivity implements RecyclerViewInterface{
    //Strings to check intents and msgs
    private static final String TAG = "NoteLauncher";
    public static final String INTENT_MESSAGE = "intent_message";

    // implements the recyclerview object
    private RecyclerView recyclerViewNote;

    // implements the noteadapter class that handles conversion between data and recyclerView
    private NoteAdapter noteAdapter;

    // stores layoutManager
    private RecyclerView.LayoutManager layoutManager;
    private MainDatabase noteDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view with note_home_page.xml
        setContentView(R.layout.note_page);
        setTitle("Note page");

        // get handle to corresponding recyclerview
        recyclerViewNote = findViewById(R.id.rvListNote);

        // Initialise the Recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerViewNote.setLayoutManager(layoutManager);

        // Initialise the adapter with an empty arraylist
        noteAdapter = new NoteAdapter(this, new ArrayList<>(), this);


        // List<Note> finalNote = new ArrayList<>();

        noteDb = Room.databaseBuilder(getApplicationContext(), MainDatabase.class, "main-database")
                .fallbackToDestructiveMigration()
                .build();

        //create asynchronous database call using Java Runnable
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Note> allNotes = noteDb.notesDao().getAllNotes();
                //adds all notes in database to list and loops through

                //runOnUi used to run setting adapter concurrently when loading
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //setting the adapter with the temp list
                        noteAdapter.setNoteData((ArrayList<Note>) allNotes);
                        //Connect the adapter to the recyclerview
                        recyclerViewNote.setAdapter(noteAdapter);
                    }
                });

            }

        });

        recyclerViewNote.setAdapter(noteAdapter);
    }

    //method to launch note detail
    public void launchNote(String msg) {
        Intent intent = new Intent(NoteLauncher.this, NoteDetail.class);
        intent.putExtra(INTENT_MESSAGE, msg);
        intent.putExtra("FLAG", "note");
        startActivity(intent);
    }

    //calls launchNote method when item in recyclerview is clicked
    @Override
    public void onItemClick(String note) { launchNote(note); }


    //instantiates the menu for notes
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.noteSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                noteAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteAdapter.getFilter().filter(newText);
                return false;
            }

        });
        return true;
    }

    //reacts to user interaction with the menu when sorting
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.noteSortName:
                noteAdapter.sort(NoteAdapter.SORT_METHOD_NAME);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // just to get a proper search symbol
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem searchViewMenuItem = menu.findItem(R.id.noteSearch);
        SearchView mSearchView = (SearchView) searchViewMenuItem.getActionView();
        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.search_small);
        return super.onPrepareOptionsMenu(menu);
    }


}