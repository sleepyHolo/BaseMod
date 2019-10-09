package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

@SpirePatch(
		clz= CardLibrary.class,
		method="addPurpleCards",
		paramtypez={}
)
public class PurpleCardsPatch
{
	public static void Postfix()
	{
		// add new cards
		for (AbstractCard card : BaseMod.getPurpleCardsToAdd()) {
			CardLibrary.add(card);
		}

		// remove old cards
		for (String cardID : BaseMod.getPurpleCardsToRemove()) {
			CardLibrary.cards.remove(cardID);
			CardLibrary.purpleCards--;
			CardLibrary.totalCardCount--;
		}
	}
}
