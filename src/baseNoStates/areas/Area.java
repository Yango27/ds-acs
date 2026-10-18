package baseNoStates.areas;


import baseNoStates.Door;

//composite pattern, an Area is an abstract class, that can represent a space, or a partition, that is
//a group of areas, we can see, that this allows us to represent the building and their partitions as a tree
//an Area stores their id and useful methods
public abstract class Area {
    protected String areaId;

    public Area(String areaId){
        this.areaId = areaId;
    }
    public abstract Space[] getSpaces(); //get all spaces in an area
    public abstract Door[] getDoorsGivingAccess(); //get all doors in an area
    public abstract Area getAreaById(String areaId); //given an Id gets the object Area
}
