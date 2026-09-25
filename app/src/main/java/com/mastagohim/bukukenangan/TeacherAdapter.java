package com.mastagohim.bukukenangan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private Context context;
    private List<Teacher> teacherList;

    public TeacherAdapter(Context context, List<Teacher> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    public void updateData(List<Teacher> teachers) {
        this.teacherList = teachers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        holder.textName.setText(teacher.getName());
        holder.textNip.setText("NIP: " + teacher.getNip());
        holder.textSubject.setText(teacher.getSubject());
    }

    @Override
    public int getItemCount() {
        return teacherList != null ? teacherList.size() : 0;
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView textName, textNip, textSubject;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            textName = itemView.findViewById(R.id.text_name);
            textNip = itemView.findViewById(R.id.text_nip);
            textSubject = itemView.findViewById(R.id.text_subject);
        }
    }
}