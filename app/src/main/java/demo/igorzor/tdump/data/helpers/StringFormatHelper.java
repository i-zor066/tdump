package demo.igorzor.tdump.data.helpers;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormatHelper {

    private static final String TAG = "StringFormatHelper";

    public StringFormatHelper() {
    }


    public String getProperDateFormat(String dateString) {
        SimpleDateFormat formatToParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatToReturn = new SimpleDateFormat("dd MMMM, YYYY");
        Date date = new Date();
        try {
            date = formatToParse.parse(dateString);
            return formatToReturn.format(date);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            return formatToReturn.format(date);
        }
    }
}
