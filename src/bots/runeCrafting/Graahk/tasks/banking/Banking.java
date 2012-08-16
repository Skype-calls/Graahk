package src.bots.runeCrafting.Graahk.tasks.banking;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

import src.bots.runeCrafting.Graahk.resources.areas.Areas;
import src.bots.runeCrafting.Graahk.resources.objects.Objects;
import src.bots.runeCrafting.Graahk.resources.variables.Variables;

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
        Variables.state = "Opening Bank";
        Walking.walk(Objects.getChest());
        if (!Variables.bankIsOpen()) {
            if (Objects.getChest() != null) {
                if (Objects.getChest().isOnScreen()) {
                    Variables.state = "Object is on screen!";
                    final Timer t = new Timer(3000);
                    for (int i = 0; i < 3; i++) {
                        if (Objects.getChest().click(false)) {
                            if (Menu.isOpen() && Menu.select("Bank")) {
                                Variables.state = "Clicked Bank, waiting for interface.";
                                t.reset();
                                while (t.isRunning() && !Variables.bankIsOpen()) {
                                    if (Players.getLocal().getSpeed() > 0) {
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
        } else if (Variables.bankIsOpen()) {
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
            } else if (Inventory.getItem(7936) != null && !Variables.HAVE_FILLED) {
                Variables.state = "Filling Pouches";
                
            }
        }
    }
}
