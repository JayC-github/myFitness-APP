package au.edu.unsw.infs3634.unswlearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

public class NoteDetail extends AppCompatActivity {

    // strings to check intents and messages
    public static final String INTENT_MESSAGE = "intent_message";
    private static final String TAG = "NoteDetail";

    private MainDatabase noteDb;


    // private TextView mNoteID;
    private EditText mNoteTitleText;
    private EditText mNoteBodyText;
    private Button mSaveBtn;
    private Button mDeleteBtn;

    // to store note ID
    private String noteID;
    // current time
    private String currentDataTime;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view with note_detail.xml
        setContentView(R.layout.note_detail);

        // get handle for view elements
        // mNoteID = findViewById(R.id.tvNoteID);
        mNoteTitleText = findViewById(R.id.editTextNoteTitle);
        mNoteBodyText = findViewById(R.id.editTextNoteBody);
        mSaveBtn = findViewById(R.id.btnConfirmNote);
        mDeleteBtn = findViewById(R.id.btnDeleteNote);

        noteDb = Room.databaseBuilder(getApplicationContext(), MainDatabase.class, "main-database")
                .fallbackToDestructiveMigration()
                .build();

        //get intent that started this activity and extract string
        Intent intent = getIntent();
        // the message will be either noteID or exercise name
        String message = intent.getStringExtra(INTENT_MESSAGE);
        String flag = intent.getStringExtra("FLAG");
        Log.d(TAG, message + flag);

//       mNoteID.setText(message);
//       mNoteMuscleGroup.setText("");
        // this is used to determine if the note detail page is open from exercise detail or note home page
        // or if it is an exercise group, if its less than 3 chars then its a note,
        // will fill fields in with note info
        if (flag.equals("note")) { // which means message = id
//            mDeleteBtn.setVisibility(View.VISIBLE);
            mSaveBtn.setText("EDIT");
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    // get the note from database by using noteID
                    Note tempNote = noteDb.notesDao().getNotes(message);
                    // updates fields in noteDetail to show note information
                    setTitle("Note for " + tempNote.getSelectedExercise());
                    mNoteTitleText.setText(tempNote.getNoteTitle());
                    mNoteBodyText.setText(tempNote.getNoteBody());
                }
            });
        } else { // flag == "exercise"
            setTitle("Add note for " + message);
            mDeleteBtn.setVisibility(View.GONE); // or can do invisible
            mSaveBtn.setText("SUBMIT");
        }

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the current Date nad time
                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
                Date date = new Date(System.currentTimeMillis());
                String currentDateTime = formatter.format(date);
                Log.d(TAG, "current date time " + currentDateTime);

                // check if the title and note are empty
                // if empty, warn user with toast message
                if (mNoteTitleText.getText().toString().length() == 0) {
                    Toast.makeText(NoteDetail.this, "Please fill in the title!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mNoteBodyText.getText().toString().length() == 0) {
                    Toast.makeText(NoteDetail.this, "Please fill in the body!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // create asynchronous database call using Java Runnable
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        int counter = noteDb.notesDao().getTableSize();
                        //similar logic to determine if flag passed is "exercise" or "note"
                        if (flag.equals("note")) {  //update note
                            noteDb.notesDao().updateNoteTitle(message, mNoteTitleText.getText().toString());
                            noteDb.notesDao().updateNoteBody(message, mNoteBodyText.getText().toString());
                            noteDb.notesDao().updateNoteTime(message, currentDateTime);
                        } else { //adds note
                            counter = counter + 1;
//                            System.out.println(counter);
                            Note newNote = new Note(String.valueOf(counter), message, mNoteTitleText.getText().toString(), mNoteBodyText.getText().toString(), currentDateTime);
                            noteDb.notesDao().insertNotes(newNote);
                            noteDb.notesDao().getAllNotes();
                        }

                    }
                });

                //once note is added/updated returns to home
                Intent intent = new Intent(NoteDetail.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // can only call it when it's from node, so message = id
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // we have the noteID from message
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        noteDb.notesDao().deleteFromNotes(message);
                    }
                });

                Intent intent = new Intent(NoteDetail.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }
}
