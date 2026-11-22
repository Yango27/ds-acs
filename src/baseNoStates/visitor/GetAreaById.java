package baseNoStates.visitor;

import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Visitor pattern, operation that gets an area given an id
public class GetAreaById implements Visitor {
  private static final Logger logger = LoggerFactory.getLogger(GetAreaById.class);
  private String areaId;
  private Area resultArea;

  public GetAreaById(String areaId) {
    this.areaId = areaId;
  }

  @Override
  public void visitPartition(Partition partition) {
    logger.debug("Searching area {}: visiting partition {}", areaId, partition.getId());
    if (partition.getId().equals(this.areaId)) {
      resultArea = partition; // if actual node is the searched one stores it
      logger.debug("Area {} found (partition)", areaId);
    } else { // otherwise for each child node do the same
      for (Area area : partition.getAreas()) {
        area.acceptVisitor(this);
        if (resultArea != null) {
          return; // stop looking for the Area if it's found
        }
      }
    }
  }

  @Override
  public void visitSpace(Space space) {
    logger.debug("Searching area {}: visiting space {}", areaId, space.getId());
    if (space.getId().equals(areaId)) {
      resultArea = space;
      logger.debug("Area {} found (space)", areaId);
    }
  }

  public Area getResultArea() {
    return resultArea;
  }
}
