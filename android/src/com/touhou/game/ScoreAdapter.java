package com.touhou.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.touhou.game.staticData.ScoreValue;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    private List<ScoreValue> scorelst;
    private Context context;
    private int myLayout;

    public ScoreAdapter(Context context, List<ScoreValue> scorelst) {
        this.context = context;
        this.scorelst = scorelst;
    }
    public ScoreAdapter(Context context,int Layout, List<ScoreValue> scorelst) {
        this.context = context;
        this.scorelst = scorelst;
        myLayout = Layout;
    }

    @Override
    public int getCount() {
        return scorelst.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(myLayout, null);

        // view values to listview
        TextView textView = convertView.findViewById(R.id.tvName);
        textView.setText(scorelst.get(position).getName());
        textView = convertView.findViewById(R.id.tvScore);
        textView.setText("Score: "+scorelst.get(position).getScore());

        return convertView;
    }
}
