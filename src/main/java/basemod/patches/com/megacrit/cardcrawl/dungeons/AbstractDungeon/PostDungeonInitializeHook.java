package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method="initializeCardPools")
public class PostDungeonInitializeHook {
    @SpireInsertPatch(loc=1415)
    public static void Insert(Object __obj_instance) {
        BaseMod.publishPostDungeonInitialize();
    }
}
