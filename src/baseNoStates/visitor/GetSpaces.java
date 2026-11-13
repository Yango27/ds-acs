package baseNoStates.visitor;

import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetSpaces implements Visitor{
    private static final Logger logger = LoggerFactory.getLogger(GetSpaces.class);
    private Space[] resultSpaces;
    @Override
    public void visitPartition(Partition partition){
        ArrayList<Space> spaces = new ArrayList<>();
        for (Area space : partition.getAreas()){ //iterates over all spaces/partitions and calls getSpaces to get their associated Space
            space.acceptVisitor(this);
            spaces.addAll(Arrays.asList(resultSpaces)); //converts Space array in a list in order to add it to the return List
        }
        resultSpaces = spaces.toArray(new Space[0]); //converts ArrayList to Array
        logger.debug("Partition {}: {} spaces found", partition.getId(), resultSpaces.length);
    }
    @Override
    public void visitSpace(Space space){
        resultSpaces = new Space[]{space};
        logger.debug("Space {} found", space.getId());
    }

    public Space[] getResultSpaces(){
        return resultSpaces;
    }
}
