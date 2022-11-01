package au.edu.unsw.infs3634.unswlearning;

import androidx.room.RoomDatabase;

public abstract class NotesDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
}
