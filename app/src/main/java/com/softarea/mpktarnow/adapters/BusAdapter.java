package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.Departue;
import com.softarea.mpktarnow.utils.ListUtils;
import com.softarea.mpktarnow.utils.StringUtils;

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
    public LinearLayout contentHolder;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      busNumber = itemView.findViewById(R.id.schedule_bus_nr);
      busDirection = itemView.findViewById(R.id.schedule_bus_dir);
      busDepartueTime = itemView.findViewById(R.id.schedule_bus_departue_time);
      contentHolder = itemView.findViewById(R.id.content_holder);
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
    holder.busDepartueTime.setText(StringUtils.replaceHTML(departue.getTime()));
    holder.contentHolder.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putString("key", "busDetails");
      result.putInt("busLine", departue.getBusLine());
      result.putInt("busId", departue.getBusId());
      result.putString("routeId", String.valueOf(departue.getId()));
      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_map, result);
    });
  }

  @Override
  public int getItemCount() {
    return departues.size();
  }
}
