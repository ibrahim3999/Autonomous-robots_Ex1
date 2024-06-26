# Drone Simulator
The main goal for this project is to try find good solution of small drone, flying inside indoor building without getting hit and crash.
The project fully autonomous 2d drone simulator, this simulator is trying to be realistic as much as it can, with lidar sensors,gyroscope sensor ,optical flow sensor and speed sensor.
We add a little bit noise to each sample to make it more realistic approach.
Basic API with real-time info and also manual controlling.
We also implemented kind of area mapping when the drone fly.
This project written in Java.

## Getting Started

When all files located inside eclipse or any other explorer we have the Maps folder which contains couple of maps with route and obstacles.
- Inside "SimulationWindow" in main we have map object with the path to any map you want to test.
- Inside "Drone" we have path to our image represents the drone itself.
After setting this up it is ready to launch.

## Sensors
- Lidar - check the distance between his spot forward and return the distance if hit, if not return 300 as max sample enabled.
In our project we set 3 lidars - one in front, second 90 degrees, third -90 degrees.
- Gyroscpoe - check the rotation of the drone. (0-360)
- Optical flow - check his location on map.
- Speed - max speed is 2m per second.

## Symbols 
- Yellow mark - mapped area.
- Black circle - his purpose to get some idea from where drone came and simply make some route that his passed.(for navigation)
- Red points - represents the wall point.
- Blue line - his whole route.

## API description
Really simple API with few buttons -
Start/pause button, speed up/down, spin -+30/-+45/-+60/90/180.
- Toggle Map - allows you to hide the real map, entering to "real time" vision.
- Toggle AI - enable/disable AI.

## Map rules
If you wish to add custom map it has to be black/white pixels- black is wall/obstacle, white is safe pass.

## V2 update
- Added return home bottom, by clicking it drone will return to starting point.
- Directed Graph feature added. (JGrapht library required)

## Known bugs
- API might be in different place depends on the map.
- Sometimes drone might crash(hit the black pixels) specially in difficult obstacles.
- Sometimes may be indifferent parameters which causing some pixels override - solution is to re-run project.

## Images


![WhatsApp Image 2024-06-06 at 22 36 03 (1)](https://github.com/ibrahim3999/Autonomous-robots_Ex1/assets/57791415/b927cc6f-c19d-43b9-b41e-910594049d7d)

![WhatsApp Image 2024-06-06 at 22 36 03](https://github.com/ibrahim3999/Autonomous-robots_Ex1/assets/57791415/594accba-9430-4fa4-9df3-d096aac617e3)

![WhatsApp Image 2024-06-06 at 22 36 04 (1)](https://github.com/ibrahim3999/Autonomous-robots_Ex1/assets/57791415/1f1ad052-8b8b-4e05-a4ce-f111c43c8500)

![WhatsApp Image 2024-06-06 at 22 36 04](https://github.com/ibrahim3999/Autonomous-robots_Ex1/assets/57791415/53e126c2-0c52-45f1-91de-e9c1c6dece0b)



![graphexample](https://user-images.githubusercontent.com/28596354/60256218-cc095680-98d9-11e9-8ab4-70c00e863df8.png)

## How to Run the Simulator
    - Clone this repository.
    - Check you have the necessary dependencies installed.
    - In the "SimulatorWindow" file at "imagePath" change the current path to the map you want.
    - Run the "SimulationWindow" file to start the simulation.


## Submited by </br>

* Ibrahim
* Niv Ginger Motilsky
* Liron Cohen

