package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen", method="initialize")
public class InitializeSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpireInsertPatch(rloc=13)
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
}
