package basemod.devcommands;

import basemod.DevConsole;

import java.util.Iterator;

public class Help extends ConsoleCommand {

    @Override
    public void execute(String[] tokens, int depth) {

        // print help info
        // Needs to be dynamic, of course

        String thang = "options are:";

        Iterator<String> var3 = getKeys();

        String s;
        while(var3.hasNext()) {
            s = var3.next();

            if(thang.length() > 0) {
                thang += ' ';
            }
            thang += s;
            if(thang.length() > 50) {
                DevConsole.log(thang + "...");
                thang = "";
            }
        }
        if(thang.length() > 0) {
            DevConsole.log(thang);
        }
    }
}
