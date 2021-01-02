package com.example.burrowwebapp.models;

import java.util.ArrayList;

public class HomeData {


    /**
     * Returns the results of searching the Home data by field and search term.
     *
     * For example, searching for Room "Living" will include results
     * with "Living Room" and "Living Room 2".
     *
     * @param column Device field that should be searched.
     * @param value Value of the field to search for.
     * @param allDevices The list of devices to search.
     * @return List of all devices matching the criteria.
     */
    public static ArrayList<Device> findByColumnAndValue(String column, String value, Iterable<Device> allDevices) {

        ArrayList<Device> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")){
            return (ArrayList<Device>) allDevices;
        }

        if (column.equals("all")){
            results = findByValue(value, allDevices);
            return results;
        }
        for (Device device : allDevices) {

            String aValue = getFieldValue(device, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(device);
            }
        }

        return results;
    }

    public static String getFieldValue(Device device, String fieldName){
        String theValue;
        if (fieldName.equals("name")){
            theValue = device.getName();
        } else if (fieldName.equals("employer")){
            theValue = device.getRoom().toString();
        } else {
            theValue = device.getProperty().toString();
        }

        return theValue;
    }

    /**
     * Search all Job fields for the given term.
     *
     * @param value The search term to look for.
     * @param allDevices The list of devices to search.
     * @return List of all devices with at least one field containing the value.
     */
    public static ArrayList<Device> findByValue(String value, Iterable<Device> allDevices) {
        String lower_val = value.toLowerCase();

        ArrayList<Device> results = new ArrayList<>();

        for (Device device : allDevices) {

            if (device.getName().toLowerCase().contains(lower_val)) {
                results.add(device);
            } else if (device.getRoom().toString().toLowerCase().contains(lower_val)) {
                results.add(device);
            } else if (device.getProperty().toString().toLowerCase().contains(lower_val)) {
                results.add(device);
            } else if (device.toString().toLowerCase().contains(lower_val)) {
                results.add(device);
            }

        }

        return results;
    }


}