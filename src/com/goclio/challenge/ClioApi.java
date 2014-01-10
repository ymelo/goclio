package com.goclio.challenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.goclio.challenge.data.Matter;
import com.goclio.challenge.data.Note;

public class ClioApi {
	public static final String URL_MATTERS = "https://app.goclio.com/api/v2/matters/";
	public static final String URL_NOTES = "https://app.goclio.com/api/v2/notes";
	public static final String URL_CREATE_NOTE = "https://app.goclio.com/api/v2/notes";
	public static final String HEADER_AUTH = "";
	public static final String HEADER_CONTENT = "application/json";
	public static final String HEADER_CONTENT_JSON_JS = "application/json,  text/javascript";

	public static final String JSON_KEY_ID = "id";
	// MATTERS
	public static final String JSON_KEY_MATTERS = "matters";
	public static final String JSON_KEY_CLIENT = "client";
	public static final String JSON_KEY_DISPLAY_N = "display_number";
	public static final String JSON_KEY_DESC = "description";

	// NOTES
	public static final String NOTE = "note";
	public static final String JSON_KEY_NOTES = "notes";
	public static final String JSON_KEY_SUBJECT = "subject";
	public static final String JSON_KEY_DATE_CREATE = "created_at";
	public static final String JSON_KEY_DATE_UPDATE = "updated_at";
	public static final String JSON_KEY_DATE = "date";
	public static final String JSON_KEY_DETAIL = "detail";
	public static final String JSON_KEY_REGARDING = "regarding";
	public static final String JSON_KEY_REGARDING_TYPE = "type";
	public static final String JSON_KEY_REGARDING_ID = "id";

	public static final String JSON_KEY_REQ_REGARDING_TYPE = "regarding_type";
	public static final String JSON_KEY_REQ_REGARDING_ID = "regarding_id";

	// MATTERS
	public static ArrayList<Matter> getAllMatters() {
		ArrayList<Matter> list = new ArrayList<Matter>(10);
		try {

			JSONObject jsonObj = new JSONObject(getHttpGetResponse(URL_MATTERS,
					null));
			JSONArray matters = jsonObj.getJSONArray(JSON_KEY_MATTERS);
			for (int i = 0; i < matters.length(); i++) {
				JSONObject jsonMatter = matters.getJSONObject(i);

				Matter m = new Matter(jsonMatter.getInt(JSON_KEY_ID),
						jsonMatter.getString(JSON_KEY_DISPLAY_N),
						jsonMatter.getString(JSON_KEY_DESC));
				list.add(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// NOTES
	public static ArrayList<Note> getNotesForMatter(int id) {

		ArrayList<Note> list = new ArrayList<Note>(10);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(JSON_KEY_REQ_REGARDING_ID, "" + id);
			map.put(JSON_KEY_REQ_REGARDING_TYPE, "Matter");
			JSONObject jsonObj = new JSONObject(getHttpGetResponse(URL_NOTES,
					map));
			JSONArray notes = jsonObj.getJSONArray(JSON_KEY_NOTES);
			for (int i = 0; i < notes.length(); i++) {
				JSONObject json = notes.getJSONObject(i);

				Note n = new Note(json.getInt(JSON_KEY_ID),
						json.getString(JSON_KEY_SUBJECT),
						json.getString(JSON_KEY_DETAIL),
						json.getString(JSON_KEY_DATE_CREATE),
						json.getString(JSON_KEY_DATE_UPDATE),
						json.getString(JSON_KEY_DATE));
				list.add(n);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static Note getNote(int noteId) {
		// String url =
		// "https://app.goclio.com/api/v2/notes?regarding_type=Matter&regardingId=500";
		return null;
	}

	/**
	 * Create a note on clio server POST request
	 * 
	 * @param note
	 *            the note to be created on the clio server
	 * @return true if the note was successfully created, false otherwise
	 * @throws JSONException
	 */
	public static boolean createNote(Note note) throws JSONException {
		JSONObject jsonRoot = new JSONObject();
		JSONObject jsonNote = new JSONObject();
		JSONObject jsonRegarding = new JSONObject();
		/*
		 * json format:
		 * 
		 * { "note": { "subject": "Email Dave", "regarding": { "type": "Matter",
		 * "id": 10 } } }
		 */

		// Regarding
		jsonRegarding.put(JSON_KEY_REGARDING_TYPE, note.getRegardingType());
		jsonRegarding.put(JSON_KEY_REGARDING_ID, note.getRegardingId());

		jsonNote.put(JSON_KEY_REGARDING, jsonRegarding);
		jsonNote.put(JSON_KEY_SUBJECT, note.getSubject());
		jsonNote.put(JSON_KEY_DETAIL, note.getDetail());

		jsonRoot.put(NOTE, jsonNote);

		Log.d("clioAPI", jsonRoot.toString());
		try {
			HttpResponse rep = getHttpPostResponse(URL_CREATE_NOTE, null, jsonRoot);
			rep.getStatusLine();
			if(rep.getStatusLine().getStatusCode() == 201) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String appendUrl(String url, Map<String, String> map) {
		if(map != null) {
			StringBuilder sb = new StringBuilder(url);
			if (map != null) {
				Iterator<Map.Entry<String, String>> entries = map.entrySet()
						.iterator();
				sb.append("?");
				while (entries.hasNext()) {
					Map.Entry<String, String> entry = entries.next();
					System.out.println("Key = " + entry.getKey() + ", Value = "
							+ entry.getValue());
					sb.append(entry.getKey()).append("=").append(entry.getValue())
							.append("&");
				}
			}
			return sb.toString().substring(0, sb.length() - 1);	
		}
		return url;
	}
	
	private static String getHttpGetResponse(String url, Map<String, String> map)
			throws ClientProtocolException, IOException {
		
		String completeUrl = appendUrl(url, map);
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(completeUrl);
		request.setHeader("Authorization", HEADER_AUTH);
		request.setHeader("Content-Type", HEADER_CONTENT);
		request.setHeader("Accept", HEADER_CONTENT);

		HttpResponse httpResponse = client.execute(request);
		HttpEntity httpEntity = httpResponse.getEntity();
		String response = EntityUtils.toString(httpEntity);
		return response;
	}

	private static HttpResponse getHttpPostResponse(String url,
			Map<String, String> map, JSONObject json) throws ClientProtocolException, IOException {
		
		String completeUrl = appendUrl(url, map);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(completeUrl);
		StringEntity se = new StringEntity(json.toString());
		
		
		httppost.setEntity(se);
	    
		httppost.setHeader("Authorization", HEADER_AUTH);
		httppost.setHeader("Content-Type", HEADER_CONTENT_JSON_JS);
		httppost.setHeader("Accept", HEADER_CONTENT);

		// Execute HTTP Post Request
		HttpResponse response = httpclient.execute(httppost);
		return response;

	}
}
