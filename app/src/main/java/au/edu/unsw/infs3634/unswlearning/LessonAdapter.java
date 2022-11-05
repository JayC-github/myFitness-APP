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
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> implements Filterable {

    Context mContextLessons;
    private List<Lesson> mLessons, mLessonsFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;
    public static final int SORT_METHOD_DIFFICULTY = 2;

    public LessonAdapter(Context context, List<Lesson> lessons, RecyclerViewInterface rvInterface) {
        mContextLessons = context;
        mLessons = lessons;
        mLessonsFiltered = lessons;
        recyclerViewInterface = rvInterface;
    }


    @NonNull
    @Override
    public LessonAdapter.LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_home_list_row, parent, false);
        return new LessonViewHolder(view, recyclerViewInterface);
    }



    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.LessonViewHolder holder, int position) {
        Lesson lesson = mLessonsFiltered.get(position);
        holder.tvLessonName.setText(lesson.getName());
        holder.tvLessonDifficulty.setText(lesson.getDifficulty());
        holder.ivLesson.setImageResource(mContextLessons.getResources().getIdentifier("biceps",
                "drawable", "au.edu.unsw.infs3634.unswlearning"));
        holder.itemView.setTag(lesson.getName());
    }

    @Override
    public int getItemCount() {
        return mLessonsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mLessonsFiltered = mLessons;
                } else {
                    ArrayList<Lesson> filteredList = new ArrayList<>();
                    for (Lesson lesson: mLessons) {
                        if (lesson.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(lesson);
                        }
                    }
                    mLessonsFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mLessonsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mLessonsFiltered = (ArrayList<Lesson>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    /**Lesson adapter set data from week 08*/
    public void setData(List<Lesson> lessons) {
        mLessons.clear();
        mLessons.addAll(lessons);
        notifyDataSetChanged();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLesson;
        TextView tvLessonName;
        TextView tvLessonDifficulty;

        public LessonViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ivLesson = itemView.findViewById(R.id.ivLesson);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvLessonDifficulty = itemView.findViewById(R.id.tvLessonDifficulty);
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
        if (mLessonsFiltered.size() > 0) {
            Collections.sort(mLessonsFiltered, new Comparator<Lesson>() {
                @Override
                public int compare(Lesson l1, Lesson l2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        return l1.getName().compareTo(l2.getName());
                    }
                    // By default sort the list by coin name
                    return l1.getName().compareTo(l2.getName());
                }

            });
        }
        notifyDataSetChanged();
    }
}
