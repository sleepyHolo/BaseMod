package basemod.patches.com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls="com.megacrit.cardcrawl.unlock.UnlockTracker",
        method="unlockCard"
)
public class UnlockCardModID
{
    public static void Prefix(@ByRef String[] key)
    {
        String modName = BaseMod.findCallingModName();
        if (modName != null && !key[0].startsWith(modName + ":")) {
            key[0] = modName + ":" + key[0];
        }
        System.out.println("unlockCard: " + key[0]);
    }
}
