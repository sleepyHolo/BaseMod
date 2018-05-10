package basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.stats.StatsScreen", method="refreshData")
public class AddCustomCharacters {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	public static void Replace(Object __obj_instance) {
		StatsScreen screen = (StatsScreen) __obj_instance;
		try {
			Field ironcladField = screen.getClass().getDeclaredField("ironclad");
			Field silentField = screen.getClass().getDeclaredField("silent");
			Field defectField = screen.getClass().getDeclaredField("defect");
			Field allField = screen.getClass().getDeclaredField("all");
			Field achievementsField = screen.getClass().getDeclaredField("achievements");
			ironcladField.setAccessible(true);
			silentField.setAccessible(true);
			defectField.setAccessible(true);
			allField.setAccessible(true);
			achievementsField.setAccessible(true);
			ironcladField.set(screen, new CharStat(AbstractPlayer.PlayerClass.IRONCLAD));
			silentField.set(screen, new CharStat(AbstractPlayer.PlayerClass.THE_SILENT));
			defectField.set(screen, new CharStat(AbstractPlayer.PlayerClass.DEFECT));
			ArrayList<CharStat> allChars = new ArrayList<>();
			allChars.add((CharStat) ironcladField.get(screen));
			allChars.add((CharStat) silentField.get(screen));
			
			for (CharStat stat : BaseMod.generateCharacterStats()) {
				allChars.add(stat);
			}
			
			allField.set(screen, new CharStat(allChars));
			achievementsField.set(screen, new AchievementGrid());
			Settings.totalPlayTime = ((CharStat) allField.get(screen)).playTime;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("failed to setup stats screen");
			logger.error("error was: " + e.toString());
			e.printStackTrace();
		}
	}
	
}
