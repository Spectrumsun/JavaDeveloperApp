package spectrumsun.com.javaDeve;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import spectrumsun.com.javaDeve.adapter.CustomListAdapter;
import spectrumsun.com.javaDeve.model.Gitapi;
import spectrumsun.com.mymy.R;
import spectrumsun.com.javaDeve.app.AppController;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Tag used to cancel the request
    private static final String urlJsonObj = "https://api.github.com/search/users?q=java+location:lagos+language:java";

    ProgressDialog pDialog;
    private List<Gitapi> gitList = new ArrayList<Gitapi>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.list);

        adapter = new CustomListAdapter(this, gitList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Downloading content...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlJsonObj, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        try {

                            String arr = response.getString("items");
                            //Log.i("imageUrl", arr);
                            JSONArray jsonArray = new JSONArray(arr);

                            for(int i = 0; i < jsonArray.length(); i++ ){

                                JSONObject jsonPart = jsonArray.getJSONObject(i);

                                final Gitapi gitapi = new Gitapi();

                                final String imageUrl =  jsonPart.getString("avatar_url");
                                final String userName = jsonPart.getString("login");
                                final String userUrl = jsonPart.getString("url");
                                final String htmlUrl = jsonPart.getString("html_url");
                                final String score = jsonPart.getString("score");

                                gitapi.setTitle(userName);
                                gitapi.setThumbnailUrl(imageUrl);
                                gitapi.setUserUrl(htmlUrl);
                                gitapi.setScore(score);

                                Log.i("imageUrl", imageUrl);
                                Log.i("userName", userName);
                                Log.i("userUrl", userUrl);
                                Log.i("htmlUrl", htmlUrl);


                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Intent intent = new Intent(getApplicationContext(), cotent.class);

                                        Gitapi currentGitapi = (Gitapi) adapter.getItem(i);
                                        String name = currentGitapi.getTitle();
                                        String site = currentGitapi.getUserUrl();
                                        String images = currentGitapi.getThumbnailUrl();
                                        //Log.i("image", images);


                                        Toast.makeText(getApplicationContext(), name , Toast.LENGTH_LONG).show();
                                        //Log.i("userName", userName);
                                        intent.putExtra("userName", name);
                                        intent.putExtra("page", site);
                                        intent.putExtra("images", images);

                                        startActivity(intent);
                                    }
                                });

                                gitList.add(gitapi);
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        pDialog.hide();
                    }
                });


                            // Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq);

            }

            @Override
            public void onDestroy() {
                super.onDestroy();
                hidePDialog();
            }

            private void hidePDialog() {
                if (pDialog != null) {
                    pDialog.dismiss();
                    pDialog = null;
                }
            }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder exit= new  AlertDialog.Builder(MainActivity.this);
        exit.setMessage("Do you really want to exit ?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                })

                .setTitle("WAIT");
        exit.show();

    }

}