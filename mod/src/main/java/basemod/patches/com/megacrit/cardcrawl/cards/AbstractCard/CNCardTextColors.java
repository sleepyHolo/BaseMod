package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SpirePatch(
        clz=AbstractCard.class,
        method="initializeDescriptionCN"
)
public class CNCardTextColors {
    private static float widthLimit = 0.0f;
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"word", "currentWidth", "numLines", "currentLine"}
    )
    public static void Insert(AbstractCard __instance, @ByRef String[] word, @ByRef float[] currentWidth, @ByRef int[] numLines, @ByRef StringBuilder[] currentLine) {
        if (word[0].startsWith("[#") && word[0].endsWith("[]")) {
            if (widthLimit == 0.0f) {
                try {
                    Field CN_DESC_BOX_WIDTH_FIELD = AbstractCard.class.getDeclaredField("CN_DESC_BOX_WIDTH");
                    CN_DESC_BOX_WIDTH_FIELD.setAccessible(true);
                    widthLimit = (float) CN_DESC_BOX_WIDTH_FIELD.get(null);

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            String wordTrim = word[0].substring(10, word[0].length() - 2);
            float wordWidth = new GlyphLayout(FontHelper.cardDescFont_N, wordTrim).width;
            if (currentWidth[0] + wordWidth > widthLimit) {
                ++numLines[0];
                __instance.description.add(new DescriptionLine(currentLine[0].toString(), currentWidth[0]));
                currentLine[0] = new StringBuilder();
                currentWidth[0] = wordWidth;
                currentLine[0].append(word[0]);
            } else {
                currentWidth[0] += wordWidth;
                currentLine[0].append(word[0]);
            }
            word[0] = "";
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(String.class, "trim");

            return new int[] {LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]};
        }
    }
}
