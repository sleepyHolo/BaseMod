package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="create")
public class PostInitializeHook {
    public static void Postfix(Object __obj_instance) {
        BaseMod.publishPostInitialize();
    }
}
