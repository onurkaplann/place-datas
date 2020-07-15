package nearplaces;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author merth
 */
 
public class NearPlaces {

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static void main(String[] args) throws IOException {

        File file = new File("data.csv"); // File which includes lat-long datas
        String key = "AIzaSyBTMOcnp-_kB01OFsEqe_USEnEV0iyA0BQ"; // YOUR_API_KEY
        String range = "2000"; // 2KM
        File file2 = new File("places.txt"); // Near places in range with types, names and distances
		int dataCount=5;

        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));

        BufferedReader reader2 = null;
        reader2 = new BufferedReader(new FileReader(file));

        String coordinate;
        String coordinate2;
        String xyAddress = "";
        String inputLine2;
        String name = "";
        String location = "";
        String types = "";
        String distance2 = "";
        String id;
        String latitude;
        String longitude;

        List places = new ArrayList();

        int control = 0;

        double x = 0;
        double y = 0;
        double x1 = 0;
        double y1 = 0;

        reader.readLine();

        for (int f = 0; f < dataCount; f++) {
            System.out.println("***");
            String[] data_xy = reader.readLine().split(",");

            id = data_xy[0];
            coordinate = data_xy[5] + "," + data_xy[6]; // Lat,Long indexes in file

            int QQQ = 0;

            URL map = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + coordinate + "&key=" + key);
            URLConnection yc = map.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("         \"formatted_address\"")) {
                    break;
                }
            }

            x = Double.parseDouble(coordinate.substring(0, coordinate.indexOf(",")));
            y = Double.parseDouble(coordinate.substring(coordinate.indexOf(",") + 1, coordinate.length()));

            URL map2 = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=*&inputtype=textquery&fields=formatted_address,name,type,geometry&locationbias=circle:" + range + "@" + coordinate + "&key=" + key);
            URLConnection yc2 = map2.openConnection();
            BufferedReader in2 = new BufferedReader(new InputStreamReader(
                    yc2.getInputStream()));

            while ((inputLine2 = in2.readLine()) != null) {
                if (inputLine2.startsWith("         \"name\" :")) {
                    QQQ++;
                }
                if (inputLine2.startsWith("         \"name\" :") && QQQ > 2) {
                    name = inputLine2.substring(inputLine2.indexOf("         \"name\" :") + 19, inputLine2.lastIndexOf("\""));
                    name = name.replaceAll(",", "");
                    name = name.replaceAll("\'", "");
                    name = name.replaceAll("/", " ");
                    control++;
                }
                if (inputLine2.startsWith("            \"location\"") && QQQ > 1) {
                    inputLine2 = in2.readLine();
                    location = inputLine2.substring(inputLine2.indexOf("\"lat\"") + 7, inputLine2.lastIndexOf(","));
                    inputLine2 = in2.readLine();
                    location += "," + inputLine2.substring(inputLine2.indexOf("\"lng\"") + 7);
                    location = location.replaceAll(" ", "");

                    x1 = Double.parseDouble(location.substring(0, location.indexOf(",")));
                    y1 = Double.parseDouble(location.substring(location.indexOf(",") + 1, location.length()));
                    double distance = distance(x, y, x1, y1);
                    distance2 = Double.toString(distance).substring(0, 5);

                    control++;
                }
                if (inputLine2.startsWith("         \"types\"") && QQQ > 2) {
                    types = inputLine2;
                    if ((inputLine2 = in2.readLine()) != "       }") {
                        types += " " + inputLine2;
                    }
                    types = types.replaceAll(" ", "");
                    types = types.substring(types.indexOf("[\"") + 2, types.indexOf("\","));
                    control++;

                }
                String data = id + "," + types + "," + name + "," + distance2;

                if (control == 3) {
                    System.out.println(data);
                    places.add(data);
                    control = 0;
                    QQQ = 0;
                }
            }
            in.close();
        }

        FileWriter printer = new FileWriter(file2, true);
        BufferedWriter fplaces = new BufferedWriter(printer);

        for (int j = 0; j < places.size(); j++) {
            fplaces.write("" + places.get(j) + "\n");

        }
        fplaces.close();

    }
}