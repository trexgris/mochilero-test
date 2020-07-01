package com.example.orero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.orero.bus.Stop;
import com.example.orero.bus.TransportElem;
import com.example.orero.utils.dfs.Graph;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.orero.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }

    private void fillToSpinner(String from_city) throws IOException {
        if (from_city == null)
            return;
        String[] destinations = getApplicationContext().getAssets().list("cr map/"+from_city);
        Spinner spinner_to = (Spinner) findViewById(R.id.spinner_to);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, destinations); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(spinnerArrayAdapter);
    }

    public void fillFromSpinner() throws IOException {
        String path = new String("cr map");
        String[] cr_cities = getApplicationContext().getAssets().list(path);

        // init graph
        Graph g = new Graph(cr_cities.length);
        for (int i = 0 ; i < cr_cities.length ; ++i) {
            String[] destinations = getApplicationContext().getAssets().list("cr map/"+cr_cities[i]);
            for(int j = 0 ; j <destinations.length; ++j) {
                g.addEdge(cr_cities[i], destinations[j]);
            }
        }

        // fill spinner
        Spinner spinner_from = (Spinner) findViewById(R.id.spinner_from);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cr_cities); //selected item will look like a spinner set from XML


        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(spinnerArrayAdapter);
        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedDiv = parent.getItemAtPosition(position).toString();
                try {
                    fillToSpinner(selectedDiv);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });
    }

    public void initGraph() {
    }

    public void run() {
       // String jsonFileString = Utils.getBusSchedule(getApplicationContext(), "test.json");//"cr map/Bagaces/Esparza/from_Bagaces_to_Esparza_stops.json");
       // Gson gson = new Gson();
       // Type stopMapType = new TypeToken<Map<String, Stop>>() {}.getType();
       // stopCityFromTo = gson.fromJson(jsonFileString, stopMapType);
        try {
            fillFromSpinner();
            initGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /*public List<String> naiveSearchImp(String From, String To) throws IOException {
        // stupid recursive implementation
        List<String> ret = new ArrayList<String>();
        ret.add(From);

        String path = new String("cr map/"+From);
        String[] d = getApplicationContext().getAssets().list(path);
        List<String> destinations = new ArrayList<String>();

        destinations = Arrays.asList(d);
        if( destinations.contains(To) ) {
            ret.add(To);
            return ret;
        }
        else
        {}

        // TODO




    }*/

    private Map<String, Stop> stopCityFromTo = null;
}