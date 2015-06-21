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

public class EditActivity extends Activity {

	EditText etName, etEmail, etPhone, etCompany, etState, etJob;
	DatabaseOpenHelper databaseOpenHelper;
	// get putExtra String value;
	String upName = "uname";
	String upPhone = "uphone";
	String upMail = "umail";
	String upCompany = "ucompany";
	String upState = "ustate";
	String upJob = "ujob";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_activity);

		databaseOpenHelper = DatabaseOpenHelper.getDbHelperInstance(this);
		ActionBar actionBar = getActionBar();
		// remove icon
		actionBar.setIcon(android.R.color.transparent);
		actionBar.setTitle("Edit Contact");

		etName = (EditText) findViewById(R.id.et_upName);
		etEmail = (EditText) findViewById(R.id.et_upEmail);
		etPhone = (EditText) findViewById(R.id.et_upPhone);
		etCompany = (EditText) findViewById(R.id.et_upCompanyName);
		etJob = (EditText) findViewById(R.id.et_upJobTitle);
		etState = (EditText) findViewById(R.id.et_upStateRegion);

		etName.setText(getIntent().getStringExtra(upName));
		etEmail.setText(getIntent().getStringExtra(upMail));
		etPhone.setText(getIntent().getStringExtra(upPhone));
		etCompany.setText(getIntent().getStringExtra(upCompany));
		etJob.setText(getIntent().getStringExtra(upJob));
		etState.setText(getIntent().getStringExtra(upState));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_ok:
			updateContactValue();
			break;
		case R.id.action_cancle:
			Intent intent = new Intent(EditActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void updateContactValue() {
		String updateKey = getIntent().getStringExtra(upPhone);

		String name = etName.getText().toString();
		String email = etEmail.getText().toString();
		String phone = etPhone.getText().toString();
		String company = etCompany.getText().toString();
		String job = etJob.getText().toString();
		String state = etState.getText().toString();

		Contact updateContact = new Contact(name, email, phone, company, job,
				state);
		int updated = databaseOpenHelper
				.updateContact(updateKey, updateContact);
		if (updated > 0) {
			Toast.makeText(getApplicationContext(),
					updated + "  row updated sucess", Toast.LENGTH_LONG)
					.show();
			Intent intent = new Intent(EditActivity.this, MainActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), updated + " no updated",
					Toast.LENGTH_LONG).show();
		}

	}

}
