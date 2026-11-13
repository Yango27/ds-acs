package baseNoStates.visitor;

import baseNoStates.Door;
import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetDoorsGivingAccess implements Visitor{
    private static final Logger logger = LoggerFactory.getLogger(GetDoorsGivingAccess.class);
    Door[] resultDoors;
    @Override
    public void visitPartition(Partition partition){
        ArrayList<Door> doors = new ArrayList<>();
        for (Area space : partition.getAreas()){ //iterates over all spaces/partitions and calls getDoorsGivingAcces to get their associated Doors
            space.acceptVisitor(this);
            doors.addAll(Arrays.asList(resultDoors)); //converts Door array in a list in order to add it to the return List
        }
        resultDoors = doors.toArray(new Door[0]); //converts ArrayList to Array
        logger.debug("Partition {}: {} doors giving access", partition.getId(), resultDoors.length);
    }
    @Override
    public void visitSpace(Space space){
        resultDoors = space.getDoors();
        logger.debug("Space {}: {} doors giving access", space.getId(), resultDoors.length);
    }

    public Door[] getResultDoors() {
        return resultDoors;
    }
}
