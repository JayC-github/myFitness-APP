package au.edu.unsw.infs3634.unswlearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//adapter for note recyclerview
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements Filterable {

    //declared context for note adapter
    Context mContextNotes;
    private ArrayList<Note> mNotes, mNotesFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;

    //constructor method
    public NoteAdapter(Context context, ArrayList<Note> notes, RecyclerViewInterface rvInterface) {
        mContextNotes = context;
        mNotes = notes;
        mNotesFiltered = notes;
        recyclerViewInterface = rvInterface;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set list row for lesson recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_home_list_row, parent, false);
        return new NoteViewHolder(view, recyclerViewInterface);

    }

    //assign value to each row in recyclerview based on position
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        Note note = mNotesFiltered.get(position);
        holder.tvNoteTitle.setText(note.getNoteTitle());
        holder.tvNoteBody.setText(note.getNoteBody());
        holder.itemView.setTag(note.getNoteID());
    }

    //return number of items in recyclerview
    @Override
    public int getItemCount() {
        return mNotesFiltered.size();
    }

    //returns updated list when searched
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mNotesFiltered = mNotes;
                } else {
                    ArrayList<Note> filteredList = new ArrayList<>();
                    for (Note note: mNotes) {
                        if (note.getNoteTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(note);
                        }
                    }
                    mNotesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mNotesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mNotesFiltered = (ArrayList<Note>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //find handle to view items from note_home_list_row.xml layout
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle;
        TextView tvNoteBody;

        public NoteViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteBody = itemView.findViewById(R.id.tvNoteContent);
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
        if (mNotesFiltered.size() > 0) {
            Collections.sort(mNotesFiltered, new Comparator<Note>() {
                @Override
                public int compare(Note n1, Note n2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        return n1.getNoteTitle().compareTo(n2.getNoteTitle());
                    }
                    return n1.getNoteTitle().compareTo(n2.getNoteTitle());
                }

            });
        }
        notifyDataSetChanged();
    }

    //set data to the adapter
    public void setNoteData(ArrayList<Note> notes) {
        mNotes.addAll(notes);
        notifyDataSetChanged();

    }


}
