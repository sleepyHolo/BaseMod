package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.Circlet;

@SpirePatch(
        cls="com.megacrit.cardcrawl.characters.AbstractPlayer",
        method="hasRelic"
)
public class HasRelicModID
{
    public static void Prefix(AbstractPlayer __instance, @ByRef String[] targetID)
    {
        if (!targetID[0].equals(Circlet.ID)) {
            String modName = BaseMod.findCallingModName();
            if (modName != null && !targetID[0].startsWith(modName + ":")) {
                targetID[0] = modName + ":" + targetID[0];
            }
        }
    }
}
