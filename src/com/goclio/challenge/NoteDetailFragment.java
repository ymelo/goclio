package com.goclio.challenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goclio.challenge.data.Note;

public class NoteDetailFragment extends Fragment {
	private TextView detail, subject, date, createAt, updateAt;
	private Note note;
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static NoteDetailFragment create(Note note) {
    	NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAGE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPageNumber = getArguments().getInt(ARG_PAGE);
        note = getArguments().getParcelable(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        // Set the title view to show the page number.
        ((TextView) rootView.findViewById(android.R.id.text1)).setText("note " + note.getId());
        detail = (TextView) rootView.findViewById(R.id.notes_detail);
        subject = (TextView) rootView.findViewById(R.id.notes_subject);
        createAt = (TextView) rootView.findViewById(R.id.notes_created_at);
        updateAt = (TextView) rootView.findViewById(R.id.notes_updated_at);
        date = (TextView) rootView.findViewById(R.id.notes_date);
        
        detail.setText(note.getDetail());
        subject.setText(note.getSubject());
        createAt.setText(note.getCreatedAt());
        updateAt.setText(note.getUpdatedAt());
        date.setText(note.getDate());
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

}