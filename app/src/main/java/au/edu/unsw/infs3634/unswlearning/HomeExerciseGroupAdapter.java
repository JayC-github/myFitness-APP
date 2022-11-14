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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeExerciseGroupAdapter extends RecyclerView.Adapter<HomeExerciseGroupAdapter.MyViewHolder> implements Filterable {
    //declared context for exercise group adapter
    Context mContextExerciseGroups;
    // two attributes: the data list and listener interface
    // one list for exercise group, one list for filtered exercise group
    private ArrayList<ExerciseGroup> mExerciseGroups, mExerciseGroupsFiltered;
    // interface for listener interface
    private RecyclerViewInterface recyclerViewInterface;
    // for sorting, now we only have one
    public static final int SORT_METHOD_NAME = 1;

    // constructor for the ExerciseGroup adapter
    public HomeExerciseGroupAdapter(Context context, ArrayList<ExerciseGroup> exerciseGroups, RecyclerViewInterface rvInterface) {
        mExerciseGroups = exerciseGroups;
        mExerciseGroupsFiltered = exerciseGroups;
        recyclerViewInterface = rvInterface;
        mContextExerciseGroups = context;
    }

    // the method to help construct list rows in the RecycleView
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public HomeExerciseGroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    // this method is to programmatically change the text values of the list row layout
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull HomeExerciseGroupAdapter.MyViewHolder holder, int position) {
        ExerciseGroup exerciseGroup = mExerciseGroupsFiltered.get(position);
        // set TextView:TargetArea by name
        holder.tvTargetArea.setText(exerciseGroup.getName());
        // set ImageView: TargetArea
        holder.ivExerciseGroup.setImageResource(mContextExerciseGroups.getResources().getIdentifier(exerciseGroup.getName(),
                "drawable", "au.edu.unsw.infs3634.unswlearning"));
        holder.itemView.setTag(exerciseGroup.getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mExerciseGroupsFiltered.size();
    }

    //returns updated list when searched
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
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    // create a ViewHolder pattern
    // provide a reference to the type of view that you are using
    // find handle to view items from home_list_row.xml layout
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

    //sort methods for list
    public void sort(final int sortMethod) {
        if (mExerciseGroupsFiltered.size() > 0) {
            Collections.sort(mExerciseGroupsFiltered, new Comparator<ExerciseGroup>() {
                @Override
                public int compare(ExerciseGroup g1, ExerciseGroup g2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        return g1.getName().compareTo(g2.getName());
                    } // else if sort by something else
                    // By default sort the list by coin name
                    return g1.getName().compareTo(g2.getName());
                }

            });
        }
        notifyDataSetChanged();
    }
}

