package me.varunon9.remotecontrolpc;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import me.varunon9.remotecontrolpc.livescreen.LiveScreenPC;
import me.varunon9.remotecontrolpc.livescreen.ScreenshotPC;

public class ActivityLivescreen extends AppCompatActivity {

    ImageView imageView;


    private Button take_pic;
    int secondspassed = 0;
    File imageFile;

    private final Handler handler = new Handler();
    //private TextView txt;
    private int count = 0;
    private final int updateFreqMs = 200; // call update every 1000 ms
    @Override

    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, updateFreqMs);
            }
        }, updateFreqMs);
    }

    private void updateTime() {
        // here you update the displayed value
        // this will be called every second indefinitely
        // do your math and generate a string to print, just showing
        // a counter here to demonstrate
        ++count;
        //txt.setText("Count = " + count);
        buttonClick();
    }

    public ActivityLivescreen() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livescreen);

        //txt = (TextView) findViewById(R.id.textnum);
        take_pic = (Button) findViewById(R.id.takepic);



        File direct = new File(Environment.getExternalStorageDirectory() + "/RemotePc");

        if (!direct.exists()) {
            if (direct.mkdir()) ; //directory is created;
        }



        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenshotPC screenshotPC=new ScreenshotPC();
                screenshotPC.execute();
                Toast.makeText(getApplicationContext(),"Screenshot Captured",Toast.LENGTH_SHORT).show();
            }
        });

        //        take_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LiveScreenPC livescreenPC=new LiveScreenPC();
//                livescreenPC.execute();
//                try{
//                    Thread.sleep(1000);
//                }catch (Exception e){e.printStackTrace();}
//
//                File imageFile = new  File("/sdcard/DCIM/harry.jpg");
//                if(imageFile.exists()){
//                    imageView= (ImageView)findViewById(R.id.imageView);
//                    imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
//                }
//            }
//        });


//            for (int i = 0; i < 5; i++) {
//                LiveScreenPC livescreenPC = new LiveScreenPC();
//                livescreenPC.execute();
//                try {
//                    Thread.sleep(3000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                File imageFile = new File("/sdcard/DCIM/harry.jpg");
//                if (imageFile.exists()) {
//                    imageView = (ImageView) findViewById(R.id.imageView);
//                    imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
//                }
//            }

    }


    public void buttonClick() {

        LiveScreenPC livescreenPC = new LiveScreenPC();
        livescreenPC.execute();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageFile = new File("/sdcard/RemotePc/harry.jpg");
        if (imageFile.exists()) {
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        }

    }
}
