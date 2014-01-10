package com.goclio.challenge;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goclio.challenge.data.Note;

public class CreateNoteActivity extends Activity implements OnClickListener{
	public static final String MATTER_ID = "com.goclio.challenge.matter_id";
	private EditText subject, details;
	private Button cancel, ok;
	/**
	 * Matter id
	 */
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_note);
		subject = (EditText) findViewById(R.id.create_note_tv_subject);
		details = (EditText) findViewById(R.id.create_note_tv_details);
		ok = (Button) findViewById(R.id.create_note_btn_ok);
		cancel = (Button) findViewById(R.id.create_note_btn_cancel);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		Intent i = getIntent();
		if(i != null) {
			id = i.getStringExtra(MATTER_ID);
			if(id == null || id.isEmpty()) {
				Toast.makeText(this, getString(R.string.error_create_note_matter_id), Toast.LENGTH_LONG).show();
			}	
		}
		else {
			Toast.makeText(this, getString(R.string.error_create_note_matter_id), Toast.LENGTH_LONG).show();
		}
		
	}
	@Override
	public void onClick(View v) {
		if(v == ok) {
			String subjectText, detailsText;
			subjectText = subject.getText().toString();
			detailsText = details.getText().toString();
			if(subjectText == null || subjectText.isEmpty() || detailsText == null || detailsText.isEmpty()) {
				Toast.makeText(this, getString(R.string.error_create_note_missing), Toast.LENGTH_LONG).show();
				return ;
			}
			Note n = new Note(subject.getText().toString(), details.getText().toString(), "Matter", Integer.valueOf(id));
			new CreateNoteAsyncTask().execute(n);
			
		}
		else if (v == cancel) {
			setResult(RESULT_CANCELED);
			finish();
		}
		
	}
	
	public class CreateNoteAsyncTask extends AsyncTask<Note, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Note... params) {
			if(params != null && params.length ==1) {
				try {
					return ClioApi.createNote(params[0]);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
			return null;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			if(result) {
				setResult(RESULT_OK);
				finish();
			}
			else {
				Toast.makeText(getApplicationContext(), getString(R.string.error_create_note_server), Toast.LENGTH_LONG).show();
			}
			
		}
		
	}
}
