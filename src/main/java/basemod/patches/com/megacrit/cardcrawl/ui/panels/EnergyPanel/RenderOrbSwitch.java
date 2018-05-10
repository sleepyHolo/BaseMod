package basemod.patches.com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.helpers.EnergyOrbRender.EnergyOrbRenderHelper;

@SpirePatch(cls="com.megacrit.cardcrawl.ui.panels.EnergyPanel", method="render")
public class RenderOrbSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	public static void Prefix(Object __obj_instance, SpriteBatch sb) {
		EnergyPanel energyPanel = (EnergyPanel) __obj_instance;
		AbstractPlayer.PlayerClass chosenClass = AbstractDungeon.player.chosenClass;
		if (!BaseMod.isBaseGameCharacter(chosenClass)) {
			try {				
				EnergyOrbRenderHelper.render(energyPanel, sb);
				
			} catch (SecurityException | IllegalArgumentException e) {
				logger.error("could not render orb for " + chosenClass.toString());
				logger.error("with exception: " + e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
}
