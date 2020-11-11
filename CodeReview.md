This is a running series of comments about code style issues found by RJW. Some of these are personal style preferences. In some cases I have made these changes to individual files so you can see examples. Typically I've only noted the first instance of a particular issue.

Please note that I haven't programmed in Java this century, so I may make some incorrect assumptions. In such cases, you are entirely entitled to make fun of me!

These are in the order I noticed them.

* Preference: Space after // on comments. It's easier to read. Regex to find/fix these (in Atom) is find \/\/([^ ]) and replace // $1

* Boilerplate comments should be replaced by something meaningful, for example:
```java
/**
 * Creates a new LimeLight object that borkborkbork (borkborkbork is my personal lorem ipsum dolor sit amet)
 */
```

* Preference: Blank line before/after block comments, it's more readable.

* Preference: Empty functions should not have a blank line in them. Either remove it, or better, replace the blank line with a comment that explains why it's empty.

* Preference: blank lines between functions. Again, it just makes it a little easier to read.

* How could this code be condensed?
```java
public void setLEDMode(double mode) {
    if (mode == 0 && getLEDMode() != 0) {
        limeTable.getEntry("ledMode").setNumber(0);
    } else if (mode == 1 && getLEDMode() != 1) {
        limeTable.getEntry("ledMode").setNumber(1);
    } else if (mode == 2 && getLEDMode() != 2) {
        limeTable.getEntry("ledMode").setNumber(2);
    } else if (mode == 3 && getLEDMode() != 3) {
        limeTable.getEntry("ledMode").setNumber(3);
    }
}
```
Well, how about...
```java
public void setLEDMode(double mode) {
    if (mode >= 0 && mode <= 3 && mode != getLEDMode()) {
        limeTable.getEntry("ledMode").setNumber(mode);
    }
}
```

* Or how about this one?
```java
public boolean limelightHasTarget() {
    if(tv.getDouble(0) == 1.0) return true;
    else return false;
}
```
First, we could clean up the style to make it more readable...
```java
public boolean limelightHasTarget() {
    if (tv.getDouble(0) == 1.0)
        return true;
    else
        return false;
}
```
Note that I added a space after the if in addition to reformatting the if/else for clarity. But even better is avoid the antipattern of if (boolean) return true else return false.
```java
public boolean limelightHasTarget() {
    return tv.getDouble(0) == 1.0;
}
```

* If a constant is used only once, there needs to be a comment where it is used that explains the meaning of its value. Why does tv.getDouble(0) == 1.0 mean we have the target? What are the possible other values? Document this stuff where it's used, that way the info will always be at your fingertips later.

* If a constant is used more than once, declare it in Constants.java, give it a descriptive name, and document it there. An example would be configuration constants that are used multiple times. I'd also consider moving all the "magic number" constants in the various files into Constants.java (such as the ones in Drivetrain.java) so they are easier to cross-reference and tweak.

* Preference: for simple functions, in particular getters and setters, I personally think it's ok to collapse them to one line, like this.
```java
public boolean limelightHasTarget() { return tv.getDouble(0) == 1.0; }
```

* Sometimes, it's worth using short variable names. There's nothing inherently wrong with the following code, but it does look awkward in the IDE, and wide lines are harder to read.
```java
@Override
public void periodic() {
  if(DebugConstants.debugMode) {
    Color detectedColor = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    if (detectedColor.blue > detectedColor.green && detectedColor.green > detectedColor.red || detectedColor.blue > 0.27) SmartDashboard.putString("DetectedColor", "Blue");
    else if (detectedColor.green > detectedColor.blue && detectedColor.blue > detectedColor.red) SmartDashboard.putString("DetectedColor", "Green");
    else if (detectedColor.red > detectedColor.green && detectedColor.green > detectedColor.blue || detectedColor.green < 0.5) SmartDashboard.putString("DetectedColor", "Red");
    else if (detectedColor.green > detectedColor.red && detectedColor.red > detectedColor.blue) SmartDashboard.putString("DetectedColor", "Yellow");
  }
}
```
So if you can find a variable name that's short and makes reasonable sense, in particular for code like this where the var is set and then immediately used, then it's a win. For example:
```java
@Override
public void periodic() {
  if(DebugConstants.debugMode) {
    Color hue = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red", hue.red);
    SmartDashboard.putNumber("Green", hue.green);
    SmartDashboard.putNumber("Blue", hue.blue);
    if (hue.blue > hue.green && hue.green > hue.red || hue.blue > 0.27)
      SmartDashboard.putString("DetectedColor", "Blue");
    else if (hue.green > hue.blue && hue.blue > hue.red)
      SmartDashboard.putString("DetectedColor", "Green");
    else if (hue.red > hue.green && hue.green > hue.blue || hue.green < 0.5)
      SmartDashboard.putString("DetectedColor", "Red");
    else if (hue.green > hue.red && hue.red > hue.blue)
      SmartDashboard.putString("DetectedColor", "Yellow");
  }
}
```

* Avoid braces around single statement if/else clauses.
```java
if (demoMode == true) {
    maxSpeed = demoSpeed;
} else {
    maxSpeed = workingSpeed;
}
```
would be better as:
```java
if (demoMode)
    maxSpeed = demoSpeed;
else
    maxSpeed = workingSpeed;
```
Note that (boolean == true) is a code smell. Finally, Java has a ternary operator, which means this could be simplified to:
```java
maxSpeed = demoMode ? demoSpeed : workingSpeed;
```

* Old programmer being paranoid. What happens if...
```java
default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
```
...happens during a match? Yeah, it shouldn't, but as the saying goes, "constants aren't, variables won't". The prudent thing to do with non-critical error conditions like this is only throw the exception when you're in debug mode. When you're running "in production", log the error to your console so you know it happened, and just return a safe value.

* OK, in this example, we have repeated function calls to *frc.robot.RobotContainer.DiskControlT.senseColor()*. There are two issues with this: first, it's inefficient, and second, what happens if somehow the color changes at some point during the execution of the if/else? Granted, in a single-threaded environment this isn't going to happen, but this is a code smell to be aware of in your future programming careers. So this code...
```java
@Override
public void execute() {
  frc.robot.RobotContainer.DiskControlT.DiskMotorController(0.25);
  if (frc.robot.RobotContainer.DiskControlT.senseColor().blue > frc.robot.RobotContainer.DiskControlT.senseColor().green && frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().red || frc.robot.RobotContainer.DiskControlT.senseColor().blue > 0.27) detected =2;
  else if (frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().blue && frc.robot.RobotContainer.DiskControlT.senseColor().blue > frc.robot.RobotContainer.DiskControlT.senseColor().red) detected = 3;
  else if (frc.robot.RobotContainer.DiskControlT.senseColor().red > frc.robot.RobotContainer.DiskControlT.senseColor().green && frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().blue || frc.robot.RobotContainer.DiskControlT.senseColor().green < 0.5) detected = 0;
  else if (frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().red && frc.robot.RobotContainer.DiskControlT.senseColor().red > frc.robot.RobotContainer.DiskControlT.senseColor().blue) detected = 1;
  if (lastDetected != detected) {
      colorChanges ++;
      lastDetected = detected;
  }
}
```
...might be better written like this (and there's another instance of this smell in GotoBall btw):
```java
@Override
public void execute() {
  frc.robot.RobotContainer.DiskControlT.DiskMotorController(0.25);
  Color hue = frc.robot.RobotContainer.DiskControlT.senseColor();
  if (hue.blue > hue.green && hue.green > hue.red || hue.blue > 0.27)
      detected = 2;
  else if (hue.green > hue.blue && hue.blue > hue.red)
      detected = 3;
  else if (hue.red > hue.green && hue.green > hue.blue || hue.green < 0.5)
      detected = 0;
  else if (hue.green > hue.red && hue.red > hue.blue)
      detected = 1;

  if (lastDetected != detected) {
      colorChanges ++;
      lastDetected = detected;
  }
}
```
Also, the above code has a bug in it. Can you find it?

* Additionally, you're repeating the color detection algorithm in two places. Better to do it once, in DiskControl where you are doing the actual sensing, and provide the result as an Enum. That way, you don't have to remember to change the code in two places.

* Beating a dead horse, as a general rule, if you call the same function more than once in a function, store the results in a variable and use that. It's more efficient. Take a look at GotoBall's execute() function for example. Also noticed a couple of times you could do it in DriveWithJoystick. Now admittedly, in this code the function calls are not that costly, but this is a good habit to get into.
