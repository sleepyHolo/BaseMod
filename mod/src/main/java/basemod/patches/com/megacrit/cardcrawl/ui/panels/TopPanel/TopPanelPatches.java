package basemod.patches.com.megacrit.cardcrawl.ui.panels.TopPanel;

import basemod.DailyModsDropdown;
import basemod.patches.com.megacrit.cardcrawl.helpers.TopPanel.TopPanelHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TopPanelPatches
{
    public static boolean renderDailyModsAsDropdown = true;
    private static DailyModsDropdown dailyModsDropdown;
    private static float titleY = Settings.HEIGHT - 28.0F * Settings.scale;
    private static float TIP_Y = Settings.HEIGHT - 120.0F * Settings.scale;
    private static float TIME_TIP_X = 1550.0f * Settings.scale - (192.0f + 45.0f) * Settings.scale;
    private static float TIME_X_POS = Settings.WIDTH - (380.0f + 192.0f + 45.0f) * Settings.scale;

    @SpirePatch(
            clz = TopPanel.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderPatch
    {
        private static AbstractDungeon.CurrentScreen saveScreen;
        private static boolean saveStopClock;

        @SpirePrefixPatch
        public static void Prefix(TopPanel __instance, SpriteBatch sb)
        {
            saveScreen = AbstractDungeon.screen;
            saveStopClock = CardCrawlGame.stopClock;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
            CardCrawlGame.stopClock = false;
        }

        @SpirePostfixPatch
        public static void Postfix(TopPanel __instance, SpriteBatch sb)
        {
            AbstractDungeon.screen = saveScreen;
            CardCrawlGame.stopClock = saveStopClock;

            if (!Settings.hideTopBar) {
                // Run timer
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP || CardCrawlGame.stopClock) {
                    __instance.timerHb.update();
                    sb.setColor(Color.WHITE);
                    sb.draw(ImageMaster.TIMER_ICON, TIME_X_POS, Settings.HEIGHT - 64.0f * Settings.scale, 64.0f * Settings.scale, 64.0f * Settings.scale);
                    Color clockColor = CardCrawlGame.stopClock ? Settings.GREEN_TEXT_COLOR : Settings.GOLD_COLOR;
                    FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont,
                            CharStat.formatHMSM(CardCrawlGame.playtime), TIME_X_POS + 60.0f * Settings.scale, titleY, clockColor);

                    if (__instance.timerHb.hovered) {
                        TipHelper.renderGenericTip(TIME_TIP_X, TIP_Y, TopPanel.LABEL[5], TopPanel.MSG[7]);
                    }
                    __instance.timerHb.render(sb);
                }

                // Mod panel items
                if (TopPanelHelper.topPanelGroup.size() > 0) {
                    TopPanelHelper.topPanelGroup.render(sb);
                }
            }
        }
    }

    @SpirePatch(
            clz = TopPanel.class,
            method = "update"
    )
    public static class UpdatePatch
    {
        @SpirePrefixPatch
        public static void Prefix(TopPanel __instance)
        {
            if (AbstractDungeon.screen != null && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.UNLOCK) {
                if (TopPanelHelper.topPanelGroup.size() > 0) {
                    TopPanelHelper.topPanelGroup.update();
                }
                if (dailyModsDropdown != null) {
                    dailyModsDropdown.update();
                }
            }
        }
    }

    @SpirePatch(
            clz = TopPanel.class,
            method = "setPlayerName"
    )
    public static class SetPlayerNamePatch
    {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(TopPanel __instance)
        {
            __instance.timerHb.move(TIME_X_POS + 70.0f * Settings.scale, Settings.HEIGHT - 32.0f * Settings.scale);
            try {
                Field gl = TopPanel.class.getDeclaredField("gl");
                gl.setAccessible(true);
                Field name_f = TopPanel.class.getDeclaredField("name");
                name_f.setAccessible(true);

                String name = (String) name_f.get(__instance);

                float nameWidth = FontHelper.getWidth(FontHelper.panelNameFont, name, Settings.scale);
                float origNameWidth = nameWidth;
                String origName = name;
                boolean tooLong = false;
                while (nameWidth > 125f * Settings.scale) {
                    tooLong = true;
                    name = name.substring(0, name.length() - 1);
                    nameWidth = FontHelper.getWidth(FontHelper.panelNameFont, name, Settings.scale);
                }
                if (tooLong) {
                    name = name + "...";
                    // Remove the "..." if adding it makes the name longer than before
                    if (FontHelper.getWidth(FontHelper.panelNameFont, name, Settings.scale) > origNameWidth) {
                        name = origName;
                    }
                }
                GlyphLayout layout = (GlyphLayout) gl.get(__instance);
                layout.setText(FontHelper.panelNameFont, name);

                gl.set(__instance, layout);
                name_f.set(__instance, name);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher matcher = new Matcher.FieldAccessMatcher(GlyphLayout.class, "width");
                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), matcher);
            }
        }

        public static void Postfix(TopPanel __instance)
        {
            // Move Twitch button right of Modifiers dropdown
            __instance.twitch.ifPresent(twitchPanel -> twitchPanel.setPosition((1180 + 110) * Settings.scale, Settings.HEIGHT));

            if (ModHelper.enabledMods != null && !ModHelper.enabledMods.isEmpty()) {
                if (renderDailyModsAsDropdown) {
                    __instance.modHbs = new Hitbox[0];
                    dailyModsDropdown = new DailyModsDropdown(ModHelper.enabledMods, 1180f, -64f);
                }
            }
        }

    }

    @SpirePatch(
            clz = TopPanel.class,
            method = "renderDailyMods",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderDailyModsPatch
    {
        public static SpireReturn<Void> Prefix(TopPanel __instance, SpriteBatch sb)
        {
            if (renderDailyModsAsDropdown) {
                dailyModsDropdown.render(sb);
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }

    }
}
