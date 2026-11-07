package baseNoStates.visitor;

import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

import java.util.ArrayList;
import java.util.Arrays;

public class GetSpaces implements Visitor{
    private Space[] resultSpaces;
    @Override
    public void visitPartition(Partition partition){
        ArrayList<Space> spaces = new ArrayList<>();
        for (Area space : partition.getAreas()){ //iterates over all spaces/partitions and calls getSpaces to get their associated Space
            space.acceptVisitor(this);
            spaces.addAll(Arrays.asList(resultSpaces)); //converts Space array in a list in order to add it to the return List
        }
        resultSpaces = spaces.toArray(new Space[0]); //converts ArrayList to Array
    }
    @Override
    public void visitSpace(Space space){
        resultSpaces = new Space[]{space};
    }

    public Space[] getResultSpaces(){
        return resultSpaces;
    }
}
