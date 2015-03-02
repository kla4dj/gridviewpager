package de.ispaylar.masterarbeit_v_0_5;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OffeneAnfragenListViewAdapter extends BaseAdapter {

    private JSONArray dataArray;
    private Activity activity;

    private static LayoutInflater inflater = null;



    public OffeneAnfragenListViewAdapter(JSONArray jsonArray, Activity a){

        dataArray = jsonArray;
        activity = a;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

//    Array length

    @Override
    public int getCount() {

        return this.dataArray.length();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //set up convert view if it is null
        ListCell cell;


//Check if the convertview is null, if it is null it probably means that this //is the first time the view has been displayed
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.offene_anfrage_zelle, null);
            cell = new ListCell();


            //------------------------------------------------------------------------------------------------------
            //--------------------------------Cellpointer to the Cells----------------------------------------------
            //------------------------------------------------------------------------------------------------------


            cell.RufenderName = (TextView) convertView.findViewById(R.id.Rufender_Name);
            cell.Anfragezeitpunkt = (TextView) convertView.findViewById(R.id.Anfragezeitpunkt);
            cell.Anfragetyp = (TextView) convertView.findViewById(R.id.Anfragetyp);
            cell.rufendeStationBild = (ImageView) convertView.findViewById(R.id.rufende_Station_Bild);
            cell.MitarbeiterRufendBild = (ImageView) convertView.findViewById(R.id.Mitarbeiter_rufend_Bild);


//          Setting the setTag to be able to reuse the convertView

            convertView.setTag(cell);

        }
        else
        {
            cell = (ListCell) convertView.getTag();
        }




        // change the data of cell
        try {


            JSONObject jsonObject = this.dataArray.getJSONObject(position);


//------------------------------------------------------------------------------------------------------
//--------------------------------Getting the relevant Data and putting it into the Cell----------------
//------------------------------------------------------------------------------------------------------



//          For the Textview it is getting the FirstName and the Lastname out of the JSON-Object
//            cell.RufenderName.setText(jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));

            String requestTime = jsonObject.getString("request_date");

            cell.Anfragezeitpunkt.setText(" " + requestTime);


//            cell.Anfragetyp.setText(" " + jsonObject.getString("operation_type"));

            String Anfragetyp = jsonObject.getString("operation_type");
            if (Anfragetyp.equals("replacement")){
                cell.Anfragetyp.setText("Springer");
            }else if (Anfragetyp.equals("expert")){
                cell.Anfragetyp.setText("Experte");
            }


            String Station = jsonObject.getString("workstation_ID");
            if (Station.equals("0")){
                cell.rufendeStationBild.setImageResource(R.drawable.station1);
            }else if (Station.equals("1")){
                cell.rufendeStationBild.setImageResource(R.drawable.station2);
            }else if (Station.equals("2")){
                cell.rufendeStationBild.setImageResource(R.drawable.station3);
            }else if (Station.equals("3")){
                cell.rufendeStationBild.setImageResource(R.drawable.station4);
            }else if (Station.equals("4")) {
                cell.rufendeStationBild.setImageResource(R.drawable.station5);
            }

//          Checking what is written in the cell MitarbeiterRufendBild

// -> if there is Teschemacher(0)-> show picture and name the cell Teschemacher...
// -> if there is Steinhaeusser(1)-> show picture Steinhaeusser...
// -> if there is Meis(2)-> show picture Meis...
// -> if there is Lock(3)-> show picture Lock...
// -> if there is Plehn(4)-> show picture Plehn...
// -> if there is Dollinger(5)-> show picture Dollinger...


            String MitarbeiterRufend = jsonObject.getString("calling_worker_ID");
            if (MitarbeiterRufend.equals("0")) {
                cell.MitarbeiterRufendBild.setImageResource(R.drawable.teschemacher);
                cell.RufenderName.setText("Ulrich");
            } else if (MitarbeiterRufend.equals("1")) {
                cell.MitarbeiterRufendBild.setImageResource(R.drawable.steinhaeusser);
                cell.RufenderName.setText("Tobias");
            } else if (MitarbeiterRufend.equals("2")) {
                cell.MitarbeiterRufendBild.setImageResource(R.drawable.meis);
                cell.RufenderName.setText("Jan-Fabian");
            } else if (MitarbeiterRufend.equals("3")) {
                cell.MitarbeiterRufendBild.setImageResource(R.drawable.lock);
                cell.RufenderName.setText("Christopher");
            } else if (MitarbeiterRufend.equals("4")) {
                cell.MitarbeiterRufendBild.setImageResource(R.drawable.plehn);
                cell.RufenderName.setText("Christian");
            } else if (MitarbeiterRufend.equals("5")) {
                cell.MitarbeiterRufendBild.setImageResource(R.drawable.dollinger);
                cell.RufenderName.setText("Christiane");
            }else if (MitarbeiterRufend.equals("6")) {
                cell.MitarbeiterRufendBild.setImageResource(R.drawable.deniz);
                cell.RufenderName.setText("Deniz");
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return convertView;
    }






//------------------------------------------------------------------------------------------------------
//--------------------------------Methode: Change for where to put that parsed data at------------------
//------------------------------------------------------------------------------------------------------


//  Methode for creating ceveral cells


    private class ListCell
    {
//        private TextView FullName;
//        private TextView Age;
//        private ImageView mobile;

        private TextView RufenderName;
        private TextView Anfragezeitpunkt;
        private TextView Anfragetyp;
        private ImageView rufendeStationBild;
        private ImageView MitarbeiterRufendBild;


    }
}

