Name - Adheep Shetty
Csulb Id - 018094724
email id - AdheepVishwanath.Shetty@student.csulb.edu/
	   adheepshetty@gmail.com
MySQL-connector version-5.0.8
I have not used the latest version of MySQL Connector but have added the jar file for sqlconnector with all the files.
If the bat files don't work please fire the commands mentioned as below:

To compile Populate.java:
javac -classpath .;mysql-connector.jar Populate.java

To run Populate.java:
java -classpath .;mysql-connector.jar Populate db.properties incident.txt officer.txt route.txt zone.txt

To compile Hw.java:
javac -classpath .;mysql-connector.jar Hw.java

To run query1:
java -classpath .;mysql-connector.jar Hw db.properties q1 4 -118.29066 34.02984 -118.29066 34.02467 -118.28577 34.02467 -118.28577 34.02984

To run query2:
java -classpath .;mysql-connector.jar Hw db.properties q2 808 500


To run query3:
java -classpath .;mysql-connector.jar Hw db.properties q3 4


To run query4:
java -classpath .;mysql-connector.jar Hw db.properties q4 8

