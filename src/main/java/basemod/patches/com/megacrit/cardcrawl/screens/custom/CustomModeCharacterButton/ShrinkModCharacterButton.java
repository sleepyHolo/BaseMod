package basemod.patches.com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(
		cls="com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton",
		method="renderOptionButton"
)
public class ShrinkModCharacterButton
{
	public static ExprEditor Instrument()
	{
		// Resize and reposition the modded character buttons to fit better with the vanilla buttons
		return new ExprEditor() {
			@Override
			public void edit(MethodCall m) throws CannotCompileException {
				if (m.getClassName().equals("com.badlogic.gdx.graphics.g2d.SpriteBatch") && m.getMethodName().equals("draw")) {
					m.replace(
							"if ($1 == this.buttonImg && !basemod.BaseMod.isBaseGameCharacter(this.c)) {" +
									"$2 = this.hb.cX - 107.5f;" +
									"$3 = this.y - 112.5f;" +
									"$4 = $5 = 110.0f;" +
									"$6 = $7 = 220.0f;" +
									"$13 = $14 = 220;" +
									"$8 *= 0.45f;" +
									"$9 *= 0.45f;" +
									"$_ = $proceed($$);" +
									"} else {" +
									"$_ = $proceed($$);" +
									"}"
					);
				}
			}
		};
	}
}
