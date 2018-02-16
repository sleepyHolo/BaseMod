package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Map;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method="initializeCardPools")
public class InitializeCardPoolsSwitch {

	@SpireInsertPatch(loc=1393, localvars={"tmpPool"})
	public static void Insert(Object __obj_instance, Object tmpPoolObj) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractPlayer.PlayerClass chosenClass = player.chosenClass;
		@SuppressWarnings("unchecked")
		ArrayList<AbstractCard> tmpPool = (ArrayList<AbstractCard>) tmpPoolObj;
		if (!chosenClass.toString().equals("IRONCLAD") && !chosenClass.toString().equals("THE_SILENT") &&
				!chosenClass.toString().equals("CROWBOT")) {
			String color = BaseMod.getColor(chosenClass.toString());
			AbstractCard card;
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				card = c.getValue();
				if ((card.color.toString().equals(color)) && (card.rarity != AbstractCard.CardRarity.BASIC) && (
						(!UnlockTracker.isCardLocked(c.getKey())) || (Settings.isDailyRun))) {
					tmpPool.add(card);
				}
			}
		}
	}
	
}
