package com.goclio.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.goclio.challenge.NoteListFragment.Callbacks;
import com.goclio.challenge.data.Note;

/**
 * An activity representing a single matter detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link MatterListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link NoteListFragment}.
 */
public class NoteListActivity extends FragmentActivity implements Callbacks {
	private boolean mTwoPane = false;
	private Fragment left;
	private NoteDetailFragment noteDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_twopane);
		// setContentView(R.layout.activity_note_list);
		Intent intent = getIntent();
		String id = intent.getStringExtra(NoteListFragment.ARG_ITEM_ID);
		if (findViewById(R.id.note_detail_container) != null) {
			mTwoPane = true;
			
			left = getSupportFragmentManager().findFragmentById(
					R.id.note_list);
			((NoteListFragment) left).setActivateOnItemClick(true);
//			left = new NoteListFragment();
//			Bundle arguments = new Bundle();
//			arguments.putString(NoteListFragment.ARG_ITEM_ID, id);
//			left.setArguments(arguments);
//			getSupportFragmentManager().beginTransaction()
//			.replace(R.id.note_list, left).commit();
//			((NoteListFragment) left).setActivatedPosition(0);
//			noteDetail = NoteDetailFragment.create(0);
//			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.note_detail_container, noteDetail);
//			//ft.addToBackStack(null);
//			ft.commit();
		}
	}

	@Override
	public void onNoteSelected(Note note) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			noteDetail = NoteDetailFragment.create(note);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.note_detail_container, noteDetail);
			//ft.addToBackStack(null);
			ft.commit();
			
		}
		else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, NoteDetailActivity.class);
			detailIntent.putExtra(NoteDetailFragment.ARG_PAGE, note);
			startActivity(detailIntent);
			//overridePendingTransition(R.anim.master_detail_slide, R.anim.master_detail_slide);	
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this,
					MatterListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed(){
		FragmentManager fm = getSupportFragmentManager();
	    if (fm.getBackStackEntryCount() > 0) {
	        fm.popBackStack();
	    } else {
	        super.onBackPressed();  
	    }
	}
	


}
