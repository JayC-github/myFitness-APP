package au.edu.unsw.infs3634.unswlearning;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey
    @NonNull

    private String noteID;
    private String selectedExercise;
    private String note;

    public Note(String noteID, String selectedExercise, String note) {
        this.noteID = noteID;
        this.selectedExercise = selectedExercise;
        this.note = note;
    }

    public Note() {}

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
