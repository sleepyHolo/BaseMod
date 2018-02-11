package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method="addRedCards")
public class RedCardsPatch {
	public static void Postfix(Object __obj_instance) {
		// add new cards
		for (AbstractCard card : BaseMod.getRedCardsToAdd()) {
			CardLibrary.add(card);
		}
		
		System.out.println("About to remove");
		
		// remove old cards
		for (String card : BaseMod.getRedCardsToRemove()) {
			System.out.println("Removing: " + card);
			CardLibrary.cards.remove(card);
			CardLibrary.redCards--;
			CardLibrary.totalCardCount--;
		}
	}
}
