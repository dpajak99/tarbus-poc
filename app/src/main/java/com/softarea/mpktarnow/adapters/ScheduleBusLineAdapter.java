package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.dao.ScheduleDAO;
import com.softarea.mpktarnow.model.BusLineStopMediator;
import com.softarea.mpktarnow.model.ScheduleDay;
import com.softarea.mpktarnow.model.ScheduleMediator;
import com.softarea.mpktarnow.utils.DatabaseUtils;
import com.softarea.mpktarnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ScheduleBusLineAdapter extends RecyclerView.Adapter<ScheduleBusLineAdapter.ViewHolder> {
  private List<BusLineStopMediator> busLineStopMediators = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public CardView cardSchedule;
    public TextView busLine;
    public ImageView ivShareButton;
    public TextView tvRouteVariants;

    public RecyclerView expandableSchedule;
    public ExpandableScheduleAdapter expandableScheduleAdapter;

    public ViewHolder(@NonNull View itemView, @NonNull Context context) {
      super(itemView);

      cardSchedule = itemView.findViewById(R.id.card_schedule);
      busLine = itemView.findViewById(R.id.busLine);
      tvRouteVariants = itemView.findViewById(R.id.tv_route_variants);

      ivShareButton = itemView.findViewById(R.id.ivShareButton);

      expandableSchedule = itemView.findViewById(R.id.rv_expandable_schedule);
      expandableScheduleAdapter = new ExpandableScheduleAdapter();
      expandableSchedule.setLayoutManager(new LinearLayoutManager(context));
      expandableSchedule.setHasFixedSize(true);
      expandableSchedule.setAdapter(expandableScheduleAdapter);
    }
  }

  public void update(List<BusLineStopMediator> busLineStopMediators) {
    this.busLineStopMediators.clear();
    this.busLineStopMediators = busLineStopMediators;
    this.notifyDataSetChanged();
  }

  public ScheduleBusLineAdapter(FragmentActivity activity) {
    this.activity = activity;
  }

  @NonNull
  @Override
  public ScheduleBusLineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_bus_line_schedule, parent, false);
    return new ViewHolder(listItem, context);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    BusLineStopMediator busLineStopMediator = busLineStopMediators.get(position);
    int busStopId = busLineStopMediator.getBusStopId();
    String line = String.valueOf(Integer.parseInt(busLineStopMediator.getLine()));
    int lineId = DatabaseUtils.getDatabase(context).dbBusStopDAO().getBusLineId(line);

    List<ScheduleDay> scheduleDays = new ArrayList<>();
    String[] dayTypes = {"RO", "WS", "SW"};
    List<List<ScheduleMediator>> schedule = new ArrayList<>();

    for(String dayType : dayTypes) {
      List<ScheduleMediator> scheduleMediatorList = DatabaseUtils.getDatabase(context).dbBusStopDAO().getSchedule(busStopId, lineId, dayType);
      scheduleDays.add(new ScheduleDay(scheduleMediatorList, dayType));
      schedule.add(scheduleMediatorList);
    }

    holder.busLine.setText(line);
    holder.cardSchedule.setOnClickListener(view -> {
      holder.expandableScheduleAdapter.update(scheduleDays);
      busLineStopMediator.setExpandable(!busLineStopMediator.isExpandable());
      if( busLineStopMediator.isExpandable() ) {
        holder.expandableSchedule.setVisibility(View.VISIBLE);
        holder.tvRouteVariants.setVisibility(View.VISIBLE);
      } else {
        holder.expandableSchedule.setVisibility(View.GONE);
        holder.tvRouteVariants.setVisibility(View.GONE);
      }
      List<String> allVariants = ScheduleDAO.getAllRouteVariants(schedule);
      holder.tvRouteVariants.setText(StringUtils.arrayToString(allVariants));
    });

    StringBuilder scheduleToDownload = new StringBuilder();
    scheduleToDownload.append(line + " - " + busLineStopMediator.getBusStopName() +  StringUtils.NEW_LINE + StringUtils.NEW_LINE);
    for( ScheduleDay scheduleDay : scheduleDays ) {
      scheduleToDownload.append(scheduleDay.getDayString());
    }
    scheduleToDownload.append( StringUtils.NEW_LINE + StringUtils.NEW_LINE );

    holder.ivShareButton.setOnClickListener(view -> {
      Intent sharingIntent = new Intent(Intent.ACTION_SEND);
      sharingIntent.setType("text/plain");
      sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, scheduleToDownload.toString() );
      sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
      activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    });
  }


  @Override
  public int getItemCount() {
    return busLineStopMediators.size();
  }

}
