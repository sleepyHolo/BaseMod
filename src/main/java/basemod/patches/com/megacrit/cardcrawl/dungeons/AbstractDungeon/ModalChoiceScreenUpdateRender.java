package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import basemod.screens.ModalChoiceScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ModalChoiceScreenUpdateRender
{
    @SpirePatch(
            cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method="update"
    )
    public static class Update
    {
        @SpireInsertPatch(
                rloc=18
        )
        public static void Insert(AbstractDungeon __instance)
        {
            if (AbstractDungeon.screen == ModalChoiceScreen.Enum.MODAL_CHOICE) {
                BaseMod.modalChoiceScreen.update();
            }
        }
    }

    @SpirePatch(
            cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method="render"
    )
    public static class Render
    {
        @SpireInsertPatch(
                rloc=51
        )
        public static void Insert(AbstractDungeon __instance, SpriteBatch sb)
        {
            if (AbstractDungeon.screen == ModalChoiceScreen.Enum.MODAL_CHOICE) {
                BaseMod.modalChoiceScreen.render(sb);
            }
        }
    }

    @SpirePatch(
            cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method="openPreviousScreen"
    )
    public static class OpenPreviousScreen
    {
        public static void Postfix(AbstractDungeon.CurrentScreen s)
        {
            if (s == ModalChoiceScreen.Enum.MODAL_CHOICE) {
                BaseMod.modalChoiceScreen.reopen();
            }
        }
    }
}
