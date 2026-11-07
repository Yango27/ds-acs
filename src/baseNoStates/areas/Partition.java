package baseNoStates.areas;

import baseNoStates.Door;
import baseNoStates.visitor.Visitor;

import java.util.ArrayList;
import java.util.Arrays;

//a partition is a class that represents an Area, and it can store more Areas in them
//basically, in a tree, the partitions are those nodes that are not leafs
//note that all of their methods are recursives
public class Partition extends Area{
    private Area[] areas;

    public Partition(Area[] areas, String areaId){
        super(areaId);
        this.areas = areas;
    }
    public Area[] getAreas() {
        return areas;
    }
    public void acceptVisitor(Visitor visitor){
        visitor.visitPartition(this);
    }
}
