package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="startNewGame")
public class StartGameHook {
    @SpireInsertPatch(loc=607)
    public static void Insert(Object __obj_instance) {
        BaseMod.publishStartGame();
    }
}
