package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method="initializeRelicList")
public class PostDungeonInitializeHook {
    public static void Postfix(Object __obj_instance) {
        BaseMod.publishPostDungeonInitialize();
    }
}
