package baseNoStates.visitor;

import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

public class GetAreaById implements Visitor{
    private String areaId;
    private Area resultArea;

    public GetAreaById(String areaId){
        this.areaId = areaId;
    }

    @Override
    public void visitPartition(Partition partition){
        if (partition.getId().equals(this.areaId)){
            resultArea = partition; //if actual node is the searched one return it
        }else { //otherwise for each child node do the same
            for (Area area : partition.getAreas()) {
                area.acceptVisitor(this);
            }
        }
    }

    @Override
    public void visitSpace(Space space){
        if(space.getId().equals(areaId)) {
            System.out.println("Searching " + areaId + ": found space " + space.getId() + "FOUND");
            resultArea = space;
        }else{
            System.out.println("Searching " + areaId + ": found space " + space.getId() + "NOT FOUND");
        }
    }

    public Area getResultArea() {
        return resultArea;
    }
}
