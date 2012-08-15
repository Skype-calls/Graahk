package src.bots.runeCrafting.Graahk;

import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;

import src.bots.runeCrafting.Graahk.tasks.walking.ToAltar;

@Manifest(authors = {"Skype_calls"}, name = "Nature Crafter", description = "Uses Graahk to make nature runes!", version = 1.0)
public class Main extends ActiveScript {

    @Override
    protected void setup() {
        provide(new ToAltar());
    }
}
