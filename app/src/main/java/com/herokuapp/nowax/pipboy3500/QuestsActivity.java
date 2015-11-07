package com.herokuapp.nowax.pipboy3500;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Quests {
    Map<String, String> questToDescMap = new HashMap();

    public Quests() {
        initializeQuestsDescriptions();
    }

    private void initializeQuestsDescriptions() {
        questToDescMap.put("Jakiś pierwszy quest", "Bardzo długi opis drugiego questa. Raz Dwa trzy" +
                "cztery pięć sześć qwertrtsdfghjkl;zxcbckshdflkjshfskdjf sad fhjsdfh sldfj hslf j");
        questToDescMap.put("Drugi Quest", "Tym razem krótszy opis");
    }

    public ArrayList<String> getListOfQuestsNames() {
        ArrayList<String> questsNames = new ArrayList<>();
        questsNames.addAll(questToDescMap.keySet());
        return questsNames;
    }

    public String getDescription(String key) {
        return questToDescMap.get(key);
    }
}

public class QuestsActivity extends ListActivity {
    Quests quests = new Quests();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
        fillInQuestsList();
    }

    private void initializeActivity() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.activity_quests);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void fillInQuestsList() {
        ArrayAdapter<String> questsAdapter = new ArrayAdapter<>(
                this, R.layout.list_item, quests.getListOfQuestsNames());
        setListAdapter(questsAdapter);
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position,  long id) {

        for (int i = 0; i < list.getCount(); i++) {
            View v = list.getChildAt(i);
            v.setBackgroundResource(R.drawable.list_button);
        }

        TextView desc = (TextView) findViewById(R.id.textViewQuestDescription);
        view.setBackgroundResource(R.drawable.list_clicked_button);
        TextView tv = (TextView) view;
        desc.setText(quests.getDescription((String) tv.getText()));
    }

    public void onStatClick (View v) {
        startActivity(new Intent(this, StatisticActivity.class));
        overridePendingTransition(0, 0);
    }

    public void onRadioClick (View v) {
        startActivity(new Intent(this, RadioActivity.class));
        overridePendingTransition(0, 0);
    }

    public void onSpecialClick (View v) {
        startActivity(new Intent(this, SpecialActivity.class));
        overridePendingTransition(0, 0);
    }

    public void onQuestsClick (View v) {
        startActivity(new Intent(this, QuestsActivity.class));
        overridePendingTransition(0, 0);
    }
}
