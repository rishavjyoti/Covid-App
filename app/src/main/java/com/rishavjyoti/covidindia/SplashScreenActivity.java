package com.rishavjyoti.covidindia;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Opening a full screen window
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        //Calling the thread for splash screen
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }
    //Class created for Splash screen
    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
            SplashScreenActivity.this.finish();
        }
    }
}
