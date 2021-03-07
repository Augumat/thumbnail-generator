# **AuGen (thumbnail-generator)**

## \>\>\>\> READ ME FIRST \<\<\<\<

### Installation instructions

To run this program, you will need to install the JRE (Java Runtime Environment) for your operating system from Oracle's website.

Once you have that, all you need to do is download the `.jar` file in the latest **"Releases"** tab at the top of this page and run it.

If you have any questions or requests, or would just like the support this project, [shoot me an email](mailto:ma62@illinois.edu?subject=[GitHub]%20Thumbnail%20Generator%20Questions) at `ma62@illinois.edu`.

Happy generating!

## Summary

Originally made to generate thumbnails for Slambana weekly stream vods, and now for House of Paign vods as well.

Uses simple image layering of character renders combined with overlaid text and pre-made thumbnail templates to generate professional-grade thumbnails for Super Smash Bros vods.

Check us out here! [\[Twitch\]](https://www.twitch.tv/crossslashstudios) [\[Youtube\]](https://www.youtube.com/channel/UCtkWBSJDL-KiXGt27LxHWwA)



## Features (Planned / Implemented)

- [x] Preview functionality (View your thumbnails without having to generate, save, and open them)
- [x] All character portraits
  - [x] Unique character alt costumes (M/F Wii Fit, Koopalings, etc.)
  - [x] All character alt costumes
- [x] Expandable background / foreground template system
  - [ ] Customizable fonts and text placement options for templates
  - [ ] Make possible to load from external "template" files
- [ ] Different ways to find fighters
  - [x] ID sorting
  - [x] Alphabetical sorting
  - [ ] Narrowing list by typing the fighter's name

And more to come!



## Gallery

![The main UI](https://media.discordapp.net/attachments/533545367816634369/632123136883556371/1.PNG)

![Selecting a fighter](https://media.discordapp.net/attachments/533545367816634369/632123139593338880/2.PNG)

![Selecting an alt costume](https://media.discordapp.net/attachments/533545367816634369/632123142004801557/3.PNG)

![Entering match info](https://media.discordapp.net/attachments/533545367816634369/632123144752201739/4.PNG)

![Previewing the thumbnail](https://media.discordapp.net/attachments/533545367816634369/632123133855268864/5.PNG)

![Generating and saving](https://media.discordapp.net/attachments/533545367816634369/632123134119641088/6.PNG)

![What are you reading these for?](https://media.discordapp.net/attachments/533545367816634369/632125315887398942/7.png)



## Changelog

v2.5+ (Check the Releases tab for further changes)

v2.4.0

- **Every alternate costume for every character in the game is now supported!**
- (I won't list them here since you can safely assume they're all in the generator now)
- Some popular Mii costumes are also included besides the base Mii renders

v2.3.2

- Updated the license to GPL 3.0
- Added Byleth!

- Reached the halfway milestone for alt costumes! :D
- The following fighters now have all of their alt costumes implemented;
  - Hero
  - Ice Climbers
  - Ike
  - Incineroar
  - Inkling
  - Isabelle
  - Ivysaur
  - Jigglypuff
  - Joker
  - Ken
  - King Dedede
  - King K. Rool
  - Kirby
  - Link
  - Little Mac
  - Lucario
  - Lucas
  - Lucina
  - Luigi
  - Mario
  - Marth

v2.3.1

- Reduced memory footprint eightfold (by fixing a poor design decision from past me)
- Significantly sped up startup time (as a consequence of the previous fix)
- The following fighters now have all of their alt costumes implemented;
  - Duck Hunt
  - Falco
  - Fox
  - Ganondorf
  - Greninja

v2.3.0

- Added support for Terry
- (almost) Reached the 1/4 milestone for alt-costume support in time for this update!  The following fighters now have all of their alt costumes implemented;
  - Banjo & Kazooie
  - Bayonetta
  - Bowser
  - Bowser Jr.
  - Captain Falcon
  - Charizard
  - Chrom
  - Cloud
  - Corrin
  - Daisy
  - Dark Pit
  - Dark Samus
  - Diddy Kong
  - Donkey Kong
  - Dr. Mario
  - Duck Hunt
  - Terry
- (allegedly) Fixed a small bug with window size

v2.2.0

- Added support for Banjo & Kazooie
- Added support for Sans as a Mii Gunner costume
- Fixed an odd bug involving font loading

v2.1.0

- Added support for Hero and his 3 main alternate costumes

v2.0.2

- Fixed a graphical bug involving window sizes

v2.0.1

- Updated UI to use correct look and feel
- Tweaked UI elements to work with new look and feel options
- Added support for Ike's Radiant Dawn alt skin
- Added support for Wario's classic costume
- Fixed incorrectly lowercase text in the Slambana template

v2.0

- Added a menu bar with various dropdown menus that can be used for changing settings
  - Added a template selection menu for easier generation of different thumbnails (Currently supports Slambana and House of Paign thumbnail types)
  - Added a sort menu to arrange Fighters by characteristics other than their order of introduction to the series when displayed in the Fighter select boxes (Currently supports default and alphabetical sorting)
  - Added an option to authentically size the preview thumbnail (No antialiasing support at the moment)
- Added character stock icons to the Fighter select and variant select boxes for easier navigation and general ease of use
- Fixed questionable design decisions from older versions such as forcing text fields to lock after a string was inputted
- Countless behind-the-scenes rewrites to make the project cleaner overall and future-proof it

v1.5.1

- Updated the "Slambana" text to be all caps
- Permanently moved compiled release versions to the Releases tab and out of the repository

v1.5

- Updated some character portraits
- Added a temporary HoP version while the rework is in progress (will be supported within the main program in the next version)

v1.4

- Fixed R.O.B. and Bowser Jr. crashes caused by trailing periods in their names
- Restructured resources folders for ease of access and to better accommodate planned features

v1.3

- Fixed typo (Boswer Jr.)

v1.2

- Resource loading overhaul
- Packaged an executable .jar file for the program, works outside of IDE now
