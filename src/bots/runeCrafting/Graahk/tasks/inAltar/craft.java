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
import src.bots.runeCrafting.Graahk.resources.pouches.Pouches;

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
                    if (Objects.getAltar().click(true)) {
                        Variables.state = "Successfully clicked Altar!!";
                        t.reset();
                        while (t.isRunning() && Inventory.getCount(true, 561) < 1) {
                            if (Players.getLocal().isMoving()) {
                                t.reset();
                            }
                            Time.sleep(50);
                        }
                    }
                } else if (Inventory.getItem(561) != null && Variables.TIME_TO_TELE == false) {
                    for (final Item z : Inventory.getItems()) {
                        if (Pouches.giantCount() > 5) {
                            if (z.getId() == 5515 || z.getId() == 5514) {
                                z.getWidgetChild().click(true);
                                Variables.EMPTY_GIANT = true;
                            }
                        }
                        if (Pouches.largeCount() > 4 && Variables.EMPTY_GIANT == true) {
                            if (z.getId() == 5512) {
                                z.getWidgetChild().click(true);
                                Variables.EMPTY_LARGE = true;
                            }
                        }
                        if (Pouches.medCount() > 3 && Variables.EMPTY_LARGE == true) {
                            if (z.getId() == 5510) {
                                z.getWidgetChild().click(true);
                                Variables.EMPTY_NEXT = true;
                                Time.sleep(150);
                            }
                        }
                        if (Variables.EMPTY_NEXT == true) {
                            Variables.state = "Final Craft...";
                            Objects.getAltar().click(true);
                            Variables.TIME_TO_TELE = true;
                            Camera.setPitch(1);
                            Camera.setAngle(206 + Random.nextInt(5, 9));
                        }
                    }
                } else if (Variables.TIME_TO_TELE == true) {
                    WidgetChild ring = Widgets.get(387, 32);
                    Variables.state = "Teleporting!";
                    if (Tabs.getCurrent() != Tabs.EQUIPMENT) {
                        Tabs.EQUIPMENT.open();
                        Time.sleep(150);
                    }
                    if (ring.click(false)) {
                        Time.sleep(100, 350);
                        if (Menu.isOpen()) {
                            Menu.select("Castle Wars");
                            Time.sleep(200, 400);
                        }
                    }
                    Tabs.INVENTORY.open();
                    Variables.RESET_VARIABLES();
                    Time.sleep(2500);
                }
            } else {
                Variables.state = "Can't find altar, attempting to walk to it.";
                Camera.setNorth();
                Walking.walk(Objects.getAltar());
            }
        }
    }
}
