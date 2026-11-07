package baseNoStates.visitor;

import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

public interface Visitor {
    public abstract void visitPartition(Partition partition);
    public abstract void visitSpace(Space space);
}
