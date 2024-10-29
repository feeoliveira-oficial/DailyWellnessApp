package com.example.dailywellnesstracker.View;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailywellnesstracker.Model.WellnessEntry;
import com.example.dailywellnesstracker.R;
import com.example.dailywellnesstracker.ViewModel.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WellnessAdapter  extends RecyclerView.Adapter<WellnessAdapter.WellnessViewHolder> {

    private List<WellnessEntry> wellnessList;
    private MainActivityViewModel viewModel;

    public WellnessAdapter(AppCompatActivity activity, List<WellnessEntry> wellnessList) {
        this.wellnessList = wellnessList != null ? wellnessList : new ArrayList<>();
        this.viewModel = new ViewModelProvider(activity).get(MainActivityViewModel.class);
    }

    public static class WellnessViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWaterIntake, textViewSleepHours, textViewExercise, textViewDate;
        public Switch switchEntryStatus;
        public WellnessViewHolder(View itemView) {
            super(itemView);
            textViewWaterIntake = itemView.findViewById(R.id.textViewWaterIntake);
            textViewSleepHours = itemView.findViewById(R.id.textViewSleepHours);
            textViewExercise = itemView.findViewById(R.id.textViewExercise);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            switchEntryStatus = itemView.findViewById(R.id.switchEntryStatus);
        }
    }
    @NonNull
    @Override
    public WellnessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new WellnessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WellnessViewHolder holder, int position) {
        WellnessEntry entry = wellnessList.get(position);

        holder.textViewWaterIntake.setText("Water: " + entry.getWaterIntake() + " cups");
        holder.textViewSleepHours.setText("Sleep: " + entry.getSleepHours() + " hours");
        holder.textViewExercise.setText("Exercise: " + entry.getExercise());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (entry.getDate() != null) {
            holder.textViewDate.setText("Date: " + sdf.format(entry.getDate().getTime()));
        }
        holder.switchEntryStatus.setChecked(entry.isCompleted());
        holder.switchEntryStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            entry.setCompleted(isChecked);

            if (isChecked) {
                viewModel.delete(entry);
                wellnessList.remove(position);
                notifyItemRemoved(position);
                Snackbar.make(holder.itemView, "Task completed and removed", Snackbar.LENGTH_LONG).show();
            } else {
                viewModel.update(entry);
            }
        });
    }

    private void updateCardColor(CardView cardView, boolean isCompleted) {
        int color = isCompleted ? Color.parseColor("#81C784") : Color.parseColor("#FFCDD2");
        cardView.setCardBackgroundColor(color);
    }


    @Override
    public int getItemCount() {
        return wellnessList.size();
    }
}
