package basemod.devcommands.potions;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Potionlist extends ConsoleCommand {

    public void execute(String[] tokens, int depth) {
        // PlayerClass doesn't matter
        List<String> allPotions = PotionHelper.getPotions(AbstractPlayer.PlayerClass.IRONCLAD, true);
        Collections.sort(allPotions);
        DevConsole.log(allPotions);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        complete = true;
        if(tokens.length > depth && tokens[depth].length() > 0) {
            tooManyTokensError();
        }
        return null;
    }
}
