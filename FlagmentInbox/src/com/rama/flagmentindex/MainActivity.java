package com.rama.flagmentindex;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Toast.makeText(this, "FragmentLayout: OnCreate()", Toast.LENGTH_SHORT)
				.show();

		setContentView(R.layout.activity_main);
	}

	public static class DetailsActivity extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			Toast.makeText(this, "DetailsActivity", Toast.LENGTH_SHORT).show();

			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				finish();
				return;
			}

			if (savedInstanceState == null) {
				DetailsFragment details = new DetailsFragment();

				details.setArguments(getIntent().getExtras());

				getFragmentManager().beginTransaction()
						.add(android.R.id.content, details).commit();
			}
		}
	}

	public static class TitleFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;
		CustomTitleAdapter adapter;
		ArrayList<Bean> lists = new ArrayList<Bean>();
		Bean b;
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			Toast.makeText(getActivity(), "TitlesFragment:onActivityCreated",
					Toast.LENGTH_LONG).show();

//			adapter = new ArrayAdapter<String>(getActivity(),
//					android.R.layout.simple_list_item_1, DataString.TITLES);

			for (int i = 0; i < DataString.TITLES.length; i++) {
				b = new Bean(DataString.TITLES[i], R.drawable.side);
				lists.add(b);
			}
			adapter = new CustomTitleAdapter(getActivity(), lists);
			setListAdapter(adapter);
			
			View detailsFrame = getActivity().findViewById(R.id.details);

			Toast.makeText(getActivity(), "detailsFrame " + detailsFrame,
					Toast.LENGTH_LONG).show();

			mDualPane = detailsFrame != null
					&& detailsFrame.getVisibility() == View.VISIBLE;

			Toast.makeText(getActivity(), "mDualPane " + mDualPane,
					Toast.LENGTH_LONG).show();

			if (savedInstanceState != null) {
				// Restore last state for checked position.
				mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			}

			if (mDualPane) {
				// In dual-pane mode, the list view highlights the selected
				// item.
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				// Make sure our UI is in the correct state.
				showDetails(mCurCheckPosition);
			} else {
				// We also highlight in uni-pane just for fun
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				getListView().setItemChecked(mCurCheckPosition, true);
			}
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			Toast.makeText(getActivity(), "onSaveInstanceState",
					Toast.LENGTH_LONG).show();

			outState.putInt("curChoice", mCurCheckPosition);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {

			Toast.makeText(getActivity(),
					"onListItemClick position is" + position, Toast.LENGTH_LONG)
					.show();

			showDetails(position);
		}

		void showDetails(int index) {
			mCurCheckPosition = index;

			//
			if (mDualPane) {
				getListView().setItemChecked(index, true);

				// Check what fragment is currently shown, replace if needed.
				DetailsFragment details = (DetailsFragment) getFragmentManager()
						.findFragmentById(R.id.details);
				if (details == null || details.getShownIndex() != index) {
					// Make new fragment to show this selection.

					details = DetailsFragment.newInstance(index);

					Toast.makeText(
							getActivity(),
							"showDetails dual-pane: create and relplace fragment",
							Toast.LENGTH_LONG).show();

					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.details, details);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}

			} else {
				Intent intent = new Intent();

				intent.setClass(getActivity(), DetailsActivity.class);

				intent.putExtra("index", index);

				startActivity(intent);
			}
		}
	}

	public static class DetailsFragment extends Fragment {

		public static DetailsFragment newInstance(int index) {
			DetailsFragment f = new DetailsFragment();

			// Supply index input as an argument.
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Toast.makeText(getActivity(), "DetailsFragment:onCreateView",
					Toast.LENGTH_LONG).show();

			ScrollView scroller = new ScrollView(getActivity());
			TextView text = new TextView(getActivity());
			int padding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 4, getActivity()
							.getResources().getDisplayMetrics());
			text.setPadding(padding, padding, padding, padding);
			scroller.addView(text);
			text.setText(DataString.DIALOGUE[getShownIndex()]);
			return scroller;
		}
	}

}
