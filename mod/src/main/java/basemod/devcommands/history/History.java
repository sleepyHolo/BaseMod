package basemod.devcommands.history;

import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.stats.RunData;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class History extends ConsoleCommand {

    public History() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        if(tokens[1].equalsIgnoreCase("random")) {
            getrandomVictorySetup();
        } else if(tokens[1].equalsIgnoreCase("last")) {
            getlastVictorySetup();
        } else {
            DevConsole.couldNotParse();
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        result.add("last");
        result.add("random");
        return result;
    }

    @Override
    public void errorMsg() {
        cmdDrawHelp();
    }

    private static void cmdDrawHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* last");
        DevConsole.log("* random");
    }






    public static void getlastVictorySetup() {
        ArrayList<RunData> rdlist = getVictories(
                characterIndex(AbstractDungeon.player));
        setLoadout(rdlist.get(rdlist.size() - 1));
    }
    public static void getrandomVictorySetup() {
        ArrayList<RunData> rdlist = getVictories(
                characterIndex(AbstractDungeon.player));
        setLoadout(rdlist.get(MathUtils.random(rdlist.size() - 1)));
    }

    public static void setLoadout(RunData rd) {
        AbstractDungeon.player.relics.clear();
        int index = 0;
        for(final String relic : rd.relics) {
            AbstractRelic ar = RelicLibrary.getRelic(relic);
            ar.instantObtain();
            //Only issue is that it immediatly wants you to use all the onEquip stuff, like astrolabe. But it's not gamebreaking.
        }

        AbstractDungeon.player.masterDeck.group.clear();
        for(final String card : rd.master_deck) {

            AbstractCard ac;
            if(card.matches(".*\\+\\d+")) {
                index = card.lastIndexOf("+");

                ac = CardLibrary.getCopy(card.substring(0, index));
                index = Integer.parseInt(card.substring(index + 1));
                for(int i = 0; i < index; i++) {
                    ac.upgrade();
                }
            } else {
                ac = CardLibrary.getCopy(card);
            }
            AbstractDungeon.player.masterDeck.group.add(ac);
        }
        AbstractDungeon.player.maxHealth = rd.max_hp_per_floor.get(rd.max_hp_per_floor.size() - 1);
        AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
    }

    public static ArrayList<RunData> getVictories(int character) {
        ArrayList<RunData> al = new ArrayList<>();

        RunHistoryScreen rhs = new RunHistoryScreen();
        rhs.refreshData();

        if(character > 0) {
            ((DropdownMenu) ReflectionHacks.getPrivate(rhs, RunHistoryScreen.class, "characterFilter")).setSelectedIndex(character);
        }

        //Set to only accept victories
        ((DropdownMenu) ReflectionHacks.getPrivate(rhs, RunHistoryScreen.class, "winLossFilter")).setSelectedIndex(1);

        try {
            Method resetRunsDropdown = RunHistoryScreen.class.getDeclaredMethod("resetRunsDropdown");
            resetRunsDropdown.setAccessible(true);
            resetRunsDropdown.invoke(rhs);
        }catch(Exception ex) {}

        return (ArrayList<RunData>) ReflectionHacks.getPrivate(rhs, RunHistoryScreen.class, "filteredRuns");
    }

    public static int characterIndex(AbstractPlayer p) {

        if(p instanceof TheSilent) {
            return 2;
        } else if(p instanceof Ironclad) {
            return 1;
        } else if(p instanceof Defect) {
            return 3;
        }

        int index = 4;

        for(Iterator var7 = BaseMod.getModdedCharacters().iterator(); var7.hasNext(); ++index) {
            AbstractPlayer character = (AbstractPlayer)var7.next();
            if (character.chosenClass == p.chosenClass) {
                break;
            }
        }
        return index;
    }
}