package basemod.patches.com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls="com.megacrit.cardcrawl.helpers.FontHelper",
        method="identifyOrb"
)
public class IdentifyOrb
{
    public static TextureAtlas.AtlasRegion Postfix(TextureAtlas.AtlasRegion __result, String word)
    {
        if (__result == null && word.equals("[E]")) {
            return BaseMod.getCardSmallEnergy();
        }
        return __result;
    }
}
