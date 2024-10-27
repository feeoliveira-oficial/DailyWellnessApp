package com.example.dailywellnesstracker.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dailywellnesstracker.Model.User;
import com.example.dailywellnesstracker.R;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    boolean selectionMode = false;
    private List<User> selectedUsers = new ArrayList<>();

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void setUsers(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setSelectionMode(boolean selectionMode) {
        this.selectionMode = selectionMode;
        notifyDataSetChanged();
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewUserName.setText(user.getUsername());

        holder.checkBoxSelectUser.setVisibility(selectionMode ? View.VISIBLE : View.GONE);
        holder.checkBoxSelectUser.setOnCheckedChangeListener(null);
        holder.checkBoxSelectUser.setChecked(selectedUsers.contains(user));

        holder.checkBoxSelectUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedUsers.add(user);
            } else {
                selectedUsers.remove(user);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (selectionMode) {
                if (selectedUsers.contains(user)) {
                    selectedUsers.remove(user);
                    holder.checkBoxSelectUser.setChecked(false);
                } else {
                    selectedUsers.add(user);
                    holder.checkBoxSelectUser.setChecked(true);
                }
            } else {
                Intent intent = new Intent(context, TaskListActivity.class);
                intent.putExtra("userId", user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewUserName;
        public CheckBox checkBoxSelectUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            checkBoxSelectUser = itemView.findViewById(R.id.checkBoxSelectUser);
        }
    }
}
