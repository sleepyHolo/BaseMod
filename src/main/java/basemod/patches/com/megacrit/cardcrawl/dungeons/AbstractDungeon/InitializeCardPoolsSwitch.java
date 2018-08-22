package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Map;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.daily.DailyMods;

import basemod.BaseMod;
import javassist.CtBehavior;

@SpirePatch(cls="com.megacrit.cardcrawl.dungeons.AbstractDungeon", method="initializeCardPools")
public class InitializeCardPoolsSwitch {

	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"tmpPool"}
	)
	public static void Insert(Object __obj_instance, Object tmpPoolObj) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractPlayer.PlayerClass chosenClass = player.chosenClass;
		@SuppressWarnings("unchecked")
		ArrayList<AbstractCard> tmpPool = (ArrayList<AbstractCard>) tmpPoolObj;
		AbstractCard card;
		if (DailyMods.cardMods.get("Diverse")){
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				card = c.getValue();
				if ((BaseMod.playerColorMap.containsValue(card.color)) && (card.rarity != AbstractCard.CardRarity.BASIC) &&
						((!UnlockTracker.isCardLocked(c.getKey())) || (Settings.isDailyRun))) {
					tmpPool.add(card);
				}
			}
		}
		else if (!BaseMod.isBaseGameCharacter(chosenClass)) {
			AbstractCard.CardColor color = BaseMod.getColor(chosenClass);
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				card = c.getValue();
				if ((card.color.equals(color)) && (card.rarity != AbstractCard.CardRarity.BASIC) && (
						(!UnlockTracker.isCardLocked(c.getKey())) || (Settings.isDailyRun))) {
					tmpPool.add(card);
				}
			}
		}
	}

	private static class Locator extends SpireInsertLocator {
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception
		{
			Matcher finalMatcher = new Matcher.MethodCallMatcher(
					"com.megacrit.cardcrawl.dungeons.AbstractDungeon", "addColorlessCards");

			int[] lines = LineFinder.findAllInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
			return new int[]{lines[lines.length-1]};
		}
	}
}
