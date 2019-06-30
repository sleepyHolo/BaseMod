package basemod.devcommands.potions;

import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.ArrayList;

public class Potions extends ConsoleCommand {

    public Potions() {
        followup.put("list", Potionlist.class);

        minExtraTokens = 2;
        maxExtraTokens = 2;
        requiresPlayer = true;
    }

    public void execute(String[] tokens, int depth) {
        if (PotionHelper.potions == null || PotionHelper.potions.isEmpty()) {
            DevConsole.log("cannot use potion command when potions are not initialized");
            DevConsole.log("start a run and try again");
            return;
        }

        int i;
        try {
            i = Integer.parseInt(tokens[1]);
            if(i >= AbstractDungeon.player.potionSlots || i < 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            DevConsole.log("Invalid Potionslot");
            return;
        }

        if (AbstractDungeon.player == null) {
            DevConsole.log("cannot obtain potion when player doesn't exist");
            return;
        }

        String potionID = "";
        for (int k = 2; k < tokens.length; k++) {
            potionID = potionID.concat(tokens[k]);
            if (k != tokens.length - 1) {
                potionID = potionID.concat(" ");
            }
        }
        // If the ID was written using underscores, find the original ID
        if (BaseMod.underScorePotionIDs.containsKey(potionID)) {
            potionID = BaseMod.underScorePotionIDs.get(potionID);
        }

        AbstractPotion p = null;
        if (PotionHelper.potions.contains(potionID)) {
            p = PotionHelper.getPotion(potionID);
        }
        if (PotionHelper.potions.contains(potionID + " Potion")) {
            p = PotionHelper.getPotion(potionID + " Potion");
        }

        if (p == null) {
            DevConsole.log("invalid potion id");
            DevConsole.log("use potion list to see valid ids");
            return;
        }

        AbstractDungeon.player.obtainPotion(i, p);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        int slots = AbstractDungeon.player.potionSlots;
        for (int i = 0; i < slots; i++) {
            result.add(String.valueOf(i));
        }

        if(result.contains(tokens[depth]) && tokens.length > depth + 1) {
            result.clear();
            if (PotionHelper.potions != null) {
                for (String key : PotionHelper.potions) {
                    result.add(key.replace(' ', '_'));
                }
            }
            if(result.contains(tokens[depth + 1])) {
                complete = true;
            }
        }


        return result;
    }

    public void errorMsg() {
        cmdPotionHelp();
    }

    public static void cmdPotionHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* list");
        DevConsole.log("* [slot] [id]");
    }
}
