package com.neeraja.weatherforecasting.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neeraja.weatherforecasting.R;
import com.neeraja.weatherforecasting.model.WeatherDayModel;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.DataObjectHolder> {
    private ArrayList<WeatherDayModel> list;

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView dateTv;
        TextView conditionTextTv;
        TextView temperatureRangeTv;
        ImageView conditionImgIv;

        public DataObjectHolder(View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.tv_date);
            conditionTextTv = (TextView) itemView.findViewById(R.id.tv_condition_text);
            temperatureRangeTv = (TextView) itemView.findViewById(R.id.tv_temperature);
            conditionImgIv = (ImageView) itemView.findViewById(R.id.iv_condition_img);
        }
    }

    public WeatherAdapter(ArrayList<WeatherDayModel> myDataset) {
        list = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        WeatherDayModel data = list.get(position);

        holder.dateTv.setText(data.getWeatherDate()); //change later
        holder.conditionTextTv.setText(data.getConditionText());
        holder.temperatureRangeTv.setText(data.getMinTempCentigrade() + "/" + data.getMaxTempCentigrade());

//        if (data.getConditionCode() == 1003) {
//            holder.conditionImgIv.setImageResource(View.VISIBLE);
//        } else {
//            holder.conditionImgIv.setImageResource(View.INVISIBLE);
//        }

    }

    public void update(ArrayList<WeatherDayModel> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(WeatherDayModel dataObj, int index) {
        list.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        list.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
