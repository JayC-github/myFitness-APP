package au.edu.unsw.infs3634.unswlearning;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface NotesDao {

    @Query("SELECT * FROM Note")
    List<Note> getNotes();

    @Query("SELECT * FROM Note where noteID == :id")
    Note getNotes(int id);

    @Delete
    void deleteNotes(Note... notes);

    @Query("DELETE FROM Note")
    void deleteAll();

    @Insert
    void insertNotes(Note... notes);

}
