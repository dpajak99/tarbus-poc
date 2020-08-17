package com.softarea.mpktarnow.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.BusAdapter;
import com.softarea.mpktarnow.dao.ScheduleDAO;
import com.softarea.mpktarnow.model.Departues;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusStopDetailsFragment extends Fragment {
  BusAdapter busAdapter;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_bus_stop_details, container, false);

    Bundle bundle = this.getArguments();
    int id = bundle.getInt("id");

    try {
      ScheduleDAO.DeserializeFromXML(id, new Callback<Departues>() {
        @Override
        public void onResponse(Call<Departues> call, Response<Departues> response) {
          Departues departues = response.body();
          busAdapter.update(departues.getDepartueList());
        }

        @Override
        public void onFailure(Call<Departues> call, Throwable t) {
          Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }

    RecyclerView busesList = root.findViewById(R.id.list_buses);
    busAdapter = new BusAdapter( getActivity());
    busesList.setHasFixedSize(true);
    busesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    busesList.setAdapter(busAdapter);



    return root;
  }
}
