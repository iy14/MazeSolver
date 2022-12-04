# MazeSolver
This solver makes use of the Union-Find ADT to receive an image of a maze and solve it.
To run the application, simply run the Maze.java class using your favourite IDE.
When prompted, enter the name of a maze PNG file (from the Mazes folder) to solve and make sure to include the .PNG extension when typing in the name.
The application reads maze images from the Mazes folder which must sit in the same directory as the src folder.
The mazes are PNG files with white backgrounds (the maze path itself is white while the maze walls are in a different color).

The program outputs whether the given maze has a solution, and displays the maze with its components colored in.
A maze with a solution will have the entire maze path (which is the white background) in the same color.
A maze without a solution will have the maze path divided into seperate colors, demonstrating where the solver reaches a dead end.
