package basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;

@SpirePatch(
        cls="com.megacrit.cardcrawl.relics.AbstractRelic",
        method="ctor",
        paramtypes={
                "java.lang.String",
                "java.lang.String",
                "com.megacrit.cardcrawl.relics.AbstractRelic$RelicTier",
                "com.megacrit.cardcrawl.relics.AbstractRelic$LandingSound"
        }
)
public class RelicIDModID
{
    public static void Prefix(AbstractRelic __instance, @ByRef String[] setId, String imgName, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx)
    {
        if (!setId[0].equals(Circlet.ID) && !BaseMod.hasModID(setId[0])) {
            String modName = BaseMod.findCallingModName();
            if (modName != null && !setId[0].startsWith(modName + ":")) {
                setId[0] = modName + ":" + setId[0];
            }
        }
    }
}
