package basemod.devcommands.blight;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.BlightHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class BlightAdd extends ConsoleCommand {
    public BlightAdd() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        String[] blightNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
        String blightName = String.join(" ", blightNameArray);
        AbstractBlight blight = AbstractDungeon.player.getBlight(blightName);
        if (blight != null) {
            blight.incrementUp();
            blight.stack();
        } else if (BlightHelper.getBlight(blightName) != null) {
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                    BlightHelper.getBlight(blightName));
        } else {
            DevConsole.log("invalid blight ID");
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        for (String id : BlightHelper.blights) {
            result.add(id.replace(' ', '_'));
        }
        return result;
    }

    public void errorMsg() {
        Blight.cmdBlightHelp();
    }
}
