package baseNoStates.areas;

import baseNoStates.Door;

//a Space represents the leafs of the tree, and it stores all the doors in that space
//we can see that in it's  methods we don't find any recursive calls, that's because we
//are at the end of the tree
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
            System.out.println("Searching " + areaId + ": found space " + super.areaId + "FOUND");
            return this;
        }else{
            System.out.println("Searching " + areaId + ": found space " + super.areaId + "NOT FOUND");
            return null;
        }
    }
}
