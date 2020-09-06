package com.rishavjyoti.covidindia;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {


    public TableFragment() {
        // Required empty public constructor
    }
    TableLayout tableLayout;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);
        Context context;
        TableRow table_head = new TableRow(getContext());
        table_head.setId(10);
        table_head.setBackgroundColor(Color.parseColor("#F6F9FF"));
        table_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView label_states = new TextView(getContext());
        label_states.setId(20);
        label_states.setText("States");
        label_states.setTextSize(15);
        label_states.setTextColor(Color.parseColor("#212121"));
        label_states.setPadding(5, 5, 5, 5);
        table_head.addView(label_states);// add the column to the table row here

        TextView label_total = new TextView(getContext());
        label_total.setId(21);// define id that must be unique
        label_total.setText("Total"); // set the text for the header
        label_total.setTextSize(15);
        label_total.setTextColor(Color.parseColor("#212121"));
        label_total.setPadding(5, 5, 5, 5); // set the padding (if required)
        table_head.addView(label_total);

        TextView label_active = new TextView(getContext());
        label_active.setId(22);// define id that must be unique
        label_active.setText("Active"); // set the text for the header
        label_active.setTextSize(15);
        label_active.setTextColor(Color.parseColor("#212121"));
        label_active.setPadding(5, 5, 5, 5); // set the padding (if required)
        table_head.addView(label_active);

        TextView label_recovered = new TextView(getContext());
        label_recovered.setId(23);
        label_recovered.setText("Recovered");
        label_recovered.setTextSize(15);
        label_recovered.setTextColor(Color.parseColor("#212121"));
        label_recovered.setPadding(5, 5, 5, 5);
        table_head.addView(label_recovered);

        TextView label_deaths = new TextView(getContext());
        label_deaths.setId(24);
        label_deaths.setText("Deaths");
        label_deaths.setTextSize(15);
        label_deaths.setTextColor(Color.parseColor("#212121"));
        label_deaths.setPadding(5, 5, 5, 5);
        table_head.addView(label_deaths);

        tableLayout.addView(table_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,                    //part4
                TableLayout.LayoutParams.WRAP_CONTENT));

        requestQueue = Volley.newRequestQueue(getContext());
        String url ="https://api.rootnet.in/covid19-in/stats/latest";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray regional = data.getJSONArray("regional");
                            TextView[] textArray = new TextView[regional.length()];
                            TableRow[] tr_head = new TableRow[regional.length()];
                            for(int i=0; i<regional.length();i++) {
                                JSONObject state = regional.getJSONObject(i);
                                String loc = state.getString("loc");
                                String totalConfirmed = state.getString("totalConfirmed");
                                String discharged = state.getString("discharged");
                                String deaths = state.getString("deaths");
                                String closed = Integer.toString(Integer.parseInt(discharged)+Integer.parseInt(deaths));
                                String active = Integer.toString(Integer.parseInt(totalConfirmed)-Integer.parseInt(closed));
                                if(loc.equals("Andaman and Nicobar Islands")){
                                    loc = "Andaman and Nicobar";
                                }else if(loc.equals("Madhya Pradesh#")){
                                    loc = "Madhya Pradesh";
                                }
                                tr_head[i] = new TableRow(getContext());
                                tr_head[i].setId(i + 40);
                                tr_head[i].setBackgroundColor(Color.WHITE);
                                tr_head[i].setLayoutParams(new TableRow.LayoutParams(
                                        TableRow.LayoutParams.MATCH_PARENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));

                                // Here create the TextView dynamically

                                textArray[i] = new TextView(getContext());
                                textArray[i].setId(i + 111);
                                textArray[i].setText(loc);
                                textArray[i].setTextColor(Color.parseColor("#212121"));
                                textArray[i].setPadding(5, 5, 5, 5);
                                tr_head[i].addView(textArray[i]);

                                textArray[i] = new TextView(getContext());
                                textArray[i].setId(i + 211);
                                textArray[i].setText(totalConfirmed);
                                textArray[i].setTextColor(Color.parseColor("#212121"));
                                textArray[i].setPadding(5, 5, 5, 5);
                                tr_head[i].addView(textArray[i]);

                                textArray[i] = new TextView(getContext());
                                textArray[i].setId(i + 311);
                                textArray[i].setText(active);
                                textArray[i].setTextColor(Color.parseColor("#212121"));
                                textArray[i].setPadding(5, 5, 5, 5);
                                tr_head[i].addView(textArray[i]);

                                textArray[i] = new TextView(getContext());
                                textArray[i].setId(i + 411);
                                textArray[i].setText(discharged);
                                textArray[i].setTextColor(Color.parseColor("#212121"));
                                textArray[i].setPadding(5, 5, 5, 5);
                                tr_head[i].addView(textArray[i]);

                                textArray[i] = new TextView(getContext());
                                textArray[i].setId(i + 511);
                                textArray[i].setText(deaths);
                                textArray[i].setTextColor(Color.parseColor("#212121"));
                                textArray[i].setPadding(5, 5, 5, 5);
                                tr_head[i].addView(textArray[i]);

                                tableLayout.addView(tr_head[i], new TableLayout.LayoutParams(
                                        TableLayout.LayoutParams.MATCH_PARENT,
                                        TableLayout.LayoutParams.WRAP_CONTENT));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Please check your internet connection.",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
        return view;
    }

}
