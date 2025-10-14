# A01 GROUP 4 iScam 

## Presentation
#### 🌐 Running the Website Locally

To view the website locally:

1. Navigate to the `fittrack-website` folder:

```bash
cd fittrack-website
```

2. Then open the `index.html` file:

**macOS/Linux:**
```bash
open index.html
```

**Windows (Command Prompt):**
```cmd
start index.html
```

## Docs
- [Presentation](a01-g04-iscam/fittrack-website/index.html)
- [i0_Vision_Statement](a01-g04-iscam/docs/i0_A01_G04_Vision_Statement.md)  
- [i0_Retro](a01-g04-iscam/docs/i0_A01_G04_Retro.md)  
- [Architecture Diagram](a01-g04-iscam/docs/ARCHITECTURE.md)  
- [i3 Retrospective](a01-g04-iscam/docs/RETROSPECTIVE.md) 

## Project Purpose
FitTrack is an app designed for physically active people to track their calorie intake and exercise amount.
  
## Application Running
Our app used:
- Gradle 8.2.0
- Android 34
- JDK 17
 
To run the app, make sure Android Studio says 'app' in the top middle of the screen, and then press the green triangle next to it. If there are any issues, syncing Gradle may help.

## Application Usage  
  
As of Iteration 3 you may register a new account at the starting screen.
If you would prefer to use a pre-registered account, you may use:
- Username: bob
- Password: password123

## Application Testing
Fittrack includes unit testing, integration testing, and system UI testing. 
To access the tests you may navigate to src/test for unit and integration tests, and src/androidTest for system tests.

There are provided files for AllTests (Unit and Integration) and AllSystemTests (System UI Testing) that may be ran to run all the included testing. 

**Note:** System Testing is intensive on the Android Emulator. To increase reliability of testing, animations are disabled during testing per Espresso's [documentation](https://developer.android.com/training/testing/espresso/setup#set-up-environment). If you are experiencing slowness running all System Tests in succession, it is recommended to run them individually to ensure reliability.
