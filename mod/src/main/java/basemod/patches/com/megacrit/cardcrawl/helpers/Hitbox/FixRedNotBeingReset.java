package basemod.patches.com.megacrit.cardcrawl.helpers.Hitbox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;

@SpirePatch2(
		clz = Hitbox.class,
		method = "render"
)
public class FixRedNotBeingReset
{
	private static final Color saved = new Color(1f, 1f, 1f, 1f);

	@SpirePrefixPatch
	public static void saveColor(SpriteBatch sb)
	{
		saved.set(sb.getColor());
	}

	@SpirePostfixPatch
	public static void restoreColor(SpriteBatch sb)
	{
		if (Settings.isDebug || Settings.isInfo) {
			sb.setColor(saved);
		}
	}
}
