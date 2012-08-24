package src.bots.runeCrafting.Graahk;

import java.awt.Color;
import java.awt.Graphics;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.game.bot.event.listener.PaintListener;
import src.bots.runeCrafting.Graahk.tasks.banking.Banking;
import src.bots.runeCrafting.Graahk.tasks.inAltar.craft;
import src.bots.runeCrafting.Graahk.tasks.walking.ToAltar;
import src.bots.runeCrafting.Graahk.resources.pouches.Pouches;
import src.bots.runeCrafting.Graahk.resources.variables.Variables;

@Manifest(authors = {"Skype_calls"}, name = "Nature Crafter", description = "Uses Graahk to make nature runes!", version = 1.0)
public class Main extends ActiveScript implements PaintListener, MessageListener {

    @Override
    protected void setup() {
        provide(new ToAltar());
        provide(new craft());
        provide(new Banking());
    }

    @Override
    public void onRepaint(Graphics g) {
        g.setColor(new Color(0, 0, 0, 195));
            g.fillRoundRect(0, 300, 200, 90, 0, 0);
            g.setColor(Color.BLUE);
            g.draw3DRect(0, 300, 200, 90, true);
            g.setColor(Color.white);
            g.drawString("Giant Pouch:" + Pouches.giantCount(), 10, 320);
            g.drawString("Large Pouch:" + Pouches.largeCount(), 10, 340);
            g.drawString("Medium Pouch:" + Pouches.medCount(), 10, 360);
            g.drawString("HaveFilled:" + Variables.HAVE_FILLED, 10, 375);
    }

    @Override
    public void messageReceived(MessageEvent e) {
        String msg = e.getMessage();
        if (msg.contains("duelling crumbles to dust")) {
            Variables.NEED_RING = true;
        }
    }
}
