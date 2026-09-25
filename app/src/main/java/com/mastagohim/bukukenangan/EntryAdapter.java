package com.mastagohim.bukukenangan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {
    private List<Entry> entryList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public EntryAdapter(Context context, List<Entry> entryList) {
        this.context = context;
        this.entryList = entryList;
        this.listener = null;
    }

    public EntryAdapter(Context context, List<Entry> entryList, OnItemClickListener listener) {
        this.context = context;
        this.entryList = entryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry entry = entryList.get(position);
        holder.title.setText(entry.getTitle());
        holder.date.setText(entry.getDate());
        holder.notePreview.setText(entry.getNote().length() > 100
                ? entry.getNote().substring(0, 100) + "..."
                : entry.getNote());
        holder.created.setText("Dibuat: " + entry.getCreated_at());

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, notePreview, created;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            date = itemView.findViewById(R.id.text_date);
            notePreview = itemView.findViewById(R.id.text_note);
            created = itemView.findViewById(R.id.text_created);
        }
    }
}