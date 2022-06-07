package common.utils;

import common.collection.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Parser {
    public static boolean isNumber(String number){
        char[] chars =number.toCharArray();
        for (char letter :
                chars) {
            if (letter != '.'){
                if ((letter < 48 || letter > 57)  ){
                    return false;
                }
            }
        }
        return true;
    }
    public static String parsLong(String number){
        if (number.isEmpty()){
            return "empty please fill number";
        }
        if(isNumber(number)) {
            if (number.contains(".")){
                return "this is not a double point number please enter whole number";
            }
            if (new BigInteger(number).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
                return "number is too big, please enter a smaller number";
            }else return "";
        }
        return "this is not a number, please enter number only";
    }
    public static String ParseInt(String number){
        if (number.isEmpty()){
            return "empty please fill number";
        }
        if(isNumber(number)) {
            if (number.contains(".")){
                return "this is  a double point number please enter whole number";
            }
            if (new BigInteger(number).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
                return "number is too big, please enter a smaller number";
            }else return "";
        }
        return "this is not a number, please enter number only";
    }
    public static String ParseDouble(String number){
        if (number.isEmpty()){
            return "empty please fill number";
        }
        if(isNumber(number)) {
            if (new BigDecimal(number).compareTo(BigDecimal.valueOf((long) Double.MAX_VALUE)) > 0) {
                return "number is too big, please enter a smaller number";
            }else if (Double.parseDouble(number) ==0){
                return "engine power can not be 0, please enter a positive number";
            }else return "";
        }
        return "this is not a number, please enter number only";
    }
    public static String ParseVehicleType(String type){
        type=type.toUpperCase().trim();
        if(Arrays.stream(VehicleType.values()).map(Enum::toString).collect(Collectors.toList()).contains(type)){
            return "";
        }
        return "please choose one of the parametrs";
    }
    public static String ParseFuelType(String type){
        type=type.toUpperCase().trim();
        if(Arrays.stream(FuelType.values()).map(Enum::toString).collect(Collectors.toList()).contains(type)){
            return "";
        }
        return "please choose one of the parametrs";
    }

}
