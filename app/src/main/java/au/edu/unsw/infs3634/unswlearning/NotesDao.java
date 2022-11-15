package au.edu.unsw.infs3634.unswlearning;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM Note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM Note where noteID == :id")
    Note getNotes(String id);

    @Query("SELECT COUNT(*) FROM Note")
    int getTableSize();

    @Delete
    void deleteNotes(Note... notes);

    @Query("DELETE FROM Note")
    void deleteAll();

    @Insert
    void insertNotes(Note... notes);
}
