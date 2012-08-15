package src.bots.runeCrafting.Graahk.tasks.walking;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.*;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

import src.bots.runeCrafting.Graahk.resources.areas.Areas;
import src.bots.runeCrafting.Graahk.resources.objects.Objects;
import src.bots.runeCrafting.Graahk.resources.paths.Paths;
import src.bots.runeCrafting.Graahk.resources.variables.Variables;

public class ToAltar extends Strategy implements Task, Condition {

    @Override
    public boolean validate() {
        return !Areas.at(Areas.CASTLE_WARS) && !Areas.at(Areas.ALTAR)
                && Game.getClientState() != Game.INDEX_MAP_LOADING;
    }

    @Override
    public void run() {
        Variables.state = "Walking to Altar";
        if (Tabs.getCurrent() != Tabs.INVENTORY) {
            Tabs.INVENTORY.open();
        }
        while (Game.getClientState() == 12) {
            Time.sleep(200);
        }
        Walking.newTilePath(Paths.TO_ALTAR).randomize(2, 2).traverse();
        Variables.RESET_VARIABLES();
        if (Calculations.distance(Players.getLocal().getLocation(), Objects.getRuins().getLocation()) < 25) {
            Variables.state = "Adjusting Camera for clicking!";
            Camera.turnTo(Objects.getRuins());
            Camera.setPitch(1);
        }
        final Timer t = new Timer(3000);
        if (Calculations.distance(Players.getLocal().getLocation(),
                Objects.getRuins().getLocation()) < 20) {
            if (Objects.getRuins().isOnScreen()) {
                Variables.state = "Object is on screen!";
                for (int i = 0; i < 3; i++) {
                    if (Objects.getRuins().click(false)) {
                        if (Menu.isOpen() && Menu.select("Enter")) {
                            Variables.state = "Clicked ruins, adjusting camera!";
                            Camera.setPitch(95);
                            t.reset();
                            while (t.isRunning()
                                    && Players.getLocal().getAnimation() == -1
                                    && !Areas.at(Areas.ALTAR)) {
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
            }
        }
    }
}
