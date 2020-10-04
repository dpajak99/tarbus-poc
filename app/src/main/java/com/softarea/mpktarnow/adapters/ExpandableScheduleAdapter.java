package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.ScheduleDay;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class ExpandableScheduleAdapter extends RecyclerView.Adapter<ExpandableScheduleAdapter.ViewHolder> {

  private List<ScheduleDay> scheduleDays = new ArrayList<>();
  private Context context;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView tvScheduleDayTitle;
    public TextView tvScheduleEmpty;
    public RecyclerView rvScheduleDepartuesList;
    public ScheduleItemAdapter scheduleDepartuesListAdapter;

    public ViewHolder(@NonNull View itemView, @NonNull Context context) {
      super(itemView);

      rvScheduleDepartuesList = itemView.findViewById(R.id.rv_schedule_departues_list);
      tvScheduleDayTitle = itemView.findViewById(R.id.tv_schedule_day_title);
      tvScheduleEmpty = itemView.findViewById(R.id.tv_bus_empty);
      scheduleDepartuesListAdapter = new ScheduleItemAdapter();
      rvScheduleDepartuesList.setLayoutManager(new GridLayoutManager(context, 5));
      rvScheduleDepartuesList.setHasFixedSize(true);
      rvScheduleDepartuesList.setAdapter(scheduleDepartuesListAdapter);
    }
  }

  public void update(List<ScheduleDay> scheduleDays) {
    this.scheduleDays.clear();
    this.scheduleDays.addAll(scheduleDays);
    this.notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ExpandableScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_schedule_day, parent, false);
    return new ViewHolder(listItem, context);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    ScheduleDay scheduleDay = scheduleDays.get(position);
    holder.tvScheduleDayTitle.setText(TimeUtils.translateDayShortcutToDayName(scheduleDay.getDayType()));
    if( scheduleDay.getScheduleMediatorList().size() == 0 ) {
      holder.tvScheduleEmpty.setVisibility(View.VISIBLE);
    }
    holder.scheduleDepartuesListAdapter.update(scheduleDay.getScheduleMediatorList());
  }

  @Override
  public int getItemCount() {
    return scheduleDays.size();
  }


}
