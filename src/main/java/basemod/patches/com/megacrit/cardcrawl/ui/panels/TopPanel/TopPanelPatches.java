package basemod.patches.com.megacrit.cardcrawl.ui.panels.TopPanel;

import basemod.ClickableUIElement;
import basemod.DailyModsDropdown;
import basemod.patches.com.megacrit.cardcrawl.helpers.TopPanel.TopPanelHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyMods;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TopPanelPatches
{
    public static boolean renderDailyModsAsDropdown = true;
    public static String ascensionString;
    private static DailyModsDropdown dailyModsDropdown;
    private static ClickableUIElement ascensionIcon;
    private static float floorX;
    private static float titleY = Settings.HEIGHT - 28.0F * Settings.scale;
    private static float TIP_Y = Settings.HEIGHT - 120.0F * Settings.scale;
    private static String[] UI_TEXT = CardCrawlGame.languagePack.getUIString("TopPanel").TEXT;

    @SpirePatch(
            clz = TopPanel.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderPatch
    {
        @SpirePostfixPatch
        public static void Postfix(TopPanel __instance, SpriteBatch sb)
        {
            if (!Settings.hideTopBar) {
                if (TopPanelHelper.topPanelGroup.size() > 0) {
                    TopPanelHelper.topPanelGroup.render(sb);
                }
            }
        }
    }

    @SpirePatch(
            clz = TopPanel.class,
            method = "renderDungeonInfo",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderDungeonInfo
    {
        public static SpireReturn<Void> Prefix(TopPanel __instance, SpriteBatch sb)
        {
            if (AbstractDungeon.floorNum > 0) {
                if (Settings.usesOrdinal){
                    if (!AbstractDungeon.isAscensionMode) {
                        return SpireReturn.Continue();
                    } else if (AbstractDungeon.ascensionLevel == 20) {
                        String message = AbstractDungeon.floorNum + TopPanel.getOrdinalNaming(AbstractDungeon.floorNum) + UI_TEXT[0];
                        float ascensionIconX = floorX + FontHelper.getWidth(FontHelper.panelNameTitleFont, message, Settings.scale) + 25.0F * Settings.scale;
                        ascensionIcon.setX(ascensionIconX);
                        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameTitleFont, message, floorX, titleY + 3.0F * Settings.scale, Settings.GOLD_COLOR);
                        sb.setColor(Color.RED.cpy());
                        ascensionIcon.render(sb);
                        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameTitleFont, String.valueOf(AbstractDungeon.ascensionLevel), ascensionIconX + 25.0F * Settings.scale, titleY + 3.0F * Settings.scale, Settings.RED_TEXT_COLOR);
                    } else {
                        String message = AbstractDungeon.floorNum + TopPanel.getOrdinalNaming(AbstractDungeon.floorNum) + UI_TEXT[0];
                        float ascensionIconX = floorX + FontHelper.getWidth(FontHelper.panelNameTitleFont, message, Settings.scale) + 25.0F * Settings.scale;
                        ascensionIcon.setX(ascensionIconX);
                        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameTitleFont, message, floorX, titleY + 3.0F * Settings.scale, Settings.GOLD_COLOR);
                        sb.setColor(Color.WHITE.cpy());
                        ascensionIcon.render(sb);
                        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameTitleFont, String.valueOf(AbstractDungeon.ascensionLevel), ascensionIconX + 25.0F * Settings.scale, titleY + 3.0F * Settings.scale, Settings.RED_TEXT_COLOR);
                    }
                }
            }

            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz = TopPanel.class,
            method = "updateAscensionHover"
    )
    public static class UpdateAscensionHoverPatch
    {
        public static SpireReturn<Void> Prefix(TopPanel __instance)
        {
            return SpireReturn.Return(null);
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
                ascensionIcon.update();
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
            try {
                Field gl = TopPanel.class.getDeclaredField("gl");
                gl.setAccessible(true);
                Field name_f = TopPanel.class.getDeclaredField("name");
                name_f.setAccessible(true);

                String name = (String) name_f.get(__instance);

                float nameWidth = FontHelper.getWidth(FontHelper.panelNameFont, name, Settings.scale);
                boolean tooLong = false;
                while (nameWidth > 125f * Settings.scale) {
                    tooLong = true;
                    name = name.substring(0, name.length() - 1);
                    nameWidth = FontHelper.getWidth(FontHelper.panelNameFont, name, Settings.scale);
                }
                if (tooLong) {
                    name = name + "...";
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
            if (DailyMods.enabledMods != null && !DailyMods.enabledMods.isEmpty()) {
                if (renderDailyModsAsDropdown) {
                    __instance.modHbs = new Hitbox[0];
                    dailyModsDropdown = new DailyModsDropdown(DailyMods.enabledMods, 1410f, 1032f);
                }
            }

            try {
                Field floorX_f = TopPanel.class.getDeclaredField("floorX");
                floorX_f.setAccessible(true);
                floorX = floorX_f.getFloat(__instance) - 125.0F * Settings.scale;
                floorX = AbstractDungeon.ascensionLevel < 11 ? floorX + 44 * Settings.scale : floorX;
                Field ascensionString_f = TopPanel.class.getDeclaredField("ascensionString");
                ascensionString_f.setAccessible(true);
                ascensionString = (String) ascensionString_f.get(__instance);
                TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("powers/powers.atlas"));
                TextureAtlas.AtlasRegion ascensionImage = atlas.findRegion("48/minion");
                ascensionIcon = new ClickableUIElement(ascensionImage, 0, 0, ascensionImage.packedWidth, ascensionImage.packedHeight)
                {
                    @Override
                    protected void onHover()
                    {
                        TipHelper.renderGenericTip(InputHelper.mX + 50.0F * Settings.scale, TIP_Y, CharacterSelectScreen.TEXT[8], ascensionString);
                    }

                    @Override
                    protected void onUnhover() {}

                    @Override
                    protected void onClick() {}

                    @Override
                    public void render(SpriteBatch sb)
                    {
                        if (AbstractDungeon.ascensionLevel == 20) {
                            sb.setColor(Color.RED);
                        } else {
                            sb.setColor(Color.WHITE);
                        }
                        sb.draw(this.region, this.x, this.y, this.region.packedWidth * Settings.scale, this.region.packedHeight * Settings.scale );
                        renderHitbox(sb);
                    }
                };
                ascensionIcon.setY(titleY - 20.0F * Settings.scale);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
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
