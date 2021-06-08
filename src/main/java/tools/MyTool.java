package tools;

import java.util.stream.IntStream;

public class MyTool {
//    'M' is statute miles (default)
//                  'K' is kilometers
//                  'N' is nautical miles

    /**
     * Copyright: this method from: https://www.geodatasource.com/developers/java
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @param unit
     * @return
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
    public static String genMultiString(String oneBlock,int times){
        StringBuffer result=new StringBuffer();
        for (int i : IntStream.range(0,times).toArray())
        {
            result.append(oneBlock);
        }
        return result.toString();
    }
}
