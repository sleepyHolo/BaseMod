package basemod.patches.com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.ui.panels.EnergyPanel", method="ctor")
public class CtorSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	public static void Postfix(Object __obj_instance) {
		EnergyPanel energyPanel = (EnergyPanel) __obj_instance;
		AbstractPlayer.PlayerClass chosenClass = AbstractDungeon.player.chosenClass;
		if (chosenClass != AbstractPlayer.PlayerClass.IRONCLAD && chosenClass != AbstractPlayer.PlayerClass.THE_SILENT &&
				chosenClass != AbstractPlayer.PlayerClass.DEFECT) {
			try {
				Field gainEnergyImgField;
				gainEnergyImgField = energyPanel.getClass().getDeclaredField("gainEnergyImg");
				gainEnergyImgField.setAccessible(true);
				gainEnergyImgField.set(energyPanel, ImageMaster.loadImage("images/ui/topPanel/energyRedVFX.png"));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("could not set gain energy img for " + chosenClass.toString());
				logger.error("with exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
