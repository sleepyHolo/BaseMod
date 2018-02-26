package basemod.patches.com.megacrit.cardcrawl.helpers.PotionLibrary; 
 
 
 
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch; 
 
 
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger; 
 
import com.megacrit.cardcrawl.potions.*; 
 
import basemod.BaseMod; 
 
@SpirePatch( 
    cls="com.megacrit.cardcrawl.helpers.PotionHelper", 
    method="getPotion" 
) 
public class PotionHelperGetPotion { 
   
  private static final Logger logger = LogManager.getLogger(PotionHelperGetPotion.class.getName()); 
   
   
  public static Object Postfix(Object __result, String potionID) 
  { 
	if(__result==null) { 
	      return null; 
	} 
    if (!(potionID.equals("Block Potion")||potionID.equals("Regen Potion")||potionID.equals("Dexterity Potion") 
        ||potionID.equals("Ancient Potion")||potionID.equals("Energy Potion")||potionID.equals("Explosive Potion") 
        ||potionID.equals("Fire Potion")||potionID.equals("Health Potion")||potionID.equals("Strength Potion") 
        ||potionID.equals("Swift Potion")||potionID.equals("Poison Potion")||potionID.equals("Weak Potion"))) { 
    	logger.info("Getting custom potion: "+potionID); 
    	try { 
    		Class potionClass = BaseMod.getPotionClass(potionID); 
    		return potionClass.newInstance(); 
    	} catch (Exception e) { 
    		logger.warn(e.getMessage()); 
    		return new FirePotion(); 
      } 
    } 
    else { 
      return __result; 
    } 
  } 
   
}