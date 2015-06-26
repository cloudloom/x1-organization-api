package com.tracebucket.x1.organization.api.rest.resource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sadath on 26-Jun-2015.
 */
public class DefaultPositionRestructureResource {
    private ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> positionStructure;

    public ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> getPositionStructure() {
        return positionStructure;
    }

    public void setPositionStructure(ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> positionStructure) {
        this.positionStructure = positionStructure;
    }
}