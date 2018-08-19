package basemod.patches.com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import javassist.CtBehavior;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.rooms.AbstractRoom",
        method = "update"
)
public class StartBattleHook {

    @SpireInsertPatch()
    public static void Insert(AbstractRoom __instance) {
        BaseMod.publishStartBattle((MonsterRoom) __instance);
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.characters.AbstractPlayer", "applyStartOfCombatPreDrawLogic");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
