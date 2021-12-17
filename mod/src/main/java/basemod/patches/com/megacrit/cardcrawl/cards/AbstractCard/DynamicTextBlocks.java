package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class DynamicTextBlocks {

    //TODO: patch apply powers and calc damage and stuff


    @SpirePatch(clz = AbstractCard.class, method = "initializeDescription")
    @SpirePatch(clz = AbstractCard.class, method = "initializeDescriptionCN")
    public static class BeDifferentColorPls {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                //Method call is basically the equivalent of a methodcallmatcher of an insert patch, checks the edit method against every method call in the function you#re patching
                public void edit(MethodCall m) throws CannotCompileException {
                    //If the method is from the class AnimationState and the method is called update
                    if (m.getClassName().equals(String.class.getName()) && m.getMethodName().equals("split")) {
                        m.replace("{ $_ = " + basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks.class.getName()+".checkForUnwrapping(this, $proceed($$)); }");
                    }
                }
            };
        }
    }

    public static String[] checkForUnwrapping(AbstractCard c, String[] splitText) {
        String baseText = String.join(" ", splitText);
        String regex = "(\\[!.*?!\\|).*?(\\|@])";
        if (baseText.matches(".*"+regex+".*")) {
            String temp = baseText.replaceAll(regex,"@temp@");
            ArrayList<String> toUnwrap = new ArrayList<>();
            Pattern p = Pattern.compile(regex);
            java.util.regex.Matcher m = p.matcher(baseText);
            while (m.find()) {
                toUnwrap.add(unwrap(c, m.group()));
            }
            while (!toUnwrap.isEmpty()){
                temp = temp.replaceFirst("@temp@", toUnwrap.get(0));
                toUnwrap.remove(0);
            }
            return temp.split(" ");
        } else {
            return baseText.split(" ");
        }
    }

    public static String unwrap(AbstractCard c, String string) {
        if (string.length() > 0 && string.charAt(0) == '[') {
            String key = string.trim();
            if (key.endsWith("@]")) {
                key = key.replace("*d", "D").replace("*b", "B").replace("*m", "M");
            }
            key = key.substring(1, key.length()-3);
            String[] parts = key.split("\\|");
            Integer var = null;
            if (parts[0].equals("!D!")) {
                var = c.damage;
            } else if (parts[0].equals("!B!")) {
                var = c.block;
            } else if (parts[0].equals("!M!")) {
                var = c.magicNumber;
            } else if (parts[0].equals("!L!")) {
                var = -1;
                if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null){
                    if (AbstractDungeon.player.hand.contains(c)) {
                        var = 0;
                    } else if (AbstractDungeon.player.drawPile.contains(c)) {
                        var = 1;
                    } else if (AbstractDungeon.player.discardPile.contains(c)) {
                        var = 2;
                    } else if (AbstractDungeon.player.exhaustPile.contains(c)) {
                        var = 3;
                    }
                }
            } else if (BaseMod.cardDynamicVariableMap.containsKey(parts[0].replace("!",""))) {
                var = BaseMod.cardDynamicVariableMap.get(parts[0].replace("!","")).value(c);
            }
            parts[0] = "";
            if (var != null) {
                boolean matched = false;
                for (String s : parts) {
                    if (s.contains("=")) {
                        String[] split = s.split("=");
                        if (split[0].contains(",")) {
                            String[] nums = split[0].split(",");
                            for (String n : nums) {
                                if(checkMatch(var, n)) {
                                    if (split.length > 1) {
                                        string = split[1];
                                    } else {
                                        string = "";
                                    }
                                    matched = true;
                                }
                            }
                        } else {
                            if (checkMatch(var, split[0])) {
                                if (split.length > 1) {
                                    string = split[1];
                                } else {
                                    string = "";
                                }
                                matched = true;
                            }
                        }
                        if (split[0].equals("@") && !matched) {
                            if (split.length > 1) {
                                string = split[1];
                            } else {
                                string = "";
                            }
                        }
                    }
                }
            } else {
                string = "";
            }
        }
        return string;
    }

    private static boolean checkMatch(Integer var, String s) {
        boolean greater = s.contains(">");
        boolean less = s.contains("<");
        boolean mod = s.contains("%");
        boolean ends = s.contains("&");
        s = s.replace(">", "").replace("<", "").replace("%", "").replace("&","");
        if (NumberUtils.isCreatable(s)) {
            int comp = var.compareTo(NumberUtils.createInteger(s));
            if (ends) {
                int len = s.length();
                return comp == 0 || comp % Math.pow(10, len) == 0;
            }
            if (mod) {
                return comp == 0 || var % NumberUtils.createInteger(s) == 0;
            }
            return ((!greater && !less && comp == 0) || (greater && comp > 0) || (less && comp < 0));
        }
        return false;
    }
}
