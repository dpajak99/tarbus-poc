package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.db.BusStopListItem;
import com.softarea.mpktarnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> implements Filterable {

  private List<BusStopListItem> busStops = new ArrayList<>();
  private List<BusStopListItem> filteredData = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView admin;
    public TextView id;
    public LinearLayout busStop;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      id = itemView.findViewById(R.id.schedule_bus_stop_id);
      name = itemView.findViewById(R.id.schedule_bus_stop_name);
      busStop = itemView.findViewById(R.id.layout_bus_stop);
    }
  }

  public void updateArticles(List<BusStopListItem> busStops) {
    this.busStops.addAll(busStops);
    this.filteredData.addAll(busStops);
    this.notifyDataSetChanged();
  }

  public ScheduleAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @NonNull
  @Override
  public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_schedule, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    BusStopListItem busStop= filteredData.get(position);
    holder.id.setText(String.valueOf(busStop.getId()));
    holder.name.setText(busStop.getName());


    holder.busStop.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putInt("id", busStop.getId());
      result.putString("BUS_STOP_NAME", busStop.getName());

      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_slide_bus_stop, result);
    });
  }

  @Override
  public int getItemCount() {
    return filteredData.size();
  }

  @Override
  public Filter getFilter() {
    return exampleFilter;
  }
  private Filter exampleFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      List<BusStopListItem> filteredList = new ArrayList<>();

      String filterPattern = StringUtils.normalize(constraint.toString().toLowerCase()).trim();
      String[] arrOfStr = filterPattern.split(" ", 5);

      if (constraint == null || constraint.length() == 0) {
        filteredList.addAll(busStops);
      } else {

        for (BusStopListItem item : busStops) {
          boolean status = false;
          for (String pattern : arrOfStr) {
            if (StringUtils.normalize(item.getName().toLowerCase()).contains(pattern)) {
              status = true;
            } else {
              status = false;
              break;
            }
          }

        if(status) {
          filteredList.add(item);
        }
        }
      }
      FilterResults results = new FilterResults();
      results.values = filteredList;
      return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      filteredData = (ArrayList<BusStopListItem>) results.values;
      notifyDataSetChanged();
    }
  };
}
