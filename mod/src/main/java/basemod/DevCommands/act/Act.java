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

public class Act extends ConsoleCommand {

    private static ArrayList<String> acts = new ArrayList<>();
    public static void initialize() {
        addAct(Exordium.ID);
        addAct(TheCity.ID);
        addAct(TheBeyond.ID);
        addAct(TheEnding.ID);

        if(Loader.isModLoaded("theJungle")) {
            addAct("theJungle:Jungle");
        }
    }
    public static void addAct(String actID) {
        if(!acts.contains(actID)) {
            acts.add(actID);
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
        if(acts.contains(tokens[depth])) {
            try {
                DevConsole.log("Skipping to act " + tokens[depth]);

                //At 0, it adds the starterdeck each time a dungeon is instantiated.
                //At 1, it adds an ascender's bane when loading exordium.
                if(AbstractDungeon.floorNum <= 1) {
                    AbstractDungeon.floorNum = 2;
                }

                CardCrawlGame.nextDungeon = tokens[depth];
                CardCrawlGame.dungeonTransitionScreen = new DungeonTransitionScreen(tokens[depth]);
                //Phase to complete to enable map control, otherwise game effectively softlocks.
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            } catch (Exception ex) {}
        } else {
            DevConsole.log("Invalid Act ID");
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return acts;
    }
}
