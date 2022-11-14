package au.edu.unsw.infs3634.unswlearning;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//database for notes declared wiht corresponding Dao
@Database(entities = {Note.class}, version = 1)

public abstract class NotesDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
}
