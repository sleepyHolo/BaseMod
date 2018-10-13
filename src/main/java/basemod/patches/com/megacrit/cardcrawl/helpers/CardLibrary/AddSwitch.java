package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.CardLibrary", method = "add")
public class AddSwitch {
	@SpireInsertPatch(rloc = 37)
	public static void Insert(AbstractCard card) {
		CardColor color = card.color;
		switch (color) {
			case RED:
			case GREEN:
			case BLUE:
			case COLORLESS:
			case CURSE:
				break;
			default:
				BaseMod.incrementCardCount(color);
				if (UnlockTracker.isCardSeen(card.cardID)) {
					BaseMod.incrementSeenCardCount(color);
				}
		}
	}
}
