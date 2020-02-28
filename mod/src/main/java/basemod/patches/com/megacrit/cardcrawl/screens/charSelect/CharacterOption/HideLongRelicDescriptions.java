package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

@SpirePatch(
		clz=CharacterOption.class,
		method="renderInfo"
)
public class HideLongRelicDescriptions
{
	public static SpireReturn<Void> Prefix(CharacterOption __instance, SpriteBatch sb, float ___infoX)
	{
		if (___infoX < -600 * Settings.scale) {
			return SpireReturn.Return(null);
		}
		return SpireReturn.Continue();
	}
}
