package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method="initialize")
public class CustomCardsPatch {
	@SpireInsertPatch(rloc=10)
	public static void Insert() {
		// add new cards
		for (AbstractCard card : BaseMod.getCustomCardsToAdd()) {
			CardLibrary.add(card);
		}
		
		// remove old cards
		ArrayList<String> cardIDs = BaseMod.getCustomCardsToRemove();
		ArrayList<String> colors = BaseMod.getCustomCardsToRemoveColors();
		for (int i = 0; i < cardIDs.size(); i++) {
			String cardID = cardIDs.get(i);
			CardLibrary.cards.remove(cardID);
			BaseMod.decrementCardCount(colors.get(i));
			CardLibrary.totalCardCount--;
		}
	}
	
}
