#Testing

Since the game is in early access we need a somewhat robust way to figure out what parts of BaseMod break each time the game is updated. Currently that way is **TestMod**.

**TestMod** is a mod bundled with BaseMod that uses all the functionality of BaseMod such that it can be run against any new version of BaseMod to ensure there are no crashes and that BaseMod's features behave as intended.

##Automated Testing
1. Run the game with **only** `BaseMod` and `TestMod` installed. `TestMod` uses `java.awt.Robot` to take over control of Mouse and Keyboard input so it can attempt an automated run through the game. It will automatically go through and attempt to test different features of `BaseMod` and will produce a log file called `testmod.log` that indicates which features worked and which features did not work.