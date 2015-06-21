package com.rama.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.rama.assignment_1.DetailsActivity;
import com.rama.assignment_1.R;
import com.rama.model.Contact;
import com.rama.model.ContactAdapter;
import com.rama.model.DatabaseOpenHelper;

public class ContactFragment extends Fragment {
	ListView lv;
	ContactAdapter adapter;
	DatabaseOpenHelper dbHelper;
	ArrayList<Contact> Contactlist;
	TextView tv;

	String nameString = "name";
	String phoneString = "phone";
	String mailString = "mail";
	String companyString = "company";
	String stateString = "state";
	String jobString = "job";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.contact_fragment, container, false);
		tv = (TextView) v.findViewById(R.id.totalEmpoyee);
		lv = (ListView) v.findViewById(R.id.list);
		dbHelper = DatabaseOpenHelper.getDbHelperInstance(getActivity());
		Contactlist = dbHelper.getAllNameContact();
		adapter = new ContactAdapter(getActivity(), Contactlist);

		// set total contacts

		int count = adapter.getCount();
		String c = String.valueOf(count);
		tv.setText(c + " contacts");

		lv.setAdapter(adapter);
		// add search
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				String phone = Contactlist.get(position).getPhone();
				String email = Contactlist.get(position).getEmail();
				String contactName = Contactlist.get(position).getName();
				String company = Contactlist.get(position).getCompanyName();
				String job = Contactlist.get(position).getJobTitle();
				String state = Contactlist.get(position).getState();

				Intent intent = new Intent(getActivity(), DetailsActivity.class);
				intent.putExtra(nameString, contactName);
				intent.putExtra(phoneString, phone);
				intent.putExtra(mailString, email);
				intent.putExtra(companyString, company);
				intent.putExtra(jobString, job);
				intent.putExtra(stateString, state);
				startActivity(intent);
			}
		});

		return v;
	}

}
