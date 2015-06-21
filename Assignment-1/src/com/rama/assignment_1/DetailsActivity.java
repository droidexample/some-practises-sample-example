package com.rama.assignment_1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rama.model.DatabaseOpenHelper;

public class DetailsActivity extends Activity {

	TextView name, email, phone, company, job, state;
	ImageView callView, mailView;
	DatabaseOpenHelper databaseOpenHelper;

	String txtId, txtName, txtEmail, txtPhone, txtCompany, txtJob, txtState;

	// get putExtra String value;
	String idString = "id";
	String nameString = "name";
	String phoneString = "phone";
	String mailString = "mail";
	String companyString = "company";
	String stateString = "state";
	String jobString = "job";

	// set put string value
	String upName = "uname";
	String upPhone = "uphone";
	String upMail = "umail";
	String upCompany = "ucompany";
	String upState = "ustate";
	String upJob = "ujob";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity);
		databaseOpenHelper = DatabaseOpenHelper
				.getDbHelperInstance(getApplicationContext());
		name = (TextView) findViewById(R.id.text_setName);
		email = (TextView) findViewById(R.id.text_setEmail);
		company = (TextView) findViewById(R.id.text_setCompany);
		phone = (TextView) findViewById(R.id.text_setPhone);
		job = (TextView) findViewById(R.id.text_setJob);
		state = (TextView) findViewById(R.id.text_setSate);
		callView = (ImageView) findViewById(R.id.imgage_call);
		mailView = (ImageView) findViewById(R.id.image_mail);

		txtId = getIntent().getStringExtra(idString);
		txtName = getIntent().getStringExtra(nameString);
		txtEmail = getIntent().getStringExtra(mailString);
		txtPhone = getIntent().getStringExtra(phoneString);
		txtCompany = getIntent().getStringExtra(companyString);
		txtJob = getIntent().getStringExtra(jobString);
		txtState = getIntent().getStringExtra(stateString);

		ActionBar actionBar = getActionBar();
		// remove icon
		actionBar.setIcon(android.R.color.transparent);
		actionBar.setTitle(txtName);
		// actionBar.setIcon(R.drawable.ic_action_back);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);

		name.setText(txtName);
		email.setText(txtEmail);
		phone.setText(txtPhone);
		company.setText(txtCompany);
		job.setText(txtJob);
		state.setText(txtState);

		callView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + txtPhone));
					startActivity(callIntent);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		mailView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DetailsActivity.this,
						MailActivity.class);
				intent.putExtra("email", txtEmail);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.action_edit:
			setEditData();
			break;
		case R.id.action_delete:
			openAlert();
			break;
		case R.id.action_favorite:

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setEditData() {
		Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
		intent.putExtra(upName, txtName);
		intent.putExtra(upPhone, txtPhone);
		intent.putExtra(upMail, txtEmail);
		intent.putExtra(upCompany, txtCompany);
		intent.putExtra(upJob, txtJob);
		intent.putExtra(upState, txtState);
		startActivity(intent);
	}

	public void openAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				DetailsActivity.this);

		builder.setTitle("Delete Contact");
		builder.setMessage("do you delete...");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String name = getIntent().getStringExtra(nameString);
				databaseOpenHelper.deleteContact(name);
				Intent nt = new Intent(DetailsActivity.this, MainActivity.class);
				startActivity(nt);
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});

		AlertDialog alertDialog = builder.create();

		alertDialog.show();
	}
}
