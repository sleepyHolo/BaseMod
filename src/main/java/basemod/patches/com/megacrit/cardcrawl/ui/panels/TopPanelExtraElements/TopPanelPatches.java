package basemod.patches.com.megacrit.cardcrawl.ui.panels.TopPanelExtraElements;

import basemod.patches.com.megacrit.cardcrawl.helpers.TopPanel.TopPanelHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

public class TopPanelPatches {

    @SpirePatch(clz = TopPanel.class, method = "render", paramtypez = {SpriteBatch.class})
    public static class RenderPatch{
        @SpirePostfixPatch
        public static void Postfix(TopPanel __instance, SpriteBatch sb) {
            if(!Settings.hideTopBar && TopPanelHelper.topPanelGroup.size() > 0) {
                TopPanelHelper.topPanelGroup.render(sb);
            }
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "update")
    public static class UpdatePatch{

        @SpirePrefixPatch
        public static void Prefix(TopPanel __instance) {
            if (AbstractDungeon.screen != null && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.UNLOCK && TopPanelHelper.topPanelGroup.size() > 0) {
                TopPanelHelper.topPanelGroup.update();
            }
        }

    }
}
