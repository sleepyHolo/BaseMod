package basemod.patches.com.megacrit.cardcrawl.screens.stats.CharStat;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.screens.stats.CharStat;

import basemod.BaseMod;

public class StatFixes {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpirePatch(cls="com.megacrit.cardcrawl.screens.stats.CharStat", method="ctor",
			paramtypes={"com.megacrit.cardcrawl.characters.AbstractPlayer$PlayerClass"})
	public static class ConstructorFix {
		
		@SpireInsertPatch(rloc=13)
		public static void Insert(Object __obj_instance, Object cObj) {
			CharStat stats = (CharStat) __obj_instance;
			AbstractPlayer.PlayerClass c = (AbstractPlayer.PlayerClass) cObj;
			System.out.println("looking at prefs for " + c.toString());
			if (c != AbstractPlayer.PlayerClass.IRONCLAD && c != AbstractPlayer.PlayerClass.THE_SILENT &&
					c != AbstractPlayer.PlayerClass.DEFECT) {
				System.out.println("loading save for " + c.toString());
				stats.pref = SaveHelper.getPrefs(c.toString());
			}
		}
		
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.screens.stats.CharStat", method="ctor",
			paramtypes={"com.megacrit.cardcrawl.characters.AbstractPlayer$PlayerClass"})
	public static class CardCountFixes {
		
		@SpireInsertPatch(rloc=42)
		public static void Insert(Object __obj_instance, Object cObj) {
			CharStat stats = (CharStat) __obj_instance;
			AbstractPlayer.PlayerClass c = (AbstractPlayer.PlayerClass) cObj;
			try {
				if (c != AbstractPlayer.PlayerClass.IRONCLAD && c != AbstractPlayer.PlayerClass.THE_SILENT &&
						c != AbstractPlayer.PlayerClass.DEFECT) {
					Field cardsUnlockedField = stats.getClass().getDeclaredField("cardsUnlocked");
					cardsUnlockedField.setAccessible(true);
					
					Field cardsDiscoveredField = stats.getClass().getDeclaredField("cardsDiscovered");
					cardsDiscoveredField.setAccessible(true);
					
					Field cardsToDiscoverField = stats.getClass().getDeclaredField("cardsToDiscover");
					cardsToDiscoverField.setAccessible(true);
					
					System.out.println("looking at character " + c.toString() + " with color " + BaseMod.getColor(c.toString()));
					
					cardsUnlockedField.set(stats, 0);
					cardsDiscoveredField.set(stats, BaseMod.getSeenCardCount(BaseMod.getColor(c.toString())));
					cardsToDiscoverField.set(stats, BaseMod.getCardCount(BaseMod.getColor(c.toString())));
						
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("could not set card counts for statistics");
				logger.error("error was: " + e.toString());
				e.printStackTrace();
			}
			
		}
		
	}
	
}
