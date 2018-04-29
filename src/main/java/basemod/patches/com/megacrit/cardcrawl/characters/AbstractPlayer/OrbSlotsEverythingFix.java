package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;

import basemod.helpers.SuperclassFinder;

public class OrbSlotsEverythingFix {
	@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="preBattlePrep")
	public static class PreBattlePrepFix {
		@SpireInsertPatch(rloc=11, localvars= {})
		public static void Insert(AbstractPlayer player) {
			if(player.masterMaxOrbs > 0 && player.chosenClass != PlayerClass.DEFECT) {
				player.maxOrbs = 0;
				player.orbs.clear();
				player.increaseMaxOrbSlots(player.masterMaxOrbs);
			}
		}
	}
	
	
	@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="render")
	public static class RenderFix {
		@SpireInsertPatch(rloc=4, localvars={})
		public static void Insert(AbstractPlayer player, SpriteBatch sb) {
			if(player.masterMaxOrbs > 0 && player.chosenClass != PlayerClass.DEFECT) {
				for(AbstractOrb o : player.orbs) {
					o.render(sb);
				}
			}
		}
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="applyStartOfTurnOrbs")
	public static class ApplyStartOfTurnOrbsFix {
		public static void Prefix(AbstractPlayer player) {
			if(player.masterMaxOrbs > 0 && player.chosenClass != PlayerClass.DEFECT) {
				for(AbstractOrb o : player.orbs) {
					o.onStartOfTurn();
				}
			}
		}
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.ui.buttons.EndTurnButton", method="disable")
	public static class DisableFix {
		public static void Prefix(EndTurnButton meObj, boolean isEnemyTurn) {
			if(AbstractDungeon.player.masterMaxOrbs > 0 && AbstractDungeon.player.chosenClass != PlayerClass.DEFECT) {
				for(AbstractOrb o : AbstractDungeon.player.orbs) {
					o.onEndOfTurn();
				}
			}
		}
	}
	
	//This might be able to be done better but it works so i'm leaving it.
	@SpirePatch(cls="com.megacrit.cardcrawl.orbs.AbstractOrb", method="setSlot")
	public static class SetSlotFix {
		public static void Replace(AbstractOrb meObj, int slotNum, int maxOrbs) {
			float dist = 160.0F * Settings.scale + maxOrbs * 10.0F * Settings.scale;
		    float angle = 100.0F + maxOrbs * 12.0F;
		    float offsetAngle = angle / 2.0F;
		    
		    if(maxOrbs < 2) {
		    	angle *= 1 / (3 - 1.0f);
		    }else {
		    	angle *= slotNum / (maxOrbs - 1.0F);
		    }
		    angle += 90.0F - offsetAngle;
		    meObj.tX = (dist * MathUtils.cosDeg(angle) + AbstractDungeon.player.drawX);
		    meObj.tY = (dist * MathUtils.sinDeg(angle) + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F);
		   
		    Field hitBoxField;
			try {
				hitBoxField = SuperclassFinder.getSuperclassField(meObj.getClass(), "hb");
				hitBoxField.setAccessible(true);
				Hitbox hb = (Hitbox) hitBoxField.get(meObj);
		    	hb.move(meObj.tX, meObj.tY);
			} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		    
		    
		}
	}
}
