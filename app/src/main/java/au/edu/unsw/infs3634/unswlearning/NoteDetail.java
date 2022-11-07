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
        setContentView(R.layout.note_detail);

        mNoteID = findViewById(R.id.tvNoteID);
        mNoteMuscleGroup = findViewById(R.id.tvNoteMuscle);
        mNoteTitleHeader = findViewById(R.id.tvNoteTitleHeading);
        mNoteBodyHeader = findViewById(R.id.tvNoteBodyHeading);
        mNoteTitleText = findViewById(R.id.editTextNoteTitle);
        mNoteBodyText = findViewById(R.id.editTextNoteBody);
        mSaveNote = findViewById(R.id.btnConfirmNote);
        mDeleteNote = findViewById(R.id.btnDeleteNote);


        noteDb = Room.databaseBuilder(getApplicationContext(), NotesDatabase.class, "notes-database")
                .fallbackToDestructiveMigration()
                .build();

        Intent intent = getIntent();
        String message = intent.getStringExtra(INTENT_MESSAGE);
        mNoteID.setText(message);
        System.out.println(INTENT_MESSAGE + "hi");


        Executors.newSingleThreadExecutor().execute(new Runnable() {

            @Override
            public void run() {
                Note tempNote = noteDb.notesDao().getNotes(message);
                mNoteMuscleGroup.setText(tempNote.getSelectedExercise());
                mNoteTitleText.setText(tempNote.getNoteTitle());
                mNoteBodyText.setText(tempNote.getNoteBody());
            }
        });


    }

    public void confirmNote(View view) {


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                int idCounter = noteDb.notesDao().getTableSize();

                idCounter = idCounter + 1;
                Note newNote = new Note(String.valueOf(idCounter), mNoteMuscleGroup.getText().toString(), mNoteTitleText.getText().toString(), mNoteBodyText.getText().toString());
                noteDb.notesDao().insertNotes(newNote);
                noteDb.notesDao().getAllNotes();
            }
        });
        //if statement here if note already exists it updates the note with the new fields
        Log.d(TAG, "note added");
        Intent intent = new Intent(NoteDetail.this, HomeLauncher.class);
        startActivity(intent);

    }


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
