package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen", method="initialize")
public class InitializeSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpireInsertPatch
	public static void Insert(Object __obj_instance) {
		logger.info("modifying character select screen");
		CharacterSelectScreen screen = (CharacterSelectScreen) __obj_instance;
		try {
			Field optionsField;
			optionsField = screen.getClass().getDeclaredField("options");
			optionsField.setAccessible(true);
			@SuppressWarnings("unchecked")
			ArrayList<CharacterOption> options = (ArrayList<CharacterOption>) optionsField.get(screen);
			for (CharacterOption option : BaseMod.generateCharacterOptions()) {
				logger.info("adding option " + option.name);
				options.add(option);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("could not add characters to character select screen");
			logger.error("with exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static class Locator extends SpireInsertLocator
	{
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
		{
			Matcher finalMatcher = new Matcher.MethodCallMatcher(
					"com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen", "positionButtons");

			return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
		}
	}
}
