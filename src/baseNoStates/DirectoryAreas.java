package baseNoStates;

import baseNoStates.areas.Area;
import baseNoStates.areas.Partition;
import baseNoStates.areas.Space;

public final class DirectoryAreas {
    private static Area rootArea;
    private static Door[] allDoors;

    public static void makeAreas(){
        allDoors = DirectoryDoors.getAllDoors().toArray(new Door[0]); //get all doors
        Door d1 = findDoorById("D1"); // exterior, parking
        Door d2 = findDoorById("D2"); // stairs, parking

        // ground floor
        Door d3 = findDoorById("D3"); // exterior, hall
        Door d4 = findDoorById("D4"); // stairs, hall
        Door d5 = findDoorById("D5"); // hall, room1
        Door d6 = findDoorById("D6"); // hall, room2

        // first floor
        Door d7 = findDoorById("D7"); // stairs, corridor
        Door d8 = findDoorById("D8"); // corridor, room3
        Door d9 = findDoorById("D9"); // corridor, IT

        // creating spaces, those areas with only doors and not other areas
        Space parking = new Space(new Door[]{d1, d2}, "parking");
        Space hall = new Space(new Door[]{d3, d4}, "hall");
        Space room1 = new Space(new Door[]{d5}, "room1");
        Space room2 = new Space(new Door[]{d6}, "room2");
        Space room3 = new Space(new Door[]{d8}, "room3");
        Space corridor = new Space(new Door[]{d7}, "corridor");
        Space IT = new Space(new Door[]{d9}, "IT");

        // creating partitions, those areas that includes other spaces
        Partition basement = new Partition(new Space[]{parking}, "basement");
        Partition groundFloor = new Partition(new Space[]{hall, room1, room2}, "groundFloor");
        Partition floor1 = new Partition(new Space[]{room3, corridor, IT}, "floor1");
        Partition stairs = new Partition(new Space[]{}, "stairs");
        Partition exterior = new Partition(new Space[]{}, "exterior");

        // creating the rootArea, basically all the building
        rootArea = new Partition(new Partition[]{basement, groundFloor, floor1, stairs, exterior}, "building");
    }
    public static Area findAreaById(String id){
        return rootArea.getAreaById(id);
    }
    public static Door findDoorById(String id){
        for (Door door : allDoors) {
            if (door.getId().equals(id)) {
                return door;
            }
        }
        System.out.println("door with id " + id + " not found");
        return null;
    }

    public static Door[] getAllDoors() {
        return allDoors;
    }
}
