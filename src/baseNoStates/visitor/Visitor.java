package baseNoStates.visitor;

import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

// Visitor pattern, we use this because in a future we might be adding more operations related to
// Areas if we didn't do this, we might need to modify each class from the package areas each time
// we need to add a new operations. Each visitor that implements a Visitor is a different Operation,
// and each Area has a acceptVisitor, that takes a Visitor and calls the function of that area class
// from that visitor and in Visitor we have one function per each Area that we have, each function
// implements that specific operation for that Area class
public interface Visitor {
  public abstract void visitPartition(Partition partition);

  public abstract void visitSpace(Space space);
}
