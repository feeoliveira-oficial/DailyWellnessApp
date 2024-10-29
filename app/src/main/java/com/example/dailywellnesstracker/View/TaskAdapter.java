package com.example.dailywellnesstracker.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailywellnesstracker.Model.WellnessEntry;
import com.example.dailywellnesstracker.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<WellnessEntry> taskList = new ArrayList<>();

    public void setTasks(List<WellnessEntry> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        WellnessEntry task = taskList.get(position);
        holder.textViewWaterIntake.setText("Water: " + task.getWaterIntake() + " cups");
        holder.textViewSleepHours.setText("Sleep: " + task.getSleepHours() + " hours");
        holder.textViewExercise.setText("Exercise: " + task.getExercise());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.textViewDate.setText("Date: " + sdf.format(task.getDate().getTime()));

        holder.switchEntryStatus.setChecked(task.isCompleted());
        holder.switchEntryStatus.setText(task.isCompleted() ? "Completed" : "Not Completed");

        holder.switchEntryStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            holder.switchEntryStatus.setText(isChecked ? "Completed" : "Not Completed");
        });
    }

    @Override
    public int getItemCount() {
        return taskList != null ? taskList.size() : 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWaterIntake, textViewSleepHours, textViewExercise, textViewDate;
        public Switch switchEntryStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWaterIntake = itemView.findViewById(R.id.textViewWaterIntake);
            textViewSleepHours = itemView.findViewById(R.id.textViewSleepHours);
            textViewExercise = itemView.findViewById(R.id.textViewExercise);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            switchEntryStatus = itemView.findViewById(R.id.switchEntryStatus);
        }
    }
}
