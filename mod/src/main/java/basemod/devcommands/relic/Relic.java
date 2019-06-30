package basemod.devcommands.relic;

import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;

public class Relic extends ConsoleCommand {
    public Relic() {
        followup.put("add", RelicAdd.class);
        followup.put("remove", RelicRemove.class);
        followup.put("desc", RelicDescription.class);
        followup.put("flavor", RelicFlavor.class);
        followup.put("list", RelicList.class);
        followup.put("pool", RelicPool.class);
    }

    @Override
    public void execute(String[] tokens, int depth) {
        cmdRelicHelp();
    }

    public static String getRelicName(String[] relicNameArray) {
        String relic = String.join(" ", relicNameArray);
        if (BaseMod.underScoreRelicIDs.containsKey(relic)) {
            relic = BaseMod.underScoreRelicIDs.get(relic);
        }
        return relic;
    }

    @Override
    public void errorMsg() {
        Relic.cmdRelicHelp();
    }

    public static void cmdRelicHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* remove [id]");
        DevConsole.log("* add [id]");
        DevConsole.log("* desc [id]");
        DevConsole.log("* flavor [id]");
        DevConsole.log("* pool [id]");
        DevConsole.log("* list [type]");
    }

}




