package baseNoStates.areas;

import baseNoStates.Door;

public class Space extends Area{
    private Door[] doors;

    public Space(Door[] doors, String areaId){
        super(areaId);
        this.doors = doors;
    }

    @Override
    public Space[] getSpaces() { //returns an array of itself
        return new Space[]{this};
    }

    @Override
    public Door[] getDoorsGivingAccess() { //returns all the doors associated with this Space
        return doors;
    }

    @Override
    public Area getAreaById(String areaId) {
        if(super.areaId.equals(areaId)) {
            return this;
        }else{
            System.out.println("area with id " + areaId + " not found");
            return null;
        }
    }
}
