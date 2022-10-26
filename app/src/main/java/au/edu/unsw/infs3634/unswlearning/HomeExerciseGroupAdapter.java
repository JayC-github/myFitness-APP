package au.edu.unsw.infs3634.unswlearning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import java.util.List;
import java.util.Locale;

public class HomeExerciseGroupAdapter extends RecyclerView.Adapter<HomeExerciseGroupAdapter.MyViewHolder> implements Filterable {

    Context mContextExerciseGroups;
    private ArrayList<ExerciseGroup> mExerciseGroups, mExerciseGroupsFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;

    public HomeExerciseGroupAdapter(Context context, ArrayList<ExerciseGroup> exerciseGroups, RecyclerViewInterface rvInterface) {
        mContextExerciseGroups = context;
        mExerciseGroups = exerciseGroups;
        mExerciseGroupsFiltered = exerciseGroups;
        recyclerViewInterface = rvInterface;
    }


    @NonNull
    @Override
    public HomeExerciseGroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeExerciseGroupAdapter.MyViewHolder holder, int position) {
        ExerciseGroup exerciseGroup = mExerciseGroupsFiltered.get(position);
        holder.tvTargetArea.setText(exerciseGroup.getName());


        holder.ivExerciseGroup.setImageResource(mContextExerciseGroups.getResources().getIdentifier(exerciseGroup.getName(),
                "drawable", "au.edu.unsw.infs3634.unswlearning"));
        holder.itemView.setTag(exerciseGroup.getName());
    }

    @Override
    public int getItemCount() {
        return mExerciseGroupsFiltered.size();
    }

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
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExerciseGroup;
        TextView tvTargetArea;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ivExerciseGroup = itemView.findViewById(R.id.ivHome);
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

    public void sort(final int sortMethod) {
        if (mExerciseGroupsFiltered.size() > 0) {
            Collections.sort(mExerciseGroupsFiltered, new Comparator<ExerciseGroup>() {
                @Override
                public int compare(ExerciseGroup g1, ExerciseGroup g2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        return g1.getName().compareTo(g2.getName());
                    }
                    // By default sort the list by coin name
                    return g1.getName().compareTo(g2.getName());
                }

            });
        }
        notifyDataSetChanged();
    }
}

