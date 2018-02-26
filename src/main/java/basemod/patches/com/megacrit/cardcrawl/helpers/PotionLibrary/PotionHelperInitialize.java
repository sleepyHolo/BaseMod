package basemod.patches.com.megacrit.cardcrawl.helpers.PotionLibrary; 
 
 
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch; 
import com.megacrit.cardcrawl.helpers.PotionHelper; 
 
import basemod.BaseMod; 
 
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger; 
 
@SpirePatch( 
    cls="com.megacrit.cardcrawl.helpers.PotionHelper", 
    method="initialize" 
) 
public class PotionHelperInitialize { 
   
  private static final Logger logger = LogManager.getLogger(PotionHelperInitialize.class.getName()); 
   
  public static void Postfix(){ 
    for(String potionID:BaseMod.getPotionIDs()) { 
      logger.info("Adding "+ potionID); 
      PotionHelper.potions.add(potionID); 
    } 
  } 
} 