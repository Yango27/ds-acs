package baseNoStates;

import baseNoStates.requests.RequestArea;
import baseNoStates.requests.RequestReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

// Test cases for milestone 1.
// Each test creates a RequestReader or a RequestArea (just like the WebServer does),
// processes it, and compares the JSON answer (answerToJson) with the expected result.
// To run: Run TestCases.main() with -ea (there is NO need to start Main or the simulator).
public class TestCases {
    // Credentials (DirectoryUsers)
    static final String ADMIN = "11343";     // Ana
    static final String MANAGER = "95783";   // Manel
    static final String EMPLOYEE = "43295";  // Eulalia
    static final String BLANK = "12345";     // Bernat
    static final String UNKNOWN = "99999";

    // Dates (2026-10-14 is a Wednesday)
    static final String WEEKDAY = "2026-10-14T10:00";
    static final String WEEKDAY_EVENING = "2026-10-14T18:30";
    static final String SATURDAY = "2026-10-17T10:00";
    static final String SUNDAY = "2026-10-18T10:00";
    static final String SUNDAY_NIGHT = "2026-10-18T03:00";
    static final String OUT_OF_DATES = "2027-04-07T10:00";

    // Texts of the reasons exactly as they appear in RequestReader
    static final String R_TIME = "Time is not Valid";
    static final String R_AREA = "Area is not Valid";
    static final String R_ACTION = "Action is not Valid";
    static final String R_USER = "user doesn't exists";

    static int passed = 0;
    static int failed = 0;
    static final List<String> failedIds = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // Doors, areas and users must be created before making any request (like Main does)
        DirectoryDoors.makeDoors();
        DirectoryAreas.makeAreas();
        DirectoryUsers.makeUsers();
        Main.clock.start();

        section("1. ESTATS LOCKED / UNLOCKED");
        reader("T01", "Lock d'una porta tancada",
                ADMIN, Actions.LOCK, WEEKDAY, "D5", true, "locked", true);
        reader("T02", "Open d'una porta locked: no s'obre",
                ADMIN, Actions.OPEN, WEEKDAY, "D5", true, "locked", true);
        reader("T03", "Unlock d'una porta locked",
                ADMIN, Actions.UNLOCK, WEEKDAY, "D5", true, "unlocked", true);
        reader("T04", "Open d'una porta unlocked",
                ADMIN, Actions.OPEN, WEEKDAY, "D5", true, "unlocked", false);
        reader("T05", "Lock d'una porta oberta: no es pot",
                ADMIN, Actions.LOCK, WEEKDAY, "D5", true, "unlocked", false);
        reader("T06", "Close d'una porta oberta",
                ADMIN, Actions.CLOSE, WEEKDAY, "D5", true, "unlocked", true);

        section("2. ESPAIS I PARTICIONS");
        area("T07", "Area lock d'una particio (basement -> D1, D2)",
                ADMIN, Actions.LOCK, WEEKDAY, "basement", "locked", "D1", "D2");
        area("T08", "Area unlock de tot l'edifici (building -> 9 portes)",
                ADMIN, Actions.UNLOCK, WEEKDAY, "building", "unlocked",
                "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9");
        area("T09", "Area lock d'un pis (ground_floor -> D3, D4, D5, D6)",
                ADMIN, Actions.LOCK, WEEKDAY, "ground_floor", "locked", "D3", "D4", "D5", "D6");
        area("T10", "Area unlock d'un espai (room1 -> D5)",
                ADMIN, Actions.UNLOCK, WEEKDAY, "room1", "unlocked", "D5");
        reader("T11", "Preparacio: s'obre D5", ADMIN, Actions.OPEN, WEEKDAY, "D5", true, "unlocked", false);
        areaStates("T12", "Area lock de ground_floor amb D5 oberta: D5 no es bloqueja",
                ADMIN, Actions.LOCK, WEEKDAY, "ground_floor",
                "D3=locked", "D4=locked", "D5=unlocked", "D6=locked");
        reader("T13", "Es tanca D5", ADMIN, Actions.CLOSE, WEEKDAY, "D5", true, "unlocked", true);
        areaStates("T14", "Area inexistent (garage): cap porta i sense error",
                ADMIN, Actions.LOCK, WEEKDAY, "garage");

        section("3. GRUPS D'USUARIS I HORARIS");
        reader("T15", "Admin diumenge a les 3:00 (sempre pot)",
                ADMIN, Actions.LOCK, SUNDAY_NIGHT, "D1", true, "locked", true);
        reader("T16", "Manager dissabte 10:00 (dl-ds 8-20h)",
                MANAGER, Actions.UNLOCK, SATURDAY, "D1", true, "unlocked", true);
        reader("T17", "Manager diumenge: dia no permes",
                MANAGER, Actions.LOCK, SUNDAY, "D1", false, "unlocked", true, R_TIME);
        reader("T18", "Manager 7-abr-2027: fora del periode de dates",
                MANAGER, Actions.LOCK, OUT_OF_DATES, "D1", false, "unlocked", true, R_TIME);
        reader("T19", "Employee obre D7 en horari",
                EMPLOYEE, Actions.OPEN, WEEKDAY, "D7", true, "unlocked", false);
        reader("T20", "Employee tanca D7",
                EMPLOYEE, Actions.CLOSE, WEEKDAY, "D7", true, "unlocked", true);
        reader("T21", "Employee fa lock: accio no permesa",
                EMPLOYEE, Actions.LOCK, WEEKDAY, "D7", false, "unlocked", true, R_ACTION);
        reader("T22", "Employee obre D1 (parking): area no permesa",
                EMPLOYEE, Actions.OPEN, WEEKDAY, "D1", false, "unlocked", true, R_AREA);
        reader("T23", "Employee a les 18:30: hora no permesa (9-17h)",
                EMPLOYEE, Actions.OPEN, WEEKDAY_EVENING, "D8", false, "unlocked", true, R_TIME);
        reader("T24", "Usuari blank: cap permis",
                BLANK, Actions.OPEN, WEEKDAY, "D8", false, "unlocked", true, R_TIME, R_AREA, R_ACTION);
        reader("T25", "Credencial desconeguda",
                UNKNOWN, Actions.OPEN, WEEKDAY, "D8", false, "unlocked", true, R_USER);
        areaDenied("T26", "Employee fa area lock de basement: totes denegades",
                EMPLOYEE, Actions.LOCK, WEEKDAY, "basement", "D1", "D2");

        section("4. UNLOCKED SHORTLY I PROPPED");
        // D3, D4 and D6 have been locked since T12; D2 has been unlocked since T08
        reader("T27", "Unlock shortly d'una porta unlocked: no fa res",
                ADMIN, Actions.UNLOCK_SHORTLY, WEEKDAY, "D2", true, "unlocked", true);
        reader("T28", "D3: employee fa unlock shortly",
                EMPLOYEE, Actions.UNLOCK_SHORTLY, WEEKDAY, "D3", true, "unlocked_shortly", true);
        reader("T29", "D3: s'obre durant els 10 s",
                EMPLOYEE, Actions.OPEN, WEEKDAY, "D3", true, "unlocked_shortly", false);
        reader("T30", "D3: es tanca abans dels 10 s",
                EMPLOYEE, Actions.CLOSE, WEEKDAY, "D3", true, "unlocked_shortly", true);
        reader("T31", "D4: unlock shortly",
                ADMIN, Actions.UNLOCK_SHORTLY, WEEKDAY, "D4", true, "unlocked_shortly", true);
        reader("T32", "D4: s'obre i es deixa oberta",
                ADMIN, Actions.OPEN, WEEKDAY, "D4", true, "unlocked_shortly", false);
        reader("T33", "D4: lock durant unlocked shortly: no es pot",
                ADMIN, Actions.LOCK, WEEKDAY, "D4", true, "unlocked_shortly", false);

        System.out.println("\n   ... esperant 5 segons ...");
        Thread.sleep(5000);
        refresh("T34", "D3 als 5 s: encara unlocked_shortly", "D3", "unlocked_shortly", true);

        System.out.println("\n   ... esperant 7 segons mes (12 s en total) ...");
        Thread.sleep(7000);
        refresh("T35", "D3 (oberta i tancada) torna a locked", "D3", "locked", true);
        refresh("T36", "D4 (oberta) passa a propped", "D4", "propped", false);
        reader("T37", "D4 propped: lock no fa res",
                ADMIN, Actions.LOCK, WEEKDAY, "D4", true, "propped", false);
        areaStates("T38", "Area lock de ground_floor amb D4 propped: D4 es queda propped",
                ADMIN, Actions.LOCK, WEEKDAY, "ground_floor",
                "D3=locked", "D4=propped", "D5=locked", "D6=locked");
        reader("T39", "D4 propped: close -> torna a locked",
                ADMIN, Actions.CLOSE, WEEKDAY, "D4", true, "locked", true);

        Main.clock.stop();
        System.out.println("\n==============================================================");
        System.out.println("RESUM: " + passed + " OK, " + failed + " FAIL de " + (passed + failed));
        if (failed > 0) {
            System.out.println("Proves fallades: " + failedIds);
        }
        System.out.println("==============================================================");
    }

    // ---------------------------------------------------------------------------
    // How each test is displayed:
    //  - "Esperat" and "Obtingut" have the SAME fields in the SAME order, so
    //    when the test is correct both lines are identical.
    //  - "Obtingut" comes from the real response of our code: process() is called and then
    //    answerToJson(), and only the fields that are checked are shown.
    //  - The doors of the areas are shown sorted by id.
    // ---------------------------------------------------------------------------

    private static void section(String title) {
        System.out.println("\n==============================================================");
        System.out.println(title);
        System.out.println("==============================================================");
    }

    private static LocalDateTime dt(String s) {
        return LocalDateTime.parse(s);
    }

    // Builds a JSON with the keys in the given order: json("a", 1, "b", "x") -> {"a":1,"b":"x"}
    private static String json(Object... keysAndValues) {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < keysAndValues.length; i += 2) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(JSONObject.quote((String) keysAndValues[i])).append(":");
            Object v = keysAndValues[i + 1];
            sb.append(v instanceof String && ((String) v).startsWith("{")
                    || v instanceof String && ((String) v).startsWith("[")
                    ? v : JSONObject.valueToString(v));
        }
        return sb.append("}").toString();
    }

    private static String list(List<String> items) {
        return "[" + String.join(",", items) + "]";
    }

    // Reader request: checks authorized, state, closed and reasons
    private static void reader(String id, String desc, String credential, String action,
                               String datetime, String doorId, boolean expAuthorized,
                               String expState, boolean expClosed, String... expReasons) {
        RequestReader request = new RequestReader(credential, action, dt(datetime), doorId);
        request.process();
        JSONObject got = request.answerToJson();

        String expected = json("doorId", doorId, "authorized", expAuthorized, "state", expState,
                "closed", expClosed, "reasons", new JSONArray(Arrays.asList(expReasons)));
        String obtained = json("doorId", got.opt("doorId"), "authorized", got.opt("authorized"),
                "state", got.opt("state"), "closed", got.opt("closed"), "reasons", got.opt("reasons"));

        boolean ok = got.optBoolean("authorized") == expAuthorized
                && expState.equals(got.optString("state"))
                && got.optBoolean("closed") == expClosed
                && sameReasons(got.optJSONArray("reasons"), expReasons);
        report(id, desc, "reader " + action + " " + doorId + " (" + credential + ", " + datetime + ")",
                expected, obtained, ok);
    }

    // Area request: each expected door with authorized=true and the expected state
    private static void area(String id, String desc, String credential, String action,
                             String datetime, String areaId, String expState, String... expDoors) {
        String[] doorStates = new String[expDoors.length];
        for (int i = 0; i < expDoors.length; i++) {
            doorStates[i] = expDoors[i] + "=" + expState;
        }
        runArea(id, desc, credential, action, datetime, areaId, true, true, doorStates);
    }

    // Area request: each door must end up in a specific state ("D7=locked", ...)
    private static void areaStates(String id, String desc, String credential, String action,
                                   String datetime, String areaId, String... expDoorStates) {
        runArea(id, desc, credential, action, datetime, areaId, false, true, expDoorStates);
    }

    // Area request: all doors must end up denied (authorized=false)
    private static void areaDenied(String id, String desc, String credential, String action,
                                   String datetime, String areaId, String... expDoors) {
        String[] doorStates = new String[expDoors.length];
        for (int i = 0; i < expDoors.length; i++) {
            doorStates[i] = expDoors[i] + "=";
        }
        runArea(id, desc, credential, action, datetime, areaId, false, false, doorStates);
    }

    // checkAuth: true -> each door must be authorized; if false and there is no state,
    // each door must be denied. checkState: the state of each door is compared.
    private static void runArea(String id, String desc, String credential, String action,
                                String datetime, String areaId, boolean checkAuth,
                                boolean checkState, String... expDoorStates) {
        RequestArea request = new RequestArea(credential, action, dt(datetime), areaId);
        request.process();
        JSONObject got = request.answerToJson();
        JSONArray gotDoors = got.optJSONArray("requestsDoors");
        if (gotDoors == null) {
            gotDoors = new JSONArray();
        }
        boolean denied = !checkAuth && !checkState;

        // Expected
        List<String> expList = new ArrayList<>();
        for (String ds : expDoorStates) {
            expList.add(doorJson(ds.split("=")[0], checkAuth ? Boolean.TRUE : (denied ? Boolean.FALSE : null),
                    checkState ? ds.split("=")[1] : null));
        }
        Collections.sort(expList);

        // Obtained (same fields)
        List<String> gotList = new ArrayList<>();
        boolean ok = gotDoors.length() == expDoorStates.length;
        for (int i = 0; i < gotDoors.length(); i++) {
            JSONObject d = gotDoors.getJSONObject(i);
            gotList.add(doorJson(d.optString("doorId"),
                    checkAuth || denied ? d.opt("authorized") : null,
                    checkState ? d.opt("state") : null));
        }
        Collections.sort(gotList);
        ok &= expList.equals(gotList);

        String expected = json("areaId", areaId, "action", action, "requestsDoors", list(expList));
        String obtained = json("areaId", got.opt("areaId"), "action", got.opt("action"),
                "requestsDoors", list(gotList));
        report(id, desc, "area " + action + " " + areaId + " (" + credential + ", " + datetime + ")",
                expected, obtained, ok);
    }

    // A door of an area request, only with the fields that are checked
    private static String doorJson(String doorId, Object authorized, Object state) {
        if (authorized != null && state != null) {
            return json("doorId", doorId, "authorized", authorized, "state", state);
        }
        if (authorized != null) {
            return json("doorId", doorId, "authorized", authorized);
        }
        return json("doorId", doorId, "state", state);
    }

    // After waiting the 10 s, we only need to look at the door state (no action is performed).
    // We don't use RequestRefresh: we read the door's JSON directly (Door.toJson).
    private static void refresh(String id, String desc, String doorId,
                                String expState, boolean expClosed) {
        JSONObject got = DirectoryDoors.findDoorById(doorId).toJson();
        String expected = json("id", doorId, "state", expState, "closed", expClosed);
        String obtained = json("id", got.opt("id"), "state", got.opt("state"), "closed", got.opt("closed"));
        boolean ok = expState.equals(got.optString("state")) && got.optBoolean("closed") == expClosed;
        report(id, desc, "estat de la porta " + doorId, expected, obtained, ok);
    }

    private static boolean sameReasons(JSONArray got, String[] expected) {
        if (got == null || got.length() != expected.length) {
            return false;
        }
        List<Object> list = got.toList();
        for (String r : expected) {
            if (!list.contains(r)) {
                return false;
            }
        }
        return true;
    }

    private static void report(String id, String desc, String request,
                               String expected, String obtained, boolean ok) {
        if (ok) {
            passed++;
        } else {
            failed++;
            failedIds.add(id);
        }
        System.out.println("\n" + id + " - " + desc);
        System.out.println("   Peticio   : " + request);
        System.out.println("   Esperat   : " + expected);
        System.out.println("   Obtingut  : " + obtained);
        System.out.println("   Resultat  : " + (ok ? "CORRECTE" : "FAIL"));
    }
}