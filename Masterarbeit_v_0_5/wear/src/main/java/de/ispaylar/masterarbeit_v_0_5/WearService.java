package de.ispaylar.masterarbeit_v_0_5;

import android.content.Intent;

import com.mariux.teleport.lib.TeleportService;

public class WearService extends TeleportService {

    @Override
    public void onCreate(){
        super.onCreate();

        //The quick way is to use setOnGetMessageTask, and set a new task
        setOnGetMessageTask(new StartActivityTask());
    }

    public class StartActivityTask extends TeleportService.OnGetMessageTask {

        @Override
        protected void onPostExecute(String  path) {

            if (path.equals("startActivity")){

                Intent startIntent = new Intent(getBaseContext(), MainActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startIntent);
            }

            //let's reset the task (otherwise it will be executed only once)
            setOnGetMessageTask(new StartActivityTask());
        }
    }
}


