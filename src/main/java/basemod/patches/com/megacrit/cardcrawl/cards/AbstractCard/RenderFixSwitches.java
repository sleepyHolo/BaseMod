package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.helpers.SuperclassFinder;

public class RenderFixSwitches {

	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderBannerImage")
	public static class RenderBannerSwitch {
		public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
		
		public static void Replace(Object __obj_instance, SpriteBatch sb, float drawX, float drawY) {
			AbstractCard card = (AbstractCard)__obj_instance;
			AbstractCard.CardRarity rarity = card.rarity;
			
			Texture bannerTexture = null;
			if (card instanceof CustomCard) {
				bannerTexture = ((CustomCard)card).getBannerSmallTexture();
			}
			if(bannerTexture == null) {
				switch(rarity.toString()) {
				case "BASIC":
				case "COMMON":
				case "CURSE":
					bannerTexture = ImageMaster.CARD_BANNER_COMMON;
					break;
				case "UNCOMMON":
					bannerTexture = ImageMaster.CARD_BANNER_UNCOMMON;
					break;
				case "RARE":
					bannerTexture = ImageMaster.CARD_BANNER_RARE;
					break;
					default:
						bannerTexture = ImageMaster.CARD_BANNER_COMMON;
				}
			}
			try {
				Method renderHelperMethod;
				Field renderColorField; 
				
				renderHelperMethod = SuperclassFinder.getSuperClassMethod(card.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
				renderHelperMethod.setAccessible(true);
				renderColorField = SuperclassFinder.getSuperclassField(card.getClass(), "renderColor");
				renderColorField.setAccessible(true);
				
				Color renderColor = (Color) renderColorField.get(card);
				renderHelperMethod.invoke(card, sb, renderColor, bannerTexture, drawX, drawY);
			}
			catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | SecurityException e) {
				logger.error("could not render banner for card " + card.getClass().toString() + " with color " + card.color.toString());
				logger.error("exception is: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderEnergy")
	public static class RenderEnergySwitch {
		public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
		
		@SpireInsertPatch(rloc = 26, localvars = { "drawX", "drawY" })
		public static void Insert(Object __obj_instance, Object sbObj, float drawX, float drawY) {
			AbstractCard card = (AbstractCard) __obj_instance;
			CardColor color = card.color;
			SpriteBatch sb = (SpriteBatch) sbObj;

			if (color != CardColor.RED && color != CardColor.GREEN && color != CardColor.BLUE
					&& color != CardColor.COLORLESS && color != CardColor.CURSE) {
				Texture orbTexture = null;
				try {
					if (card instanceof CustomCard) {
						orbTexture = ((CustomCard) card).getOrbSmallTexture();
					}
					if (orbTexture == null){
						Texture baseModTexture = BaseMod.getEnergyOrbTexture(color.toString());
						if (baseModTexture == null) {
							orbTexture = new Texture(BaseMod.getEnergyOrb(color.toString()));
							BaseMod.saveEnergyOrbTexture(color.toString(), orbTexture);
						} else {
							orbTexture = baseModTexture;
						}
					}
				} catch (NullPointerException e) {
					logger.error("could not load texture for energy orb for card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("exception is: " + e.getMessage());
					e.printStackTrace();
					orbTexture = ImageMaster.CARD_COLORLESS_ORB;
				}
				try {
					// use reflection hacks to invoke renderHelper (without float scale)
					Method renderHelperMethod;
					Field renderColorField; 
					
					
					renderHelperMethod = SuperclassFinder.getSuperClassMethod(card.getClass(), "renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
					renderHelperMethod.setAccessible(true);
					renderColorField = SuperclassFinder.getSuperclassField(card.getClass(), "renderColor");
					renderColorField.setAccessible(true);
					
						
					Color renderColor = (Color) renderColorField.get(card);
					renderHelperMethod.invoke(card, sb, renderColor, orbTexture, drawX, drawY);
				} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | SecurityException e) {
					logger.error("could not render energy for card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("exception is: " + e.getMessage());
					e.printStackTrace();
				}

			}

		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderOuterGlow")
	public static class RenderOuterGlowSwitch {
		public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
		
		@SpireInsertPatch(rloc = 9)
		public static void Insert(Object __obj_instance, Object sbObj) {
			AbstractCard card = (AbstractCard) __obj_instance;
			CardColor color = card.color;
			SpriteBatch sb = (SpriteBatch) sbObj;
			if (color != CardColor.RED && color != CardColor.GREEN && color != CardColor.BLUE
					&& color != CardColor.COLORLESS && color != CardColor.CURSE) {
				Color glowColor = BaseMod.getGlowColor(color.toString());
				if (glowColor == null) {
					glowColor = Color.WHITE;
				}
				try {
					// use reflection hacks to invoke renderHelper (with float scale)
					Field current_x;
					current_x = AbstractCard.class.getDeclaredField("current_x");
					current_x.setAccessible(true);
					Field current_y;
					current_y = AbstractCard.class.getDeclaredField("current_y");
					current_y.setAccessible(true);
					Field tintColor;
					tintColor = AbstractCard.class.getDeclaredField("tintColor");
					tintColor.setAccessible(true);
					Method renderHelperMethod = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class,
							Color.class, Texture.class, float.class, float.class, float.class);
					renderHelperMethod.setAccessible(true);
					renderHelperMethod.invoke(card, sb, glowColor, card.getCardBg(),
							((Float)current_x.get(card)) - 256.0f, ((Float)current_y.get(card)) - 256.0f, 1.0F + ((Color)tintColor.get(card)).a / 5.0f);
				} catch (Exception e) {
					logger.error("could not render outer glow for card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("with exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderAttackBg")
	public static class RenderAttackBgSwitch {
		public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
		
		// unfortunately due to the way the code for renderAttackBg is set up
		// it will in the default case for the switch statement render a default
		// background but I'm hoping that rendering over the same spot
		// will just cover up the default background

		public static void Postfix(Object __obj_instance, Object sbObj, float x, float y) {
			AbstractCard card = (AbstractCard) __obj_instance;
			CardColor color = card.color;
			SpriteBatch sb = (SpriteBatch) sbObj;

			if (color != CardColor.RED && color != CardColor.GREEN && color != CardColor.BLUE
					&& color != CardColor.COLORLESS && color != CardColor.CURSE) {
				Texture bgTexture = null;
				try {
					if (card instanceof CustomCard) {
						bgTexture = ((CustomCard) card).getBackgroundSmallTexture();
					}
					if (bgTexture == null) {
						Texture baseModTexture = BaseMod.getAttackBgTexture(color.toString());
						if (baseModTexture == null) {
							bgTexture = new Texture(BaseMod.getAttackBg(color.toString()));
							BaseMod.saveAttackBgTexture(color.toString(), bgTexture);
						} else {
							bgTexture = baseModTexture;
						}
					}
				} catch (NullPointerException e) {
					logger.error("could not load texture for attack bg on card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("exception is: " + e.getMessage());
					e.printStackTrace();
					bgTexture = ImageMaster.CARD_SKILL_BG_BLACK;
				}
				try {
					// use reflection hacks to invoke renderHelper (without float scale)
					Method renderHelperMethod = SuperclassFinder.getSuperClassMethod(card.getClass(), "renderHelper", SpriteBatch.class,
							Color.class, Texture.class, float.class, float.class);
					renderHelperMethod.setAccessible(true);
					Field renderColorField = SuperclassFinder.getSuperclassField(card.getClass(), "renderColor");
					renderColorField.setAccessible(true);
					Color renderColor = (Color) renderColorField.get(card);
					renderHelperMethod.invoke(card, sb, renderColor, bgTexture, x, y);
				} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | SecurityException e) {
					logger.error("could not set card attack bg on card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("exception is: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}


	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderPowerBg")
	public static class RenderPowerBgSwitch {
		public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
		
		// unfortunately due to the way the code for renderAttackBg is set up
		// it will in the default case for the switch statement render a default
		// background but I'm hoping that rendering over the same spot
		// will just cover up the default background
	
		public static void Postfix(Object __obj_instance, Object sbObj, float x, float y) {
			AbstractCard card = (AbstractCard) __obj_instance;
			CardColor color = card.color;
			SpriteBatch sb = (SpriteBatch) sbObj;
			
			if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
					&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
				Texture bgTexture = null;
				try {
					if (card instanceof CustomCard) {
						bgTexture = ((CustomCard) card).getBackgroundSmallTexture();
					}
					if (bgTexture == null) {
						Texture baseModTexture = BaseMod.getPowerBgTexture(color.toString());
						if (baseModTexture == null) {
							bgTexture = new Texture(BaseMod.getPowerBg(color.toString()));
							BaseMod.savePowerBgTexture(color.toString(), bgTexture);
						} else {
							bgTexture = baseModTexture;
						}
					}
				} catch (NullPointerException e) {
					logger.error("could not load texture for power bg on card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("exception is: " + e.getMessage());
					e.printStackTrace();
					bgTexture = ImageMaster.CARD_SKILL_BG_BLACK;
				}
				try {
					// use reflection hacks to invoke renderHelper (without float scale)
					Method renderHelperMethod = SuperclassFinder.getSuperClassMethod(card.getClass(), "renderHelper", SpriteBatch.class,
							Color.class, Texture.class, float.class, float.class);
					renderHelperMethod.setAccessible(true);
					Field renderColorField = SuperclassFinder.getSuperclassField(card.getClass(), "renderColor");
					renderColorField.setAccessible(true);
					Color renderColor = (Color) renderColorField.get(card);
					renderHelperMethod.invoke(card, sb, renderColor, bgTexture, x, y);
				} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | SecurityException e) {
					logger.error("could not set card skill bg on card " + card.getClass().toString() + " with color " + color.toString());
				}
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderSkillBg")
	public static class RenderSkillBgSwitch {
		public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
		
		// unfortunately due to the way the code for renderAttackBg is set up
		// it will in the default case for the switch statement render a default
		// background but I'm hoping that rendering over the same spot
		// will just cover up the default background

		public static void Postfix(Object __obj_instance, Object sbObj, float x, float y) {
			AbstractCard card = (AbstractCard) __obj_instance;
			CardColor color = card.color;
			SpriteBatch sb = (SpriteBatch) sbObj;
			
			if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
					&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
				Texture bgTexture = null;
				try {
					if (card instanceof CustomCard) {
						bgTexture = ((CustomCard) card).getBackgroundSmallTexture();
					}
					if (bgTexture == null) {
						Texture baseModTexture = BaseMod.getSkillBgTexture(color.toString());
						if (baseModTexture == null) {
							bgTexture = new Texture(BaseMod.getSkillBg(color.toString()));
							BaseMod.saveSkillBgTexture(color.toString(), bgTexture);
						} else {
							bgTexture = baseModTexture;
						}
					}
				} catch (NullPointerException e) {
					logger.error("could not load texture for skill bg on card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("exception is: " + e.getMessage());
					e.printStackTrace();
					bgTexture = ImageMaster.CARD_SKILL_BG_BLACK;
				}
				try {
					// use reflection hacks to invoke renderHelper (without float scale)
					Method renderHelperMethod = SuperclassFinder.getSuperClassMethod(card.getClass(), "renderHelper", SpriteBatch.class,
							Color.class, Texture.class, float.class, float.class);
					renderHelperMethod.setAccessible(true);
					Field renderColorField = SuperclassFinder.getSuperclassField(card.getClass(), "renderColor");
					renderColorField.setAccessible(true);
					Color renderColor = (Color) renderColorField.get(card);
					renderHelperMethod.invoke(card, sb, renderColor, bgTexture, x, y);
				} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | SecurityException e) {
					logger.error("could not set card skill bg on card " + card.getClass().toString() + " with color " + color.toString());
				}
			}
		}
	}
	
}
