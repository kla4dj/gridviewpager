package de.ispaylar.masterarbeit_v_0_5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OffeneAnfragen extends Activity {

    private JSONArray jsonArray;



    private ListView AlleOffenenAnfragenListView;

//    Für den Fall, dass man nur den Inhalt der Tabelle anzeigen möchte
//    private TextView responseTextView;

    TeleportClient mTeleportClient;
    TeleportClient.OnSyncDataItemTask mSubmit;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offene_anfragen_liste);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mTeleportClient = new TeleportClient(this);

        mSubmit = new OnSyncDataItemToDatabaseTask();
        mTeleportClient.setOnSyncDataItemTask(mSubmit);

//        Für den Fall, dass man nur den Inhalt der Tabelle anzeigen möchte
//        responsiveTextView = (TextView) findViewById(R.id.resposiveTextView);
//
//        new GetAlleOffenenAnfragenTask().execute(new ApiConnector());


        //Connecting the variable with the actual layout
        AlleOffenenAnfragenListView = (ListView) findViewById(R.id.AlleOffenenAnfragenListView);


        new GetAlleOffenenAnfragenTask().execute(new ApiConnector());

//      was passiert wenn ich auf ein Item im ListView klicke
        AlleOffenenAnfragenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //----------Depending on which position it will be clicked, the JSONArray will be the one which got clicked

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String clickedAnfrageRufenderName = null;

                try {


                    JSONObject anfrageClicked = jsonArray.getJSONObject(position);

                    System.out.println(anfrageClicked);

//                    String clickedAnfrage = anfrageClicked.getString("callingworkerID").toString();
//
//                    if (clickedAnfrage.equals("0")) {
//                        clickedAnfrageRufenderName = "Ulrich";
//
//                    } else if (clickedAnfrage.equals("1")) {
//                        clickedAnfrageRufenderName = "Tobias";
//
//                    } else if (clickedAnfrage.equals("2")) {
//                        clickedAnfrageRufenderName = "Jan-Fabian";
//
//                    } else if (clickedAnfrage.equals("3")) {
//                        clickedAnfrageRufenderName = "Christopher";
//
//                    } else if (clickedAnfrage.equals("4")) {
//                        clickedAnfrageRufenderName = "Christian";
//
//                    } else if (clickedAnfrage.equals("5")) {
//                        clickedAnfrageRufenderName = "Christiane";
//
//                    } else if (clickedAnfrage.equals("6")) {
//                        clickedAnfrageRufenderName = "Deniz";
//
//                }

            } catch (JSONException e) {
                    e.printStackTrace();
                }


                String msg = "Wollen Sie die offene Anfrage von" + clickedAnfrageRufenderName + " an Station annehmen?";//+position + "ausgewählt" +view.toString();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OffeneAnfragen.this);
                dialogBuilder.setTitle("Auftragsannahme");
                dialogBuilder.setMessage(msg);
                dialogBuilder.setPositiveButton("Annehmen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();

            }});
    }



    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
    }

    //ShowToast Class für SyncDataItem and Databaseinput!!! That's where the rock'n'roll happens
    public class OnSyncDataItemToDatabaseTask extends TeleportClient.OnSyncDataItemTask {

        InputStream is = null;

        @Override
        protected void onPostExecute(DataMap dataMap) {

//            https://www.youtube.com/watch?v=MdyZKewSwFg

            String s = dataMap.getString("userIDstring");

            String idstring = s.substring(0,1);
            String timestamp = s.substring(1);


            //Setting the nameValuePairs
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //Adding the string variabls inside the nameValuePairs
            nameValuePairs.add(new BasicNameValuePair("userIDstring", idstring));
            nameValuePairs.add(new BasicNameValuePair("userIDstring", timestamp));

            // Setting up the connection inside the try catch block

            try {
//                //Setting up the default http client
//                HttpClient httpClient = new DefaultHttpClient();
//
//                //Setting up the http post method and passing the url in case
//                // of online database and the ip address in case of localhost database.
//                //And the php file which serves as the link between the android app
//                // and the database
//
////                HttpPost httpPost = new HttpPost("http://192.168.0.15:8888/tutorial.php");



                //--------------------------------new GET-Part - START------------------------------------------------
                HttpClient httpclient = new DefaultHttpClient();

                HttpResponse response = httpclient.execute(new HttpGet("http://192.168.10.1/workstation_lib/gear/call.php?idstring="+idstring+"&timestamp="+timestamp));
                is = response.getEntity().getContent();
                //--------------------------------new GET-Part - End------------------------------------------------





//                //Passing the nameValuePairs inside the httpPost
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                //Getting the response
//                HttpResponse response = httpClient.execute(httpPost);
//
//                //Setting up the entity
//                HttpEntity entity = response.getEntity();
//
//                //Setting up the content inside an input stream reader
//                is = entity.getContent();

                //Displaying a toast if the data is entered successfully
                String msg = "Data entered successfully in Database - ";
                String time = "  Timestamp : ";
                String workerID = " WorkerID : ";
                Toast.makeText(getApplicationContext(), msg + workerID + idstring + time + timestamp, Toast.LENGTH_LONG).show();
            }

            //Writing the catch blocks to handle the exceptions
            catch (ClientProtocolException e) {
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Log_tag", "IOException");
                e.printStackTrace();
            }



            //let's reset the task (otherwise it will be executed only once)
            mTeleportClient.setOnSyncDataItemTask(new OnSyncDataItemToDatabaseTask());


        }
    }
//                try
//                {
//                    //Get the customer which was clicked
//                    JSONObject offeneAnfrageClicked = jsonArray.getJSONObject(position);
//
//                    //Send Customer ID Here we go from the current Activity to the OffeneAnfragenDetailsActivity
//                    Intent showDetails = new Intent(getApplicationContext(), OffeneAnfragenDetailsActivity.class);
//
//         //------------------------------------------------------------------------------------------------------
//         // --------------------------------Anpassen an die entsprechende Stelle, die analysiert werden soll----------------
//         //------------------------------------------------------------------------------------------------------
//                    showDetails.putExtra("RequestID", offeneAnfrageClicked.getInt("ID"));
//
//                    startActivity(showDetails);
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }



    //  Anzeige aller Offenen Anfragen in einem ListView
    public void setListAdapter(JSONArray jsonArray)
    {
        jsonArray = jsonArray;
        AlleOffenenAnfragenListView.setAdapter(new OffeneAnfragenListViewAdapter(jsonArray,this));

    }

//    Anzeige aller im jsonArray enthaltenen
//    public void setTextToTextView(JSONArray jsonArray){
//        String s  = "";
//        for(int i=0; i<jsonArray.length();i++){
//
//            JSONObject json = null;
//            try {
//                json = jsonArray.getJSONObject(i);
//                s = s +
//                        "Name und Produkt : "+json.getString("calling_worker_ID")+" "+json.getString("product_ID")+"\n"+
//                        "Station : "+json.getInt("workstation_ID")+"\n"+
//                        "Anfragezeitpunkt : "+json.getString("request_date")+"\n\n";
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        this.responseTextView.setText(s);
//
//    }

    private class GetAlleOffenenAnfragenTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].OffeneAnfragen();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            //setTextToTextView(jsonArray);
            setListAdapter(jsonArray);


        }
    }

}

