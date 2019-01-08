package basemod.patches.com.megacrit.cardcrawl.screens.custom.CustomModeScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(
		clz=CustomModeScreen.class,
		method="updateCharacterButtons"
)
public class PositionCharacterButtons
{
	static final int MAX_BUTTONS_PER_ROW = 10;

	public static ExprEditor Instrument()
	{
		return new ExprEditor() {
			@Override
			public void edit(MethodCall m) throws CannotCompileException
			{
				if (m.getClassName().equals(CustomModeCharacterButton.class.getName()) && m.getMethodName().equals("update")) {
					m.replace("$_ = $proceed(" +
							PositionCharacterButtons.class.getName() + ".alterX(i, $1), " +
							PositionCharacterButtons.class.getName() + ".alterY(i, $2));"
					);
				}
			}
		};
	}

	@SuppressWarnings("unused")
	public static float alterX(int i, float x)
	{
		if (i >= MAX_BUTTONS_PER_ROW) {
			int rows = i / MAX_BUTTONS_PER_ROW;
			x -= (MAX_BUTTONS_PER_ROW * rows) * 100 * Settings.scale;
		}
		return x;
	}

	@SuppressWarnings("unused")
	public static float alterY(int i, float y)
	{
		if (i >= MAX_BUTTONS_PER_ROW) {
			int rows = i / MAX_BUTTONS_PER_ROW;
			return y - rows * 100 * Settings.scale;
		}
		return y;
	}
}
