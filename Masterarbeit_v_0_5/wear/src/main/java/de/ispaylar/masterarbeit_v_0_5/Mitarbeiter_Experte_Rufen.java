package de.ispaylar.masterarbeit_v_0_5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;


public class Mitarbeiter_Experte_Rufen  extends Activity implements DelayedConfirmationView.DelayedConfirmationListener {

    TeleportClient mTeleportClient;
    TeleportClient.OnSyncDataItemTask mOnSyncDataItemTask;

    private DelayedConfirmationView delayedconfirmationview_experte;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mitarbeiter_experte_rufen);

        DelayedConfirmationView delayedconfirmationview_experte  =  (DelayedConfirmationView)    findViewById(R.id.delayed_confirmation_experte_view);

        delayedconfirmationview_experte.setTotalTimeMs(5000);
        delayedconfirmationview_experte.setListener(this);
        delayedconfirmationview_experte.start();

        //instantiate the TeleportClient with the application Context
        mTeleportClient = new TeleportClient(this);

        //Create and initialize task
        mOnSyncDataItemTask = new ButtonOnSyncDataItemTask();
        //mMessageTask = new ShowToastFromOnGetMessageTask(); // as seen in Teleport-master


        //let's set the two task to be executed when an item is synced or a message is received
        mTeleportClient.setOnSyncDataItemTask(mOnSyncDataItemTask);
        //mTeleportClient.setOnGetMessageTask(mMessageTask);


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


//    DateFormat df = new SimpleDateFormat("HH.mm.ss");
//    String timestamp = df.format(Calendar.getInstance().getTime());

//    Long timestamp = ((Long) ((new Date()).getTime()/1000)).longValue();

    Long timestamp = (Long) (System.currentTimeMillis()/1000L);

//    String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.);


//    DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//    String timestamp = df.format(Calendar.getInstance().getTime());

//    //Setting the timestamp for sending a unique string
//    Long tsLong = System.currentTimeMillis()/1000;
//    String timestamp = tsLong.toString();

    @Override
    public void onTimerFinished(View view) {
//        Intent i = new Intent(this, ConfirmationActivity.class);
//        i.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
//        i.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.msg_success));
//        startActivity(i);
//        finish();
        String userID = "1" + timestamp;
//        String AgeExperte = "42";


        //set the AsyncTask to execute when the Data is Synced
        mTeleportClient.setOnSyncDataItemTask(new ButtonOnSyncDataItemTask());

        //Let's sync a String!
        mTeleportClient.syncString("userIDstring", userID.toString());
//        mTeleportClient.syncString("age", AgeExperte.toString());

        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    @Override
    public void onTimerSelected(View view) {

//        Vibrator vibe = (Vibrator)    getSystemService(Context.VIBRATOR_SERVICE);
//        long pattern[]={0,500,110,500,110,450,110,200,110,170,40,450,110,200,110,170,40,500};

        // getIntent().removeExtra("i");
//        finish();
        startActivity(new Intent(this, MainActivity.class));
        finish();
//        vibe.vibrate(pattern,-1);
    }


    public class ButtonOnSyncDataItemTask extends TeleportClient.OnSyncDataItemTask {

        protected void onPostExecute(DataMap dataMap) {

            String s = dataMap.getString("userIDstring");

            Toast.makeText(getApplicationContext(), "DataItem - " + s, Toast.LENGTH_SHORT).show();

            mTeleportClient.setOnSyncDataItemTask(new ButtonOnSyncDataItemTask());
        }
    }

}




