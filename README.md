Minechem IV
=========

Minechem 5 is a mod about chemistry and chemical industry. Originally maintained by LJDP and Rushmead, Minechem 5 is maintained by Pixlepix and Mandrake, With help from open source contributors, and is based off the work of the previous iterations Minechem II, Minechem III and Minechem IV. Split apart materials into their most basic components, then mesh them all together into something new. The possibilities are immense!

Minechem can be found at: http://www.minechemmod.com/

## License:

> Minechem v4.x is licensed under Creative Commons Attribution-ShareAlike 3.0 Unported. <br />
The details of the licence can be found at: http://creativecommons.org/licenses/by-sa/3.0/us/

## Contributors and permissions
The list of contributors can be found here: http://pastebin.com/raw.php?i=LsumDirg

## Disclaimer
This mod contains many street drug jokes, If this offends you. Please do not use this mod.

## Setup
Currently, this only applies to linux operating systems, when you're using the Intellij IDEA. At some point in the future, I'll add more (like an actual setup script or something)

Open a terminal to the folder where you've put the Minechem source code.
Run the following commands, in order:
```
./gradlew cleancache --refresh-dependencies
./gradlew setupDecompWorkspace
./gradlew idea
mkdir lib
wget "http://www.chickenbones.craftsaddle.org/Files/goto.php?file=NotEnoughItems-dev&version=1.6.4" -O lib/NotEnoughItems.jar
wget "http://www.chickenbones.craftsaddle.org/Files/goto.php?file=CodeChickenCore-dev&version=1.6.4" -O lib/CodeChickenCore.jar
```
