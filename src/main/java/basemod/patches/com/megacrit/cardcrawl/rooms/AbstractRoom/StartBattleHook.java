package basemod.patches.com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.rooms.AbstractRoom",
        method = "update"
)
public class StartBattleHook {

    @SpireInsertPatch(rloc = 44)
    public static void Insert(AbstractRoom __instance) {
        BaseMod.publishStartBattle((MonsterRoom) __instance);
    }

}
