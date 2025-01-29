package basemod.patches.com.megacrit.cardcrawl.screens.options.OptionsPanel;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;
import com.megacrit.cardcrawl.screens.options.ToggleButton;
import javassist.*;
import javassist.bytecode.Descriptor;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.NewExpr;

import java.util.ArrayList;
import java.util.List;

@SpirePatch2(
		clz = OptionsPanel.class,
		method = "refresh"
)
public class FixToggleHitboxSizeInRun
{
	public static void Raw(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException
	{
		ClassPool pool = ctBehavior.getDeclaringClass().getClassPool();
		CtClass ctOptionsPanel = ctBehavior.getDeclaringClass();
		CtClass ctToggleButton = pool.get(ToggleButton.class.getName());

		CtMethod ctRefresh = CtNewMethod.make(
				CtClass.voidType,
				"refresh",
				null,
				null,
				"enabled = getPref();",
				ctToggleButton
		);
		ctToggleButton.addMethod(ctRefresh);

		CtConstructor optionsCtor = ctOptionsPanel.getDeclaredConstructor(null);
		CtMethod optionsRefresh = ctOptionsPanel.getDeclaredMethod("refresh");

		List<Integer> lineNumbers = new ArrayList<>();
		List<String> fieldNames = new ArrayList<>();

		// Find which toggles are large in the constructor
		optionsCtor.instrument(new ExprEditor() {
			@Override
			public void edit(NewExpr e) throws CannotCompileException
			{
				if (e.getClassName().equals(ToggleButton.class.getName()) && Descriptor.numOfParameters(e.getSignature()) == 5) {
					lineNumbers.add(e.getLineNumber());
				}
			}

			@Override
			public void edit(FieldAccess f) throws CannotCompileException
			{
				try {
					if (f.isWriter() && f.getClassName().equals(OptionsPanel.class.getName()) && f.getField().getType().getName().equals(ToggleButton.class.getName())) {
						if (lineNumbers.contains(f.getLineNumber())) {
							fieldNames.add(f.getFieldName());
						}
					}
				} catch (NotFoundException e) {
					throw new CannotCompileException(e);
				}
			}
		});

		lineNumbers.clear();

		// Find line numbers of toggle fields that should be large
		optionsRefresh.instrument(new ExprEditor() {
			@Override
			public void edit(FieldAccess f) throws CannotCompileException
			{
				try {
					if (f.isWriter() && f.getClassName().equals(OptionsPanel.class.getName()) && f.getField().getType().getName().equals(ToggleButton.class.getName())) {
						if (fieldNames.contains(f.getFieldName())) {
							lineNumbers.add(f.getLineNumber());
						}
					}
				} catch (NotFoundException e) {
					throw new CannotCompileException(e);
				}
			}
		});
		// Alter new ToggleButton(...) calls to be large if they correspond to line numbers of fields that should be large
		optionsRefresh.instrument(new ExprEditor() {
			@Override
			public void edit(NewExpr e) throws CannotCompileException
			{
				if (lineNumbers.contains(e.getLineNumber()) && Descriptor.numOfParameters(e.getSignature()) == 4 && e.getClassName().equals(ToggleButton.class.getName())) {
					e.replace("$_ = $proceed($$, true);");
				}
			}
		});
	}
}
