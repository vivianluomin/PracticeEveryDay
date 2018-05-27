package vivian.com.musicplayer;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asus1 on 2017/7/22.
 */

public class LrcHandle {
    private List<String> words = new ArrayList<>();
    private List<Integer> Times = new ArrayList<>();

    public void ReadLRC(String path){


        ConvertFileCode c = new ConvertFileCode();
        Log.d("path",path);

        //c.convertfile(path);



    }
    public List<String> getWords() {
        return words;
    }

    public List<Integer> getTime() {
        return Times;
    }
    // 分离出时间
    private int timeHandler(String string) {
        string = string.replace(".", ":");
        String timeData[] = string.split(":");
// 分离出分、秒并转换为整型
        int minute = Integer.parseInt(timeData[0]);
        int second = Integer.parseInt(timeData[1]);
        int millisecond = Integer.parseInt(timeData[2]);

        // 计算上一行与下一行的时间转换为毫秒数
        int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;

        return currentTime;
    }


    private void addTimeToList(String string) {
        Matcher matcher = Pattern.compile(
                "\\[\\d{1,2}:\\d{1,2}([\\.:]\\d{1,2})?\\]").matcher(string);
        if (matcher.find()) {
            String str = matcher.group();
            Times.add(timeHandler(str.substring(1,
                    str.length() - 1)));
        }

    }
}
