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

    @SpirePatch(
            clz = CurlUpPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class CurlUpPowerPatch {
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
                    "return new " + CurlUpPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = DarkEmbracePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DarkEmbracePowerPatch {
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
                    "return new " + DarkEmbracePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = DemonFormPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DemonFormPowerPatch {
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
                    "return new " + DemonFormPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = DexterityPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DexterityPowerPatch {
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
                    "return new " + DexterityPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = DoubleDamagePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DoubleDamagePowerPatch {
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
                    "return new " + DoubleDamagePower.class.getName() + "(owner, amount, justApplied);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = DoubleTapPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DoubleTapPowerPatch {
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
                    "return new " + DoubleTapPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }
    
    @SpirePatch(
    clz = DrawCardNextTurnPower.class,
    method = SpirePatch.CONSTRUCTOR
    )
    public static class DrawCardNextTurnPowerPatch {
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
                    "return new " + DrawCardNextTurnPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = DrawPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DrawPowerPatch {
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
                    "return new " + DrawPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = DrawReductionPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DrawReductionPowerPatch {
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
                    "return new " + DrawReductionPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = EchoPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EchoPowerPatch {
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
                    "return new " + EchoPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ElectroPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ElectroPowerPatch {
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
                    "return new " + ElectroPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = EnergizedBluePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EnergizedBluePowerPatch {
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
                    "return new " + EnergizedBluePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = EnergizedPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EnergizedPowerPatch {
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
                    "return new " + EnergizedPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = EntanglePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EntanglePowerPatch {
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
                    "return new " + EntanglePower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = EnvenomPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EnvenomPowerPatch {
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
                    "return new " + EnvenomPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = EquilibriumPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EquilibriumPowerPatch {
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
                    "return new " + EquilibriumPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = EvolvePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class EvolvePowerPatch {
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
                    "return new " + EvolvePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ExplosivePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ExplosivePowerPatch {
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
                    "return new " + ExplosivePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = FadingPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class FadingPowerPatch {
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
                    "return new " + FadingPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = FeelNoPainPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class FeelNoPainPowerPatch {
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
                    "return new " + FeelNoPainPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = FireBreathingPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class FireBreathingPowerPatch {
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
                    "return new " + FireBreathingPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = FlameBarrierPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class FlameBarrierPowerPatch {
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
                    "return new " + FlameBarrierPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = FlightPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class FlightPowerPatch {
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
                    "return new " + FlightPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = FocusPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class FocusPowerPatch {
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
                    "return new " + FocusPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ForcefieldPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ForceFieldPowerPatch {
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
                    "return new " + ForcefieldPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = FrailPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class FrailPowerPatch {
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
                    "return new " + FrailPower.class.getName() + "(owner, amount, justApplied);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = GainStrengthPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class GainStrengthPowerPatch {
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
                    "return new " + GainStrengthPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = GenericStrengthUpPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class GenericStrengthUpPowerPatch {
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
                    "return new " + GenericStrengthUpPower.class.getName() + "(owner, name, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = GrowthPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class GrowthPowerPatch {
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
                    "return new " + GrowthPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = HeatsinkPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class HeatsinkPowerPatch {
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
                    "return new " + HeatsinkPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = HelloPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class HelloPowerPatch {
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
                    "return new " + HelloPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = HexPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class HexPowerPatch {
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
                    "return new " + HexPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = InfiniteBladesPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class InfiniteBladesPowerPatch {
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
                    "return new " + InfiniteBladesPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = IntangiblePlayerPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class IntangiblePlayerPowerPatch {
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
                    "return new " + IntangiblePlayerPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = IntangiblePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class IntangiblePowerPatch {
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
                    "return new " + IntangiblePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = InvinciblePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class InvinciblePowerPatch {
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
                    "return new " + InvinciblePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = JuggernautPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class JuggernautPowerPatch {
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
                    "return new " + JuggernautPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = LightningMasteryPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class LightningMasteryPowerPatch {
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
                    "return new " + LightningMasteryPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = LockOnPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class LockOnPowerPatch {
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
                    "return new " + LockOnPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = LoopPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class LoopPowerPatch {
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
                    "return new " + LoopPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = LoseStrengthPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class LoseStrengthPowerPatch {
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
                    "return new " + LoseStrengthPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = MagnetismPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class MagnetismPowerPatch {
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
                    "return new " + MagnetismPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = MalleablePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class MalleablePowerPatch {
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
                    "return new " + MalleablePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = MayhemPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class MayhemPowerPatch {
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
                    "return new " + MayhemPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ModeShiftPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ModeShiftPowerPatch {
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
                    "return new " + ModeShiftPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = NextTurnBlockPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NextTurnBlockPowerPatch {
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
                    "return new " + NextTurnBlockPower.class.getName() + "(owner, amount, name);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = NightmarePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NightmarePowerPatch {
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
                    "return new " + NightmarePower.class.getName() + "(owner, amount, card);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = NoBlockPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NoBlockPowerPatch {
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
                    "return new " + NoBlockPower.class.getName() + "(owner, amount, justApplied);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = NoDrawPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NoDrawPowerPatch {
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
                    "return new " + NoDrawPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = NoxiousFumesPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NoxiousFumesPowerPatch {
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
                    "return new " + NoxiousFumesPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = PainfulStabsPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PainfulStabsPowerPatch {
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
                    "return new " + PainfulStabsPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = PanachePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PanachePowerPatch {
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
                    "return new " + PanachePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = PenNibPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PenNibPowerPatch {
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
                    "return new " + PenNibPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = PhantasmalPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PhantasmalPowerPatch {
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
                    "return new " + PhantasmalPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = PlatedArmorPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PlatedArmorPowerPatch {
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
                    "return new " + PlatedArmorPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = RagePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class RagePowerPatch {
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
                    "return new " + RagePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ReactivePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ReactivePowerPatch {
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
                    "return new " + ReactivePower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = ReboundPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ReboundPowerPatch {
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
                    "return new " + ReboundPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = RechargingCorePower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class RechargingCorePowerPatch {
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
                    "return new " + RechargingCorePower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = RegenerateMonsterPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class RegenerateMonsterPowerPatch {
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
                    "return new " + RegenerateMonsterPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = RegenPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class RegenPowerPatch {
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
                    "return new " + RegenPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = RegrowPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class RegrowPowerPatch {
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
                    "return new " + RegrowPower.class.getName() + "(owner);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }

    @SpirePatch(
            clz = RepairPower.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class RepairPowerPatch {
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
                    "return new " + RepairPower.class.getName() + "(owner, amount);",
                    ctClass
            );
            ctClass.addMethod(method);
        }
    }
}
