package basemod.patches.com.megacrit.cardcrawl.screens.options.OptionsPanel;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch2(
		clz = OptionsPanel.class,
		method = "refresh"
)
public class FixLanguageDropdownSizeInRun
{
	public static ExprEditor Instrument()
	{
		return new ExprEditor() {
			@Override
			public void edit(FieldAccess f) throws CannotCompileException
			{
				if (f.isWriter() && f.getClassName().equals(OptionsPanel.class.getName()) && f.getFieldName().equals("languageDropdown")) {
					f.replace("");
				}
			}
		};
	}
}
