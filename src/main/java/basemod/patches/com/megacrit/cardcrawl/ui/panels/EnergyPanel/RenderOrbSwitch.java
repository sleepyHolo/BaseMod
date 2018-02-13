package basemod.patches.com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.ui.panels.EnergyPanel", method="renderOrb")
public class RenderOrbSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	public static void Postfix(Object __obj_instance, SpriteBatch sb) {
		EnergyPanel energyPanel = (EnergyPanel) __obj_instance;
		AbstractPlayer.PlayerClass chosenClass = AbstractDungeon.player.chosenClass;
		if (!chosenClass.toString().equals("IRONCLAD") && !chosenClass.toString().equals("THE_SILENT") &&
				!chosenClass.toString().equals("CROWBOT")) {
			
			try {
				Field totalCountField;
				totalCountField = energyPanel.getClass().getDeclaredField("totalCount");
				totalCountField.setAccessible(true);
				int totalCount = totalCountField.getInt(energyPanel);
				if (totalCount == 0) {
					Method renderRedOrbDisabled = energyPanel.getClass().getDeclaredMethod("renderRedOrbDisabled", SpriteBatch.class);
					renderRedOrbDisabled.setAccessible(true);
					renderRedOrbDisabled.invoke(energyPanel, sb);
				} else {
					Method renderRedOrb = energyPanel.getClass().getDeclaredMethod("renderRedOrb", SpriteBatch.class);
					renderRedOrb.setAccessible(true);
					renderRedOrb.invoke(energyPanel, sb);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				logger.error("could not render orb for " + chosenClass.toString());
				logger.error("with exception: " + e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
}
