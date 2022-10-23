# FAST MEAL SERVICE API
## By John Lavender

---

### WHAT IS THIS PROJECT?

This project was written in the span of two days (over the weekend of October 22, 2022, as part of the 
application process for an internship at a particular company. This is a simple API project with a mock database table
and a command-line gui app. 

---

### TASK REQUIREMENTS  

<p align="center">
    <img src="docs/img/Requirements1.png" width="350" alt="requirements">
</p>
<p align="center">
    <img src="docs/img/Requirements2.png" width="350" alt="requirements">
</p>
<p align="center">
    <img src="docs/img/Requirements3.png" width="350" alt="requirements">
</p>

---

### MY APPROACH

I decided to treat these requirements like those of an API service. I have 
some experience working with Spring Boot and so designed the directory structure
reminiscent of that found in a lot of Spring Boot apps.

- src
  - main
    - api
      - db
      - domain
      - exceptions
      - factories
      - services
      - utils
    - tests
    - utils
    - App.java [main class]

(For the sake of brevity, App.java, which is the main class, is the only Java class listed)

The main class in this structure is not integral to the API functioning. The core class that 
is exposed to callers is MealService.java. This is the class that App.java uses to interact 
with the API. App.java is merely a UI shell.

---

### RUNNING THE PROGRAM

#### REQUIREMENTS:

- JRE (Java Runtime Environment) version 8 or higher 
  - Project tested with Java 8, should run the same on newer versions
- Terminal (MacOS, Linux), Powershell (Windows), or similar command line application

#### INSTRUCTIONS:

<ol>
    <li>Download either the JAR file by itself or the .zip file for the entire repository</li>
    <li>Navigate using a command line app to the directory containing the JAR file</li>
    <li> 
        <span>There are two options for running the jar</span>
        <ol>
            <li>
                <span>Enter "java -jar Fast-Meal-Service-API.jar test" to run the test suite</span>
                <ul>
                    <li>This will run the test method(s) contained in tests/Tests.java</li> 
                    <li>These are the same tests as those listed out in the "Task Requirements" documentation above</li>
                    <li>Each test will print output including a "PASS" or "FAIL" statement</li>
                    <li>Once all the tests have run, the program will exit</li>
                </ul>
            </li>
            <li>
                <span>
                    Enter "java -jar Fast-Meal-Service-API.jar" to run the normal program.
                    (Same command as the first but without the word "test" at the end)
                </span>
                <ul>
                    <li>This will run the command-line gui app</li>
                    <li>Instructions for running the command-line gui app enumerated below</li>
                </ul>
            </li>
        </ol>
    </li>
</ol>

If for any reason you want to build from source instead of the running the jar, you can load this project into IntelliJ 
and create a run configuration for run the main method in App.java.

Alternatively, if for any reason you prefer or need to do so (such as inability to install JRE locally), you can look 
into running Java on Docker:
https://www.jetbrains.com/help/idea/running-a-java-app-in-a-container.html#run_java_app_in_container

#### OUTPUT FOR "java -jar Fast-Meal-Service-API.jar test"

<p align="center">
    <img src="docs/img/TestSampleOutput.png" width="350" alt="requirements">
</p>

#### OUTPUT FOR "java -jar Fast-Meal-Service-API.jar"

<p align="center">
    <img src="docs/img/GuiFirstOutput.png" width="350" alt="requirements">
</p>

---

### RUNNING THE COMMAND-LINE GUI APP

1. You'll first be presented with options to handle ordering meals or else checkout.

<p align="center">
    <img src="docs/img/gui-walkthrough/1.png" width="350" alt="requirements">
</p>

2. When you select the first option, you'll then be presented with these options.

<p align="center">
    <img src="docs/img/gui-walkthrough/2.png" width="350" alt="requirements">
</p>

3. We'll select the first option which is to view meal menus and rules. You'll need to choose which meal type to view.

<p align="center">
    <img src="docs/img/gui-walkthrough/3.png" width="350" alt="requirements">
</p>

4. If you go back to the last menu now and choose to add a meal order, you'll see this.

<p align="center">
    <img src="docs/img/gui-walkthrough/4.png" width="350" alt="requirements">
</p>

5. Let's enter "Breakfast 1,2,3".

<p align="center">
    <img src="docs/img/gui-walkthrough/5.png" width="350" alt="requirements">
</p>

6. If you entered invalid output, you'll get an error message and a chance to try again.

<p align="center">
    <img src="docs/img/gui-walkthrough/6.png" width="350" alt="requirements">
</p>
<p align="center">
  <img src="docs/img/gui-walkthrough/7.png" width="350" alt="requirements">
</p>

7. You can view your current orders.

<p align="center">
  <img src="docs/img/gui-walkthrough/8.png" width="350" alt="requirements">
</p>

8. You can also choose to delete orders if desired. Select the index of the meal to delete. Invalid input is handled.

<p align="center">
  <img src="docs/img/gui-walkthrough/9.png" width="350" alt="requirements">
</p>
<p align="center">
  <img src="docs/img/gui-walkthrough/10.png" width="350" alt="requirements">
</p>
<p align="center">
  <img src="docs/img/gui-walkthrough/11.png" width="350" alt="requirements">
</p>

9. Lastly, back at the first menu, you can checkout (or exit) the app.

<p align="center">
  <img src="docs/img/gui-walkthrough/12.png" width="350" alt="requirements">
</p>

---

### THANK YOU FOR TAKING THE TIME TO LOOK OVER MY PROJECT. I HOPE YOU ENJOYED IT! 






