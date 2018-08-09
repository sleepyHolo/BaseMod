package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.CardLibrary", method = "add")
public class AddSwitch {
	@SpireInsertPatch(rloc = 37)
	public static void Insert(Object cardObj) {
		AbstractCard card = (AbstractCard) cardObj;
		CardColor color = card.color;
		if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
				&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
			BaseMod.incrementCardCount(color);
			if (UnlockTracker.isCardSeen(card.cardID)) {
				BaseMod.incrementSeenCardCount(color);
			}
		}
	}
}
