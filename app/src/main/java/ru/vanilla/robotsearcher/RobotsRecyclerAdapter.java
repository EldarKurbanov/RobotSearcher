package ru.vanilla.robotsearcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RobotsRecyclerAdapter extends RecyclerView.Adapter<RobotsRecyclerAdapter.RobotsViewHolder> {

    ArrayList<String> ipRobots;

    public RobotsRecyclerAdapter(ArrayList<String> ipRobots) {
        this.ipRobots = ipRobots;
    }

    @NonNull
    @Override
    public RobotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new RobotsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RobotsViewHolder holder, int position) {
        holder.ipTextView.setText(ipRobots.get(position));
    }

    @Override
    public int getItemCount() {
        return ipRobots.size();
    }

    public static class RobotsViewHolder extends RecyclerView.ViewHolder {
        TextView ipTextView;

        public RobotsViewHolder(@NonNull View itemView) {
            super(itemView);
            ipTextView = itemView.findViewById(R.id.textViewIP);
        }
    }
}
