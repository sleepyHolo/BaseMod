package basemod.patches.com.megacrit.cardcrawl.rooms.CampfireUI;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;

import basemod.BaseMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.CampfireUI", method="initializeButtons")
public class InitializeButtonsSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpireInsertPatch(rloc=14)
	public static void Insert(Object __obj_instance) {
		CampfireUI ui = (CampfireUI) __obj_instance;
		AbstractPlayer.PlayerClass selection = CardCrawlGame.chosenCharacter;
		if (!selection.toString().equals("IRONCLAD") && !selection.toString().equals("THE_SILENT") &&
				!selection.toString().equals("CROWBOT")) {
			try {
				Field buttonsField = ui.getClass().getDeclaredField("buttons");
				buttonsField.setAccessible(true);
				@SuppressWarnings("unchecked")
				ArrayList<AbstractCampfireOption> buttons = (ArrayList<AbstractCampfireOption>) buttonsField.get(ui);
				buttons.add(new SmithOption(AbstractDungeon.player.masterDeck.getUpgradableCards().size() > 0));
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				logger.error("could not create campfire smith button for player " + CardCrawlGame.chosenCharacter.toString()); 
				logger.error("exception is: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
}
