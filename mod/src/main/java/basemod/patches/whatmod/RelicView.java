package basemod.patches.whatmod;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;

@SpirePatch(
    clz=SingleRelicViewPopup.class,
    method="renderTips"
)
public class RelicView
{
    public static void Postfix(SingleRelicViewPopup __instance, SpriteBatch sb)
    {
        if (WhatMod.enabled) {
			AbstractRelic relic = ReflectionHacks.getPrivate(__instance, SingleRelicViewPopup.class, "relic");
            Hitbox nextHb = ReflectionHacks.getPrivate(__instance, SingleRelicViewPopup.class, "nextHb");

            WhatMod.renderModTooltipBottomLeft(sb, Settings.WIDTH / 2f + 340f * Settings.scale, nextHb.cY + 160f * Settings.scale, relic.getClass());
		}
    }
}
