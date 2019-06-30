package basemod.devcommands;

import com.megacrit.cardcrawl.core.Settings;

public class Info extends ConsoleCommand {

    @Override
    public void execute(String[] tokens, int depth) {
        Settings.isInfo = !Settings.isInfo;
    }
}
