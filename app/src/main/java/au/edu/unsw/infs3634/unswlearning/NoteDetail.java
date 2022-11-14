package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.security.PrivateKey;
import java.util.concurrent.Executors;

public class NoteDetail extends AppCompatActivity {

    //Strings to check intents and msgs
    public static final String INTENT_MESSAGE = "intent_message";
    private static final String TAG = "NoteDetail";

    private NotesDatabase noteDb;


    private TextView mNoteID;
    private TextView mNoteMuscleGroup;
    private TextView mNoteTitleHeader;
    private TextView mNoteBodyHeader;
    private EditText mNoteTitleText;
    private EditText mNoteBodyText;
    private Button mSaveNote;
    private Button mDeleteNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view with note_detail.xml
        setContentView(R.layout.note_detail);

        //get handle for view elements
        mNoteID = findViewById(R.id.tvNoteID);
        mNoteMuscleGroup = findViewById(R.id.tvNoteMuscle);
        mNoteTitleHeader = findViewById(R.id.tvNoteTitleHeading);
        mNoteBodyHeader = findViewById(R.id.tvNoteBodyHeading);
        mNoteTitleText = findViewById(R.id.editTextNoteTitle);
        mNoteBodyText = findViewById(R.id.editTextNoteBody);
        mSaveNote = findViewById(R.id.btnConfirmNote);
        mDeleteNote = findViewById(R.id.btnDeleteNote);

        //instantiate new maindatabase object for "main-database"
        noteDb = Room.databaseBuilder(getApplicationContext(), NotesDatabase.class, "notes-database")
                .fallbackToDestructiveMigration()
                .build();

        //get intent that started this activity and extract string
        Intent intent = getIntent();
        String message = intent.getStringExtra(INTENT_MESSAGE);

        mNoteID.setText(message);
        mNoteMuscleGroup.setText("");

        //this is used to determine if the message passed is a note id
        //or if it is an exercise group, if its less than 3 chars then its a note,
        //will fill fields in with note info
        if (message.length() <= 3) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {

                @Override
                public void run() {
                    Note tempNote = noteDb.notesDao().getNotes(message);
                    //updates fields in notedetail to show note information
                    mNoteMuscleGroup.setText(tempNote.getSelectedExercise());
                    mNoteTitleText.setText(tempNote.getNoteTitle());
                    mNoteBodyText.setText(tempNote.getNoteBody());
                }
            });
        }



    }

    //method to add note to database
    public void confirmNote(View view) {

        //create asynchronous database call using Java Runnable
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                int idCounter = noteDb.notesDao().getTableSize();
                //similar logic to determine if message passed is noteID used as above
                if (mNoteID.getText().length() <= 3){  //update note
                    noteDb.notesDao().updateNoteTitle(mNoteID.getText().toString(), mNoteTitleText.getText().toString());
                    noteDb.notesDao().updateNoteBody(mNoteID.getText().toString(), mNoteBodyText.getText().toString());
                    System.out.println(mNoteID.getText().length());
                } else { //adds note
                    idCounter = idCounter + 1;
                    System.out.println(idCounter);
                    Note newNote = new Note(String.valueOf(idCounter), mNoteID.getText().toString(), mNoteTitleText.getText().toString(), mNoteBodyText.getText().toString());
                    noteDb.notesDao().insertNotes(newNote);
                    noteDb.notesDao().getAllNotes();
                }

            }
        });
        Log.d(TAG, "note added");

        //once note is added/updated returns to home
        Intent intent = new Intent(NoteDetail.this, HomeLauncher.class);
        startActivity(intent);

    }

    //method to delete selected note from database and return to home
    public void deleteNote(View view) {
        String message = (String) mNoteID.getText();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                noteDb.notesDao().deleteFromNotes(message);
            }
        });

        Intent intent = new Intent(NoteDetail.this, HomeLauncher.class);
        startActivity(intent);
    }


}
