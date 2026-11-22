package baseNoStates.areas;

import baseNoStates.visitor.Visitor;

// a partition is a class that represents an Area, and it can store more Areas in them
// basically, in a tree, the partitions are those nodes that are not leafs
public class Partition extends Area {
  private Area[] areas;

  public Partition(Area[] areas, String areaId) {
    super(areaId);
    this.areas = areas;
  }

  public Area[] getAreas() {
    return areas;
  }

  public void acceptVisitor(Visitor visitor) {
    visitor.visitPartition(this);
  }
}
