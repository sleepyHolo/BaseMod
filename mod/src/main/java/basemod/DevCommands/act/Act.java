package basemod.DevCommands.act;

import basemod.BaseMod;
import basemod.DevCommands.ConsoleCommand;
import basemod.DevConsole;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Act extends ConsoleCommand {

    private static Map<String, Integer> acts = new HashMap<>();
    public static void initialize() {
        addAct(Exordium.ID, 1);
        addAct(TheCity.ID, 2);
        addAct(TheBeyond.ID, 3);
        addAct(TheEnding.ID, 4);

        if(Loader.isModLoaded("theJungle")) {
            addAct("theJungle:Jungle", 2);
        }
    }
    public static void addAct(String actID, int actNum) {
        if(!acts.containsKey(actID)) {
            acts.put(actID, actNum);
        } else {
            BaseMod.logger.error("Act " + actID + " is already registered!");
        }
    }

    public Act() {
        requiresPlayer = true;
        minExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        if(acts.containsKey(tokens[depth])) {
            try {
                DevConsole.log("Skipping to act " + tokens[depth]);

                //At 0, it adds the starterdeck each time a dungeon is instantiated.
                //At 1, it adds an ascender's bane when loading exordium.
                if(AbstractDungeon.floorNum <= 1) {
                    AbstractDungeon.floorNum = 2;
                }

                CardCrawlGame.nextDungeon = tokens[depth];
                CardCrawlGame.dungeonTransitionScreen = new DungeonTransitionScreen(tokens[depth]);
                AbstractDungeon.actNum = acts.get(tokens[depth]);
                //Phase to complete to enable map control, otherwise game effectively softlocks.
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            } catch (Exception ex) {}
        } else {
            DevConsole.log("Invalid Act ID");
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> tmp = new ArrayList<>();
        for(final String s : acts.keySet()) {
            tmp.add(s);
        }
        return tmp;
    }
}
