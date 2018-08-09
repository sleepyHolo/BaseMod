package basemod.patches.com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(
		cls="com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton",
		method=SpirePatch.CONSTRUCTOR
)
public class CtorSwitch
{
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());

	public static void Postfix(CustomModeCharacterButton __instance, AbstractPlayer.PlayerClass c, boolean locked)
	{
		if (!BaseMod.isBaseGameCharacter(c)) {
			try {
				Field charStringsField = CustomModeCharacterButton.class.getDeclaredField("charStrings");
				charStringsField.setAccessible(true);

				Class<?> characterClass = BaseMod.getPlayerClass(c);
				Method getLoadout = characterClass.getMethod("getLoadout");
				CharSelectInfo charInfo = (CharSelectInfo) getLoadout.invoke(null);

				CharacterStrings charStrings = new CharacterStrings();
				charStrings.NAMES = new String[]{charInfo.name};
				charStrings.TEXT = new String[]{charInfo.flavorText};

				charStringsField.set(__instance, charStrings);
			} catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				logger.error("could not create character loadout for " + c.toString());
				logger.error("with exception: " + e.getMessage());
				e.printStackTrace();
			}

			try {
				// fix texture loading
				Field buttonImgField = CustomModeCharacterButton.class.getDeclaredField("buttonImg");
				buttonImgField.setAccessible(true);
				buttonImgField.set(__instance, new Texture(BaseMod.getPlayerButton(c)));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("could not create character select button for " + c.toString());
				logger.error("with exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
