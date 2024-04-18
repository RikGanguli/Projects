package com.example.civiladvocacy;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Candidates;

public class GetPoliticianData {
    private static RequestQueue q;
    private static List<Candidates> candidate_list = new ArrayList<>();
    private static final String api_key = "AIzaSyA4VfjVr_Wx9_CQM5VVWLeorgeZMf2RxSc";
    private static MainActivity m_act;
    private static final String link = "https://www.googleapis.com/civicinfo/v2/representatives";

    public static void extract_data(MainActivity activity, String address) {
        m_act = activity;
        q = Volley.newRequestQueue(m_act);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());

        Uri.Builder lnk = Uri.parse(link).buildUpon();
        lnk.appendQueryParameter("key", api_key);
        lnk.appendQueryParameter("address", address);
        String url;
        url = lnk.build().toString();
        Response.Listener<JSONObject> listener = response -> {
            prefs.edit().putString("location", address).apply();
            json_data_parser(response.toString());
        };
        Response.ErrorListener error = error1 -> {
            m_act.data_update(null);
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, error);
        q.add(jsonObjectRequest);
    }

    private static void json_data_parser(String s) {
        try
        {
            JSONObject json_object = new JSONObject(s);
            JSONObject normalizedInputObj = json_object.getJSONObject("normalizedInput");
            String address = normalizedInputObj.getString("line1")+" "+ normalizedInputObj.getString("city")+" "+ normalizedInputObj.getString("state")+" "+ normalizedInputObj.getString("zip") ;
            JSONArray officesJSONArr = (JSONArray) json_object.getJSONArray("offices");
            JSONArray officialsJSONArr = (JSONArray) json_object.getJSONArray("officials");
            List<Candidates> governmentOfficials = new ArrayList<>();

            for (int i = 0; i < officialsJSONArr.length(); i++)
            {
                JSONObject officialIndicesObj = (JSONObject) officialsJSONArr.get(i);
                String name  = officialIndicesObj.getString("name");
                String party = officialIndicesObj.getString("party");
                String photoUrl = "";

                if(officialIndicesObj.has("photoUrl"))
                    photoUrl = officialIndicesObj.getString("photoUrl");

                System.out.println("PhotoURl :::::::::"+photoUrl);

                String phoneNo = "";

                if(officialIndicesObj.has("phones"))
                    phoneNo = ((JSONArray) officialIndicesObj.getJSONArray("phones")).get(0).toString();

                String urls = "";

                if(officialIndicesObj.has("urls"))
                    urls = ((JSONArray) officialIndicesObj.getJSONArray("urls")).get(0).toString();

                String emails = "";

                if(officialIndicesObj.has("emails"))
                    emails = ((JSONArray) officialIndicesObj.getJSONArray("emails")).get(0).toString();

                String officialAddress = "";

                if(officialIndicesObj.has("address")) {
                    JSONObject officialAdd = (JSONObject) (((JSONArray) officialIndicesObj.getJSONArray("address")).get(0));

                    officialAddress = officialAdd.getString("line1") + " " + officialAdd.getString("city") + " " + officialAdd.getString("state") + " " + officialAdd.getString("zip");
                }

                List<String[]> list = new ArrayList<>();

                if(officialIndicesObj.has("channels")) {
                    JSONArray officialChannel = (JSONArray) officialIndicesObj.getJSONArray("channels");

                    for (int j = 0; j < officialChannel.length(); j++) {
                        JSONObject channelObj = (JSONObject) officialChannel.get(j);
                        String[] channel = {channelObj.getString("type"), channelObj.getString("id")};
                        list.add(channel);
                    }
                }
                Candidates candidates1 = new Candidates(name, party, photoUrl, officialAddress, phoneNo,emails, urls, list);
                governmentOfficials.add(candidates1);
            }

            candidate_list = new ArrayList<>();

            for (int i = 0; i < officesJSONArr.length(); i++) {
                JSONObject officialIndicesObj = (JSONObject) officesJSONArr.get(i);
                String post = officialIndicesObj.getString("name");
                JSONArray officialIndices = (JSONArray) officialIndicesObj.getJSONArray("officialIndices");
                for (int j = 0; j < officialIndices.length(); j++) {
                    int index = Integer.parseInt(officialIndices.get(j).toString());
                    Candidates go = governmentOfficials.get(index);
                    Candidates finalGovObj = new Candidates(address, post, go.getCandidate_name(), go.getPol_party(), go.getCandidate_address(), go.getPh_num(),go.getHyperlinks(),go.getEmail(),go.getDp_link(),go.getCandidate_lst());
                    candidate_list.add(finalGovObj);
                }

            }

            m_act.data_update(candidate_list);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
