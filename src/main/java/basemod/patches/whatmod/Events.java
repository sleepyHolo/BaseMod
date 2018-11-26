package basemod.patches.whatmod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(
		clz=AbstractEvent.class,
		method="renderText"
)
public class Events
{
	public static void Postfix(AbstractEvent __instance, SpriteBatch sb)
	{
		if (WhatMod.enabled
				&& !(__instance instanceof NeowEvent)
				&& !(__instance instanceof SpireHeart)
				&& AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
			WhatMod.renderModTooltip(sb, __instance.getClass(), 1500.0f * Settings.scale, 900.0f * Settings.scale);
		}
	}
}
