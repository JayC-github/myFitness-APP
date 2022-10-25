package au.edu.unsw.infs3634.unswlearning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeExerciseGroupAdapter extends RecyclerView.Adapter<HomeExerciseGroupAdapter.MyViewHolder> {

    private List<ExerciseGroup> mExerciseGroups;
    private RecyclerViewInterface recyclerViewInterface;

    public HomeExerciseGroupAdapter(List<ExerciseGroup> exerciseGroups, RecyclerViewInterface rvInterface) {
        mExerciseGroups = exerciseGroups;
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
        ExerciseGroup exerciseGroup = mExerciseGroups.get(position);
        holder.tvTargetArea.setText(exerciseGroup.getName());
        //holder.ivExerciseGroup.setImageAlpha(); whatever the field is for images);
    }

    @Override
    public int getItemCount() {
        return mExerciseGroups.size();
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

}
