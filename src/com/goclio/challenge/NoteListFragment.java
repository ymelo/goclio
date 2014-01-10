package com.goclio.challenge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.goclio.challenge.data.Note;

/**
 * A fragment representing a single matter detail screen. This fragment is
 * either contained in a {@link MatterListActivity} in two-pane mode (on
 * tablets) or a {@link NoteListActivity} on handsets.
 */
public class NoteListFragment extends ListFragment {
	public static interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onNoteSelected(Note note);
	}

	/**
	 * The list used by the adapter (stores all the notes displayed)
	 */
	private ArrayList<Note> list;
	/**
	 * The id of the matter
	 */
	private String id;

	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	public static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {

		@Override
		public void onNoteSelected(Note note) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public NoteListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		// setListAdapter(new
		// ArrayAdapter<DummyContent.DummyItem>(getActivity(),
		// android.R.layout.simple_list_item_activated_1,
		// android.R.id.text1, DummyContent.NOTES));
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			id = intent.getExtras().getString(ARG_ITEM_ID);
			new NoteAsyncTask().execute(id);
		}
		setHasOptionsMenu(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		// mCallbacks.onNoteSelected(DummyContent.ITEMS.get(position).id);
		mCallbacks.onNoteSelected(list.get(position));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.

			outState.putInt(STATE_ACTIVATED_POSITION, getListView()
					.getSelectedItemPosition());
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	public void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	public class NoteAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list = ClioApi.getNotesForMatter(Integer.valueOf(params[0]));
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// on UI thread, update UI
			setListAdapter(new ArrayAdapter<Note>(getActivity(),
					android.R.layout.simple_list_item_activated_1,
					android.R.id.text1, list));
			super.onPostExecute(result);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_note_fragment, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add_note) {

			Intent intent = new Intent(this.getActivity(),
					CreateNoteActivity.class);
			intent.putExtra(CreateNoteActivity.MATTER_ID, id);
			startActivityForResult(intent, 1);
		}
		return true; // consume
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			// Reload notes
			// pros: We do not depend on any data sent by the create activity
			// cons: Network usage
			setListAdapter(null);
			setListShown(false);
			new NoteAsyncTask().execute(id);

		} else {
			// Do not reload
		}
	}
}
