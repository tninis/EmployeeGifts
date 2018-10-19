package cloud.tninis.employeegifts;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utilities {
    static final Double  INCREMENT=0.041666;
    JsonUtilities jsonUtil=new JsonUtilities();
    public int findDays(String dateFrom,String dateTo,BonusPeriod period)
    {
        long days=0;
        int add=0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date dateF = sdf.parse(dateFrom+" 00:00:01");
            Date dateT = sdf.parse(dateTo+" 23:59:59");
            long diff = dateT.getTime() - dateF.getTime();

            days=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) +1;
            if(period== BonusPeriod.Christmas &&days >=245)
                days--;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)days;
    }

    public Double[] Calculate(Context ctx,int days, double amount, TypeOfEmployee employeeType, BonusPeriod period) throws JSONException {
        Double[] result=new Double[3];
        JSONArray table=jsonUtil.getJsonArray(ctx,period);
        double factor= Double.parseDouble(table.getJSONObject(days-1).getString(employeeType.name()));
       double easterGift=amount*factor;
       double incrementTotal=easterGift*INCREMENT;

       result[0]=easterGift;
       result[1]=incrementTotal;
       result[2]=easterGift+incrementTotal;

       return result;
    }

}
