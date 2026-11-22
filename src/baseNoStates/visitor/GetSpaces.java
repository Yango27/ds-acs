package baseNoStates.visitor;

import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Visitor pattern, gets all the spaces given an Area
public class GetSpaces implements Visitor {
  private static final Logger logger = LoggerFactory.getLogger(GetSpaces.class);
  private Space[] resultSpaces;

  @Override
  public void visitPartition(Partition partition) {
    ArrayList<Space> spaces = new ArrayList<>();
    // iterates over all spaces/partitions and calls acceptVisitor to get their associated Space
    for (Area space : partition.getAreas()) {
      space.acceptVisitor(this);
      // converts Space array in a list in order to add it to the return List
      spaces.addAll(Arrays.asList(resultSpaces));
    }
    resultSpaces = spaces.toArray(new Space[0]); // converts ArrayList to Array
    logger.debug("Partition {}: {} spaces found", partition.getId(), resultSpaces.length);
  }

  @Override
  public void visitSpace(Space space) {
    resultSpaces = new Space[] {space};
    logger.debug("Space {} found", space.getId());
  }

  public Space[] getResultSpaces() {
    return resultSpaces;
  }
}
