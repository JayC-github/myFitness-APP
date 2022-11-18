package au.edu.unsw.infs3634.unswlearning;

import java.sql.Array;
import java.util.ArrayList;

/**
 * ExerciseGroup object declaration
 */
public class ExerciseGroup {

    private String name;

    //getters and setters
    public ExerciseGroup(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    /**
     * method to return a list of hardcoded exercise groups for our prototype,
     * we chose the most relevant groups
     * @return      ArrayList of hardcoded exercise groups
     */
    public static ArrayList<ExerciseGroup> getExerciseGroup() {
        ArrayList<ExerciseGroup> groups = new ArrayList<>();
        groups.add(new ExerciseGroup("abdominals"));
        groups.add(new ExerciseGroup("abductors"));
        groups.add(new ExerciseGroup("biceps"));
        groups.add(new ExerciseGroup("calves"));
        groups.add(new ExerciseGroup("chest"));
        groups.add(new ExerciseGroup("forearms"));
        groups.add(new ExerciseGroup("glutes"));
        groups.add(new ExerciseGroup("lats"));
        groups.add(new ExerciseGroup("neck"));
        groups.add(new ExerciseGroup("quadriceps"));
        groups.add(new ExerciseGroup("traps"));
        groups.add(new ExerciseGroup("triceps"));

        return groups;

    }

    /**
     * method to return a specific exercise group based on a searched query
     * @param query     the search term
     * @return          the matching ExerciseGroup
     */
    public static ExerciseGroup findGroup(String query) {
        ArrayList<ExerciseGroup> groups = ExerciseGroup.getExerciseGroup();
        for (final ExerciseGroup group: groups) {
            if(group.getName().toLowerCase().equals(query.toLowerCase())) {
                return group;
            }
        }
        return null;
    }

}
