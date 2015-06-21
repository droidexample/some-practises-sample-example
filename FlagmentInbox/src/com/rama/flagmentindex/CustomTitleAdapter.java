package com.rama.flagmentindex;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomTitleAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Bean> titles = new ArrayList<Bean>();

	public CustomTitleAdapter(Context context, ArrayList<Bean> titles) {
		this.context = context;
		this.titles = titles;
	}

	@Override
	public int getCount() {

		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return titles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_patern, null);

		}
		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.title_style);
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		txtTitle.setText(titles.get(position).getTitle());
		imgIcon.setImageResource(titles.get(position).getIcon());
		return convertView;
	}

}
