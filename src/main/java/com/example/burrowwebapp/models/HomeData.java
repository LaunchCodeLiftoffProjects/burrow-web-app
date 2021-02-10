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
     * @param allDevices The list of user devices to search.
     * @return List of all devices matching the criteria.
     */
    public static ArrayList<Device> findByColumnAndValue(String column, String value, Iterable<Device> allDevices, Iterable<Component> allComponents) {

        ArrayList<Device> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")){
            return (ArrayList<Device>) allDevices;
        }

        if (column.equals("all")){
            results = findByValue(value, allDevices, allComponents);
            return results;
        }
        for (Component component : allComponents) {

            String aValue = getFieldValue(component, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase()) && !results.contains(component.getDevice())) {
                results.add(component.getDevice());
            }
        }

        return results;
    }

    public static String getFieldValue(Component component, String fieldName){
        String theValue;
        if (fieldName.equals("device")){
            theValue = component.getDevice().getName();
        } else if (fieldName.equals("room")){
            theValue = component.getDevice().getRoom().toString();
        } else if (fieldName.equals("property")){
            theValue = component.getDevice().getRoom().getProperty().toString();
        } else if (fieldName.equals("component description")){
            theValue = component.getDescription();
        } else {
            theValue = component.getDevice().getComponents().toString();
        }

        return theValue;
    }

    /**
     * Search all Device fields for the given term.
     *
     * @param value The search term to look for.
     * @param allDevices The list of user devices to search.
     * @return List of all devices with at least one field containing the value.
     */
    public static ArrayList<Device> findByValue(String value, Iterable<Device> allDevices, Iterable<Component> allComponents) {
        String lower_val = value.toLowerCase();

        ArrayList<Device> results = new ArrayList<>();

        for (Device device : allDevices) {

            if (device.getName().toLowerCase().contains(lower_val)) {
                results.add(device);
            } else if (device.getRoom().toString().toLowerCase().contains(lower_val)) {
                results.add(device);
            } else if (device.getRoom().getProperty().toString().toLowerCase().contains(lower_val)) {
                results.add(device);
            } else if (device.getComponents().toString().toLowerCase().contains(lower_val)) {
                results.add(device);
            } else if (device.toString().toLowerCase().contains(lower_val)) {
                results.add(device);
            }

        }

        for (Component component : allComponents) {

            if (component.getDescription().toLowerCase().contains(lower_val)) {
                if (!results.contains(component.getDevice())) {
                    results.add(component.getDevice());
                }
            }
        }

        return results;
    }


}