package src.bots.runeCrafting.Graahk.resources.objects;

import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;

/*
 * This class will hold all the objects needed to perform all the tasks. 
 */
public class Objects {
    
    public static SceneObject getObelisk() {
        return SceneEntities.getNearest(new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject loc) {
                return loc != null && loc.getId() == 29938;
            }
        });
    }

    public static SceneObject getRuins() {
        return SceneEntities.getNearest(new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject loc) {
                return loc != null && loc.getId() == 2460;
            }
        });
    }

    public static SceneObject getAltar() {
        return SceneEntities.getNearest(new Filter<SceneObject>() {

            @Override
            public boolean accept(SceneObject loc) {
                return loc != null && loc.getId() == 2486;
            }
        });
    }
}
