package au.edu.unsw.infs3634.unswlearning;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

//note object declaration
@Entity
public class Note {
    @PrimaryKey
    @NonNull
    private String noteID;
    private String selectedExercise;
    private String noteBody;
    private String noteTitle;

    public Note(String noteID, String selectedExercise, String noteTitle, String noteBody) {
        this.noteID = noteID;
        this.selectedExercise = selectedExercise;
        this.noteTitle = noteTitle;
        this.noteBody= noteBody;

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




}
