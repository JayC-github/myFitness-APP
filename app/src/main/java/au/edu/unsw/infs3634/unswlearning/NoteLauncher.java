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
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NoteLauncher extends AppCompatActivity implements RecyclerViewInterface{
    private static final String TAG = "NoteLauncher";
    public static final String INTENT_MESSAGE = "intent_message";

    private RecyclerView recyclerViewNote;
    private NoteAdapter noteAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainDatabase noteDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_home_page);

        recyclerViewNote = findViewById(R.id.rvListNote);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewNote.setLayoutManager(layoutManager);

        noteAdapter = new NoteAdapter(this, new ArrayList<>(), this);


        List<Note> allNote = new ArrayList<>();
        List<Note> finalNote = new ArrayList<>();

        noteDb = Room.databaseBuilder(getApplicationContext(), MainDatabase.class, "main-database")
                .fallbackToDestructiveMigration()
                .build();

        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            String message = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG, "Intent Message = " + message);
            ExerciseGroup exerciseGroup = ExerciseGroup.findGroup(message);
            setTitle(exerciseGroup.getName());
            System.out.println(exerciseGroup.getName());

            recyclerViewNote.setAdapter(noteAdapter);
            Executors.newSingleThreadExecutor().execute(new Runnable() {

                @Override
                public void run() {
                    List<Note> tempNote = new ArrayList<>();
                    tempNote = noteDb.notesDao().getAllNotes();

                    for (Note note: tempNote) {
                        System.out.println(note.getNoteID());
                        System.out.println(note.getSelectedExercise());
                        System.out.println(note.getNoteTitle());


                        if (note.getSelectedExercise().toLowerCase().contains(exerciseGroup.getName().toLowerCase())) {
                            finalNote.add(note);
                        }
                    }
                    //System.out.println(finalNote.get(1).getNoteTitle());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println(finalNote.get(1).getNoteTitle());
                            noteAdapter.setNoteData((ArrayList<Note>) finalNote);
                        }
                    });

                }

            });

            //System.out.println(finalNote); //at this point finalNote is empty for some reason
            }
        }


    public void launchNote(String msg) {
        Intent intent = new Intent(NoteLauncher.this, NoteDetail.class);
        intent.putExtra(ExerciseDetail.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String note) {
        launchNote(note);
    }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.noteSortName:
                noteAdapter.sort(NoteAdapter.SORT_METHOD_NAME);
                return true;
            case R.id.noteSortCategory:
                noteAdapter.sort(NoteAdapter.SORT_METHOD_DIFFICULTY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}