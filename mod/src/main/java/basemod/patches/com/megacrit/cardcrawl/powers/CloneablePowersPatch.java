package basemod.patches.com.megacrit.cardcrawl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import javassist.*;

public class CloneablePowersPatch {

    @SpirePatch(
            clz = AccuracyPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AccuracyPowerPatch {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool pool = ctClass.getClassPool();

            CtClass ctCloneablePowerInterface = pool.get(CloneablePowerInterface.class.getName());
            ctClass.addInterface(ctCloneablePowerInterface);
            CtClass ctAbstractPower = pool.get(AbstractPower.class.getName());

            CtMethod method = CtNewMethod.make(
                    ctAbstractPower, // Return
                    "makeCopy", // Method name
                    new CtClass[]{},
                    null, // Exceptions
                    "return new " + AccuracyPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = AfterImagePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AfterImagePowerPatch {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool pool = ctClass.getClassPool();

            CtClass ctCloneablePowerInterface = pool.get(CloneablePowerInterface.class.getName());
            ctClass.addInterface(ctCloneablePowerInterface);
            CtClass ctAbstractPower = pool.get(AbstractPower.class.getName());

            CtMethod method = CtNewMethod.make(
                    ctAbstractPower, // Return
                    "makeCopy", // Method name
                    new CtClass[]{},
                    null, // Exceptions
                    "return new " + AfterImagePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

}
