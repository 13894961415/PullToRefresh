package com.example.administrator.pulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/11/4.
 */

class PullAdapter extends BaseAdapter {

    private Context context;
    private List<String> datas;


    public PullAdapter(Context context,List<String> datas){
        this.context = context;
        this.datas = datas;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView ==null){

            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.task_name = convertView.findViewById(R.id.pulltextviewid);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.task_name.setText(""+datas.get(position));

        return convertView;
    }


    class ViewHolder {

        TextView task_name;

    }

}
