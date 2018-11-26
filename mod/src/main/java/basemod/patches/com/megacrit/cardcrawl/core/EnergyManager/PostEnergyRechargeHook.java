package basemod.patches.com.megacrit.cardcrawl.core.EnergyManager;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(cls="com.megacrit.cardcrawl.core.EnergyManager", method="recharge")
public class PostEnergyRechargeHook {
    @SpireInsertPatch(rloc=10)
    public static void Insert(Object __obj_instance) {
        BaseMod.publishPostEnergyRecharge();
    }
}
