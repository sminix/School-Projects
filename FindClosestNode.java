import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
* Given a coordinates file (.co format), and latitude and longitude coordinates, this program finds
* and prints the ID of the node closest to those coordinates.
*
* <pre>
* <code>
*
* Usage:
*   java FindClosestNode dataSet lat lon
*
* For example:
* To find the node closest to the Strand Bookstore in New York City:
*
* $ java FindClosestNode ny.co 40.733305 -73.990969
* $ Closest Node: 186410
*
* To find the code closest to the Hayden Planetarium on NYC:
*
* $ java FindClosestNode ny.co 40.781523 -73.973259
* $ Closest Node: 188474
* </code>
* </pre>
*/
public class FindClosestNode{
    public static void main(String[] args) throws FileNotFoundException{
        if(args.length != 3){
            System.out.println("Usage: java FindClosestNode dataSet lat lon");
            System.out.println("    lat and lon must be given in decimal degrees");
            System.out.println("    For example, the Math Building at UNO is as 30.028593, -90.068400");
            System.exit(1);
        }
        File f = new File(args[0]);
        double lat = Double.parseDouble(args[1]);
        double lon = Double.parseDouble(args[2]);
        Scanner fileReader = new Scanner(f);
        String line = "";
        String closestNodeID = "";
        double minDistance = Double.MAX_VALUE;
        // for each line, if that node is closer to the input coordinates than
        // the known best node, update our minDistance and closestNodeID
        while(fileReader.hasNextLine()){
            line = fileReader.nextLine();
            String[] tokens = line.split(" ");
            // line is formatted as follows
            // v NodeID lon lat
            // lat and lon are in decimal degrees to 6 decimal places of precision,
            // listed as integers
            if(tokens[0].equals("v")){ // if the line lists a vertex
                String ID = tokens[1];
                double curLat = Double.parseDouble(tokens[3]);
                curLat = curLat * 0.000001; // need to move decimal place to proper location
                double curLon = Double.parseDouble(tokens[2]);
                curLon = curLon * 0.000001;
                double distance = calculateHaversineDist(lat, lon, curLat, curLon);
                if(distance < minDistance){
                    closestNodeID = ID;
                    minDistance = distance;
                }
            }
        }
        System.out.println("Closest Node: " + closestNodeID);
    }

    /**
    * Calculate and return the Haversine Distance between two sets of coordinates.
    * <p>
    * The Haversine distance is the distance between two points on a sphere. While the earth is
    * not perfectly spherical, this is good enough for our purposes.
    * @param lat1 the latitude of the first point, in decimal degrees
    * @param lon1 the longitude of the first point, in decimal degrees
    * @param lat2 the latitude of the second point, in decimal degrees
    * @param lon2 the longitude of the second point, in decimal degrees
    * @return the distance between the two points, in kilometers
    */
    public static double calculateHaversineDist(double lat1, double lon1, double lat2, double lon2){
        double radius = 6371; // Earth average radius in kilometers
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2-lat1);
        double deltaLon = Math.toRadians(lon2-lon1);

        double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = radius * c;
        return distance;
    }
}
