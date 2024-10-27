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
import java.util.List;

public class WellnessAdapter  extends RecyclerView.Adapter<WellnessAdapter.WellnessViewHolder> {

    private List<WellnessEntry> wellnessList;
    private AppCompatActivity activity;
    private MainActivityViewModel viewModel;

    public static class WellnessViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWaterIntake, textViewSleepHours, textViewExercise, textViewDate;
        public CardView cardView;
        public Switch switchEntryStatus;
        public WellnessViewHolder(View itemView) {
            super(itemView);
            textViewWaterIntake = itemView.findViewById(R.id.textViewWaterIntake);
            textViewSleepHours = itemView.findViewById(R.id.textViewSleepHours);
            textViewExercise = itemView.findViewById(R.id.textViewExercise);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            cardView = itemView.findViewById(R.id.cardViewWellness);
            switchEntryStatus = itemView.findViewById(R.id.switchEntryStatus);
        }
    }
    @NonNull
    @Override
    public WellnessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wellness_item, parent, false);
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
        if (entry.isCompleted()) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#4CAF50"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFCDD2"));
        }

        holder.switchEntryStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            entry.setCompleted(isChecked);

            if (isChecked) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#81C784"));
            } else {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFCDD2"));
            }

            if (isChecked) {
                viewModel.delete(entry);
                wellnessList.remove(position);
                notifyItemRemoved(position);
                Snackbar.make(holder.itemView, "Task completed and removed", Snackbar.LENGTH_LONG).show();
            } else {
                viewModel.update(entry);
            }

        });

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_edit_task, null);
            builder.setView(dialogView);

            EditText editWaterIntake = dialogView.findViewById(R.id.editTextEditWaterIntake);
            EditText editSleepHours = dialogView.findViewById(R.id.editTextEditSleepHours);
            Spinner editExercise = dialogView.findViewById(R.id.spinnerEditExercise);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    holder.itemView.getContext(), R.array.exercise_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editExercise.setAdapter(adapter);

            editWaterIntake.setText(entry.getWaterIntake());
            editSleepHours.setText(entry.getSleepHours());

            AlertDialog dialog = builder.create();
            dialog.show();

        });

    }

    @Override
    public int getItemCount() {
        return wellnessList.size();
    }
}
