# Project 3 Prep

**For tessellating pluses, one of the hardest parts is figuring out where to place each plus/how to easily place plus on screen in an algorithmic way.
If you did not implement tesselation, please try to come up with an algorithmic approach to place pluses on the screen. This means a strategy, pseudocode, or even actual code! 
Consider the `HexWorld` implementation provided near the end of the lab (within the lab video). Note that `HexWorld` was the lab assignment from a previous semester (in which students drew hexagons instead of pluses). 
How did your proposed implementation/algorithm differ from the given one (aside from obviously hexagons versus pluses) ? What lessons can be learned from it?**

Answer:

My proposed implementation differed from the given one in many different ways. Firstly, the video implemented a series of recursive calls to create hexagons. My proposed solution was iterative in its approach, and divided the task of creating pluses into making 5 squares. Additionally, the video had methods for drawing rows and columns, while I thought of doing so in the Square class. I learned the lesson of simplifying my code by creating helper methods instead of doing things at once!

**Can you think of an analogy between the process of tessellating pluses and randomly generating a world using rooms and hallways?
What is the plus and what is the tesselation on the Project 3 side?**

Answer:

The process of tesselating pluses and randomly generating a world using rooms and hallways is the following: the pluses are like the rooms, and the hallways would be the tessellations.

**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating pluses.**

Answer:

I would think of writing the method to create the backbone of what a room is, much like how I did so for the pluses. Then, after understanding and implementing them correctly, I would proceed to use the Random library to tessellate.

**What distinguishes a hallway from a room? How are they similar?**

Answer:

A room is basically a hallway that is filled with some tiles. A hallway have tile.NOTHING, while rooms can be made of whatever the user chooses. They are similar because they are still tiles!