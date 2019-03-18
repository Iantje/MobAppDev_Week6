package me.iantje.gamebacklog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.iantje.gamebacklog.R;
import me.iantje.gamebacklog.model.BacklogItem;

public class BacklogAdapter extends RecyclerView.Adapter<BacklogAdapter.ViewHolder> {

    private List<BacklogItem> allItems;

    public BacklogAdapter(List<BacklogItem> allItems) { this.allItems = allItems; }

    @NonNull
    @Override
    public BacklogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_cardview, viewGroup, false);
        BacklogAdapter.ViewHolder viewHolder = new BacklogAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BacklogItem item = allItems.get(i);

        // Update UI
        viewHolder.updateUI(item);
    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }

    public void swapList (List<BacklogItem> newList) {
        allItems = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView platform;
        TextView date;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextview);
            platform = itemView.findViewById(R.id.platformTextview);
            date = itemView.findViewById(R.id.dateTextview);
            status = itemView.findViewById(R.id.statusTextview);
        }

        public void updateUI(BacklogItem item) {
            title.setText(item.getTitle());
            platform.setText(item.getPlatform());
            status.setText(item.getStatus().toString());

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(item.getLastUpdated());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY", new Locale("NL_nl"));
            date.setText(dateFormat.format(c.getTime()));
        }
    }
}
