package basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.BaseMod;

public class BackgroundFix {

	@SpirePatch(cls="com.megacrit.cardcrawl.screens.SingleCardViewPopup",
			method="renderCardBack")
	public static class BackgroundTexture {
		public static void Postfix(Object __obj_instance, Object sbObject) {
			try {
				SingleCardViewPopup popup = (SingleCardViewPopup) __obj_instance;
				SpriteBatch sb = (SpriteBatch) sbObject;
				Field cardField;
				cardField = popup.getClass().getDeclaredField("card");
				cardField.setAccessible(true);
				AbstractCard card = (AbstractCard) cardField.get(popup);
				AbstractCard.CardColor color = card.color;
				switch (card.type) {
				case ATTACK:
					if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
							&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
						Texture bgTexture = BaseMod.getAttackBgPortraitTexture(color.toString());
						if (bgTexture == null) {
							bgTexture = new Texture(BaseMod.getAttackBgPortrait(color.toString()));
							BaseMod.saveAttackBgPortraitTexture(color.toString(), bgTexture);
						}
						sb.draw(bgTexture, Settings.WIDTH / 2.0F - 512.0F, Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
					}
					break;
				case POWER:
					if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
							&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
						Texture bgTexture = BaseMod.getPowerBgPortraitTexture(color.toString());
						if (bgTexture == null) {
							bgTexture = new Texture(BaseMod.getPowerBgPortrait(color.toString()));
							BaseMod.savePowerBgPortraitTexture(color.toString(), bgTexture);
						}
						sb.draw(bgTexture, Settings.WIDTH / 2.0F - 512.0F, Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
					}
					break;
				default:
					if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
							&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
						Texture bgTexture = BaseMod.getSkillBgPortraitTexture(color.toString());
						if (bgTexture == null) {
							bgTexture = new Texture(BaseMod.getSkillBgPortrait(color.toString()));
							BaseMod.saveSkillBgPortraitTexture(color.toString(), bgTexture);
						}
						sb.draw(bgTexture, Settings.WIDTH / 2.0F - 512.0F, Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
					}
					break;
				}
			} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.screens.SingleCardViewPopup",
			method="renderCost")
	public static class EnergyOrbTexture {
		@SpireInsertPatch(rloc=70)
		public static void Insert(Object __obj_instance, Object sbObject) {
			try {
				SingleCardViewPopup popup = (SingleCardViewPopup) __obj_instance;
				SpriteBatch sb = (SpriteBatch) sbObject;
				Field cardField;
				cardField = popup.getClass().getDeclaredField("card");
				cardField.setAccessible(true);
				AbstractCard card = (AbstractCard) cardField.get(popup);
				AbstractCard.CardColor color = card.color;
				if (card.cost > -2) {
					if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
							&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
						Texture orbTexture = BaseMod.getEnergyOrbPortraitTexture(color.toString());
						if (orbTexture == null) {
							orbTexture = new Texture(BaseMod.getEnergyOrbPortrait(color.toString()));
							BaseMod.saveEnergyOrbPortraitTexture(color.toString(), orbTexture);
						}
						
						sb.draw(orbTexture, Settings.WIDTH / 2.0F - 82.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F - 82.0F + 380.0F * Settings.scale, 82.0F, 82.0F, 164.0F, 164.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 164, 164, false, false);
					}
				}
			} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
