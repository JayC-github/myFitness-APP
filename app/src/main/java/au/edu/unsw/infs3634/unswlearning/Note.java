package au.edu.unsw.infs3634.unswlearning;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Note {
    @PrimaryKey
    @NonNull

    private int noteID;
    private String selectedExercise;
    private String noteBody;
    private String noteTitle;

    public Note(int noteID, String selectedExercise, String noteBody, String noteTitle) {
        this.noteID = noteID;
        this.selectedExercise = selectedExercise;
        this.noteBody= noteBody;
        this.noteTitle = noteTitle;
    }



    public Note() {}

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
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


}
