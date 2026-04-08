COMP3005 FINAL TERM PROJECT ASSIGNMENT
James Noel 101306496

PROJECT FILE DETAILS:
- Project folder is named 'COMP3005_TermProject_FilmStreamingService'.
- Database is named 'movie_streaming.db' is located inside the 'data/' folder 
  within the project folder root. SQL insert script was included to simplify testing.

SETUP INSTRUCTIONS:

Newer Java versions recommended to compile and run. Java 11 may display syntax errors.

Command Prompt
- Locate the project folder
	- i.e., cd C:\Users\YourName\Desktop\School\COMP3005\COMP3005_TermProject_FilmStreamingService
	- To compile: type 'javac -cp "data/*" -d out src/*.java'.
	- To run: type 'java -cp "out;data/*" View'.

IntelliJ IDEA
- Open the project COMP3005_TermProject_FilmStreamingService in Intellij IDEA.
- File -> Project Structure -> Modules -> Dependencies -> Add '+' -> JARs or Directories -> 
	locate and select 'sqlite-jdbc-3.51.3.0.jar' in the data/ folder -> Apply -> OK
- Inside the project root, navigate to 'src/View.java'.
- Inside View.java, right-click the file and select "Run 'View.main()'".

ADDITIONAL NOTES:
-Any warning messages displayed after compiling and running the program do not affect the functionality of the application
and are just security measures from the java runtime after detecting the database JDBC driver using the restricted native system methods.



