@echo off
mkdir "src/main/java/com/megacrit/cardcrawl/powers"
cp "../_lib/decompiled/com/megacrit/cardcrawl/powers/IntangiblePower.java" "src/main/java/com/megacrit/cardcrawl/powers/IntangiblePower.java"
patch -p0 --directory="src/main/java" < "src/main/java/com/megacrit/cardcrawl/com.megacrit.cardcrawl.diff"
pause