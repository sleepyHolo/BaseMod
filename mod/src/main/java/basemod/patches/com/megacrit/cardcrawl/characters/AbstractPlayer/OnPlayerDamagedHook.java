package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.screens.custom.CustomModeScreen.PositionCharacterButtons;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = {DamageInfo.class}
)
public class OnPlayerDamagedHook {
    public OnPlayerDamagedHook() {
    }

    @SpireInsertPatch(
            localvars = {"damageAmount"},
            locator = OnPlayerDamagedHook.LocatorPre.class
    )
    public static void InsertPre(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount) {
        int damage = BaseMod.publishOnPlayerDamaged(damageAmount[0], info);
        if (damage < 0) {
            damage = 0;
        }
        damageAmount[0] = damage;
    }

    private static class LocatorPre extends SpireInsertLocator {
        private LocatorPre() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
