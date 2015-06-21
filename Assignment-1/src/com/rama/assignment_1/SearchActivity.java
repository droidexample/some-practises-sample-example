package com.rama.assignment_1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;

import com.rama.model.Contact;
import com.rama.model.ContactAdapter;
import com.rama.model.DatabaseOpenHelper;

public class SearchActivity extends Activity {

	ListView list;
	ContactAdapter adapter;
	DatabaseOpenHelper dbHelper;
	ArrayList<Contact> Contactlist;
	ActionBar actionBar;

	String idString = "id";
	String nameString = "name";
	String phoneString = "phone";
	String mailString = "mail";
	String companyString = "company";
	String stateString = "state";
	String jobString = "job";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		actionBar = getActionBar();
		// remove action bar icon
		actionBar.setIcon(android.R.color.transparent);
		actionBar.setTitle("");
		// actionBar.setIcon(R.drawable.ic_action_back);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);

		list = (ListView) findViewById(R.id.serchLv);
		dbHelper = DatabaseOpenHelper
				.getDbHelperInstance(getApplicationContext());
		Contactlist = dbHelper.getAllNameContact();
		adapter = new ContactAdapter(getApplicationContext(), Contactlist);

		list.setAdapter(adapter);
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				String key = Contactlist.get(position).getId();
				String phone = Contactlist.get(position).getPhone();
				String email = Contactlist.get(position).getEmail();
				String contactName = Contactlist.get(position).getName();
				String company = Contactlist.get(position).getCompanyName();
				String job = Contactlist.get(position).getJobTitle();
				String state = Contactlist.get(position).getState();

				Intent intent = new Intent(SearchActivity.this,
						DetailsActivity.class);
				intent.putExtra(idString, key);
				intent.putExtra(nameString, contactName);
				intent.putExtra(phoneString, phone);
				intent.putExtra(mailString, email);
				intent.putExtra(companyString, company);
				intent.putExtra(jobString, job);
				intent.putExtra(stateString, state);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setSubmitButtonEnabled(true);

		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				adapter.filter(query);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				adapter.filter(newText);
				return true;
			}
		};
		searchView.setOnQueryTextListener(textChangeListener);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(SearchActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
