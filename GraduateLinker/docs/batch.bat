REM Mr. A. Maganlal
REM Computer Science 2A 2018-2022
REM This batch file is setup to be more robust than previous versions.

REM Turn echo off and clear the screen.
@echo off
cls

REM Good batch file coding practice.
setlocal enabledelayedexpansion

REM Paths for JDK
REM Remember to change JAVA_HOME to the correct path on your system
echo Change JAVA_HOME path

set JAVA_HOME=C:\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
REM Paths for JavaFX
set USE_JAVAFX=true
set JAVAFX_HOME=C:\javafx-17
set JAVAFX_MODULES=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media
set JAVAFX_ARGS=
if %USE_JAVAFX%==true (set JAVAFX_ARGS=--module-path %JAVAFX_HOME%\lib --add-modules=%JAVAFX_MODULES%)
echo %USE_JAVAFX%, %JAVAFX_ARGS%

REM Variable for error messages
set ERRMSG=

:VERSION
echo ~~~ Checking Version ~~~
javac -version
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error checking version"
    GOTO ERROR
)
java -version
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG="Error checking version"
    GOTO ERROR
)

pause
REM Move to correct folder.
echo Build script set to run in Project folder
cd ..

REM Variables for batch
set PRAC_BIN=.\bin
set PRAC_DOCS=.\docs
set PRAC_JDOC=JavaDoc
set PRAC_LIB=.\lib\*
set PRAC_SRC=.\src


REM Run project by running Main.
:RUN
echo ~~~ Running project ~~~
java %JAVAFX_ARGS% -jar .\dist\minproj.jar
IF /I "%ERRORLEVEL%" NEQ "0" (
    set ERRMSG=~~! Error running project !~~
    GOTO ERROR
)
GOTO END

REM Something went wrong, display error.
:ERROR
echo ~~! Fatal error with project !~~
echo %ERRMSG%

REM Move back to docs folder and wait.
:END
echo ~~~ End ~~~
cd %PRAC_DOCS%
pause