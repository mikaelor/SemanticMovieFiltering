THIS TEXT FILE IS MEANT TO HELP THE READER RUN THE CODE PROPERLY.

THIS PROJECT WAS MADE WITH AND SHOULD WORK WITH JAVA 9
------------------------------------------------------------------
To start using the code provided, follow these steps:

If you have not already downloaded Java SDK, download it Java SE Development kit.
THEN
Go to 'file' in the top navigational bar, select 'project structure'.
You will find the Project SDK settings. Select any SDK after version 8
Also right underneath this setting, there is a dropdown for Language level.
SELECT DEFAULT. IF THIS DOES NOT WORK, FIND THE OPTION THAT FITS THE SDK VERSION.


If you have not already downloaded Apache Jena, visit Apache Jena's website and
download the jar file.
THEN 
Go to 'file' in the top navigational bar, select 'project structure'.
You will find a category called 'Libraries'. Select this and click on the '+' sign down left in this module.
Navigate to the place you've downloaded the Jena folder and enter this folder.
Enter the folder that says 'lib' and select this folder to add it to the project structure library.



NOTE: TO RUN THIS CODE EITHER COMMENT OUT THE JSOUP CLASS OR FOLLOW THESE STEPS:
If you have not already downloaded Jsoup, download it from Jsoup.org
THEN
Go to 'file' in the top navigational bar, select 'project structure'.
You will find a category called 'Libraries'. Select this and click on the '+' sign down left in this module.
Navigate to the place you've downloaded the Jsoup.jar file and add it to the project structure.

------------------------------------------------------------------
After completing the project structure steps you will have to change some paths for the Scanner to read the csv files. Follow these steps for the code to compile properly.

Step 1:
	Open the src folder.
	Find the class called CsvReader and open it.
	Find the class called SoundHandler and open it.

Step 2: 	
	In these classes there are Strings representing the csv files we want scan. 
	All of these strings will have a line comment right above it stating:
	CHANGE THIS STRING WITH THE FULL PATH FROM ML-LATEST-SMALL/...

	To change this string to the correct path, first click on the ml-latest-small
	folder, then find the respective files as stated in the line comment and right
	click it. There will pop up some options, click on the one that say: "Copy Path"
	Then replace the current string with the one you just copied.

Run the class called 'Main'. This will run our program in an interface.
If you do not select anything and then press 'Search' after opening the interface, every movie will be returned with their movie title.
