package com.rama.assignment_1;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rama.fragment.ContactFragment;
import com.rama.fragment.FavoriteFragment;

public class MainActivity extends Activity {
	ActionBar actionBar;
	ContactFragment contactFragment;
	FavoriteFragment favoriteFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// set background SQLite action bar menu

		actionBar = getActionBar();
		actionBar.setSplitBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#00968C")));
		actionBar.setTitle("Contact Manager ");
		setTab();
	}

	private void setTab() {
		contactFragment = new ContactFragment();
		favoriteFragment = new FavoriteFragment();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// set tab
		ActionBar.Tab contact = actionBar.newTab();
		ActionBar.Tab favorite = actionBar.newTab();
		ActionBar.Tab social = actionBar.newTab();
		// set icon
		favorite.setIcon(R.drawable.ic_action_important);
		contact.setIcon(R.drawable.ic_action_person);
		social.setIcon(R.drawable.ic_action_group);
		// set listener
		favorite.setTabListener(new MytabListener());
		contact.setTabListener(new MytabListener());
		actionBar.addTab(favorite);
		actionBar.addTab(contact);
		actionBar.selectTab(contact);

	}

	class MytabListener implements TabListener {

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (tab.getPosition() == 0) {
				ft.replace(android.R.id.content, favoriteFragment);
			} else if (tab.getPosition() == 1) {
				ft.replace(android.R.id.content, contactFragment);

			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_search:
			Intent searchIntent = new Intent(MainActivity.this,
					SearchActivity.class);
			Bundle banAnimation = ActivityOptions.makeCustomAnimation(
					getApplicationContext(), R.anim.animation_next,
					R.anim.animaton_pre).toBundle();
			startActivity(searchIntent, banAnimation);

			break;
		case R.id.action_add:
			Intent intent = new Intent(MainActivity.this, AddContact.class);
			startActivity(intent);
			break;
		case R.id.action_more:

			break;

		}

		return super.onOptionsItemSelected(item);
	}

}
