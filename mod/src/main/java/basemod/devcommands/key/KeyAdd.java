package basemod.devcommands.key;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import java.util.ArrayList;

public class KeyAdd extends ConsoleCommand {

    public KeyAdd() {
        requiresPlayer = true;
        minExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        Settings.isFinalActAvailable = true;
        switch(tokens[depth]) {
            case "emerald":
                if(!Settings.hasEmeraldKey) {
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.GREEN));
                }
                break;
            case "sapphire":
                if(!Settings.hasSapphireKey) {
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
                }
                break;
            case "ruby":
                if(!Settings.hasRubyKey) {
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
                }
                break;
            case "all":
                if(!Settings.hasRubyKey) {
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
                }
                if(!Settings.hasSapphireKey) {
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
                }
                if(!Settings.hasEmeraldKey) {
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.GREEN));
                }
                break;

            default:
                DevConsole.log("Key does not exist.");
                break;
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("emerald");
        tmp.add("sapphire");
        tmp.add("ruby");
        tmp.add("all");
        return tmp;
    }
}
