package com.example.dailywellnesstracker.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.textViewTask.setText("Water: " + task.getWaterIntake() +
                                    " cups, Sleep: " + task.getSleepHours() +
                                    " hours, Exercise: " + task.getExercise());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.textViewDate.setText("Date: " + sdf.format(task.getDate().getTime()));
    }

    @Override
    public int getItemCount() {
        return taskList != null ? taskList.size() : 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTask, textViewDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTaskDetails);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
