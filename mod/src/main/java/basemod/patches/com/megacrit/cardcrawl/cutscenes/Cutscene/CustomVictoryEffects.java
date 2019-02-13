package basemod.patches.com.megacrit.cardcrawl.cutscenes.Cutscene;

import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SpirePatch(
		clz=VictoryScreen.class,
		method="updateVfx"
)
public class CustomVictoryEffects
{
	public static void Postfix(VictoryScreen __instance)
	{
		if (!BaseMod.isBaseGameCharacter(AbstractDungeon.player.chosenClass)) {
			if (AbstractDungeon.player instanceof CustomPlayer) {
				try {
					Field f = VictoryScreen.class.getDeclaredField("effect");
					f.setAccessible(true);

					ArrayList<AbstractGameEffect> effects = (ArrayList<AbstractGameEffect>) f.get(__instance);
					((CustomPlayer) AbstractDungeon.player).updateVictoryVfx(effects);
				} catch (IllegalAccessException | NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
