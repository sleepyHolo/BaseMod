package basemod.DevCommands;

import basemod.BaseMod;
import basemod.DevCommands.act.ActCommand;
import basemod.DevCommands.blight.Blight;
import basemod.DevCommands.clear.Clear;
import basemod.DevCommands.debug.Debug;
import basemod.DevCommands.deck.Deck;
import basemod.DevCommands.draw.Draw;
import basemod.DevCommands.energy.Energy;
import basemod.DevCommands.event.Event;
import basemod.DevCommands.fight.Fight;
import basemod.DevCommands.gold.Gold;
import basemod.DevCommands.hand.Hand;
import basemod.DevCommands.history.History;
import basemod.DevCommands.hp.Hp;
import basemod.DevCommands.key.KeyCommand;
import basemod.DevCommands.kill.Kill;
import basemod.DevCommands.maxhp.MaxHp;
import basemod.DevCommands.potions.Potions;
import basemod.DevCommands.power.Power;
import basemod.DevCommands.relic.Relic;
import basemod.DevCommands.unlock.Unlock;
import basemod.DevConsole;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.*;

public abstract class ConsoleCommand {

    protected Map<String, Class> followup = new HashMap<>();

    protected boolean simpleCheck = false;
    protected boolean requiresPlayer = false;
    protected int minExtraTokens = 0;
    protected int maxExtraTokens = 1;

    protected abstract void execute(String[] tokens, int depth);
    protected ArrayList<String> extraOptions(String[] tokens, int depth) {
        if(followup.isEmpty()) {
            if (tokens.length > depth && tokens[depth].length() > 0) {
                randomizeWtf();
            } else {
                complete = true;
            }
        }
        return new ArrayList<>();
    }
    protected void errorMsg(String[] tokens) {errorMsg();}
    protected void errorMsg() {}

    private ConsoleCommand last(String[] tokens, int[] depth, boolean forExecution) throws IllegalAccessException, InstantiationException, InvalidCommandException {
        if (depth[0] < tokens.length - (forExecution ? 0 : 1) && followup.containsKey(tokens[depth[0]].toLowerCase())) {
            ConsoleCommand cc = ((ConsoleCommand) followup.get(tokens[depth[0]].toLowerCase()).newInstance());
            depth[0] = depth[0] + 1;
            return cc.last(tokens, depth, forExecution);
        } else {
            if(requiresPlayer && AbstractDungeon.player == null) {
                if(forExecution) {
                    DevConsole.log("This action can only be executed during a run.");
                } else {
                    duringRun = true;
                }
            } else if(forExecution && tokens.length < depth[0] + minExtraTokens) {
                this.errorMsg();
            } else if(!forExecution && maxExtraTokens > 0 && tokens.length > maxExtraTokens + depth[0] && tokens[depth[0] + maxExtraTokens].length() > 0) {
                randomizeWtf();
                wtf = true;
            } else {
                return this;
            }
        }
        throw new InvalidCommandException();
    }

    private ArrayList<String> autocomplete(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        for(final String key : followup.keySet()) {
            if(key.toLowerCase().startsWith(tokens[depth].toLowerCase())) {
                result.add(key);
            }
        }
        ArrayList<String> extras = extraOptions(tokens, depth);
        if(extras != null) {
            for (final String key : extras) {
                if (key.toLowerCase().startsWith(tokens[tokens.length - 1].toLowerCase())) {
                    result.add(key);
                }
            }
        }
        Collections.sort(result);
        return result;
    }


    private static ConsoleCommand getLastCommand(String[] tokens, int[] depth, boolean forExecution) {
        try {
            ConsoleCommand cc = new ConsoleCommand() {
                @Override
                protected void execute(String[] tokens, int depth) {}
            };
            cc.followup = root;
            cc = cc.last(tokens, depth, forExecution);

            return cc;
        } catch(InvalidCommandException ex) {}
        catch(Exception ex) {
            if(forExecution) {
                DevConsole.log("An error occurred.");
            }
            BaseMod.logger.error(ex.getStackTrace());
        }
        return null;
    }

    private static Map<String, Class> root = new HashMap<>();
    public static void initialize() {
        root.put("deck", Deck.class);
        root.put("potion", Potions.class);
        root.put("blight", Blight.class);
        root.put("clear", Clear.class);
        root.put("debug", Debug.class);
        root.put("draw", Draw.class);
        root.put("energy", Energy.class);
        root.put("event", Event.class);
        root.put("fight", Fight.class);
        root.put("gold", Gold.class);
        root.put("hand", Hand.class);
        root.put("help", Help.class);
        root.put("hp", Hp.class);
        root.put("info", Info.class);
        root.put("kill", Kill.class);
        root.put("maxhp", MaxHp.class);
        root.put("power", Power.class);
        root.put("relic", Relic.class);
        root.put("unlock", Unlock.class);
        root.put("history", History.class);
        root.put("act", ActCommand.class);
        root.put("endingkey", KeyCommand.class);

        ActCommand.initialize();
    }
    public static Iterator<String> getKeys() {
        return root.keySet().iterator();
    }

    public static void AddCommand(String key, Class val) {
        if(root.containsKey(key)) {
            BaseMod.logger.error("Command \"" + key + "\" already exists.");
        } else if(key.matches("[a-zA-Z:]+")) {
            BaseMod.logger.error("Commands cannot contain whitespaces.");
        } else {
            root.put(key, val);
        }
    }

    public static void execute(String[] tokens) {
        ConsoleCommand cc;
        int[] depth = new int[]{0};
        if((cc = getLastCommand(tokens, depth, true)) != null) {
            cc.execute(tokens, depth[0]);
        }
    }

    public static boolean complete;
    public static boolean isNumber;
    public static boolean duringRun;
    public static boolean wtf;
    public static ArrayList<String> suggestions(String[] tokens) {
        complete = false;
        isNumber = false;
        duringRun = false;
        wtf = false;
        ConsoleCommand cc;
        int[] depth = new int[]{0};
        if((cc = getLastCommand(tokens, depth, false)) != null) {
            ArrayList<String> result = cc.autocomplete(tokens, depth[0]);

            if(cc.simpleCheck && result.contains(tokens[depth[0]])) {
                result.clear();
                ConsoleCommand.complete = true;
            }

            if(!complete && !duringRun && !wtf) {
                return result;
            }
        }
        return new ArrayList<>();
    }


    public static ArrayList<String> smallNumbers() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            result.add(String.valueOf(i));
        }
        isNumber = true;
        return result;
    }
    public static ArrayList<String> mediumNumbers() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 10; i <= 90; i += 10) {
            result.add(String.valueOf(i));
            result.add(String.valueOf(i * 10));
        }
        isNumber = true;
        return result;
    }
    public static ArrayList<String> bigNumbers() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 100; i <= 900; i += 100) {
            result.add(String.valueOf(i));
            result.add(String.valueOf(i * 10));
        }
        isNumber = true;
        return result;
    }
    public static ArrayList<String> getCardOptions() {
        ArrayList<String> result = new ArrayList<>();
        for (String key : CardLibrary.cards.keySet()) {
            result.add(key.replace(' ', '_'));
        }
        return result;
    }

    public static ArrayList<String> getCardOptionsFromCardGroup(CardGroup cg) {
        ArrayList<String> result = new ArrayList<>();
        for (AbstractCard card : cg.group) {
            String cardid = card.cardID.replace(' ', '_');
            if (!result.contains(cardid)) {
                result.add(cardid);
            }
        }
        return result;
    }

    public static ArrayList<String> getRelicOptions() {
        ArrayList<String> result = new ArrayList<>();
        for (String id : BaseMod.listAllRelicIDs()) {
            result.add(id.replace(' ', '_'));
        }
        return result;
    }



    public static String errormsg;
    public static void randomizeWtf() {
        String[] possibilities = new String[]{
                "What are you even doing?",
                "Quit it!",
                "Really, stop!",
                "Typing more doesn't do anything!",
                "What have I done to you?",
                "Less parameters, dude!",
                "Layer 8 error.",
                "Staaaaahp",
                "Seriously?",
                "...",
                "I hope at least you're having fun, cause I don't."
        };
        errormsg = possibilities[MathUtils.random(possibilities.length - 1)];
        wtf = true;
    }
}
