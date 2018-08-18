package basemod.patches.com.megacrit.cardcrawl.cards;

import basemod.helpers.BaseModTags;
import basemod.helpers.CardTags;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
		cls="com.megacrit.cardcrawl.cards.red.PerfectedStrike",
		method="isStrike"
)
public class PerfectedStrikePatch
{
	public static boolean Postfix(boolean __result, AbstractCard c)
	{
		return __result || CardTags.hasTag(c, BaseModTags.STRIKE);
	}
}
