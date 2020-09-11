package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.AutoRefresh;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.model.NearBusStop;

import java.util.ArrayList;
import java.util.List;

public class NearBusStopAdapter extends RecyclerView.Adapter<NearBusStopAdapter.ViewHolder> {

  private List<Float> directions = new ArrayList<>();
  private List<Integer> meters = new ArrayList<>();

  private List<NearBusStop> nearBusStopsSorted = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;
  ViewHolder holderglobal;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView tvMeters;
    public ImageView posArrow;
    public LinearLayout busStop;
    View view;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.schedule_bus_stop_name);
      busStop = itemView.findViewById(R.id.layout_near_bus_stop);
      tvMeters = itemView.findViewById(R.id.meters);
      posArrow = itemView.findViewById(R.id.pos_arrow);
      view = itemView;
    }
  }

  public void update(List<NearBusStop> nearBusStops) {
    this.nearBusStopsSorted.clear();
    this.nearBusStopsSorted.addAll(nearBusStops);
    this.notifyDataSetChanged();
  }

  public List<NearBusStop> getNearBusStopsSorted() {
    return nearBusStopsSorted;
  }

  public void setDirections(List<Float> directions) {
    this.directions = directions;
  }

  public void setMeters(List<Integer> meters) {
    this.meters = meters;
  }

  public NearBusStopAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @NonNull
  @Override
  public NearBusStopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_near_bus_stop, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holderglobal = holder;
    NearBusStop nearBusStop = nearBusStopsSorted.get(position);
    BusStop busStop = nearBusStop.getBusStop();

    holder.name.setText(busStop.getName());


    new AutoRefresh() {
      @Override
      public void refreshFunction() {
        holder.tvMeters.setText(meters.get(position) + " ");
        holder.posArrow.setRotation(directions.get(position));
      }
    };

    holder.busStop.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putInt("id", busStop.getId());

      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_bus_stop_details, result);
    });
  }

  @Override
  public int getItemCount() {
    return nearBusStopsSorted.size();
  }

}
