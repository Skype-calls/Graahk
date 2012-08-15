package src.bots.runeCrafting.Graahk.resources.areas;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Contains all the areas needed.
 */
public class Areas {

    public static boolean at(final Area a) {
        return a.contains(Players.getLocal().getLocation());
    }
    public static Area CASTLE_WARS = new Area(new Tile(2430, 3100, 0),
            new Tile(2472, 3074, 0));
    public static Area ALTAR = new Area(new Tile(2390, 4820, 0),
            new Tile(2420, 4900, 0));
    public static Area IN_BANK = new Area(new Tile(2446, 3098, 0),
            new Tile(2438, 3080, 0));
}
