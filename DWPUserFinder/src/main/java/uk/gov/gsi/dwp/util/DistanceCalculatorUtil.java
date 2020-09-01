//package uk.gov.gsi.dwp.util;
//
//import uk.gov.gsi.dwp.model.Location;
//
///**
// * Util class to provide distance between two location
// * using their coordinates
// */
//public final class DistanceCalculatorUtil {
//
//    public static double findDistance(double latitude, double longitude, Location refLocation) {
//        if ((latitude == refLocation.getLatitude())
//                && (longitude == refLocation.getLongitude())) {
//            return 0;
//        }
//        else {
//            double theta = longitude - refLocation.getLongitude();
//            double dist = Math.sin(Math.toRadians(latitude))
//                    * Math.sin(Math.toRadians(refLocation.getLatitude()))
//                    + Math.cos(Math.toRadians(latitude))
//                    * Math.cos(Math.toRadians(refLocation.getLatitude()))
//                    * Math.cos(Math.toRadians(theta));
//            dist = Math.acos(dist);
//            dist = Math.toDegrees(dist);
//            dist = dist * 60 * 1.1515;
//
//            return (dist);
//        }
//    }
//}
