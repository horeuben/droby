package com.example.reube.droby.Database;

/**
 * Created by reube on 23/5/2017.
 */
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// To interface with the cloud database
public class DatabaseUtilities {
    // database details
    static String url = "https://drobysql.azurewebsites.net/";

    public static String getResult(String statement) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            //need to replace spaces with %20 for url to work
            statement = statement.replaceAll(" ","%20");
            URL execute = new URL(url+statement);
            connection = (HttpURLConnection) execute.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                //buffer.append(line+"\n");
                buffer.append(line);
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Error retrieving data... Check Internet connection.";

    }

}
