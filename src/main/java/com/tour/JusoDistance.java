package com.tour;

public class JusoDistance {

    public double degreeToRadian(float degree){
        return degree * Math.PI / 180.0;
    }

    public double radianToDegree(float radian){
        return radian * 180d / Math.PI;
    }

    public double getDistance(float user_lat, float user_lng, float str_lat, float str_lng){

        double userDistance1, userDistance2;
        userDistance1 = user_lng - str_lng;
        userDistance2 = Math.sin(degreeToRadian(user_lat)) * Math.sin(degreeToRadian(str_lat)) + Math.cos(degreeToRadian(user_lat))
                            * Math.cos(degreeToRadian(str_lat)) * Math.cos(degreeToRadian((float) userDistance1));

        userDistance2 = Math.acos(userDistance2);
        userDistance2 = radianToDegree((float) userDistance2);

        userDistance2 = userDistance2 * 60 * 1.1515;
        userDistance2 = userDistance2 * 1.609344;    // 단위 mile 에서 km 변환.
        userDistance2 = userDistance2 * 1000.0;      // 단위  km 에서 m 로 변환


        return userDistance2;
    }

}
