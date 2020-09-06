package com.rishavjyoti.covidindia;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private com.example.CovidIndia.HomeViewModel mViewModel;
    private TextView tvtotal,tvdeaths,tvrecovered,tvactive,tvclosed,tvrecovpercent,tvdeathpercent,tvDate,tvloc,tvnewtotal,tvpercentincrease,
    tvnewrecovered,tvnewdeaths,tvcases,tvrate;
    private RequestQueue requestQueue;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    private String loc = "India";
    private int i;
    private double index;
    /////////////////////////////////////////////////
    private String total,deaths,recovered,active,closed;
    private float recoverepercent,deathpercent;
    /////////////////////////////////////////////////
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        setupUIViews(view);
//        String currentDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
//        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//        tvDate.setText("Last updated: "+currentDate+" at "+currentTime);
        Context context;
        if (i!=0){
            jsonParseIndia();
        }
        requestQueue = Volley.newRequestQueue(getActivity());
        return view;
    }
    //Checking list item clicked
    private void jsonParse(){
        String url ="https://api.rootnet.in/covid19-in/stats/latest";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Date = response.getString("lastRefreshed");
                            tvDate.setText("Last Updated:"+Date);
                            JSONObject data = response.getJSONObject("data");
                            JSONArray regional = data.getJSONArray("regional");
                            JSONObject object = regional.getJSONObject((int)index-1);
                            total = object.getString("totalConfirmed");
                            deaths = object.getString("deaths");
                            recovered = object.getString("discharged");
                            jsonParseLast(total,recovered,deaths);
                            tvtotal.setText(total);
                            tvdeaths.setText(deaths);
                            tvrecovered.setText(recovered);
                            closed = Integer.toString(Integer.parseInt(recovered)+Integer.parseInt(deaths));
                            active = Integer.toString(Integer.parseInt(total)-Integer.parseInt(closed));
                            tvclosed.setText(closed);
                            tvactive.setText(active);
                            recoverepercent=(Float.parseFloat(recovered)/Float.parseFloat(closed))*100;
                            deathpercent = (Float.parseFloat(deaths)/Float.parseFloat(closed))*100;
                            tvrecovpercent.setText(Integer.toString((int)recoverepercent)+"%");
                            tvdeathpercent.setText(Integer.toString((int)deathpercent)+"%");
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
    }
    //Json parser
    private void jsonParseIndia(){
        i++;
        String url ="https://api.rootnet.in/covid19-in/stats/latest";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Date = response.getString("lastRefreshed");
                            tvDate.setText("Last Updated:"+Date);
                            JSONObject data = response.getJSONObject("data");
                            JSONArray summary = data.getJSONArray("unofficial-summary");
                            for (int i = 0; i < summary.length(); i++){
                                JSONObject object = summary.getJSONObject(i);
                                total = object.getString("total");
                                deaths = object.getString("deaths");
                                recovered = object.getString("recovered");
                                active = object.getString("active");
                                jsonParseLastIndia(total,recovered,deaths);
                                tvtotal.setText(total);
                                tvdeaths.setText(deaths);
                                tvrecovered.setText(recovered);
                                tvactive.setText(active);
                                closed = Integer.toString(Integer.parseInt(total)-Integer.parseInt(active));
                                tvclosed.setText(closed);
                                recoverepercent=(Float.parseFloat(recovered)/Float.parseFloat(closed))*100;
                                deathpercent = (Float.parseFloat(deaths)/Float.parseFloat(closed))*100;
                                tvrecovpercent.setText(Integer.toString((int)recoverepercent)+"%");
                                tvdeathpercent.setText(Integer.toString((int)deathpercent)+"%");
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
    }
    private void jsonParseLastIndia(final String Total, String Recovered, String Deaths){
        String url ="https://api.rootnet.in/covid19-in/stats/history";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            JSONObject last = data.getJSONObject(data.length()-2);
                            JSONObject summary = last.getJSONObject("summary");
                            String total = summary.getString("total");
                            String recovered = summary.getString("discharged");
                            String deaths = summary.getString("deaths");
                            int newrecovered = Integer.parseInt(Recovered)-Integer.parseInt(recovered);
                            int newdeaths = Integer.parseInt(Deaths)-Integer.parseInt(deaths);
                            int newtotal = Integer.parseInt(Total)-Integer.parseInt(total);
                            tvnewtotal.setText(Integer.toString(newtotal));
                            tvnewrecovered.setText(Integer.toString(newrecovered));
                            tvnewdeaths.setText(Integer.toString(newdeaths));
                            float percent = (newtotal/Float.parseFloat(total))*100;
                            tvpercentincrease.setText(Integer.toString((int)percent)+"%");
                        }catch (JSONException e) {
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
    }
    private void jsonParseLast(final String Total, String Recovered, String Deaths){
        String url ="https://api.rootnet.in/covid19-in/stats/history";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            JSONObject last = data.getJSONObject(data.length()-2);
                            JSONArray regional = last.getJSONArray("regional");
                            JSONObject object = regional.getJSONObject((int)index-1);
                            String total = object.getString("totalConfirmed");
                            String recovered = object.getString("discharged");
                            int newrecovered = Integer.parseInt(Recovered)-Integer.parseInt(recovered);
                            int newdeaths =Integer.parseInt(Deaths)-Integer.parseInt(deaths);
                            int newtotal = Integer.parseInt(Total)-Integer.parseInt(total);
                            tvnewtotal.setText(Integer.toString(newtotal));
                            tvnewrecovered.setText(Integer.toString(newrecovered));
                            tvnewdeaths.setText(Integer.toString(newdeaths));
                            float percent = (newtotal/Float.parseFloat(total))*100;
                            tvpercentincrease.setText(Integer.toString((int)percent)+"%");
                        }catch (JSONException e) {
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
    }
    //UI listeners
    private void setupUIViews(View view) {
        tvtotal = view.findViewById(R.id.tvTotal);
        tvactive = view.findViewById(R.id.tvActive);
        tvdeaths = view.findViewById(R.id.tvDeaths);
        tvclosed = view.findViewById(R.id.tvClosed);
        tvrecovered = view.findViewById(R.id.tvRecovered);
        tvrecovpercent = view.findViewById(R.id.tvRecoveredPercent);
        tvdeathpercent = view.findViewById(R.id.tvDeathPercent);
        tvDate = view.findViewById(R.id.tvDate);
        tvloc = view.findViewById(R.id.tvLoc);
        tvnewtotal = view.findViewById(R.id.tvNewCases);
        tvpercentincrease = view.findViewById(R.id.tvPercentIncrease);
        tvnewrecovered = view.findViewById(R.id.tvNewRecovered);
        tvnewdeaths = view.findViewById(R.id.tvNewDeaths);
        tvcases = view.findViewById(R.id.tvCases);
        tvrate = view.findViewById(R.id.tvRate);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(com.example.CovidIndia.HomeViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getloc().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                loc=string;
                tvloc.setText("Covid-19 cases in "+loc);
                if(loc.equals("India")){
                    jsonParseIndia();
                }
            }
        });
        mViewModel.getIndex().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                index = integer;
                jsonParse();
            }
        });
        mViewModel.getI().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                i = integer;
                jsonParse();
            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
