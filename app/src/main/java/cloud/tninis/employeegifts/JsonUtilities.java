package cloud.tninis.employeegifts;



import android.content.Context;
import android.support.annotation.RawRes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class JsonUtilities {

    public JSONArray getJsonArray(Context context, BonusPeriod period){
        InputStream is=null;
        if(period==BonusPeriod.Easter)
            is= context.getResources().openRawResource(R.raw.easter_table);
        if(period==BonusPeriod.Christmas)
            is= context.getResources().openRawResource(R.raw.christmas_table);

        Writer writer = new StringWriter();
        JSONArray array=null;
        char[] buffer = new char[1024];
        try
        {
            Reader reader = new BufferedReader(new InputStreamReader(is));
            int n;
            while ((n = reader.read(buffer)) != -1)
            {
                writer.write(buffer, 0, n);
            }
            is.close();
            String jsonString = writer.toString();
            array = new JSONArray(jsonString);
        }
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException e){e.printStackTrace();}

        return array;
    }

}
