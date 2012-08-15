package src.bots.runeCrafting.Graahk.resources.variables;

/**
 * Random Variables / Methods go in here if they do not fall in any other
 * places..
 */
public class Variables {

    public static String state = "Doing Nothing";
    /*
     * Teleport To Castle Wars
     */
    public static boolean TIME_TO_TELE = false;
    /*
     * Empty Pouches
     */
    public static boolean EMPTY_MASSIVE, EMPTY_MEDIUM, EMPTY_GIANT, EMPTY_LARGE, EMPTY_NEXT = false;

    /*
     * Used to reset variables once you start walking to altar...
     */
    public static void RESET_VARIABLES() {
        EMPTY_MASSIVE = false;
        EMPTY_MEDIUM = false;
        EMPTY_LARGE = false;
        EMPTY_GIANT = false;
        EMPTY_NEXT = false;
        TIME_TO_TELE = false;
    }
}
