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

    @Query("SELECT * FROM Note where noteTitle == :title")
    Note getNoteTitle(String title);

    @Query("SELECT COUNT(*) FROM Note")
    int getTableSize();

    @Delete
    void deleteNotes(Note... notes);

    @Query("DELETE FROM Note")
    void deleteAll();

    @Query("DELETE FROM Note where noteID == :id")
    void deleteFromNotes(String id);

    @Insert
    void insertNotes(Note... notes);

    @Query("UPDATE Note SET noteTitle = :title where noteId == :id")
    void updateNoteTitle(String id, String title);

    @Query("UPDATE Note SET noteBody = :body where noteId == :id")
    void updateNoteBody(String id, String body);

}
