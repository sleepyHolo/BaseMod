package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import basemod.BaseMod;
import basemod.abstracts.CustomCardWithRender;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class CreateCardImageSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "createCardImage")
	public static class FixTexturesAndColors {
		
		@SpireInsertPatch(rloc = 41)
		public static void Insert(Object __obj_instance) {
			AbstractCard card = (AbstractCard) __obj_instance;
			CardColor color = card.color;
			if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
					&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
				try {
					if(card instanceof CustomCardWithRender) {
						Field bgColor;		
						bgColor = card.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("bgColor");
						bgColor.setAccessible(true);
						bgColor.set(card, BaseMod.getBgColor(color.toString()));
						Field backColor;
						backColor = card.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("backColor");
						backColor.setAccessible(true);
						backColor.set(card, BaseMod.getBackColor(color.toString()));
						Field frameColor = card.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("frameColor");
						frameColor.setAccessible(true);
						frameColor.set(card, BaseMod.getFrameColor(color.toString()));
						Field frameOutlineColor = card.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("frameOutlineColor");
						frameOutlineColor.setAccessible(true);
						frameOutlineColor.set(card, BaseMod.getFrameOutlineColor(color.toString()));
						Field descBoxColor = card.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("descBoxColor");
						descBoxColor.setAccessible(true);
						descBoxColor.set(card, BaseMod.getDescBoxColor(color.toString()));
					} else {
						Field bgColor;		
						bgColor = card.getClass().getSuperclass().getSuperclass().getDeclaredField("bgColor");
						bgColor.setAccessible(true);
						bgColor.set(card, BaseMod.getBgColor(color.toString()));
						Field backColor;
						backColor = card.getClass().getSuperclass().getSuperclass().getDeclaredField("backColor");
						backColor.setAccessible(true);
						backColor.set(card, BaseMod.getBackColor(color.toString()));
						Field frameColor = card.getClass().getSuperclass().getSuperclass().getDeclaredField("frameColor");
						frameColor.setAccessible(true);
						frameColor.set(card, BaseMod.getFrameColor(color.toString()));
						Field frameOutlineColor = card.getClass().getSuperclass().getSuperclass().getDeclaredField("frameOutlineColor");
						frameOutlineColor.setAccessible(true);
						frameOutlineColor.set(card, BaseMod.getFrameOutlineColor(color.toString()));
						Field descBoxColor = card.getClass().getSuperclass().getSuperclass().getDeclaredField("descBoxColor");
						descBoxColor.setAccessible(true);
						descBoxColor.set(card, BaseMod.getDescBoxColor(color.toString()));
					}
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					logger.error("could not set card image properties on card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("with exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "createCardImage")
	public static class FixLogOutput {
		
		public static ExprEditor Instrument()
		{
		    return new ExprEditor() {
		        @Override
		        public void edit(MethodCall m) throws CannotCompileException
		        {
		            if (m.getMethodName().equals("info")) {
		                m.replace("");
		            }
		        }
		    };
		}
		
	}
	
}
