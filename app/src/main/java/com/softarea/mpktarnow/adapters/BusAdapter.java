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
import com.softarea.mpktarnow.model.Departue;
import com.softarea.mpktarnow.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

  private List<Departue> departues= new ArrayList<>();;

  FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView busNumber;
    public TextView busDirection;
    public TextView busDepartueTime;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
    }
  }

  public void update(List<Departue> departues) {
    Collections.sort(departues, new ListUtils.Sortbyroll());
    this.departues.addAll(departues);
    this.notifyDataSetChanged();
  }

  public BusAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @NonNull
  @Override
  public BusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
    Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_bus, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Departue departue = departues.get(position);

    holder.busNumber.setText(String.valueOf(departue.getBusLine()));
    holder.busDirection.setText(departue.getBusDirection());
    holder.busDepartueTime.setText(departue.getTime());
  }

  @Override
  public int getItemCount() {
    return departues.size();
  }
}
