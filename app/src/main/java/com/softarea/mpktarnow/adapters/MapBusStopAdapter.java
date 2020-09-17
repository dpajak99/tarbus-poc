package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.data.remote.model.Departue;
import com.softarea.mpktarnow.utils.ListUtils;
import com.softarea.mpktarnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapBusStopAdapter extends RecyclerView.Adapter<MapBusStopAdapter.ViewHolder> {

  private List<Departue> departues= new ArrayList<>();;

  FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView busNumber;
//    public TextView busDirection;
    public TextView busDepartueTime;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
//      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
    }
  }

  public void update(List<Departue> departues) {
    Collections.sort(departues, new ListUtils.Sortbyroll());
    this.departues.addAll(departues);
    this.notifyDataSetChanged();
  }

  public void clear() {
    this.departues.clear();
    this.notifyDataSetChanged();
  }

  public MapBusStopAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @NonNull
  @Override
  public MapBusStopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
    Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_map_bus, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Departue departue = departues.get(position);

    holder.busNumber.setText(String.valueOf(departue.getBusLine()));
//    holder.busDirection.setText(departue.getBusDirection());
    holder.busDepartueTime.setText(StringUtils.replaceHTML(departue.getTime()));
  }

  @Override
  public int getItemCount() {
    return departues.size();
  }
}
