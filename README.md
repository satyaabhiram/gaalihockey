# gaalihockey

GitHub IDs and UFIDs:
Satya Abhiram Theli     (satyaabhiram)      5958-3952
Akshay Rechintala       (akshay4581)        4581-6988
Neel Rami               (neel_rami)         7712-3151

Contributions:
We designed the project together. Identified and discussed the thread-safety aspects of the project.
Satya Abhiram Theli wrote thread management and server/client setup.
Akshay Rechintala wrote thread-safety, and game physics.
Neel Rami setup the GUI in JavaFX.

Description:
Our project is a 2 player online air-hockey game. Server can handle multiple games simultaneously.
On the server side, one thread is created for every match dynamically,
which in turn creates multiple threads for moving the puck, watching for collisions, communicating with players.
Data races were dealt with Reentrant locks on position and velocity variables. We used immutable objects to keep XY coordinates thread-safe.

Instructions:
For running the project, import the project and import the javafx jars (Java 11 does not include javafx).
Run the src/com/gaalihockey/server/Server.java file – which starts the server.
Run src/com/gaalihockey/client/Client.java file - this class starts the client side.
You need at least two clients to start playing the game.