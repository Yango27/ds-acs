package baseNoStates.areas;


import baseNoStates.Door;

//composite pattern

public abstract class Area {
    protected String areaId;

    public Area(String areaId){
        this.areaId = areaId;
    }
    public abstract Space[] getSpaces(); //get all spaces in an area
    public abstract Door[] getDoorsGivingAccess(); //get all doors in an area
    public abstract Area getAreaById(String areaId);
}
