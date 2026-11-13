package baseNoStates;

import baseNoStates.doorstates.DoorState;
import baseNoStates.doorstates.Locked;
import baseNoStates.doorstates.Unlocked;
import baseNoStates.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Door, a class that represents a single door, and stores their id, if they are closed, and a
//state, it is used to process a request, this request is basically to do an action to the door
public class Door {
  private final String id;
  private boolean closed; // physically
  private DoorState doorState;
  private static final Logger logger = LoggerFactory.getLogger(Door.class);

  public Door(String id) {
    this.id = id;
    closed = true;
    this.doorState = new Unlocked(this);
  }

  //RequestReader calls for the Door processRequest
  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if it's closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
      logger.info("Authorized");
    } else {
      logger.warn("Not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  //given an action, sets the state of the door
  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        doorState.open();
        break;
      case Actions.CLOSE:
        doorState.close();
        break;
      case Actions.LOCK:
        doorState.lock();
        break;
      case Actions.UNLOCK:
        doorState.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        doorState.shortly_unlocked();
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public void setState(DoorState doorState){
    this.doorState = doorState;
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return doorState.returnState();
  }

  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }
}
