package com.rama.contactsapiexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Browser;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button add, show, delete, update, history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		add = (Button) findViewById(R.id.btnAdd);
		show = (Button) findViewById(R.id.btnShow);
		delete = (Button) findViewById(R.id.btnDelete);
		update = (Button) findViewById(R.id.btnUpdate);
		history = (Button) findViewById(R.id.btnHistory);

		add.setOnClickListener(this);
		show.setOnClickListener(this);
		delete.setOnClickListener(this);
		update.setOnClickListener(this);
		history.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShow:
			displayContacts();
			break;
		case R.id.btnAdd:
			addContact("Alexa", "12903");
			break;
		case R.id.btnDelete:
			deleteContact("U");
			break;
		case R.id.btnUpdate:
			updateContact("Rama", "7689");
			break;
		case R.id.btnHistory:
			showHistory();
		default:
			break;
		}
	}

	private void showHistory() {
		ContentResolver cr = getContentResolver();
		Cursor historyCursor = cr.query(Browser.BOOKMARKS_URI, null, null,
				null, null);

		if (historyCursor != null && historyCursor.getCount() > 0) {
			do {
				String url = historyCursor.getString(historyCursor
						.getColumnIndex(Browser.BookmarkColumns.URL));
				String time = historyCursor.getString(historyCursor
						.getColumnIndex(Browser.BookmarkColumns.DATE));
				Toast.makeText(getApplicationContext(), url + " " + time,
						Toast.LENGTH_LONG).show();

			} while (historyCursor.moveToNext());
		}

	}

	private void deleteContact(String name) {
		ContentResolver cr = getContentResolver();
		String where = ContactsContract.Data.DISPLAY_NAME + "=?";
		String[] params = new String[] { name };
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(where, params).build());
		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
	}

	private void addContact(String name, String phoneNumber) {
		ContentResolver cr = getContentResolver();
		Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		if (contactCursor != null && contactCursor.getCount() > 0) {
			while (contactCursor.moveToNext()) {
				String existName = contactCursor
						.getString(contactCursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				if (existName.contains(name)) {
					Toast.makeText(getApplicationContext(),
							"The contact name: " + name + " already exists",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ContentProviderOperation operation = ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
						"accountname@gmail.com")
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
						"com.google").build();

		ops.add(operation);
		operation = ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build();
		ops.add(operation);
		operation = ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
						phoneNumber).build();
		ops.add(operation);

		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}

		Toast.makeText(getApplicationContext(),
				"Created new contact: " + name + " " + phoneNumber,
				Toast.LENGTH_LONG).show();

	}

	private void displayContacts() {
		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				String id = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				int hasPhoneNumber = Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
				if (hasPhoneNumber > 0) {
					Cursor phoneCur = resolver.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=?", new String[] { id }, null);
					if (phoneCur != null && phoneCur.getCount() > 0) {
						phoneCur.moveToFirst();
						do {
							String phoneNumber = phoneCur
									.getString(phoneCur
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							Toast.makeText(
									getApplicationContext(),
									"Contact Name :" + name + " Phone Number: "
											+ phoneNumber, Toast.LENGTH_LONG)
									.show();
						} while (phoneCur.moveToNext());
					}
					phoneCur.close();
				}

			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	private void updateContact(String name, String number) {
		ContentResolver cr = getContentResolver();

		String where = ContactsContract.Data.DISPLAY_NAME + "=? AND"
				+ ContactsContract.Data.MIMETYPE + "=? AND"
				+ String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE)
				+ "=?";
		String[] params = new String[] {
				name,
				ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
				String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME) };

		@SuppressWarnings("deprecation")
		Cursor phoneCur = managedQuery(ContactsContract.Data.CONTENT_URI, null,
				where, params, null);

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		if ((null == phoneCur)) {
			addContact(name, number);
		} else {
			ops.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(where, params)
					.withValue(ContactsContract.CommonDataKinds.Phone.DATA,
							number).build());
		}
		phoneCur.close();
		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
		Toast.makeText(this,
				"Updated the phone number of 'Sample Name' to: " + number,
				Toast.LENGTH_SHORT).show();
	}

}
