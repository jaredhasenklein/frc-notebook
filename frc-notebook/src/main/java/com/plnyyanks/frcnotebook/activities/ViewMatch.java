package com.plnyyanks.frcnotebook.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.plnyyanks.frcnotebook.Constants;
import com.plnyyanks.frcnotebook.R;
import com.plnyyanks.frcnotebook.background.GetMatchInfo;
import com.plnyyanks.frcnotebook.datatypes.Event;
import com.plnyyanks.frcnotebook.datatypes.Match;
import com.plnyyanks.frcnotebook.datatypes.Note;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewMatch extends Activity {

    private static String matchKey,eventKey,nextKey,previousKey;
    private static Event parentEvent;
    private static Match match;
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(StartActivity.getThemeFromPrefs());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .commit();
        }

        activity = this;
        ActionBar bar = getActionBar();
        bar.setTitle(parentEvent.getEventName()+" - "+parentEvent.getEventYear());
        bar.setSubtitle(eventKey);

        if(matchKey == null) return;

        new GetMatchInfo(this).execute(previousKey,matchKey,nextKey,eventKey);
    }

    public static void setMatchKey(String key){
        matchKey = key;
        match = StartActivity.db.getMatch(matchKey);
        parentEvent = match.getParentEvent();
        eventKey = parentEvent.getEventKey();

        nextKey = matchKey.replaceFirst("\\d+$",Integer.toString(match.getMatchNumber() + 1));
        previousKey = matchKey.replaceFirst("\\d+$",Integer.toString(match.getMatchNumber()-1));
        Log.d(Constants.LOG_TAG,"Set View Match Vars, matchKey:"+matchKey+", eventKey:"+eventKey+", next: "+nextKey+", prev: "+previousKey);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void previousMatch(View view) {
        setMatchKey(previousKey);
        Intent intent = new Intent(this, ViewMatch.class);
        startActivity(intent);
    }
    public void nextMatch(View view) {
        setMatchKey(nextKey);
        Intent intent = new Intent(this, ViewMatch.class);
        startActivity(intent);
    }

}