package basemod.patches.com.megacrit.cardcrawl.vfx.CardTrailEffect;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.vfx.CardTrailEffect", method="ctor")
public class CtorSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	public static void Postfix(Object __obj_instance, float x, float y) {
		CardTrailEffect trailEffect = (CardTrailEffect) __obj_instance;
		AbstractPlayer.PlayerClass chosenClass = AbstractDungeon.player.chosenClass;
		if (!chosenClass.toString().equals("IRONCLAD") && !chosenClass.toString().equals("THE_SILENT") &&
				!chosenClass.toString().equals("CROWBOT")) {
			try {
				Field colorField;
				colorField = trailEffect.getClass().getSuperclass().getDeclaredField("color");
				colorField.setAccessible(true);
				colorField.set(trailEffect, new Color(1.0F, 0.4F, 0.1F, 1.0F));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("could not set card trail effect color for " + chosenClass.toString());
				logger.error("with exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
