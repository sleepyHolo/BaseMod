package basemod.devcommands.power;

import basemod.BaseMod;
import basemod.ConsoleTargetedPower;
import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;

import java.util.ArrayList;

public class Power extends ConsoleCommand {
    public Power() {
        requiresPlayer = false;
        minExtraTokens = 2;
        maxExtraTokens = 2;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        String powerID = "";
        int amount = 1;
        for (int i = 1; i < tokens.length - 1; i++) {
            powerID = powerID.concat(tokens[i]).concat(" ");
        }
        try {
            amount = Integer.parseInt(tokens[tokens.length - 1]);
        } catch (Exception e) {
            powerID = powerID.concat(tokens[tokens.length - 1]);
        }
        powerID = powerID.trim();

        // If the ID was written using underscores, find the original ID
        if (BaseMod.underScorePowerIDs.containsKey(powerID)) {
            powerID = BaseMod.underScorePowerIDs.get(powerID);
        }

        Class power;
        try {
            power = BaseMod.getPowerClass(powerID);
        } catch (Exception e) {
            BaseMod.logger.info("failed to load power " + powerID);
            DevConsole.log("could not load power");
            cmdPowerHelp();
            return;
        }

        try {
            new ConsoleTargetedPower(power, amount);
        } catch (Exception e) {
            DevConsole.log("could not make power");
            cmdPowerHelp();
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        for (String key : BaseMod.getPowerKeys()) {
            result.add(key.replace(' ', '_'));
        }

        if(result.contains(tokens[depth]) && tokens.length > depth + 1) {
            result.clear();
            result = ConsoleCommand.smallNumbers();

            if(tokens[depth + 1].matches("\\d+")) {
                complete = true;
            }
        }

        return result;
    }

    @Override
    public void errorMsg() {
        cmdPowerHelp();
    }

    private static void cmdPowerHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* [id] [amt]");
    }
}
