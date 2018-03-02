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
import basemod.patches.com.megacrit.cardcrawl.helpers.EnergyOrbRender.EnergyOrbRenderHelper;

@SpirePatch(cls="com.megacrit.cardcrawl.ui.panels.EnergyPanel", method="render")
public class RenderOrbSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	public static void Prefix(Object __obj_instance, SpriteBatch sb) {
		EnergyPanel energyPanel = (EnergyPanel) __obj_instance;
		AbstractPlayer.PlayerClass chosenClass = AbstractDungeon.player.chosenClass;
		if (!chosenClass.toString().equals("IRONCLAD") && !chosenClass.toString().equals("THE_SILENT") &&
				!chosenClass.toString().equals("CROWBOT")) {
			
			try {
				Field totalCountField;
				totalCountField = energyPanel.getClass().getDeclaredField("totalCount");
				totalCountField.setAccessible(true);
				int totalCount = totalCountField.getInt(energyPanel);
				
				EnergyOrbRenderHelper.render(energyPanel, sb);
				
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException /*| NoSuchMethodException | InvocationTargetException */ e) {
				logger.error("could not render orb for " + chosenClass.toString());
				logger.error("with exception: " + e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
}
