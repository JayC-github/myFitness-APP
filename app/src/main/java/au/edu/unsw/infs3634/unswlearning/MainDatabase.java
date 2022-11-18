package au.edu.unsw.infs3634.unswlearning;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class, Lesson.class}, version = 4)

/**
 * Room database for notes and lessons
 */
public abstract class MainDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
    public abstract LessonDao lessonDao();
}
