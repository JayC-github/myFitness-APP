package au.edu.unsw.infs3634.unswlearning;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Note object declaration
 */
@Entity
public class Note {
    @PrimaryKey
    @NonNull
    private String noteID;
    private String selectedExercise;
    private String noteBody;
    private String noteTitle;
    private String latestUpdate;

    /**
     * constructor for notes
     * @param noteID            unique identifier for note
     * @param selectedExercise  which exercise the note was made for
     * @param noteTitle         note title
     * @param noteBody          note contents
     * @param latestUpdate      last updated time
     */
    public Note(String noteID, String selectedExercise, String noteTitle, String noteBody, String latestUpdate) {
        this.noteID = noteID;
        this.selectedExercise = selectedExercise;
        this.noteTitle = noteTitle;
        this.noteBody= noteBody;
        this.latestUpdate = latestUpdate;
    }

    public Note() {}

    //getters and setters

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    public String getSelectedExercise() {
        return selectedExercise;
    }

    public void setSelectedExercise(String selectedExercise) {
        this.selectedExercise = selectedExercise;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public String getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(String latestUpdate) {
        this.latestUpdate = latestUpdate;
    }
}
