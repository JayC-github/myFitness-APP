package au.edu.unsw.infs3634.unswlearning;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Notes> getNotes();

    @Query("SELECT * FROM notes where noteID == :id")
    Notes getNotes(int id);

    @Delete
    void deleteNotes(Notes... notes);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Insert
    void insertNotes(Notes... notes);

}
