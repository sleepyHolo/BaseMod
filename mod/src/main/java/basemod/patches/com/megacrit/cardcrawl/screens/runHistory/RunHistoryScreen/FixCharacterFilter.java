package basemod.patches.com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.stats.RunData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class FixCharacterFilter
{
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());

	private static final int ALL_CHARACTERS_TEXT = 23;
	
	@SpirePatch(
			clz=RunHistoryScreen.class,
			method="refreshData"
	)
	public static class RefreshData
	{
		public static void Postfix(RunHistoryScreen __instance)
		{
			ArrayList<String> charFilterOptions = new ArrayList<>();
			charFilterOptions.add(RunHistoryScreen.TEXT[ALL_CHARACTERS_TEXT]);
			for (AbstractPlayer character : CardCrawlGame.characterManager.getAllCharacters()) {
				charFilterOptions.add(character.getLocalizedCharacterName());
			}
			String[] optionsAsArray = new String[charFilterOptions.size()];
			for (int i = 0; i < charFilterOptions.size(); i++) {
				optionsAsArray[i] = charFilterOptions.get(i);
			}
			
			try {
				Field characterFilterField = RunHistoryScreen.class.getDeclaredField("characterFilter");
				characterFilterField.setAccessible(true);
				characterFilterField.set(__instance, new DropdownMenu(__instance, optionsAsArray, FontHelper.cardDescFont_N, Settings.CREAM_COLOR));
				
				Method resetRunsDropdown = RunHistoryScreen.class.getDeclaredMethod("resetRunsDropdown");
				resetRunsDropdown.setAccessible(true);
				resetRunsDropdown.invoke(__instance);
			} catch (Exception e) {
				logger.error("could not fix character filter for run history screen");
			}
		}
		
	}
	
	@SpirePatch(
			clz=RunHistoryScreen.class,
			method="characterText"
	)
	public static class CharacterText
	{
		public static String Postfix(String __result, RunHistoryScreen __instance, String chosenCharacter)
		{
			AbstractPlayer.PlayerClass playerClass = AbstractPlayer.PlayerClass.valueOf(chosenCharacter);
			if (!BaseMod.isBaseGameCharacter(playerClass)) {
				AbstractPlayer character = BaseMod.findCharacter(playerClass);
				if (character != null) {
					return character.getLocalizedCharacterName();
				}
			}
			
			return __result;
		}
	}
	
	@SpirePatch(
			clz=RunHistoryScreen.class,
			method="resetRunsDropdown"
	)
	public static class ResetRunsDropdown
	{
		@SpireInsertPatch(
				rloc=34,
				localvars={"includeMe", "data"}
		)
		public static void Insert(RunHistoryScreen __instance, @ByRef boolean[] includeMe, RunData data)
		{
			try {
				Field characterFilterField = RunHistoryScreen.class.getDeclaredField("characterFilter");
				characterFilterField.setAccessible(true);
			
				int selectedIndex = ((DropdownMenu) characterFilterField.get(__instance)).getSelectedIndex();

				if (selectedIndex > 0) {
					AbstractPlayer.PlayerClass compareTo = CardCrawlGame.characterManager.getAllCharacters().get(selectedIndex - 1).chosenClass;
					if (compareTo != null) {
						String runCharacter = data.character_chosen;
						includeMe[0] = includeMe[0] && (runCharacter.equals(compareTo.name()));
					}
				}
			} catch (Exception e) {
				logger.error("unable to filter characters on run history screen");
			}
		}
	}
}
