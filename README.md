# HappyTails - A Dog Walking App
**Happy Tails** is an Android application developed in Android Studio using Java. The app allows dog owners to find other dogs nearby, track walks using Google Maps API, and store walk details in an SQLite database.

## Features and Functionalities
Login or Register
![LaunchAppPage](Screenshot_20250208_182927_Happy_Tails.jpg)

Login Page:
![LoginPage](Screenshot_20250208_182931_Happy_Tails.jpg)

Register page 1: Enter Owner's information
![Register1](Screenshot_20250208_183227_Happy_Tails.jpg)

Register page 2: Enter Dog's information
![Register2](Screenshot_20250208_193436_Happy_Tails.jpg)

Track you walk using Google Maps
![StartWalk](Screenshot_20250208_182954_Happy_Tails.jpg)
![FinishWalk](Screenshot_20250205_164627_Happy_Tails.jpg)

Dog walk summary page that displays date, time walked, distance walked, dog name
![WalksPage](Screenshot_20250208_183131_Happy_Tails.jpg)

HappyTails friends page that displays current users in your city
![FriendsPage](Screenshot_20250208_190503_Happy_Tails.jpg)

Update your information page
![UpdateInfoPage](Screenshot_20250208_183211_Happy_Tails.jpg)

## Installation
### **1. Clone the Repository**  
```sh
git clone https://github.com/yourusername/happy_tails.git
cd happy_tails
```

### **2. Open in Android Studio**
Open Android Studio
Click File>Open
Select the happy_tails folder

### **3. Set Up Google Maps API Key**
Go to Google Cloud Console and create a new project.
Enable the Google Maps SDK for Android.
Generate an API key and copy it.
Open AndroidManifest.xml and add:
```sh
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY_HERE"/>
```

### **4. Run the app**
