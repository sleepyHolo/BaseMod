package basemod.DevCommands.potions;

import basemod.DevCommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.helpers.PotionHelper;

import java.util.ArrayList;
import java.util.Collections;

public class Potionlist extends ConsoleCommand {

    public void execute(String[] tokens, int depth) {
        Collections.sort(PotionHelper.potions);
        DevConsole.log(PotionHelper.potions);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        complete = true;
        if(tokens.length > depth && tokens[depth].length() > 0) {
            randomizeWtf();
        }
        return null;
    }
}
