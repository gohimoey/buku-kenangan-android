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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private List<Student> studentList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Student student);
    }

    public StudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateData(List<Student> students) {
        this.studentList = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.textName.setText(student.getName());
        holder.textNim.setText("NIM: " + student.getNim());
        holder.textMajorClass.setText(student.getMajor() + " - " + student.getClassName());
    }

    @Override
    public int getItemCount() {
        return studentList != null ? studentList.size() : 0;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView textName, textNim, textMajorClass;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            textName = itemView.findViewById(R.id.text_name);
            textNim = itemView.findViewById(R.id.text_nim);
            textMajorClass = itemView.findViewById(R.id.text_major_class);

            itemView.setOnClickListener(v -> {
                if (itemView.getTag() instanceof Student) {
                    Student student = (Student) itemView.getTag();
                    if (getAdapterPosition() >= 0 && itemView.getTag() != null) {
                        // Handle click
                    }
                }
            });
        }
    }
}