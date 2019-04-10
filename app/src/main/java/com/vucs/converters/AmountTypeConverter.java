package com.vucs.converters;

public class AmountTypeConverter {
    public static String getAmount(Integer a) {
        String result = "";
        String amount = String.valueOf(a);
        int count=1;
        for(int i = amount.length() -1; i>= 0 ; i--){
            if (count % 2 ==0 && count !=2){
                result = "," +result;
            }
            result = amount.charAt(i) +result;
            count++;
        }

        return result;
    }
}
