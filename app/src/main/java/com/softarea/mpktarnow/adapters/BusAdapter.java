package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
<<<<<<< HEAD
import com.softarea.mpktarnow.model.DepartueItem;
=======
import com.softarea.mpktarnow.data.remote.model.Departue;
>>>>>>> master
import com.softarea.mpktarnow.utils.ListUtils;
import com.softarea.mpktarnow.utils.StringUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

  private List<DepartueItem> remoteDatabaseDepartues = new ArrayList<>();

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView busNumber;
    public TextView busDirection;
    public TextView busDepartueTime;
    public LinearLayout contentHolder;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
      contentHolder = itemView.findViewById(R.id.content_holder);
    }
  }

  public void update(List<DepartueItem> remoteDatabaseDepartues) {
    this.remoteDatabaseDepartues.clear();
    Collections.sort(remoteDatabaseDepartues, new ListUtils.Sortbyroll());
    this.remoteDatabaseDepartues.addAll(remoteDatabaseDepartues);
    this.notifyDataSetChanged();
  }

  public void clear() {
    this.remoteDatabaseDepartues.clear();
    this.notifyDataSetChanged();
  }

  public BusAdapter() {
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
    DepartueItem departueItem = remoteDatabaseDepartues.get(position);

    holder.busNumber.setText(departueItem.getBusLine() + "\n" + departueItem.getBusId());
    holder.busDirection.setText(departueItem.getDestination());
    if( departueItem.getLiveTime().length() != 0 ) {
      holder.busDepartueTime.setText(StringUtils.replaceHTML(departueItem.getLiveTime()));
    } else {
      holder.busDepartueTime.setText(TimeUtils.min2HHMM(departueItem.getDepartueTime()));
    }

    holder.contentHolder.setOnClickListener(view -> {
      Bundle result = new Bundle();
<<<<<<< HEAD
      result.putInt("busLine", departueItem.getBusLine());
      result.putInt("busId", departueItem.getBusId());
      result.putInt("wariantId", departueItem.getWariantId());
=======
      result.putInt("busLine", departue.getBusLine());
      result.putInt("busId", departue.getBusId());
      result.putInt("wariantId", departue.getWariantId());
      result.putString("routeId", String.valueOf(departue.getId()));
>>>>>>> master
      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_bus_details_map, result);
    });
  }

  @Override
  public int getItemCount() {
    return remoteDatabaseDepartues.size();
  }
}
