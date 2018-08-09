package basemod.patches.com.megacrit.cardcrawl.events.Vampires;

import java.util.Iterator;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomCard;

@SpirePatch(cls="com.megacrit.cardcrawl.events.thecity.Vampires", method="replaceAttacks")
public class ReplaceAttacks {
	@SpireInsertPatch(rloc=8)
	public static void Insert(Object __obj_instance) {
		for (Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator(); i.hasNext();) {
			AbstractCard e = i.next();
			if (e instanceof CustomCard && ((CustomCard)e).isStrike()) {
				i.remove();
			}
		}
	}
}
