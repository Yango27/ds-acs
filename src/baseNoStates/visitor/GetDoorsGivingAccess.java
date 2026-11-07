package baseNoStates.visitor;

import baseNoStates.Door;
import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

import java.util.ArrayList;
import java.util.Arrays;

public class GetDoorsGivingAccess implements Visitor{
    Door[] resultDoors;
    @Override
    public void visitPartition(Partition partition){
        ArrayList<Door> doors = new ArrayList<>();
        for (Area space : partition.getAreas()){ //iterates over all spaces/partitions and calls getDoorsGivingAcces to get their associated Doors
            space.acceptVisitor(this);
            doors.addAll(Arrays.asList(resultDoors)); //converts Door array in a list in order to add it to the return List
        }
        resultDoors = doors.toArray(new Door[0]); //converts ArrayList to Array
    }
    @Override
    public void visitSpace(Space space){
        resultDoors = space.getDoors();
    }

    public Door[] getResultDoors() {
        return resultDoors;
    }
}
