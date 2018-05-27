package vivian.com.musicplayer;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by asus1 on 2017/7/27.
 */

public class ConvertFileCode {
    public String convertfile(String path){
        File file = new File(path);
        FileInputStream  fis = null;
        BufferedInputStream bis = null;
        BufferedReader reader = null;
        String text = "";
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            bis.mark(4);
            byte[] first3bytes = new byte[3];
            bis.read(first3bytes);
            bis.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB && first3bytes[2] == (byte) 0xBF) {// utf-8
                               reader = new BufferedReader(new InputStreamReader(bis, "utf-8"));
            } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFE) {

                                 reader = new BufferedReader(new InputStreamReader(bis, "unicode"));
            } else if (first3bytes[0] == (byte) 0xFE && first3bytes[1] == (byte) 0xFF) {

                                 reader = new BufferedReader(new InputStreamReader(bis, "utf-16be"));
            } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFF) {

                                 reader = new BufferedReader(new InputStreamReader(bis, "utf-16le"));
            } else {

                                 reader = new BufferedReader(new InputStreamReader(bis, "GBK"));
            }

            String str = reader.readLine();
            while(str != null){
                text = text +str+"/n";
                str = reader.readLine();
                if(str== null){
                    break;
                }
                Log.d("lrc",text);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return text;
    }
}
