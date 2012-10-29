package com.krsoft.piqu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import twitter4j.*;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String twitterID = "blabla@gmail.com";
        String twitterPassword = "blabla";
        
        Twitter twitter = new Twitter(twitterID,twitterPassword);
        List statuses = twitter.getFriendsTimeline();
        System.out.println("Showing friends timeline.");
        for(int i=0; i < statuses.size() ; i++) {
          Status status = (Status)statuses.get(i);
            System.out.println(status.getUser().getName() + ":" +
                               status.getText()); 
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
