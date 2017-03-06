# Product Introduction #
Athena is a crowd-based safety mobile application that focuses on the prevention, reaction and salvation of human caused emergencies. Athena crowd-sourced dangerous area that has been recorded by its community and alert it’s user through periodic tracking, alerting the users when they are near. Particularly useful for single traveller in a foreign country. During emergency, Athena has an alarm system that can be activated both automatically and manually to request help from nearby users and to the pre-set list of contacts. Athena also comes with vital information such as nearby police station and hospital for every country. In the case of no internet connectivity, partial help information such as emergency hotline is also available for the user.

# 1. User Story #

To keep track of what the users expect from our mobile application. Admin perspective will be considered in the later stage due to potential conflict against the viewing and accessing of users tracking information. 
User Stories [Completed]

*	Users can be on high alert to be ready for any form of assault. 
*	High alert should be able to activate the emergency alert if the users has no response. 
*	Users can make use of the emergency alert to scare off the attacker. 
*	Users can pre-set the numbers of NOK in the application.
*	Users can cancel off the emergency alert. 
*	Users can choose between to cancel because of false alarm or they are safe again. 
*	Users will have access to vital information such as nearest hospital or local authority numbers when in need.
*	Users can edit the time and range of the tracking based on their preference. (range no longer included
*	Users can see if area is dangerous.
*	Users can record and alert others if the area is dangerous.
*	Users can report false reporting of danger area.
*	Users can still use partial app of the users.
*	The system can automatic send out help signals to nearby users. 
*	The system can automatic can capture and send out vital information in the case of an assault.
*	Users can easily trigger the emergency alert at anywhere with just 1 step. 
*	Users can still make use of the app at offline mode.

User Stories [Cancelled]
*	Users can make use of the application without connectivity.
*	Users can still know dangerous area without connectivity.
*	Users can still track their location without connectivity.
*	Users can still use high alert without connectivity
*	Users can still trigger and send out emergency alert without connectivity.

# 2. Features #

The features of this application are separated into 3 different areas that focus on pre-incidents (prevention measures), during-incidents (emergency features) and post-incidents (reporting and help).

## 2.1. Prevention ##
### 2.1.1. Danger Zone ###
The danger zones are circles shown in the Google map. Those areas are mark with past records of emergency activation (red circle) and reported danger zone (yellow circle). Danger zone will have additional information that provides the users with more information of why the zone is dangerous. Based on the current location, the app will be able to identify to danger level to the user (number of overlap). Danger zone can also be reported once by each user. Once a zone has been reported more than 10 times, it will be taken off the map. 

To prevent the overloading of information, the team has created the loading of danger area such that only information within 20km (a country size, most than enough to pre-planned journey) of the user and in happened in the recent half a year will be loaded. These setting and the idea of a reported danger zone are decided through users’ feedback session with the targeted audience. (Females of age 20-40).

### 2.1.2. Tracking System ###
The tracking system will automatically track the user periodically. The interval of each location tracking can be adjusted through advance option in the setting page. Interval are split into 3 different type, mainly standard tracking, alert mode tracking and emergency tracking. To ensure that the tracking will not be affected by the existing of the app, the tracking are construct using alarm manager and service. This ensures that tracking will still be running in the background when the users are not using the app. The tracking will only stop when the users kill the process of the application.

### 2.1.3. Help Information Display ###
Help information page will always display the route to nearest hospital or police station and important local authority contact information. The help information will be generated based on the last track location of the user. This information are gathered personally by the team and not done through API. Information is also compared with various government website to ensure the accuracy.

### 2.1.4. •	Contact Management ###
Allow users to manage their contacts that will be contacted during emergency.

## 2.2. Emergency ##
### 2.2.1. High Alert Mode ###
Tracking are done more frequently. For a user set interval, the application will also prompt a check to the users through vibration. The check is to be answered by the user within user set count down. If the user failed to response 2 times consecutively, emergency alert will be triggered.

### 2.2.2. Emergency Mode ###
The emergency mode can be activated by sliding up on the main activity or by the high alert. Upon activation, the emergency mode will track current location of the user and send the recorded information to the NOK via email and SMS (with local authority information). At the same time, siren will also be activated with speaker mode on loudest volume. To de-activate the emergency, user need to key in passcode that they have set for the application. (Passcode is suggested by the users’ feedback session, in the case if the attacker wants to de-activate the alarm)

## 2.3. Post-Incident ##
### 2.3.1. Cancellation of Emergency Mode ###
When the user is safe again, user will be directed to the help information to seek for help.

### 2.3.2. Cancel due to False alarm ###
When the user declares that it is false alarm, user will be redirect back to main page. The emergency will not be considered in GIS record.

### 2.3.3. Emergency List Page ###
Allow users to view their emergency record that they have activated before. Each emergency record will have the tracking information throughout the activation of the emergency. This allows the users to pass the information clearly to the authority in the case if they need it. Time and date in this page are all set to the time zone of the activated. For example, first activation is at Hong Kong, the time shown will be Hong Kong time of the activation and if second activation is at Singapore, time shown on that record will be Singapore time.

# 3. Stages of Development #

## 3.1. First Stage (Without web services) ##
* Tracking System [High]
* High Alert Mode [Medium]
* Emergency Alert (Without no connection feature & SOS to nearby users) [Medium]
* Help information Page [Medium]
* Basic Cancellation (Without choices) [Low]

## 3.2. Second Stage (With web services to store tracking info) ##
* User Registration w/ Facebook & Google [High]
* Emergency Alert (With SOS to nearby users) [Low]
* Cancellation of Emergency Mode (With the two choices) [Medium]
* Notification System to Android (Ask user for response / running in background) [Low]
* Facebook Sharing [Low]

## 3.3. Third Stage (GIS) ##
* Danger zone features w/ google map. [High]
* Wearable Tech [Medium]
* Backend admin functions (by front end website) [Low]
* Solve all connectivity issues. [Low]
