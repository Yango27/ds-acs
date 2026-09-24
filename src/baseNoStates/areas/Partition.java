package baseNoStates.areas;

import baseNoStates.Door;

import java.util.ArrayList;
import java.util.Arrays;

public class Partition extends Area{
    private Area[] areas;

    public Partition(Area[] areas, String areaId){
        super(areaId);
        this.areas = areas;
    }

    //note that these two methods are recursive calls
    @Override
    public Space[] getSpaces() { //get all spaces inside that area
        ArrayList<Space> spaces = new ArrayList<>();
        for (Area space : this.areas){ //iterates over all spaces/partitions and calls getSpaces to get their associated Space
            spaces.addAll(Arrays.asList(space.getSpaces())); //converts Space array in a list in order to add it to the return List
        }
        return spaces.toArray(new Space[0]); //converts ArrayList to Array
    }

    @Override
    public Door[] getDoorsGivingAccess() { //get all doors inside that area, useful since the request will go door by door for locking/unlocking
        ArrayList<Door> doors = new ArrayList<>();
        for (Area space : this.areas){ //iterates over all spaces/partitions and calls getDoorsGivingAcces to get their associated Doors
            doors.addAll(Arrays.asList(space.getDoorsGivingAccess())); //converts Door array in a list in order to add it to the return List
        }
        return doors.toArray(new Door[0]); //converts ArrayList to Array
    }

    @Override
    public Area getAreaById(String areaId) { //recursive search, gets the area by id for the request
        if (super.areaId.equals(areaId)){
            return this; //if actual node is the searched one return it
        }else{ //otherwise for each child node do the same
            for (Area area : this.areas){
                Area result = area.getAreaById(areaId);
                if (result != null) {
                    return result;
                }
            }
        }
        System.out.println("area with id " + areaId + " not found");
        return null;
    }
}
