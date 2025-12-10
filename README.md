# Fifth-Edition-Forge
A D&amp;D5e character creator app built off of an external Api. My entry for Moblie Dev Project 2.

This app is built off of the back of the api at this link, https://www.dnd5eapi.co/. It is based on the rulings of DND Fifth Edition as of it's launch state and is also incomplete in what it covers. Due to it being incomplete, there is a lack of options and or information on given sections of my app, however the functionality is still plain to see and I did what I could with some of the more limited responses such as the api's background call response.

The way in which I'll break down this functionality will be screen by screen. I'll comment on what screens use what ViewModels and I will group them in such an order that the ViewModels used are consistent with the order in which the screens are covered.

# "The Tavern" - Home Screen - sharedVM
As the name might imply, this is the place where one can find adventurers and ne'er-do-wells of all shapes and sizes. In technical terms this is the page that covers the database integration and the viewing and filtering of the local list of characters. 

It contains styled boxes for each character displayed in which the colour scheme and icon shown are dependant on what class the respective character has chosen, eg: Barbarian has an orange based colour scheme and barbarian class logo whereas Monk has a blue/teal clolour-shceme and a monk class logo. There is also a section near the top of the screen for the "currently chosen character", which is the character who's details you've viewed (or created) last which helps to identify what character will be used for actions such as the importing and exporting in the Port screen.

At the very top of the screen there is a search function that takes in a string input and will filter the contents loaded from the DB to return only characters whose names and or class match the user's input. This is a simple albeit very useful sorting mechanism.

# Character Details Screen
No fancy name for this one as it has no navigation label per say, instead you access it either on creation of a character or by clicking on one of the character boxes present in the Tavern screen.

This displays a list of all the information collected about the character from their creation in a clean and concise way once again building off of my dynamic UI colour-scheme system.

It also offers a deletion option for a given character should you want to remove them from the DB.

There really isn't much to this screen functionality wise, it just servers as a way to view the results of the functionality from the following screen.

# "The Forge" - Character Creation - forgeVM + sharedVM
This right here is where the majority of the work went for this project. Forge alone was the reason for every single api call I added and it is easily the most layered view as can be seen by my insistence on splitting it up into 6 different composables with it's own composable package "Forge sections" just to keep my codebase clean and organised.

As to not repeat myself for every section, I'll make a quick statement covering the UI design of every forge section in general as opposed to individually, but rest assured I have as much to say for these sections as I do for some entire screens. My focus when designing the forge UI was simple. Keep it neat, keep consistent formatting, keep it displaying properly regardless of screen size, keep the vertical scrolling to a minimum. To achieve this I used a lot of smaller composalbles consisting of a styled box with a lazy row (see proficiencies, ability score, traits etc) and then put instances of these composables inside a lazy column. I also used sliding animated visibility segments to keep the amount of scrolling needed to a minimum. So instead of clicking on a class and having it's details drop down, they would slide in from the right instead.

It should warrant a mention that the backgrounds section is left barren by the api's lack of background responses. The UI is coded to treat backgrounds like classes wherein there is a list with a chosen background's details sliding in from the right, however the api only stores 1 background and contains no details of it to display bar it's name. This is the most egregious instance of the api holding back my work.

In the end whilst I'm not completely happy with how the UI turned out here, it is at the very least serviceable and I would say it was a solid 7/10 result. Not great but not bad either.

# "Dice" - D&amp;D5e Dice roller - diceVM + sharedVM
This screen offers a simple yet comprehensive dice roller that is capable of doing every single type of roll that can be required for playing D&amp;D5E. As someone who has played their fair share of D&amp;D in my time, I will say I'm delighted by how this turned out. Like I said every roll that is required by the game can be made here and it also tracks your roll history which is a great quality of life feature for players.

There isn't much else to this screen as it was more of a tangential feature to the character creation core and therefore isn't fully integrated with the rest of the app's functionality. That being said however it is still an incredibly useful tool for D&amp;D players.

# "The Port" - Importing/Exporting of Characters  - portVM + sharedVM
The way the game of D&amp;D works requires the person running the game (the DM) to keep multiple players' characters on hand at any given moment. I therefore thought that adding in the functionality to export and import characters via JSON files would be a nice addition to my already existing set of features.

This screen gives you 3 buttons. The first is to export the currently chosen character from your Tavern screen. When pressed it will open your phones "Downloads" folder and ask what name you want to save the JSON as. Similarly when hitting the import button your "Downloads" folder will be opened and you will be asked what JSON you want to import form (sadly I only had time to implement singular importing not importing from multiple JSONs at once). The last button is mostly irrelevant, it simply clears the import/exports messages shown on the screen.

#Dynamic Colour-scheme - Adaptive UI - sharedVM
This here is why sharedVM has been used in every screen above. Whenever a character is created it has a class attribute. Depending on this class attribute it is assigned a different map of colours and a specific drawable, AKA the "Class Theme". The entire apps UI palette will change based on what the current character's "Class Theme" is. Repeating the previous example from the Tavern, if your current character is Barbarian then the app will take on a colour palette of different shades of orange, however if you instead have a current character who is a Monk, then the apps UI will adapt to this and change to a colour-scheme of blues and teals. This is an app wide change and in total there are only 2 composable functions (1 with any colour) in the entire project that is not in some way dependant on this adaptive colour-scheme.
