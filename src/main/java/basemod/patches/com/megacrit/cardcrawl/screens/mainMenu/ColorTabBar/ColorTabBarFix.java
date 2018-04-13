package basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import com.megacrit.cardcrawl.screens.mainMenu.TabBarListener;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ColorTabBarFix
{
    public static class Enums
    {
        @SpireEnum
        public static ColorTabBar.CurrentTab MOD;
    }

    public static class Fields
    {
        public static int modTabIndex = 0;

        static ArrayList<Hitbox> hitboxes;
    }

    @SpirePatch(
            cls="com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar",
            method="ctor"
    )
    public static class Ctor
    {
        public static void Postfix(ColorTabBar __instance, TabBarListener delegate)
        {
            Fields.hitboxes = new ArrayList<>();
            AbstractCard.CardColor[] colors = AbstractCard.CardColor.values();
            for (int icolor = AbstractCard.CardColor.CURSE.ordinal() + 1; icolor < colors.length; ++icolor) {
                Fields.hitboxes.add(new Hitbox(235.0f * Settings.scale, 51.0f * Settings.scale));
            }
        }
    }

    @SpirePatch(
            cls="com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar",
            method="update"
    )
    public static class Update
    {
        public static void Postfix(ColorTabBar __instance, float y)
        {
            boolean anyHovered = false;
            for (int i=0; i<Fields.hitboxes.size(); ++i) {
                Fields.hitboxes.get(i).move(157.0f * Settings.scale, y - Render.SPACING * i * Settings.scale - 14.0f * Settings.scale);
                Fields.hitboxes.get(i).update();
                if (!anyHovered && Fields.hitboxes.get(i).justHovered) {
                    anyHovered = true;
                    CardCrawlGame.sound.playA("UI_HOVER", -0.4f);
                }
                if (InputHelper.justClickedLeft) {
                    if (Fields.hitboxes.get(i).hovered) {
                        try {
                            Field curTab = ColorTabBar.class.getDeclaredField("curTab");
                            curTab.setAccessible(true);
                            ColorTabBar.CurrentTab oldTab = (ColorTabBar.CurrentTab)curTab.get(__instance);
                            if (oldTab != Enums.MOD || Fields.modTabIndex != i) {
                                curTab.set(__instance, Enums.MOD);
                                Fields.modTabIndex = i;
                                Field f = ColorTabBar.class.getDeclaredField("delegate");
                                f.setAccessible(true);
                                TabBarListener delegate = (TabBarListener)f.get(__instance);
                                delegate.didChangeTab(__instance, Enums.MOD);
                            }
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(
            cls="com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar",
            method="render"
    )
    public static class Render
    {
        private static final float SPACING = 64.0f;

        @SpireInsertPatch(
                localvars={"curTab"}
        )
        public static void Insert(ColorTabBar __instance, SpriteBatch sb, float y, ColorTabBar.CurrentTab curTab)
        {
            AbstractCard.CardColor[] colors = AbstractCard.CardColor.values();
            for (int icolor = AbstractCard.CardColor.CURSE.ordinal() + 1; icolor < colors.length; ++icolor) {
                int i = icolor - AbstractCard.CardColor.CURSE.ordinal();

                Color color = BaseMod.getTrailVfxColor(AbstractCard.CardColor.values()[icolor].name()).cpy();
                if (curTab != Enums.MOD || Fields.modTabIndex != i-1) {
                    color = color.lerp(Color.GRAY, 0.5f);
                }
                sb.setColor(color);
                sb.draw(ImageMaster.COLOR_TAB_BAR, 40.0f * Settings.scale, y - SPACING * i * Settings.scale, 0, 0, 235.0F, 102.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1334, 102, false, false);

                Color textcolor = Settings.GOLD_COLOR;
                if (curTab != Enums.MOD || Fields.modTabIndex != i-1) {
                    textcolor = Color.GRAY;
                    sb.setColor(Color.GRAY);
                } else {
                    sb.setColor(Color.WHITE);
                }
                FontHelper.renderFontCentered(sb, FontHelper.bannerFont, capitalizeWord(AbstractCard.CardColor.values()[icolor].name()), 157.0f * Settings.scale, y - (SPACING * i * Settings.scale) + 50.0f * Settings.scale, textcolor, 0.6f);
            }

            for (int i=0; i<Fields.hitboxes.size(); ++i) {
                Fields.hitboxes.get(i).render(sb);
            }
        }

        public static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar", "getBarColor");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(
            cls="com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar",
            method="getBarColor"
    )
    public static class GetBarColor
    {
        public static Color Postfix(Color __result, ColorTabBar __instance)
        {
            if (__result.equals(Color.WHITE)) {
                try {
                    Field f = ColorTabBar.class.getDeclaredField("curTab");
                    f.setAccessible(true);
                    if (f.get(__instance) == Enums.MOD) {
                        return BaseMod.getTrailVfxColor(AbstractCard.CardColor.values()[AbstractCard.CardColor.CURSE.ordinal() + 1 + Fields.modTabIndex].name());
                    }
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }

            return __result;
        }
    }

    private static String capitalizeWord(String str)
    {
        if (str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + (str.length() > 1 ? str.substring(1).toLowerCase() : "");
    }
}
