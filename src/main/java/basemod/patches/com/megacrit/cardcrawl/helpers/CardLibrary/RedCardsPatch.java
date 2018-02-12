package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method="addRedCards")
public class RedCardsPatch {
	public static void Postfix() {
		// add new cards
		for (AbstractCard card : BaseMod.getRedCardsToAdd()) {
			CardLibrary.add(card);
		}
		
		// remove old cards
		for (AbstractCard card : BaseMod.getRedCardsToRemove()) {
			CardLibrary.cards.remove(card.cardID);
			CardLibrary.redCards--;
			CardLibrary.totalCardCount--;
		}
	}
}
