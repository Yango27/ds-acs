package baseNoStates.areas;

import baseNoStates.visitor.Visitor;

// composite pattern, an Area is an abstract class, that can represent a space, or a partition, that
// is a group of areas, we can see, that this allows us to represent the building and their
// partitions as a tree an Area stores their id and useful methods
// also, now in the second milestone, area is part of the visitor pattern, basically Area is a tree,
// and we can have many operations in this tree, so, in order to be able to add operations, without
// needing to change any class from the areas package we use the visitor pattern, more on Visitor
public abstract class Area {
  protected String areaId;

  public Area(String areaId) {
    this.areaId = areaId;
  }

  public abstract void acceptVisitor(Visitor visitor);

  public String getId() {
    return areaId;
  }
}
