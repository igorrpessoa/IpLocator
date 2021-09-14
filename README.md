# IpLocator

- [How it works](#how-it-works)
- [Reference Documentation](#reference-documentation)
- [To Run](#to-run)


## How it works

This app enables you to inform the IP from a place you want to locate, and we will compare with the one you are right now!

It will give information like Country, City, Timezone and Local Time for both locations, and will compare the time difference between them.

*Since we have non-commercial usage for IPGeolocationAPI, we don't have access to URL calls;


### Reference Documentation

This is a Java + Angular project that is able to:

1. Detect the user public local IP;
2. Call the “https://ipgeolocation.io/” API to get information about the local IP and the IP informed on the input field.
3. Display the information for Your Location and the Location Informed
4. Calculate and display the total hours difference between both locations.


It will validate inputs:
- Non Null
- IPv4/IPv6 pattern

### To Run

To run the project you will have to:
1. Go to Iplocator folder
2. Run the back end with ```mvn spring-boot:run```
3. Go to IpLocator/src/main/ui folder
4. Run the front end with ```ng serve --proxy-config proxy.conf.json```
5. Access the application in localhost:4200


![alt text](https://raw.githubusercontent.com/igorrpessoa/IpLocator/main/src/main/resources/Iplocator.PNG)

Video link for preview: https://vimeo.com/605128164/86d97beb63

**This application was created by Igor Rodrigues Pessoa**
