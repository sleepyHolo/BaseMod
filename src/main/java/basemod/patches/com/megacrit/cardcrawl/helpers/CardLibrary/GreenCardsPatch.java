package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method="addGreenCards")
public class GreenCardsPatch {
	public static void Postfix(Object __obj_instance) {
		// add new cards
		for (AbstractCard card : BaseMod.getGreenCardsToAdd()) {
			CardLibrary.add(card);
		}
		
		// remove old cards
		for (String card : BaseMod.getGreenCardsToRemove()) {
			CardLibrary.cards.remove(card);
			CardLibrary.greenCards--;
			CardLibrary.totalCardCount--;
		}
	}
}
