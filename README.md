# gaalihockey
[https://github.com/satyaabhiram/gaalihockey](https://github.com/satyaabhiram/gaalihockey)

**Description:**  
Our project is a 2 player online air-hockey game. Server can handle multiple games simultaneously.  
On the server side, one thread is created for every match dynamically,
which in turn creates multiple threads for moving the puck, watching for collisions, communicating with players.  
Data races were dealt with Reentrant locks on position and velocity variables. We used immutable objects to keep XY coordinates thread-safe.  

**Instructions:**  
Download [JavaFX 11](https://gluonhq.com/products/javafx/)  
Extract the downloaded archive.  
Set ```JAVAFX``` environment path to the ```lib``` folder in the unzipped folder and restart your terminal  

Clone the repository or extract the downloaded repository zip
```
cd gaalihockey
make
cd out
```
Start the server
```
java --module-path $JAVAFX --add-modules=javafx.controls com.gaalihockey.server.Server
```
Start Player 1
```
java --module-path $JAVAFX --add-modules=javafx.controls com.gaalihockey.client.Client
```
Start Player 2 (You need at least two clients to start playing the game)
```
java --module-path $JAVAFX --add-modules=javafx.controls com.gaalihockey.client.Client
```
