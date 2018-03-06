package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import basemod.BaseMod;
import basemod.abstracts.CustomCardWithRender;
import basemod.helpers.SuperclassFinder;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderAttackBg")
public class RenderAttackBgSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	// unfortunately due to the way the code for renderAttackBg is set up
	// it will in the default case for the switch statement render a default
	// background but I'm hoping that rendering over the same spot
	// will just cover up the default background

	public static void Postfix(Object __obj_instance, Object sbObj, float x, float y) {
		AbstractCard card = (AbstractCard) __obj_instance;
		CardColor color = card.color;
		SpriteBatch sb = (SpriteBatch) sbObj;
		
		//This checks whether or not the card that is being rendered is a CustomCardWithRender and uses that cards render method to render the new background color
		if(card instanceof CustomCardWithRender) {
			CustomCardWithRender custCard = (CustomCardWithRender)card;
			
			custCard.renderCard(sb, x, y);
		}else {
			if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
					&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
				Texture bgTexture;
				try {
					Texture baseModTexture = BaseMod.getAttackBgTexture(color.toString());
					if (baseModTexture == null) {
						bgTexture = new Texture(BaseMod.getAttackBg(color.toString()));
						BaseMod.saveAttackBgTexture(color.toString(), bgTexture);
					} else {
						bgTexture = baseModTexture;
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
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
					logger.error("could not set card attack bg on card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("exception is: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
}
