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

public class NoteDetail extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "intent_message";
    private static final String TAG = "NoteDetail";
    private int idCounter = 0;
    private NotesDatabase noteDb;

    private TextView mNoteMuscleGroup;
    private TextView mNoteTitleHeader;
    private TextView mNoteBodyHeader;
    private EditText mNoteTitleText;
    private EditText mNoteBodyText;
    private Button mSaveNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail);

        mNoteMuscleGroup = findViewById(R.id.tvNoteMuscle);
        mNoteTitleHeader = findViewById(R.id.tvNoteTitleHeading);
        mNoteBodyHeader = findViewById(R.id.tvNoteBodyHeading);
        mNoteTitleText = findViewById(R.id.editTextNoteTitle);
        mNoteBodyText = findViewById(R.id.editTextNoteBody);
        mSaveNote = findViewById(R.id.btnConfirmNote);

        noteDb = Room.databaseBuilder(getApplicationContext(), NotesDatabase.class, "notes-database").build();

    }

    public void confirmNote(View view) {

        Note newNote = new Note(idCounter, mNoteMuscleGroup.getText().toString(), mNoteTitleText.getText().toString(), mNoteBodyText.getText().toString());
        noteDb.notesDao().insertNotes(newNote);
        idCounter = idCounter + 1;
        Intent intent = new Intent(NoteDetail.this, HubController.class);
        startActivity(intent);
    }


}
