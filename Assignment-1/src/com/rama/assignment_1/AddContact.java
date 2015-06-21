package com.rama.assignment_1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.rama.model.Contact;
import com.rama.model.DatabaseOpenHelper;

public class AddContact extends Activity {

	EditText Name, Email, Phone, Company, JobTitle, State;

	// data base Helper
	DatabaseOpenHelper databaseOpenHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact_activity);
		ActionBar actionBar = getActionBar();
		// remove icon
		actionBar.setIcon(android.R.color.transparent);
		actionBar.setTitle("Create Contact ");

		allInitialization();

		databaseOpenHelper = DatabaseOpenHelper
				.getDbHelperInstance(getApplicationContext());

	}

	public void allInitialization() {
		// spCountry = (Spinner) findViewById(R.id.spCountry);

		Name = (EditText) findViewById(R.id.etName);
		Email = (EditText) findViewById(R.id.etEmail);
		Phone = (EditText) findViewById(R.id.etPhone);
		Company = (EditText) findViewById(R.id.etCompanyName);
		JobTitle = (EditText) findViewById(R.id.etJobTitle);
		State = (EditText) findViewById(R.id.etStateRegion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_done:
			saveContactData();

			break;
		case R.id.action_discard:
			Intent intent = new Intent(AddContact.this, MainActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void saveContactData() {
		String name = Name.getText().toString();
		String email = Email.getText().toString();
		String phone = Phone.getText().toString();
		String company = Company.getText().toString();
		String jobtitle = JobTitle.getText().toString();
		String state = State.getText().toString();

		Contact contact = new Contact(name, email, phone, company, jobtitle,
				state);

		if (!name.equals("") && !email.equals("") && !phone.equals("")) {

			long inserted = databaseOpenHelper.insertContacts(contact);
			if (inserted > 0) {
				Toast.makeText(getApplicationContext(), "data insert success",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(AddContact.this, MainActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "data insert failed",
						Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(getApplicationContext(),
					"please complete the both fill ", Toast.LENGTH_LONG).show();
		}

	}

}
