package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

@SpirePatch(
        cls="com.megacrit.cardcrawl.cards.AbstractCard",
        method="renderDynamicVariable"
)
public class RenderCustomDynamicVariable
{
    private static Logger logger = LogManager.getLogger();
    private static final GlyphLayout gl = new GlyphLayout();

    public static float Replace(AbstractCard __instance, char key, float start_x, float draw_y, int i, BitmapFont font, SpriteBatch sb, Character end)
    {
        // Get any private variables we need
        Color textColor = Settings.CREAM_COLOR;
        try {
            Field f = AbstractCard.class.getDeclaredField("textColor");
            f.setAccessible(true);
            textColor = (Color)f.get(__instance);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        // Main body of method
        StringBuilder stringBuilder = new StringBuilder();
        Color c = null;
        int num = 0;
        DynamicVariable dv = BaseMod.cardDynamicVariableMap.get(Character.toString(key));
        if (dv != null) {
            if (dv.isModified(__instance)) {
                num = dv.value(__instance);
                if (num >= dv.baseValue(__instance)) {
                    c = Settings.GREEN_TEXT_COLOR;
                } else {
                    c = Settings.RED_TEXT_COLOR;
                }
            } else {
                c = textColor;
                num = dv.baseValue(__instance);
            }
        } else {
            logger.error("No dynamic card variable found for key \"" + key + "\"!");
            c = textColor;
        }
        stringBuilder.append(num);
        gl.setText(font, stringBuilder.toString());
        FontHelper.renderRotatedText(sb, font, stringBuilder.toString(),
                __instance.current_x, __instance.current_y,
                start_x - __instance.current_x + gl.width / 2.0f,
                i * 1.45f * -font.getCapHeight() + draw_y - __instance.current_y + -6.0f,
                __instance.angle, true, c);
        if (end != null) {
            FontHelper.renderRotatedText(sb, font, Character.toString(end),
                    __instance.current_x, __instance.current_y,
                    start_x - __instance.current_x + gl.width + 4.0f * Settings.scale,
                    i * 1.45f * -font.getCapHeight() + draw_y - __instance.current_y + -6.0f,
                    0.0f, true, Settings.CREAM_COLOR);
            stringBuilder.append(end);
        }
        stringBuilder.append(' ');
        gl.setText(font, stringBuilder.toString());
        return gl.width;
    }
}
