import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

public class HelperAI {
    public static final String LOGIN_URL = "http://54.65.120.143:8888/hackathon/login";

    public static String getToken() throws IOException {
        HttpURLConnection connection=null;
        try {
            URL url = new URL(LOGIN_URL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();


            JSONObject user = new JSONObject();
            user.put("CustID", "G299319364");
            user.put("UserID", "G299319364");
            user.put("PIN", "9364");
            user.put("Token", "123456789");

            OutputStream out = connection.getOutputStream();
            out.write(user.toString().getBytes());
            out.flush();
            out.close();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");

            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);

            }
            String tokenValue = sb.toString().split(":")[1];
            //System.out.println(tokenValue);
            tokenValue = tokenValue.substring(1, tokenValue.lastIndexOf('"') );
            System.out.println(tokenValue);


            reader.close();

            connection.disconnect();

            return tokenValue;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0";
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0";
        }

    }


    public static final String FExR_URL = "http://54.65.120.143:8888/hackathon/ForeignExchangeRate";
    public static void getForeignExchangeRate(String tokenValue) throws IOException {
        HttpURLConnection connection = null;
        try {

            URL url = new URL(FExR_URL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();


            JSONObject user = new JSONObject();
            user.put("CustID", "G299319364");
            user.put("Token", tokenValue);

            OutputStream out = connection.getOutputStream();
            out.write(user.toString().getBytes());
            out.flush();
            out.close();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");

            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);


            reader.close();


            connection.disconnect();


        }catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        String token = getToken();
        getForeignExchangeRate(token);
    }
}
