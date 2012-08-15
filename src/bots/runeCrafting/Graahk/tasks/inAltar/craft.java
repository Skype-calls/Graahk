package src.bots.runeCrafting.Graahk.tasks.inAltar;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import src.bots.runeCrafting.Graahk.resources.areas.Areas;
import src.bots.runeCrafting.Graahk.resources.objects.Objects;
import src.bots.runeCrafting.Graahk.resources.variables.Variables;

/**
 * Crafts, Empties, Teleports...
 */
public class craft extends Strategy implements Task {

    @Override
    public boolean validate() {
        return Areas.at(Areas.ALTAR);
    }

    @Override
    public void run() {
        Variables.state = "Crafting Runes!";
        if (Objects.getAltar() != null) {
            if (Objects.getAltar().isOnScreen()) {
                if (Inventory.getItem(7936) != null && Inventory.getItem(561) == null) {
                    Variables.state = "We has ess but no nats, crafting!";
                    final Timer t = new Timer(2000);
                    if (Objects.getAltar().click(false)) {
                        if (Menu.isOpen()
                                && Menu.select("Craft-rune")) {
                            Variables.state = "Successfully clicked Altar!!";
                            t.reset();
                            while (t.isRunning() && Inventory.getCount(true, 561) < 1 && Players.getLocal().isMoving()) {
                                if (Players.getLocal().getSpeed() > 0) {
                                    t.reset();
                                }
                                Time.sleep(50);
                            }
                        }
                    }
                } else if (Inventory.getItem(7936) == null && Inventory.getItem(561) != null && Variables.TIME_TO_TELE == false) {
                    for (final Item z : Inventory.getItems()) {
                        if (Variables.EMPTY_GIANT == false) {
                            if (z.getId() == 5515
                                    || z.getId() == 5514) {
                                z.getWidgetChild().click(true);
                                Time.sleep(50);
                                Variables.EMPTY_GIANT = true;
                            }
                        }
                        if (Variables.EMPTY_GIANT == true && Variables.EMPTY_LARGE == false) {
                            if (z.getId() == 5512) {
                                z.getWidgetChild().click(true);
                                Time.sleep(50);
                                Variables.EMPTY_LARGE = true;
                            }
                        }
                        if (Variables.EMPTY_LARGE == true && Variables.EMPTY_MEDIUM == false) {
                            if (z.getId() == 5510) {
                                z.getWidgetChild().click(true);
                                Time.sleep(150);
                                Variables.EMPTY_MEDIUM = true;
                            }
                        }
                        Variables.state = "Final Craft...";
                        Objects.getAltar().click(true);
                        Camera.setPitch(1);
                        Variables.TIME_TO_TELE = true;
                        Camera.setAngle(206 + Random.nextInt(5, 9));
                    }
                } else if (Variables.TIME_TO_TELE == true) {
                    WidgetChild ring = Widgets.get(387, 32);
                    Variables.state = "Teleporting!";
                    if (Tabs.getCurrent() != Tabs.EQUIPMENT) {
                        Tabs.EQUIPMENT.open();
                        Time.sleep(150);
                    }
                    ring.click(false);
                    Time.sleep(200, 300);
                    Menu.select("Castle Wars");
                    Time.sleep(600, 700);
                    Tabs.INVENTORY.open();
                    Variables.RESET_VARIABLES();
                    Time.sleep(2500);
                }
            } else {
                Variables.state = "Can't find altar, attempting to walk to it.";
                Walking.walk(Objects.getAltar());
            }
        }
    }
}
