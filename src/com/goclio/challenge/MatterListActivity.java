package com.goclio.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * An activity representing a list of matters. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link NoteListActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MatterListFragment} and the item details (if present) is a
 * {@link NoteListFragment}.
 * <p>
 * This activity also implements the required
 * {@link MatterListFragment.Callbacks} interface to listen for item selections.
 */
public class MatterListActivity extends FragmentActivity implements
		FragmentCallbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	private Fragment left, right;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matter_list);
		// setContentView(R.layout.activity_matter_twopane);

		if (findViewById(R.id.matter_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			left = getSupportFragmentManager().findFragmentById(
					R.id.matter_list);
//			middle = new MatterDetailFragment();
//			right = new NoteDetailFragment();
			
			((MatterListFragment) left).setActivateOnItemClick(true);
			
//			Bundle arguments = new Bundle();
//			arguments.putString(MatterDetailFragment.ARG_ITEM_ID, "1");
//			
//			middle.setArguments(arguments);
//			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.matter_detail_container, middle);
//			ft.replace(R.id.note_detail_container, right).commit();
//			
//			ft = getSupportFragmentManager().beginTransaction();
//			ft.hide(right);
//			ft.commit();
			
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link MatterListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onMatterSelected(int id) {
		{
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, NoteListActivity.class);
			detailIntent.putExtra(NoteListFragment.ARG_ITEM_ID, String.valueOf(id));
			
			startActivity(detailIntent);
		}
	}

	@Override
	public void onNoteSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			
			Bundle arguments = new Bundle();
			arguments.putString(NoteListFragment.ARG_ITEM_ID, id);
			right = new NoteDetailFragment();
			right.setArguments(arguments);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.matter_detail_container, right);
			ft.addToBackStack(null);
			ft.commit();
		} else {
			Intent detailIntent = new Intent(this, NoteDetailActivity.class);
			detailIntent.putExtra(NoteListFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
			overridePendingTransition(R.anim.master_detail_slide,
					R.anim.master_detail_slide);
		}
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
