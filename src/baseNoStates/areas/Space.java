package baseNoStates.areas;

import baseNoStates.Door;
import baseNoStates.visitor.Visitor;

//a Space represents the leafs of the tree, and it stores all the doors in that space
//we can see that in it's  methods we don't find any recursive calls, that's because we
//are at the end of the tree
public class Space extends Area{
    private Door[] doors;

    public Space(Door[] doors, String areaId){
        super(areaId);
        this.doors = doors;
    }
    public Door[] getDoors() {
        return doors;
    }
    public void acceptVisitor(Visitor visitor){
        visitor.visitSpace(this);
    }
}
