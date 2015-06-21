package com.rama.listviewfilter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	FrameLayout historyContainer;
	ViewStub viewStub;
	final ArrayList<String> historyList = new ArrayList<String>();
	private static final ArrayList<String> historyData = new ArrayList<String>();

	String displayName = "";
	ListView historyListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		historyContainer = (FrameLayout) findViewById(R.id.history_layout_container);
		EditText filterEditText = (EditText) findViewById(R.id.fiterText);
		filterEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				final ArrayList<String> tempHistoryList = new ArrayList<String>();
				tempHistoryList.addAll(historyList);
				for (String data : historyList) {
					if (data.indexOf(s.toString()) == -1) {
						tempHistoryList.remove(data);
					}
				}
				viewStub = new ViewStub(MainActivity.this,
						R.layout.history_schedule);
				viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {

					@Override
					public void onInflate(ViewStub stub, View inflated) {
						setUIElements(inflated, tempHistoryList);
					}
				});

				historyContainer.addView(viewStub);
				viewStub.inflate();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		setViewStub();
	}

	private void setViewStub() {
		historyList.add("first");
		historyList.add("second");
		historyList.add("Three");
		historyList.add("Four");
		historyList.add("Five");
		historyList.add("six");
		historyList.add("Seven");
		historyList.add("eight");
		historyList.add("Nine");

		viewStub = new ViewStub(MainActivity.this, R.layout.history_schedule);
		viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {

			@Override
			public void onInflate(ViewStub stub, View inflated) {
				setUIElements(inflated, historyList);
			}
		});

		historyContainer.addView(viewStub);
		viewStub.inflate();
	}

	private void setUIElements(View v, List<String> history) {
		if (v != null) {
			historyData.clear();
			historyData.addAll(historyList);
			historyListView = (ListView) findViewById(R.id.history_list);
			historyListView.setAdapter(new SearchAdapter(this));

			registerForContextMenu(historyListView);
		}
	}

	private static class SearchAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private static final ArrayList<String> historyData = new ArrayList<String>();

		public SearchAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		static class ViewHolder {
			TextView txtName;
		}

		@Override
		public int getCount() {
			return historyData.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.history_list, null);
				holder = new ViewHolder();
				holder.txtName = (TextView) convertView
						.findViewById(R.id.history_text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txtName.setText(historyData.get(position));
			return convertView;
		}
	}
}
