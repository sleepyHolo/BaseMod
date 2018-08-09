package basemod.patches.whatmod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

@SpirePatch(
    cls="com.megacrit.cardcrawl.screens.SingleRelicViewPopup",
    method="renderTips"
)
public class RelicView
{
    public static void Postfix(SingleRelicViewPopup __instance, SpriteBatch sb)
    {
        try {
            Field relicField = SingleRelicViewPopup.class.getDeclaredField("relic");
            relicField.setAccessible(true);
            AbstractRelic relic = (AbstractRelic) relicField.get(__instance);

            WhatMod.renderModTooltip(sb, relic.getClass());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
