package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

@SpirePatch(
		cls="com.megacrit.cardcrawl.helpers.CardLibrary",
		method="getEachRare"
)
public class GetEachRareSwitch
{
	public static CardGroup Postfix(CardGroup __result, AbstractPlayer.PlayerClass chosenClass)
	{
		if (!BaseMod.isBaseGameCharacter(chosenClass)) {
			__result.clear();
			for (AbstractCard c : CardLibrary.cards.values()) {
				if (c.rarity == AbstractCard.CardRarity.RARE && BaseMod.getColor(chosenClass.name()).equals(c.color.name())) {
					__result.addToBottom(c.makeCopy());
				}
			}
		}
		return __result;
	}
}
