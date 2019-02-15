package basemod.patches.com.megacrit.cardcrawl.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.powers.*;
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

    @SpirePatch(
            clz = AmplifyPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AmplifyPowerPatch {
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
                    "return new " + AmplifyPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = AngerPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AngerPowerPatch {
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
                    "return new " + AngerPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = AngryPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AngryPowerPatch {
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
                    "return new " + AngryPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ArtifactPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ArtifactPowerPatch {
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
                    "return new " + ArtifactPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = AttackBurnPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AttackBurnPowerPatch {
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
                    "return new " + AttackBurnPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BackAttackPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BackAttackPowerPatch {
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
                    "return new " + BackAttackPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BarricadePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BarricadePowerPatch {
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
                    "return new " + BarricadePower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BeatOfDeathPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BeatOfDeathPowerPatch {
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
                    "return new " + BeatOfDeathPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BerserkPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BerserkPowerPatch {
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
                    "return new " + BerserkPower.class.getName() + "(name, owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BiasPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BiasPowerPatch {
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
                    "return new " + BiasPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BlurPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BlurPowerPatch {
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
                    "return new " + BlurPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BrutalityPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BrutalityPowerPatch {
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
                    "return new " + BrutalityPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BufferPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BufferPowerPatch {
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
                    "return new " + BufferPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = BurstPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class BurstPowerPatch {
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
                    "return new " + BurstPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ChokePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ChokePowerPatch {
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
                    "return new " + ChokePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = CombustPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class CombustPowerPatch {
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
                    "return new " + CombustPower.class.getName() + "(owner, hpLoss, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ConfusionPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ConfusionPowerPatch {
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
                    "return new " + ConfusionPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ConservePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ConservePowerPatch {
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
                    "return new " + ConservePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ConstrictedPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ConstrictPowerPatch {
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
                    "return new " + ConstrictedPower.class.getName() + "(owner, source, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = CorpseExplosionPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class CorpseExplosionPowerPatch {
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
                    "return new " + CorpseExplosionPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = CorruptionPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class CorruptionPowerPatch {
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
                    "return new " + CorruptionPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = CreativeAIPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class CreativeAIPowerPatch {
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
                    "return new " + CreativeAIPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = CuriosityPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class CuriosityPowerPatch {
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
                    "return new " + CuriosityPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }
}
