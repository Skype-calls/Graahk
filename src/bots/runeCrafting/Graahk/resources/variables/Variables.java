package src.bots.runeCrafting.Graahk.resources.variables;

import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;

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
     * Filling Pouches
     */
    public static boolean HAVE_FILLED = false;

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

    /*
     * Check if Bank is Open
     */
    public static boolean bankIsOpen() {
        return (Settings.get(1248) & 0x70000000) >>> 30 != 0;
    }
    
    /*
     * Deposit All Except
     */
    public static boolean depositAllExcept(final int... ids) { // Credits to
        // Azerbaijan
        boolean deposited = false;
        int var = 0;
        final int count = Inventory.getCount(true), itemCount = Inventory.getCount(true, ids);
        w:
        while (var < (count - itemCount)) {
            l:
            for (final Item item : Inventory.getItems()) {
                if (!Widgets.get(762).validate()) {
                    break w;
                }
                for (final int id : ids) {
                    if (item.getId() == id) {
                        continue l;
                    }
                }
                final int invCount = Inventory.getCount(true, item.getId());
                if (item.getWidgetChild().validate()) {
                    if (item.getWidgetChild().interact(
                            invCount > 1 ? "Deposit-All" : "Deposit")) {
                        if (waitFor(new Condition() {

                            @Override
                            public boolean validate() {
                                return Inventory.getCount(true, item.getId()) < invCount;
                            }
                        }, 3000)) {
                            var += invCount;
                        }
                        if (var == (count - itemCount)) {
                            deposited = true;
                            break w;
                        }
                    }
                }
            }
        }
        return deposited;
    }

    public static boolean waitFor(final Condition c, final long timeout) {
        boolean isValid = false;
        final long past = System.currentTimeMillis();
        final long total = (past + timeout);
        while (System.currentTimeMillis() < total) {
            if (c.validate()) {
                isValid = true;
                break;
            }
            Time.sleep(50);
        }
        return isValid;
    }
}
