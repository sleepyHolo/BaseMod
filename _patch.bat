@echo off

mkdir "src/main/java/com/megacrit/cardcrawl/map"
mkdir "src/main/java/com/megacrit/cardcrawl/monsters"
mkdir "src/main/java/com/megacrit/cardcrawl/powers"
mkdir "src/main/java/com/megacrit/cardcrawl/ui/buttons"
mkdir "src/main/java/com/megacrit/cardcrawl/ui/campfire"
mkdir "src/main/java/com/megacrit/cardcrawl/ui/panels"

cp "../_lib/decompiled/com/megacrit/cardcrawl/map/MapGenerator.java" "src/main/java/com/megacrit/cardcrawl/map/MapGenerator.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/monsters/MonsterGroup.java" "src/main/java/com/megacrit/cardcrawl/monsters/MonsterGroup.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/powers/IntangiblePower.java" "src/main/java/com/megacrit/cardcrawl/powers/IntangiblePower.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/ui/buttons/CancelButton.java" "src/main/java/com/megacrit/cardcrawl/ui/buttons/CancelButton.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/ui/buttons/EndTurnButton.java" "src/main/java/com/megacrit/cardcrawl/ui/buttons/EndTurnButton.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/ui/campfire/AbstractCampfireOption.java" "src/main/java/com/megacrit/cardcrawl/ui/campfire/AbstractCampfireOption.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/ui/panels/DiscardPilePanel.java" "src/main/java/com/megacrit/cardcrawl/ui/panels/DiscardPilePanel.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/ui/panels/DrawPilePanel.java" "src/main/java/com/megacrit/cardcrawl/ui/panels/DrawPilePanel.java"
cp "../_lib/decompiled/com/megacrit/cardcrawl/ui/panels/TopPanel.java" "src/main/java/com/megacrit/cardcrawl/ui/panels/TopPanel.java"

patch -p0 --directory="src/main/java" < "src/main/java/com/megacrit/cardcrawl/com.megacrit.cardcrawl.diff"
pause