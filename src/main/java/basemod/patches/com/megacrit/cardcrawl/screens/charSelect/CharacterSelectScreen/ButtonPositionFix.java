package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import basemod.BaseMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen", method = "positionButtons")
public class ButtonPositionFix {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());

	public static void Replace(Object __obj_instance) {
		CharacterSelectScreen screen = (CharacterSelectScreen) __obj_instance;
		try {
			Field optionsField;
			optionsField = screen.getClass().getDeclaredField("options");
			optionsField.setAccessible(true);
			@SuppressWarnings("unchecked")
			ArrayList<CharacterOption> options = (ArrayList<CharacterOption>) optionsField.get(screen);
			
			int count = options.size();
			System.out.println("Fixing character select offsets");
			float offsetX = Settings.WIDTH / 2.0F - count / 2.0F * 220.0F * Settings.scale +
					(count % 2 == 0 ? 0.5F * 220.0F * Settings.scale : 0);
			for (int i = 0; i < count; i++) {
				((CharacterOption) options.get(i)).move(offsetX + i * 220.0F * Settings.scale,
						190.0F * Settings.scale);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("could not setup character buttons on character select screen");
			logger.error("with exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
