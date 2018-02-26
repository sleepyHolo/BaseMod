package basemod.patches.com.megacrit.cardcrawl.potions.Color; 
 
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch; 
import com.megacrit.cardcrawl.potions.AbstractPotion; 
 
import basemod.BaseMod; 
 
@SpirePatch( 
    cls="com.megacrit.cardcrawl.potions.AbstractPotion", 
    method="initializeColor" 
) 
public class AbstractPotionInitializeColor { 
   
  public static void Postfix(Object __object__instance) { 
    AbstractPotion potion = (AbstractPotion) __object__instance; 
    if(BaseMod.getPotionLiquidColor(potion.ID)!=null) { 
      potion.liquidColor=BaseMod.getPotionLiquidColor(potion.ID); 
      potion.liquidColor=BaseMod.getPotionLiquidColor(potion.ID); 
      potion.liquidColor=BaseMod.getPotionLiquidColor(potion.ID); 
    } 
  } 
} 