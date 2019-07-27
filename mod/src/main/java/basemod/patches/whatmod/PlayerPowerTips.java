package basemod.patches.whatmod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

@SpirePatch(
    clz=AbstractCreature.class,
    method="renderPowerTips"
)
public class PlayerPowerTips
{
    private static String modName = null;

    @SpireInsertPatch(
        locator=Locator.class,
        localvars={"p"}
    )
    public static void Insert(AbstractCreature __instance, SpriteBatch sb, AbstractPower power)
    {
        modName = WhatMod.findModName(power.getClass());
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPower.class, "region48");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    public static PowerTip alterTip(PowerTip tip)
    {
        if (WhatMod.enabled && modName != null) {
            tip.body = FontHelper.colorString(modName, "p") + " NL " + tip.body;
        }
        return tip;
    }

    @SpirePatch(
		clz=AbstractCreature.class,
        method="renderPowerTips"
    )
    public static class Nested
    {
        public static ExprEditor Instrument()
        {
            return new ExprEditor()
            {
                @Override
                public void edit(NewExpr e) throws CannotCompileException
                {
                    if (e.getClassName().equals(PowerTip.class.getName())) {
                        e.replace(String.format("$_ = %s.alterTip($proceed($$));", PlayerPowerTips.class.getName()));
                    }
                }
            };
        }
    }
}
