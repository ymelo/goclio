package com.goclio.challenge;
/**
 * A callback interface that all activities containing this fragment must
 * implement. This mechanism allows activities to be notified of item
 * selections.
 */
public interface FragmentCallbacks {
	/**
	 * Callback for when an item has been selected.
	 */
	public void onMatterSelected(int i);
	public void onNoteSelected(String id);
}
