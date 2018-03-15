# Bruinstrooms

## Description

An Android application allowing users to rate restrooms at UCLA along with
post reviews for others to see. Users will also be able to view restroom
amentities such as handicap accessibility, number of sinks, mirrors, etc.
In the event of emergency such as clogged toilet or facuet that won't turn off,
users will be able to send emergency alerts to those viewing the restroom or
even to maintenance for repair. 

The frontend was created in Android Studio and backend utilized Firebase.

We are looking to expand functionality to include geolocation for finding
restrooms nearest you.

## Setup

Clone directory and you're good to go. Ensure target API is for your system to ensure proper working. 
If you are adding a new restroom, run the Python3 script with
```bash
python3 RestroomGenerator.py
```
to generate the syntax for the restroom to add a new restroom in the Firebase Database.

Carefully add this in Main Activity in the `initializeRestrooms()` call under the previous other calls
of the same format. **THIS IS IMPORTANT:** Comment out the other restroom initializations that were there
previously including the `.setValue()` calls on that restroom. This step is vital as if you do not do this,
you will wipe out **ALL** ratings of the restrooms previously. 

## Deploying

Remove `initializeRestrooms()` call from MainActivity.java in `onCreate()` or Restroom Data will be wiped 
out every single time. 

Contact Brian to update database permissions *before* deploying. 

## Completed:
Updated March 15, 2018

### Home Page
- Recycler View displays all restrooms with basic information (Vince)
- Loads the restrooms into an array List for Recycler View (Brian) 
- Allow users to click one restroom and link it to the page that shows all of it's data. (Vince)
- ~~Load image of restroom building to the template icon (Vince)~~
- Show star rating on the home page next to each restroom (Brian)

### Restroom Page
- Page shows basic information about restroom with amentities (Brian)
- allows users to submit comments and put their names (Brian)
- Reviews have a timestamp so reviews can be ordered by time (Brian) 
- Send Emergency feature (Brian)
- Link the View Reviews button to the Reviews Page (Vince)
- ~~Load image of restroom building to template icon (Brian)~~
- Load map image of restroom to template icon (Vince)
- ~~Use Helper class to get address depending on buiding name, link to Map activity (Brian)~~
- Add to Favorites (Vince)
- ~~Share Restroom~~ 

### Reviews Page
- Backend work done for loading in the Reviews for that restroom into an ArrayList (Brian)
- Add recycler adapter to recycler view the page for the reviews (Vince)
- Indicate thtat there are no reveiws yet if array list size is 0 (Vince)
- make a separate array list in a static Favorites class to store the restrooms that have been favorited (Vince)
- recycler view for the ones in this array list, otherwise say "No Favorites" (Vince)

## Future Ideas:

- Bluetooth chat room feature for users to converse while they are in the same restroom
- low-power Bluetooth beacons in bathrooms around select buildings, so our users can use 
our app to detect the number of restrooms around them and the proximity of those restrooms
