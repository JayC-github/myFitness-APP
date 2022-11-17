package au.edu.unsw.infs3634.unswlearning;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    //Return list of all notes from the table
    @Query("SELECT * FROM Note")
    List<Note> getAllNotes();

    //Return specific note with provided noteID
    @Query("SELECT * FROM Note where noteID == :id")
    Note getNotes(String id);

    //Return specific note with provided note title
    @Query("SELECT * FROM Note where noteTitle == :title")
    Note getNoteTitle(String title);

    //Return the number of notes stored in the table
    @Query("SELECT COUNT(*) FROM Note")
    int getTableSize();

    //Delete note(s) from table
    @Delete
    void deleteNotes(Note... notes);

    //Delete all notes from table using @Query
    @Query("DELETE FROM Note")
    void deleteAll();

    //Delete note from table with provided noteID
    @Query("DELETE FROM Note where noteID == :id")
    void deleteFromNotes(String id);

    //Insert note(s) into notes table
    @Insert
    void insertNotes(Note... notes);

    //Updates existing note title with provided noteID
    @Query("UPDATE Note SET noteTitle = :title where noteId == :id")
    void updateNoteTitle(String id, String title);

    //Updates existing note body with provided noteID
    @Query("UPDATE Note SET noteBody = :body where noteId == :id")
    void updateNoteBody(String id, String body);

    //Updates existing note time with provided noteID
    @Query("UPDATE Note SET latestUpdate = :currentDateTime where noteId == :id")
    void updateNoteTime(String id, String currentDateTime);
}
