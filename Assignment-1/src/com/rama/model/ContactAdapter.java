package com.rama.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rama.assignment_1.R;

public class ContactAdapter extends BaseAdapter implements Filterable{

	LayoutInflater inflater;
	private Context mcontext;
	private List<Contact> singleName = null;
	private ArrayList<Contact> allContacts = new ArrayList<Contact>();

	public ContactAdapter(Context context, ArrayList<Contact> names) {
		mcontext = context;
		this.singleName = names;
		inflater = LayoutInflater.from(mcontext);
		this.allContacts.addAll(names);
	}

	public class ViewHolder {
		TextView name;
	}

	@Override
	public int getCount() {
		return singleName.size();
	}

	@Override
	public Contact getItem(int position) {
		return singleName.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.contact_list, null);
			holder.name = (TextView) convertView.findViewById(R.id.setName);
			// String str = ((TextView) convertView.findViewById(R.id.txt_eng))
			// .getText().toString();

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(singleName.get(position).getName());

		return convertView;
	}

	// Filter method
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		singleName.clear();
		if (charText.length() == 0) {
			singleName.addAll(allContacts);
		} else {
			for (Contact cts : allContacts) {
				if (cts.getName().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					singleName.add(cts);
				}
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public Filter getFilter() {
		
		return null;
	}

}
