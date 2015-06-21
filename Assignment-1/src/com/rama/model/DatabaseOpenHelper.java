package com.rama.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	private static DatabaseOpenHelper dbInstance;
	ArrayList<Contact> addBookMarkListWords;
	private static final String DB_NAME = "contactList";
	private static final int DB_VERSION = 2;

	private static final String TABLE_NAME = "contact_table";
	public static final String ID = "_id";
	public static final String NAME_FIELD = "name";
	public static final String EMAIL_FIELD = "email";
	public static final String PHONE_FIELD = "phone";
	public static final String COMPANY_FIELD = "companyname";
	public static final String JOBTITLE_FIELD = "jobtitle";
	public static final String STATE_FIELD = "region";

	public static final String CONTACT_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
			+ " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_FIELD
			+ " TEXT, " + EMAIL_FIELD + " TEXT, " + PHONE_FIELD + " TEXT, "
			+ COMPANY_FIELD + " TEXT, " + JOBTITLE_FIELD + " TEXT, "
			+ STATE_FIELD + " TEXT)";

	private DatabaseOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

		// TODO Auto-generated constructor stub
	}

	public static DatabaseOpenHelper getDbHelperInstance(Context context) {
		if (dbInstance == null) {
			dbInstance = new DatabaseOpenHelper(context);
		}
		return dbInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CONTACT_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	// insert Item table

	public long insertContacts(Contact conts) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME_FIELD, conts.getName());
		values.put(EMAIL_FIELD, conts.getEmail());
		values.put(PHONE_FIELD, conts.getPhone());
		values.put(COMPANY_FIELD, conts.getCompanyName());
		values.put(JOBTITLE_FIELD, conts.getJobTitle());
		values.put(STATE_FIELD, conts.getState());
		long insert = db.insert(TABLE_NAME, null, values);

		db.close();
		return insert;
	}

	// data fatch table

	public ArrayList<Contact> getAllContacts() {
		ArrayList<Contact> allContats = new ArrayList<Contact>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
				null, null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String name = cursor.getString(cursor
						.getColumnIndex(NAME_FIELD));
				String email = cursor.getString(cursor
						.getColumnIndex(EMAIL_FIELD));
				String phone = cursor.getString(cursor
						.getColumnIndex(PHONE_FIELD));
				String company = cursor.getString(cursor
						.getColumnIndex(COMPANY_FIELD));
				String title = cursor.getString(cursor
						.getColumnIndex(JOBTITLE_FIELD));
				String state = cursor.getString(cursor
						.getColumnIndex(STATE_FIELD));
				Contact e = new Contact(name, email, phone, company, title,
						state);
				allContats.add(e);
				cursor.moveToNext();

			}
		}
		cursor.close();
		db.close();
		return allContats;

	}

	// get name contact

	public ArrayList<Contact> getAllNameContact() {
		ArrayList<Contact> allnames = new ArrayList<Contact>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
				NAME_FIELD + " ASC");

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String name = cursor.getString(cursor
						.getColumnIndex(NAME_FIELD));
				String email = cursor.getString(cursor
						.getColumnIndex(EMAIL_FIELD));
				String phone = cursor.getString(cursor
						.getColumnIndex(PHONE_FIELD));
				String company = cursor.getString(cursor
						.getColumnIndex(COMPANY_FIELD));
				String title = cursor.getString(cursor
						.getColumnIndex(JOBTITLE_FIELD));
				String state = cursor.getString(cursor
						.getColumnIndex(STATE_FIELD));
				Contact e = new Contact(name, email, phone, company, title,
						state);

				allnames.add(e);
				cursor.moveToNext();

			}
		}
		cursor.close();
		db.close();
		return allnames;

	}

	// ..............update data base method...................
	public int updateContact(String key, Contact contact) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(NAME_FIELD, contact.getName());
		values.put(EMAIL_FIELD, contact.getEmail());
		values.put(PHONE_FIELD, contact.getPhone());
		values.put(COMPANY_FIELD, contact.getCompanyName());
		values.put(JOBTITLE_FIELD, contact.getJobTitle());
		values.put(STATE_FIELD, contact.getState());

		int updated = db.update(TABLE_NAME, values, PHONE_FIELD + "=?",
				new String[] { key });

		db.close();
		return updated;
	}

	// ..............dalete data base method...................

	public void deleteContact(String del) {

		// get reference of the bookmarks database
		SQLiteDatabase db = this.getWritableDatabase();

		// delete word
		db.delete(TABLE_NAME, NAME_FIELD + " = ?", new String[] { "" + del });
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		// return count
		return cursor.getCount();

	}

}
