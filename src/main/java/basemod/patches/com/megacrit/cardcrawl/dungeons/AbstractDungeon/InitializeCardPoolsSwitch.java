package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.daily.DailyMods;

import basemod.BaseMod;
import javassist.CtBehavior;

@SpirePatch(
		clz=AbstractDungeon.class,
		method="initializeCardPools"
)
public class InitializeCardPoolsSwitch {

	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"tmpPool"}
	)
	public static void Insert(AbstractDungeon __instance, ArrayList<AbstractCard> tmpPool) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractPlayer.PlayerClass chosenClass = player.chosenClass;
		AbstractCard card;

		if (AbstractPlayer.customMods == null) {
			AbstractPlayer.customMods = new ArrayList<>();
		}

		// Diverse
		if (DailyMods.cardMods.get(Diverse.ID)) {
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				card = c.getValue();
				if ((BaseMod.playerColorMap.containsValue(card.color)) && (card.rarity != AbstractCard.CardRarity.BASIC) &&
						((!UnlockTracker.isCardLocked(c.getKey())) || (Settings.isDailyRun))) {
					tmpPool.add(card);
				}
			}
		} else if (!BaseMod.isBaseGameCharacter(chosenClass)) {
			// Normal modded character
			AbstractCard.CardColor color = BaseMod.getColor(chosenClass);
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				card = c.getValue();
				if ((card.color.equals(color)) && (card.rarity != AbstractCard.CardRarity.BASIC) && (
						(!UnlockTracker.isCardLocked(c.getKey())) || (Settings.isDailyRun))) {
					tmpPool.add(card);
				}
			}

			// Red/Green/Blue Cards modifiers for modded characters
			try {
				if (AbstractPlayer.customMods.contains(RedCards.ID)) {
					Method addRedCards = AbstractDungeon.class.getDeclaredMethod("addRedCards", ArrayList.class);
					addRedCards.setAccessible(true);
					addRedCards.invoke(__instance, tmpPool);
				}
				if (AbstractPlayer.customMods.contains(GreenCards.ID)) {
					Method addGreenCards = AbstractDungeon.class.getDeclaredMethod("addGreenCards", ArrayList.class);
					addGreenCards.setAccessible(true);
					addGreenCards.invoke(__instance, tmpPool);
				}
				if (AbstractPlayer.customMods.contains(BlueCards.ID)) {
					Method addBlueCards = AbstractDungeon.class.getDeclaredMethod("addBlueCards", ArrayList.class);
					addBlueCards.setAccessible(true);
					addBlueCards.invoke(__instance, tmpPool);
				}
				if (AbstractPlayer.customMods.contains(ColorlessCards.ID)) {
					Method addColorlessCards = AbstractDungeon.class.getDeclaredMethod("addColorlessCards", ArrayList.class);
					addColorlessCards.setAccessible(true);
					addColorlessCards.invoke(__instance, tmpPool);
				}
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		if (!DailyMods.cardMods.get(Diverse.ID)) {
			// Modded character cards modifiers
			CustomMod charMod = new CustomMod("Modded Character Cards", "p", false);
			for (AbstractPlayer.PlayerClass pc : BaseMod.playerClassMap.keySet()) {
				if (pc == chosenClass) {
					continue;
				}
				 String ID = pc.name() + charMod.name;
				 if (AbstractPlayer.customMods.contains(ID)) {
				 	BaseMod.logger.info("[INFO] Adding " + BaseMod.getTitle(pc) + " cards into card pool.");
				 	AbstractCard.CardColor color = BaseMod.getColor(pc);
				 	for (AbstractCard c : CardLibrary.cards.values()) {
				 		if (c.color == color && c.rarity != AbstractCard.CardRarity.BASIC &&
								(!UnlockTracker.isCardLocked(c.cardID) || Settings.treatEverythingAsUnlocked())) {
				 			tmpPool.add(c);
						}
					}
				 }
			}
		}
	}

	private static class Locator extends SpireInsertLocator {
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception
		{
			Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "addColorlessCards");

			int[] lines = LineFinder.findAllInOrder(ctBehavior, finalMatcher);
			return new int[]{lines[lines.length-1]};
		}
	}
}
