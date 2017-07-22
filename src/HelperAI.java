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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public static final String FExR_URL = "http://54.65.120.143:8888/hackathon/HouseValuation";
    public static String getHousePrice(String tokenValue, String city, String dist) throws IOException {
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
            user.put("City", city);
            user.put("Dist", dist);


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

            String ApartmentPrice = sb.toString().substring(sb.indexOf("ApartmentPrice") + 17, sb.indexOf("ApartmentPrice") + 21);
            System.out.println("公寓每坪單價(萬): " + ApartmentPrice);
            String MansionPrice = sb.toString().substring(sb.indexOf("MansionPrice") + 15, sb.indexOf("MansionPrice") + 19);
            System.out.println("大樓每坪單價(萬): " + MansionPrice);

            reader.close();
            connection.disconnect();

            return city + dist + "的公寓每坪單價是" + ApartmentPrice + "萬,大樓每坪單價是" + MansionPrice + "萬";
        }catch (MalformedURLException e) {
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
    public static void main(String[] args) throws IOException {
        String token = getToken();
        String city = "臺南市";
        String dist = "中西區";
        String outputString = getHousePrice(token, city, dist);
        System.out.println(outputString);
    }
}
