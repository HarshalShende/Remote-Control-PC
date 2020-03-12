package me.varunon9.remotecontrolpc.livescreen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ScreenshotPC extends AsyncTask<Void,Void,Void> {

    Socket s;
    DataOutputStream ds;
    PrintWriter pw;


    @Override
    protected Void doInBackground(Void... voids) {
        //String msg= voids[0];
        try{
            s=new Socket("192.168.225.23",7803);
//            pw=new PrintWriter(s.getOutputStream());
//            pw.write(msg);
//            pw.close();
//            s.close();

            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            byte buffer[]= (byte[]) ois.readObject();
            long n=System.currentTimeMillis();
            String str=String.valueOf(n);
             FileOutputStream fos =new FileOutputStream("/sdcard/RemotePc/shot"+str+".jpg");
             fos.write(buffer);
             fos.close();
             s.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }
}
