package de.ispaylar.masterarbeit_v_0_5;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;


public class OffeneAnfragenDetailsActivity  extends Activity {

    //------------------------------------------------------------------------------------------------------
    //------------------------------Je nach Ausgabe und Eingabefeld hier Änderungen vornehmen---------------
    //------------------------------------------------------------------------------------------------------
    private EditText RufenderName;
    private EditText Anfragetyp;
    private EditText AssistierenderName;

    //------------------------------------------------------------------------------------------------------
    //------------------------------CustomerID aus OffeneAnfragen Onclick-Methode---------------------------
    //------------------------------------------------------------------------------------------------------

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //------------------------------------------------------------------------------------------------------
        //------------------------------Je nach Ausgabe und Änderung offene_anfrage_details.layout ändern--------------
        //------------------------------------------------------------------------------------------------------
        setContentView(R.layout.offene_anfrage_details);
        //------------------------------------------------------------------------------------------------------
        //------------------------------Je nach Ausgabe und Eingabefeld hier Änderungen vornehmen---------------
        //------------------------------------------------------------------------------------------------------
        RufenderName = (EditText) findViewById(R.id.RufenderName);
        Anfragetyp = (EditText) findViewById(R.id.Anfragetyp);
        AssistierenderName = (EditText) findViewById(R.id.AssistierenderName);


        //------------------------------------------------------------------------------------------------------
        //------------------------------CustomerID aus OffeneAnfragen Onclick-Methode---------------------------
        //------------------------------------------------------------------------------------------------------
        //get Customer ID, wenn nicht
        ID = getIntent().getIntExtra("ID", -1);

        if(ID> 0)
        {
            //we have customer ID passed corretly.
            new GetOffeneAnfragenDetails().execute(new ApiConnector());
        }
    }

    private class GetOffeneAnfragenDetails extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            //------------------------------------------------------------------------------------------------------
            //------------------------------CustomerID aus OffeneAnfragen Onclick-Methode---------------------------
            //------------------------------------------------------------------------------------------------------
            return params[0].GetOffeneAnfragenDetails(ID);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            try
            {
                JSONObject Anfrage = jsonArray.getJSONObject(0);

                //------------------------------------------------------------------------------------------------------
                //------------------------------customer und Stringname je nach Wunsch Ändern---------------------------
                //------------------------------------------------------------------------------------------------------


                RufenderName.setText(Anfrage.getString("calling_worker_ID"));
                Anfragetyp.setText(Anfrage.getString("operation_type"));
                AssistierenderName.setText(""+Anfrage.getInt("assistant_worker_ID"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}

