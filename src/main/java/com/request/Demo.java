package com.request;

import com.services.DataService;

public class Demo {

    private static long time;


    public static void main(String[] args) {
        String url = "https://youla.ru/moskva/auto/s-probegom?attributes[price][to]=11000000000&attributes[price][from]=10000&attributes[term_of_placement][from]=-7%20days&attributes[term_of_placement][to]=now&attributes[auto_state][0]=161308&attributes[auto_owner_type][0]=161309&attributes[auto_mileage][from]=&attributes[auto_mileage][to]=100000&attributes[auto_drive_type][0]=20805&attributes[auto_gear_type][0]=20810&attributes[auto_gear_type][1]=20808&attributes[auto_gear_type][2]=20807&attributes[auto_engine_power][from]=1&attributes[auto_engine_power][to]=1600&attributes[auto_engine][0]=20751&attributes[auto_engine][1]=20748&attributes[auto_color][0]=20755&attributes[auto_color][1]=20772&attributes[auto_color][2]=20765&attributes[auto_color][3]=20766&attributes[auto_year][from]=&attributes[auto_year][to]=2013";
        String[] array = url.split("\\?");

        String domain = array[0];
        String attributes = array[1];
        DataService dataService = new DataService(domain, attributes);
        dataService.getModelRequest();


    }
}
