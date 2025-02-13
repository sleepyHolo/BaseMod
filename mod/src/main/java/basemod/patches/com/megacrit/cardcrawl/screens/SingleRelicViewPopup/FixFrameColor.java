package basemod.patches.com.megacrit.cardcrawl.screens.SingleRelicViewPopup;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

@SpirePatch2(
		clz = SingleRelicViewPopup.class,
		method = "generateFrameImg"
)
public class FixFrameColor
{
	@SpirePrefixPatch
	public static void fix(AbstractRelic ___relic)
	{
		___relic.isSeen = UnlockTracker.isRelicSeen(___relic.relicId);
	}
}
