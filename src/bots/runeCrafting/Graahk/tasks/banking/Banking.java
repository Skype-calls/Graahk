package src.bots.runeCrafting.Graahk.tasks.banking;

import java.awt.event.KeyEvent;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
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
 * Banking...
 */
public class Banking extends Strategy implements Task {

    @Override
    public boolean validate() {
        return Areas.at(Areas.IN_BANK);
    }

    @Override
    public void run() {
        WidgetChild tele = Widgets.get(1188, 24);
        Variables.state = "Opening Bank";
        if (!Bank.isOpen() && Variables.HAVE_FILLED == false) {
            if (Objects.getChest() != null) {
                if (Objects.getChest().isOnScreen()) {
                    Variables.state = "Object is on screen!";
                    final Timer t = new Timer(3000);
                    for (int i = 0; i < 3; i++) {
                        if (Objects.getChest().click(false)) {
                            if (Menu.isOpen() && Menu.select("Use")) {
                                Variables.state = "Clicked Bank, waiting for interface.";
                                t.reset();
                                while (t.isRunning() && !Bank.isOpen()) {
                                    if (Players.getLocal().isMoving()) {
                                        t.reset();
                                    }
                                    Time.sleep(50);
                                }
                                i = 3;
                            } else {
                                Mouse.move(Random.nextInt(0, 500), Random.nextInt(0, 500));
                                --i;
                            }
                        }
                    }
                } else {
                    Walking.walk(Objects.getChest());
                }
            }
        } else if (Bank.isOpen()) {
            if (Variables.NEED_RING == true) {
                Bank.withdraw(2552, 1);
                if (Inventory.getItem(2552) != null) {
                    Inventory.getItem(2552).getWidgetChild().click(false);
                    Time.sleep(100);
                    Menu.select("Wear");
                    Variables.NEED_RING = false;
                }
            }
            if (Inventory.getItem(561) != null) {
                Variables.depositAllExcept(24205, 5510, 5515, 5514, 5512, 5509);
                Time.sleep(500);
            } else if (Inventory.getItem(7936) == null) {
                if (Inventory.getItem(5510) == null) {
                    Bank.withdraw(5510, 1);
                }
                if (Inventory.getItem(5512) == null) {
                    Bank.withdraw(5512, 1);
                }
                if (Inventory.getItem(5514) == null && Inventory.getItem(5515) == null) {
                    Bank.withdraw(5514, 1);
                    Bank.withdraw(5515, 1);
                }
                Bank.withdraw(7936, Random.nextInt(-1, 0));
            } else if (Inventory.getItem(7936) != null && Variables.HAVE_FILLED == false) {
                Variables.state = "Filling Pouches";
                for (final Item z : Inventory.getItems()) {
                    if (Pouches.giantCount() < 5 && Variables.FILLING_GIANT == false) {
                        if (z.getId() == 5514 || z.getId() == 5515) {
                            if (Inventory.getCount(7936) > 5) {
                                z.getWidgetChild().click(false);
                                Menu.select("Fill");
                                Variables.FILLING_GIANT = true;
                            }
                        }
                    } else if (Pouches.largeCount() < 5 && Variables.FILLING_LARGE == false) {
                        if (z.getId() == 5512) {
                            if (Inventory.getCount(7936) > 5) {
                                z.getWidgetChild().click(false);
                                Menu.select("Fill");
                                Variables.FILLING_LARGE = true;
                            }
                        }
                    } else if (Pouches.medCount() < 4 && Variables.FILLING_MEDIUM == false) {
                        if (z.getId() == 5510) {
                            if (Inventory.getCount(7936) > 5) {
                                z.getWidgetChild().click(false);
                                Menu.select("Fill");
                                Variables.FILLING_MEDIUM = true;
                            }
                        }
                    }
                }
                Bank.withdraw(7936, Random.nextInt(-1, 0));
                Variables.HAVE_FILLED = true;
            } else if (Variables.HAVE_FILLED == true) {
                Widgets.get(747, 5).click(true);
            }
        } else if (tele.validate()) {
            Keyboard.sendKey((char) KeyEvent.VK_2);
            Camera.setPitch(93);
            Camera.setAngle(Random.nextInt(250, 280));
            Time.sleep(250, 500);
        }
    }
}
