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
