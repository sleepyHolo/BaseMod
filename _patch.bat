@echo off
patch -p0 --directory="src/main/java" < "src/main/java/com/megacrit/cardcrawl/com.megacrit.cardcrawl.diff"