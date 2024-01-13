Run /Archive Blue/src/nl/tue/MainMenu to start the game.
Please try to click the skill buttons on the top left cornor.

The development of this game provides an excellent opportunity to study deeply and demonstrate the topics of game design and UX design.
The game design in our game development is embodied in the design of gameplay levels and character attributes. This includes designing a combat mechanism that can keep a proper balance of fairness with reasonable stats of characters, creating a well-structured game flow that can keep players engaged and challenged, and developing appropriate and diverse role skills to avoid boredom, ensuring that the game mechanism is both enjoyable and challenging enough that can test players' skills. We divided the gameplay into two parts, i.e. the normal level and the boss level. Players can get familiar with the operation and master the use of role skills in the normal level, and then face more difficult challenges and gain a greater sense of accomplishment in the next boss level. Besides writing the program, we considered more problems. We focused on how users interact with products and perceive them. In conclusion, the game design in our development is to create the core of the game experience, combine the satisfactory battles, a gradual game glow and immersion, and bring unforgettable and pleasant adventures to the players.
UX design is another topic in developing our game. This includes designing an intuitive and unobtrusive user interface, so that players can choose to play games or view credits on the main page, or simply quit. During the combat the key information of the characters and enemies are displayed for the players to better decide their strategies. We paid attention to the feedback mechanism of players and add visual effects to enhance the overall immersion of players, guide players and provide information about the combat situation. The combat mechanism we designed can achieve smooth and satisfactory gun action, ensuring that players can feel a sense of pleasure when repelling groups of enemies.  In the development process, we constantly played the newest version of the game and try to optimize it, improve the product according to user tests and adopt iterative and collaborative methods to ensure that the final product can not only run normally, but also provide excellent and satisfactory user experience. By paying attention to these aspects, we managed to improve the overall fun of players and ensured the accessibility and user-friendliness of the game. In addition, we also focused on aesthetics. We used the existing popular elements of art materials and character design to enhance the game experience, create a fantastic world, and enhance the emotional connection between players and game characters. In addition, to optimize the performance of the game, we used multiple threads to ensure that the game runs smoothly  to ensure that players can completely immerse themselves in the game. We are committed to creating a game that not only entertains players, but also allows players to immerse themselves in and feel satisfied with the whole game experience, putting the end-user experience first rather than just paying attention to the functionality of the code.


Game Design is demonstrated by:
1. **Game Background and Scrolling**:
   - The code loads and displays a background image (`bg`) for the game.
   - It scrolls the background horizontally as the game progresses, creating a sense of movement and progression in the game world (`scrollXPos` and `SCROLL_SPEED`).

2. **Game Characters**:
   - The code creates several game characters, such as `character1Yuka`, `character2Misaka`, `character3Shokuho`, `character4Fuuka`, `soldier1`, `soldier2` and `boss`. These characters are initialized with various attributes like position, abilities, and health points.
   - The characters are drawn on the screen, and their shield bars, health bars and skill cool down are displayed using the `addHP` method. Shield bars, Health bars and Skill Cool Down indicate the characters' status and are essential for gameplay.

3. **Game Objects and Abilities**:
   - The code handles various in-game objects and abilities, such as "Shield," "Heal," "Rail Gun," , "Bomb" and "Charm." Buttons for activating these abilities are created and displayed on the game screen, or boss triggers "Bomb" automatically.
   - The game logic allows these abilities to be unleashed under certain conditions, enhancing the gameplay and providing strategic options.

4. **Game Events and Combat**:
   - The code manages game events and combat sequences.
   - It triggers encounters with soldiers and a boss character when the game progresses (`scrollXPos` reaching specific positions).
   - The code implements combat sequences, including character attacks and enemy responses, taking into account health points, damage, and cooldowns for special abilities.

5. **Background Music**:
   - The code includes functionality for playing background music, enhancing the gaming experience.

6. **User Interface (UI)**:
   - The code creates buttons for player interaction, allowing the player to trigger character abilities and actions.
   - The UI elements are positioned on the screen to provide a user-friendly interface for controlling the game characters.

7. **Game Over and Victory Conditions**:
   - The code handles victory conditions, ending the game when the boss character is defeated.
   - It transitions to a "Victory Screen" when the game ends, showing that the player has achieved victory.
   - The code handles defeated conditions, ending the game when the all characters are defeated.
   - It transitions to a "GameOver Screen" when the game ends, showing that the player has been defeated.

8. **Overall Gameplay Flow**:
   - The code structure and logic manage the overall gameplay flow, including scrolling the background, character movements, combat sequences, ability usage, and progression.

9. **Main Menu Screen:** The code creates a main menu screen that provides options for starting the game, accessing credits, and quitting. This is a common element in game design and allows players to interact with the game before starting gameplay.

10. **Background Music:** The main menu includes background music, enhancing the overall atmosphere and setting the mood for the game. Background music is a crucial aspect of game design as it can help immerse players in the game world.

11. **Buttons:** The code creates interactive buttons for Start Game, Credits, and Quit. Buttons are a standard way of providing user interaction in games. When players click these buttons, specific actions are triggered, such as starting the game or showing credits.

12. **Button Click Actions:** Each button is associated with a specific action when clicked. For example, the "Start Game" button initiates the game by launching the main gameplay screen. The "Credits" button displays a credits screen, which is a common feature in games to acknowledge the development team and provide additional information.



UX Experience is demonstrated by: 
1. **Game Graphics and Visuals**:
   - The code loads and displays high-quality background images, characters, and abilities, creating an immersive visual experience for players. The use of detailed character sprites and animations makes the game visually appealing.
   - The scrolling background provides a sense of movement and progression, making the game world feel dynamic.

2. **User Interface (UI)**:
   - The code includes interactive UI elements (buttons) that allow players to control character actions. These buttons are visually appealing and clearly labeled, making it easy for players to understand their functions.

3. **Feedback and Indicators**:
   - Health bars are displayed for characters, and they change in real-time as characters take damage or receive healing. Shield Bar are displayed to also notify the engamement in a more interesting way. This feedback mechanism provides players with a clear understanding of their characters' status and helps them make strategic decisions.

4. **Game Sound and Music**:
   - The code incorporates background music that enhances the gaming experience. The music contributes to the atmosphere of the game and keeps players engaged.

5. **Game Progression**:
   - The scrolling background and the trigger points for encounters and battles provide a sense of progression. As players advance through the game, they encounter different challenges and scenarios, keeping the gameplay interesting and dynamic.

6. **Ability Activation**:
   - The code allows players to unleash character abilities by clicking on the corresponding buttons. The visual representation of abilities, such as shields and projectiles, provides immediate feedback to players when these abilities are activated.

7. **Strategic Depth**:
   - The inclusion of multiple characters and abilities adds depth to the gameplay. Players can strategize and choose when to use specific abilities to optimize their chances of success in battles.

8. **Game Over and Victory Screens**:
   - When the game ends, the code transitions to a "Victory Screen," or "GameOver Screen" providing a sense of achievement or encouragement for players. These screen celebrates their success and provides closure to the gaming experience.

9. **Responsive Controls**:
   - The UI buttons are designed to be responsive and easy to interact with. This responsiveness ensures that players can control their characters effectively and enjoy a smooth gaming experience.

10. **Engagement and Challenge**:
    - The code introduces encounters and boss battles at specific points in the game, offering challenges to players. This element of challenge and progression keeps players engaged and motivated to continue playing.

11. **Immersive Main Menu:** The main menu screen enhances the overall user experience by providing an immersive and visually appealing interface. The background image and music contribute to creating an engaging environment.

12. **Ease of Navigation:** The button layout and design are user-friendly, making it easy for players to navigate and select their desired actions. This contributes to a positive user experience.

13. **Feedback:** When buttons are clicked, there is feedback in the form of printed messages to the console. While this feedback is more developer-oriented, it can be useful during game development for debugging and confirming that button actions are being triggered correctly.

14. **Seamless Transition:** When the "Start Game" or "Credits" button is clicked, the main menu frame is disposed of, and the game or credits screen is displayed in fullscreen. This transition provides a seamless user experience without unnecessary interruptions.




