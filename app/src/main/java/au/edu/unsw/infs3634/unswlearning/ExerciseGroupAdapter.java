package au.edu.unsw.infs3634.unswlearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Adapter class for exercise group recyclerview
 */
public class ExerciseGroupAdapter extends RecyclerView.Adapter<ExerciseGroupAdapter.MyViewHolder> implements Filterable {
    //declared context for exercise group adapter
    Context mContextExerciseGroups;
    // two attributes: the data list and listener interface
    // one list for exercise group, one list for filtered exercise group
    private ArrayList<ExerciseGroup> mExerciseGroups, mExerciseGroupsFiltered;
    // interface for listener interface
    private RecyclerViewInterface recyclerViewInterface;
    // for sorting, now we only have one
    public static final int SORT_METHOD_NAME = 1;


    /**
     * constructor for the ExerciseGroup adapter
     * @param exerciseGroups   list of exercise groups
     * @param rvInterface      the exercise group recyclerview interface
     * @param context          declared context for exercise groups
     */
    public ExerciseGroupAdapter(ArrayList<ExerciseGroup> exerciseGroups, RecyclerViewInterface rvInterface, Context context) {
        mExerciseGroups = exerciseGroups;
        mExerciseGroupsFiltered = exerciseGroups;
        recyclerViewInterface = rvInterface;
        mContextExerciseGroups = context;
    }

    // the method to help construct list rows in the RecyclerView
    // Create new views (invoked by the layout manager)

    /**
     * method to help construct list rows in the RecyclerView
     * @param parent    parent view
     * @param viewType  viewType of parent
     * @return          the updated ViewHolder
     */
    @NonNull
    @Override
    public ExerciseGroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    /**
     * method to update each list row item with corresponding exercise group name and image
     * @param holder    viewholder for exercise group adapter
     * @param position  position of corresponding exercise group in the recyclerview list
     */
    @Override
    public void onBindViewHolder(@NonNull ExerciseGroupAdapter.MyViewHolder holder, int position) {
        ExerciseGroup exerciseGroup = mExerciseGroupsFiltered.get(position);
        // set TextView:TargetArea by name
        holder.tvTargetArea.setText(StringUtils.capitalize(exerciseGroup.getName()));

        // set ImageView: TargetArea
        holder.ivExerciseGroup.setImageResource(mContextExerciseGroups.getResources().getIdentifier(exerciseGroup.getName() + "1",
                "drawable", mContextExerciseGroups.getPackageName()));
        holder.itemView.setTag(exerciseGroup.getName());
    }

    /**
     * method to return size of dataset
     * @return      size of dataset
     */
    @Override
    public int getItemCount() {
        return mExerciseGroupsFiltered.size();
    }


    /**
     * method to return and update recyclerview with filtered list of exercise groups when searched
     * @return      filterResults with values from filtered arrayList
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mExerciseGroupsFiltered = mExerciseGroups;
                } else {
                    ArrayList<ExerciseGroup> filteredList = new ArrayList<>();
                    for (ExerciseGroup group: mExerciseGroups) {
                        if (group.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(group);
                        }
                    }
                    mExerciseGroupsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mExerciseGroupsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mExerciseGroupsFiltered = (ArrayList<ExerciseGroup>) filterResults.values;
                notifyDataSetChanged(); // notify the adapter class that the list has updated
            }
        };
    }




    /**
     * find handle to view items from home_list_row.xml layout
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExerciseGroup;
        TextView tvTargetArea;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ivExerciseGroup = itemView.findViewById(R.id.ivTargetArea);
            tvTargetArea = itemView.findViewById(R.id.tvTargetArea);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        recyclerViewInterface.onItemClick((String) itemView.getTag());
                    }
                }
            });
        }

    }
    /**
     * method for sorting exercise groups and updating the recyclerview
     * @param sortMethod    method to sort by name alphabetically
     */
    public void sort(final int sortMethod) {
        if (mExerciseGroupsFiltered.size() > 0) {
            Collections.sort(mExerciseGroupsFiltered, new Comparator<ExerciseGroup>() {
                @Override
                public int compare(ExerciseGroup g1, ExerciseGroup g2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        return g1.getName().compareTo(g2.getName());
                    } // else if sort by something else
                    // By default sort the list by group name
                    return g1.getName().compareTo(g2.getName());
                }

            });
        }
        notifyDataSetChanged();
    }
}

