package basemod.patches.com.megacrit.cardcrawl.powers.IntangiblePower;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(cls="com.megacrit.cardcrawl.powers.IntangiblePower", method="atEndOfTurn")
public class FixPlayerIntangiblePower {
    private static boolean execute = true;

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (execute) {
                    execute = false;
                    String newMethodBody = "" +
                        "public void atEndOfTurn() {" +
                        "   if (this.owner instanceof com.megacrit.cardcrawl.characters.AbstractPlayer) {" +
                        "       com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(" +
                        "           new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(" +
                        "               this.owner, this.owner, \"Intangible\"" +
                        "           )" +
                        "       );" +
                        "   }" +
                        "}";

                    CtClass ipClass = m.getEnclosingClass();
                    CtMethod atEndOfRound = CtNewMethod.make(newMethodBody, ipClass);
                    ipClass.addMethod(atEndOfRound);
                }
            }
        };
    }
}