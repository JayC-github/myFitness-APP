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

/**
 * adapter for note recyclerview
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements Filterable {

    //declared context for note adapter
    Context mContextNotes;
    private ArrayList<Note> mNotes, mNotesFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;

    /**
     * constructor for lesson adapter
     * @param context           declared context for notes
     * @param notes             list of notes
     * @param rvInterface       the note recyclerview interface
     */
    public NoteAdapter(Context context, ArrayList<Note> notes, RecyclerViewInterface rvInterface) {
        mContextNotes = context;
        mNotes = notes;
        mNotesFiltered = notes;
        recyclerViewInterface = rvInterface;
    }

    /**
     * method to help construct list rows in the RecyclerView
     * @param parent    parent view
     * @param viewType  viewType of parent
     * @return          the updated ViewHolder
     */
    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set list row for lesson recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_row, parent, false);
        return new NoteViewHolder(view, recyclerViewInterface);

    }

    /**
     * method to update each list row item with corresponding note name and last updated time
     * @param holder    viewholder for note adapter
     * @param position  position of corresponding lesson in the recyclerview list
     */
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        Note note = mNotesFiltered.get(position);
        holder.tvNoteTitle.setText(note.getNoteTitle());
        holder.tvNoteTime.setText(note.getLatestUpdate());
        holder.itemView.setTag(note.getNoteID());
    }

    /**
     * method to return size of dataset
     * @return      size of dataset
     */
    @Override
    public int getItemCount() {
        return mNotesFiltered.size();
    }

    /**
     * method to return and update recyclerview with filtered list of notes when searched
     * @return      filterResults with values from filtered arrayList
     */
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

    /**
     * find handle to view items from note_list_row.xml layout
     */
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle;
        TextView tvNoteTime;
        TextView tvNoteBody;

        public NoteViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteTime = itemView.findViewById(R.id.tvNoteTime);
            // tvNoteBody = itemView.findViewById(R.id.tvNoteContent);
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
     * method for sorting notes and updating the recyclerview
     * @param sortMethod    method to sort by name alphabetically
     */
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

    /**
     * method to set data to adapter
     * @param notes     arraylist of notes to be set to adapter
     */
    public void setNoteData(ArrayList<Note> notes) {
        mNotes.addAll(notes);
        notifyDataSetChanged();

    }


}
