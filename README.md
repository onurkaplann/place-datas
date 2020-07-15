https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=*&inputtype=textquery&fields=formatted_address,name,type,geometry&locationbias=circle:2000@41.994586517,-87.657610865&key=AIzaSyB6GjUHFlh2X8xy5PkOEjhQ5Hqo71O1igQ
ID,Date,Primary Type,Location Description,Arrest,Latitude,Longitude
8421488,01/01/2012 01:19:00 AM,ARSON,RESIDENCE PORCH/HALLWAY,False,41.994586517,-87.657610865
8423396,01/02/2012 10:48:00 PM,ARSON,RESIDENCE-GARAGE,False,41.873414354,-87.728269323


Determines location from latitude and longitude datas. By using this location, determines near places within 2 kilometers.

# File format and Google API Key

File should be in *csv* format
-
First line need to contains headers :

|ID | ... | Latitude | Longitude |
|-|-|-|-|
|1|...|40.821481|29.9198613|


Google API Key
-
You need to activate *Google Places API* and *Google Geocoding API*:

https://console.developers.google.com/

Then enter your key, enter number of data and set range:
```java
File file = new File("CSV_FILE_LOC"); // File which contains lat-long datas
String key = "AIzaSyCsIINddvmeN2H11Wbmu-2k_ViR8y1_Ps4"; // YOUR_API_KEY
String range = "2000"; // 2KM
File file2 = new File("places.txt"); // Near places in range with types, names and distances
int dataCount = 5; 
```

a sample place data format which Google's offered us:
```
{
         "formatted_address" : "Kabaoğlu Mahallesi, 41000 İzmit/Kocaeli, Türkiye",
         "geometry" : {
            "location" : {
               "lat" : 40.82272779999999,
               "lng" : 29.922092
            },
            "viewport" : {
               "northeast" : {
                  "lat" : 40.82407678029148,
                  "lng" : 29.9234409802915
               },
               "southwest" : {
                  "lat" : 40.82137881970849,
                  "lng" : 29.9207430197085
               }
            }
         },
         "name" : "Kocaeli Üniversitesi Rektörlük",
         "types" : [ "university", "point_of_interest", "establishment" ]
      }
```

places.txt format:
(ID,Type,Name,Distance from main location)
```
1,university,Kocaeli Üniversitesi Rektörlük,0.233
```
