# CSULB Police Assistant

## Scenario
<p>CSULB-PD organizes its officers into squads, and divides its coverage area into smaller areas, called
“zones”. Each zone is has a squad assigned to provide coverage. CSULD-PD also has pre-designated
patrol routes for officers to follow when patrolling. Lastly, CSULD-PD needs to track crimes and
incidents that happen within its coverage area.</p>

## Task Assigned
<p>In order for CSULD-PD to better keep track of is patrol resources so that it can manage and deploy
them more efficiently and serve the community better, I had been given a task to build a database that
will help monitor and track the location of CSULD-PD officers, patrol routes, and zones, etc.</p>

## Project Summary
<p>This project  involves implementation of a simple spatial database,accessing a database programmatically 
using Java Database Connectivity (JDBC).MySQL supports the storage,indexing and querying of spatial data 
through spatial extensions based onthe OpenGIS geometry model.I haveused the spatial relations between 
geometric objects approximated by their minimum bounding rectangles(MBRs)
instead of the actual geometry object itself.</p>

## Tools Used
* Open-GIS standard functions on MySQL 
* Java Standard Edition JDK 

## Input Files-
### 1. Zone.txt:
Each zone is represented by a 2D polygon, with the following data.:
* Col 1: Unique Zone ID
* Col 2: Zone name
* Col 3: Currently assigned squad number
* Col 4: Number of vertices on the polygon
* The numbers after column 4 are the coordinates of the vertices. They are ordered as
  long1, lat1, long2, lat2, …, longn, latn.<br>
For example, a row:<br>
15, "Zone X", 3, 4, -118.3, 34.03, -118.3, 34.02, -118.2, 34.02, -118.2, 34.03<br>
Represents a zone with ID 15, the name “Zone X”, has squad number 3 assigned to it, and having
4 vertices with geo-coordinates:<br>
34.03°N 118.3°W, 34.02°N 118.3°W, 34.02°N 118.2°W, 34.03°N 118.2°W
### 2.Officer.txt:
Each officer has:
* Col 1: Unique Badge number
* Col 2: Name
* Col 3: Squad number
* Col 4 & 5: Current location longitude and latitude.
### 3.Route.txt:
Each patrol route has:
* Col 1: Unique Route number
* Col 2: Number of vertices on the route
* The numbers after column 2 are the coordinates of the vertices:
  long1, lat1, long2, lat2, …, longn, latn.<br>
  Patrol routes do not loop back to the first point.
### 4.Incident.txt: 
Each incident has:
* Col 1: Unique Incident ID
* Col 2: Type of incident
* Col 3 & 4: Incident location (longitude and latitude)<br>
There is **createdb.sql** file containing SQL statements that will create a database
*PublicSafety* containing all required tables.<br>
A **dropdb.sql** file that drops the database, along with all tables, types, and all
other objects that were created.
**Populate.java** to implement a Java program  that gets the names of the input data
files as command line parameters and populate the data contained within them into the database by
executing individual insert statements for each row using JDBC.<br>
**Hw.java**  to implement a Java program that provides the capability to run queries on
the system from the Windows command line or Linux/Mac shell environment.<br>
**compile_hw.bat** and **compile_populate.bat** to compile Hw.java and Populate.java respectively.<br>
**plq1.bat**, **plq2.bat**, **plq3.bat**, **plq4.bat** to run Hw.java each with different queries.

## Output
Output shows results for different queries.
### 1.Range Query:
Finds all incidents that occurred in the polygon bounded by 4 vertices:<br>
34.03°N 118.3°W, 34.02°N 118.3°W, 34.02°N 118.2°W, 34.03°N 118.2°W<br>
And displays the ID, location and type of each incident on screen, one line per incident, ordered by
incident ID:<br>
### 2.Point Query:
Finds all officers that are within 250 meters of the location of incident #15 and
display the badge number, distance (rounded to nearest meter) and name of each officer, ordered with
the nearest officer first:<br>
### 3.Find Squad:
Given a squad number, returns the zone that it is currently assigned to, and lists all officers assigned to
that squad. For each officer, also indicates if their current location is inside or outside of the assigned
zone and lists the officers in the squad in ascending order of their badge number<br>
### 4.Route Coverage:
Given the route number of a patrol route, lists the zones that the patrol route passes through (intersects).
Lists zones in ascending order by their zone ID.

## Outcome
<p>By doing this project I gained practical experience of how to write JDBC code to access spatial database.
Even organizing its officers into squads, and dividing its coverage area into smaller areas and helping them to track crimes and
incidents that happen within its coverage area. </p>
