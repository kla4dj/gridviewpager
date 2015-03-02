package de.ispaylar.masterarbeit_v_0_5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

//    TeleportClient mTeleportClient;
//    TeleportClient.OnSyncDataItemTask mOnSyncDataItemTask;

    LinearLayout mitarbeiter_springer_rufen, mitarbeiter_experte_rufen;
    Button button_springer_rufen, button_experte_rufen;

    ImageView view;
    AnimationDrawable frameAnimation;


    //------------------Start of OnCreate------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mitarbeiter_auswahl);

        mitarbeiter_experte_rufen = (LinearLayout) findViewById(R.id.mitarbeiter_experte_rufen_layout);
        mitarbeiter_springer_rufen = (LinearLayout) findViewById(R.id.mitarbeiter_springer_rufen_layout);
        button_experte_rufen = (Button) findViewById(R.id.button_experte_rufen);
        button_springer_rufen = (Button) findViewById(R.id.button_springer_rufen);


        view = (ImageView) findViewById(R.id.heart_animation);
        view.setBackgroundResource(R.drawable.heart_animation);
        frameAnimation = (AnimationDrawable) view.getBackground();

//        //instantiate the TeleportClient with the application Context
//        mTeleportClient = new TeleportClient(this);
//
//        //Create and initialize task
//        mOnSyncDataItemTask = new ButtonOnSyncDataItemTask();
//        //mMessageTask = new ShowToastFromOnGetMessageTask(); // as seen in Teleport-master
//
//
//        //let's set the two task to be executed when an item is synced or a message is received
//        mTeleportClient.setOnSyncDataItemTask(mOnSyncDataItemTask);
//        //mTeleportClient.setOnGetMessageTask(mMessageTask);




    }
//-------------------here ends oncreate-------------------


    // Called when Activity becomes visible or invisible to the user
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Starting the animation when in Focus
            frameAnimation.start();
        } else {
            // Stoping the animation when not in Focus
            frameAnimation.stop();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mTeleportClient.connect();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mTeleportClient.disconnect();
//
//    }







//  -------------OnClick Experte-------------

    public void OnClickCallExperte(View v){ //maybe also (View arg0)

//        String name = "1" + timestamp;
////        String AgeExperte = "42";
//
//
//        //set the AsyncTask to execute when the Data is Synced
//        mTeleportClient.setOnSyncDataItemTask(new ButtonOnSyncDataItemTask());
//
//        //Let's sync a String!
//        mTeleportClient.syncString("name", name.toString());
////        mTeleportClient.syncString("age", AgeExperte.toString());

        //getSystemService has to be inside of the oncreate
        final Vibrator vibe                  =   (Vibrator)  getSystemService(Context.VIBRATOR_SERVICE);

        startActivity(new Intent(MainActivity.this, Mitarbeiter_Experte_Rufen.class));
        vibe.vibrate(500);
        finish();

    }



//  -------------OnClick Springer-------------

    public void OnClickCallSpringer(View v){
        startActivity(new Intent(MainActivity.this, Mitarbeiter_Springer_Rufen.class));

        //getSystemService has to be inside of the oncreate
        final Vibrator vibe                  =   (Vibrator)  getSystemService(Context.VIBRATOR_SERVICE);
//        startActivity(new Intent(this, WorkerSelection.class));
        vibe.vibrate(100);
        finish();

    }

    public void OnClickLogo(View v){
        startActivity(new Intent(this, WorkerSelection.class));

        finish();
    }


//     public void OnClickCallSpringer(View v){ //maybe also (View arg0)
//
//            //Storing the values inside the string variables
//            String NameSpringer = "Springer";
//            String AgeSpringer = "21";
//
//
//            //set the AsyncTask to execute when the Data is Synced
//            mTeleportClient.setOnSyncDataItemTask(new ButtonOnSyncDataItemTask());
//
//            //Let's sync a String!
//            mTeleportClient.syncString("string", NameSpringer.toString());
//            mTeleportClient.syncString("age", AgeSpringer.toString());
//
//            startActivity(new Intent(MainActivity.this, Mitarbeiter_Springer_Rufen.class));
//            vibe.vibrate(500);
//     }

//    //Task to show the String from DataMap with key "string" when a DataItem is synced
//    public class ButtonOnSyncDataItemTask extends TeleportClient.OnSyncDataItemTask {
//
//        protected void onPostExecute(DataMap dataMap) {
//
//            String s = dataMap.getString("name");
//
//            Toast.makeText(getApplicationContext(), "DataItem - " + s, Toast.LENGTH_SHORT).show();
//
//            mTeleportClient.setOnSyncDataItemTask(new ButtonOnSyncDataItemTask());
//        }
//    }


}

