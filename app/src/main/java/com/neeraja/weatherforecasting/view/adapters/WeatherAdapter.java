package com.neeraja.weatherforecasting.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neeraja.weatherforecasting.R;
import com.neeraja.weatherforecasting.model.WeatherDayModel;
import com.neeraja.weatherforecasting.utils.Constants;
import com.neeraja.weatherforecasting.utils.Utils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

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
        String dateStr = data.getWeatherDate();
        Date date = null;
        if (Utils.isValidString(dateStr)) {
            date = Utils.getDate(dateStr, Constants.SIMPLE_DATE_FORMAT_REVERSE);
        }

        if (date != null) {
            if (Utils.isToday(date.getTime())) {
                holder.dateTv.setText("Today");
            } else if (Utils.isTomorrow(date)) {
                holder.dateTv.setText("Tomorrow");
            } else {
                String displayDateStr = Utils.getTime(date,Constants.displayDateFormat);
                holder.dateTv.setText(displayDateStr);
            }
        }

        holder.conditionTextTv.setText(data.getConditionText());
        holder.temperatureRangeTv.setText(((int) data.getMinTempCentigrade()) + "\u2103"
                + "/" + ((int) data.getMaxTempCentigrade()) + "\u2103");

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
