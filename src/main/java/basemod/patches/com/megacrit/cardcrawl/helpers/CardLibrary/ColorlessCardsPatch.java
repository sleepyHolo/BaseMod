package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method="addColorlessCards")
public class ColorlessCardsPatch {

    public static void Postfix() {
    	// add new cards
		for (AbstractCard card : BaseMod.getColorlessCardsToAdd()) {
			CardLibrary.add(card);
		}
		
		// remove old cards
		for (String card : BaseMod.getColorlessCardsToRemove()) {
			CardLibrary.cards.remove(card);
			CardLibrary.colorlessCards--;
			CardLibrary.totalCardCount--;
		}
    }
}
