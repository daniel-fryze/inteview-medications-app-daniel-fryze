# Medications Book - an interview app (how-to)

Hi. I have created this sample app, generally for the purpose of the recruitment process. However I had a great fun doing this for the last 'a couple of' hours. And I hope you will enjoy verifying it as well.

That's why I have also created this short 'how-to' document to make it easy for you to run it and smoke-test it.

So let's start.


## Table of contents

* Environments and URLs where applications are available for testing
* A quick look at what kind of components (and how) communicate to each other
* Tools, frameworks and technologies chosen for this fight
* How to run the application on the local machine after 'git pull'
* How to use the application - which button to click and which better not to
* Data storage options used - where our data is saved
* Rest API overview (to test the API itself if needed)


## Available testing environments

I have deployed the sample applications to publicly available servers, so that you can quickly run the app without the need to rebuild it, configure and install on local environments (anyway, this is also possible). They are available at the following URLs:

1. Client web application:
  * deployed to Heroku server
    * available at this URL: http://meds-app-daniel-fryze-web.herokuapp.com/
2. Backend REST service:
  * deployed to Heroku server as well (different app) 
    * available at this URL: http://meds-app-daniel-fryze-rest-api.herokuapp.com/medication
    * (detailed description on how to use the API in next sections)

(none of the above apps has any security enabled, so that you don't need any special credentials to log in)

Other URLs which can be useful for testing:

1. Access to Firebase store service at: https://meds-app-daniel-fryze-firebase-database.firebaseio.com
  * (login: temporary.mate@gmail.com, password: nothingtoadd7)
2. Access to database deployed to Amazon RDS:
  * (host: us-cdbr-iron-east-03.cleardb.net, port: 3306, user: bd513f1f4c0069, password: 0da13f37)
(it may happen that for the presentation purposes I will change the config to use H2 on the test env instead of RDS due to net performance reasons, cause recently when I tested it on separate machines i used to cause big delays)


## A quick look at what the components and how communicate with each other:

The general testing environments ecosystem currently deployed looks as follows:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xtl1/t31.0-8/12747377_10208875028578610_8906946411802289448_o.jpg)

It's just an overview. However, I hope we will have a chance to deep into these topics more during direct meeting. 


## An even quicker look at the tools I have used to implement this 'proof-of-concept' app:

It looks as on the picture below:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xpf1/t31.0-8/10265506_10208875028538609_6472442415265422439_o.jpg)

Here I hope even more to talk to you about it deeply (what and why).


## Let's have a look how to run the application locally after pulling from Git:

In order to run the app locally we need to do the following steps:

### Client web application:

Prerequisites:

* All you need is your local machine with any preferable operationg system, connected to the internet, any web browser and Node.js environment installed locally (install guide: https://nodejs.org/en/download/package-manager/).

Steps to follow:

1) Clone this Git repository to the local hard drive.

2) Navivate to the folder named 'meds-app-daniel-fryze-web' (the one containing i.e. files: 'package.json' and 'server.js') which resides inside the project parent directory.

3) Install all _npm_ dependencies required by the project using the command:

```
npm install
```

4) Install all _bower_ dependencies required by the project using the command:
```
bower install
```

5) Run the build process of the project using the command:
```
gulp
```

6) After these three commands executed successfully, we should see the followin message in the console:
```
Server started http://localhost:8888
```

If it's there, let's open the browser and navigate to this URL.

If everything is OK, we should see the web app.


### Server REST API application:

Prerequisites:

* All you need is your local machine with any preferable operationg system, connected to the internet, Maven 3 or higher installed on it as well as Java 8 installed and set as current JAVA_HOME.

Steps to follow:

1) Clone this Git repository to the local hard drive.

2) Navivate to the folder named 'meds-app-daniel-fryze-rest-api' (the one containing i.e. file 'pom.xml') which resides inside the project parent directory.

3) Build the project code using the command:
```
mvn clean package install -DskipTests=true
```

(running unit tests and integration tests will be described in detail in the following sections)

3) Run the application (initially in local version: using in-memory db and mock firebase) using the following command:
```
mvn spring-boot: run -Dprofiles=dev-mock
```

To run the application in _dev_ mode but connected to local MySQL database + test Firebase account run the alternative command (however in this case to make it work, you need to configure and run local MySQL database as well):
```
mvn spring-boot: run -Dprofiles=dev-integration
```

If everything goes ok, you should see among the logs the following message:
```
Tomcat started on port(s): 9200 (http)
```

If so, you can open the web browser and navigate to the following URL:
http://localhost:9200/medication?namePattern=a?partialSearch=true
```
[]
```

I you see the following content (not any error), it means that the REST API is deployed successfully. Otherwise something must have gone wrong.


## A short tutorial on how to use the app in general:

1. Dashboard screen:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xat1/t31.0-8/12017569_10208874799772890_3538486433482151429_o.jpg)

This screen is used to trace in real time the statistics on data stored in Firebase. The data in section **1** are the total counters of reads in the Firebase database. They are bound using three-way binding so that any change in Firebase is immediately reflected on the form. The same applies to sections **2** and **3** where the whole array is bounded to the model in Firebase, so that not only changes to data are reflected but also if a new data appears (for examples, many reads for a particular medication), the statistics are automatically updated on the screen without reload need.

2. Search screen - part 1:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xta1/t31.0-8/10271227_10208874799052872_1200362000970157163_o.jpg)

The second screen is a typical search engine. Using control **2** we can search for medications in out REST API using patterns, so that we don't need to type the full name, only a part is enough to invoke a successful search. The button **3** invokes the search process, and the button **4** clears the whole data on the screen. Number **1** points to the main menu of the application.

3. Search screen - part 2:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xlp1/t31.0-8/12747892_10208874799092873_1008472057306191566_o.jpg)

When the searched has been invoked, we get the appropriate message in the left-bottom secion of the screen. On above screen, there were no entities found for this search query, so the appropriate message is shown to the user **3**. We have also visible the current search query, for which the search has been invoked in section. It's useful, because we can change the query afterwards for the next search and even then we still see what query the visible results correspond to.

4. Search screen - part 4:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xpt1/t31.0-8/12710982_10208874799132874_2674602805845592070_o.jpg)

If the query results in some entities being found in the database, we can see them in readable clickable array on the left side **2**. What's important this search doesn't invoke the Firebase reads counters update. It's only a search of basic information about the medications: name and producer only, so we can invoke them any time we want.

5. Search screen - part 5:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xpt1/t31.0-8/12716206_10208874799452882_7743891688416887822_o.jpg)

When we click on one of the found items in the list, we load the details for a selected med item. The details appear in the right-bottom section of the screen **1**. What's important, exactly now the Firebase read counter is invoked. We can also see (the same way like in the previous phase of searching) the name of the medication for which the data-loading has been invoked **2**. What's more, we can also see the current number of time this particular medication has been loaded in the system so far **3** (this data is also real-time bound to the value in Firebase, so if anybody else tries to load the details about this med, we will see the counter updated without the reloading need. We can also manually refresh the loaded data, so that the other data (which origin from REST API and database) can be updated if they are changed in the meantime by someone else. Each click of this button increases the number of reads for this medication in Firebase database of course.

6. Adding screen:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xlp1/t31.0-8/s2048x2048/12719313_10208874799412881_1591354570528415955_o.jpg)

This is a typical screen for adding the entity to the system via REST API. We have input controls (each of them in the placeholder informs us what kind of data it expects to get). We have the most important input (medication name) **1** which is required. We have validation of course on all fields. And the button which invokes the adding operation **2**.

7. Modal notifications - part 1:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xlp1/t31.0-8/s2048x2048/12719388_10208874799532884_3794219280967416370_o.jpg)

If anything goes wrong during the process of adding the new entity we get the information **1** about the process failure with the detailed description of the failure's reason from the server **2** (we get this in JSON in response), different for each error scenario. In this particular case on the screen, we got the error, because we were trying to add the new medication with the name already existing in the database. It's worth to mention, that such modals are displayed for any possible anticipated or not error situation when communicating with REST API.

8. Modal notifications - part 2:

![alt tag](https://scontent-vie1-1.xx.fbcdn.net/hphotos-xlp1/t31.0-8/s2048x2048/12719116_10208874799732889_891967589358922207_o.jpg)

But of course, not everything always goes wrong. If the operation is successful we alse get the appropriate success message **1**.


## A quick look at our storages:

1. Database diagrams:

There's not a lot to talk about here at the moment :)

2. Firebase storage structure:


## REST API Overview:

a
a
a
a

In case of any other questions, I am open to discussion in any kind and at any time. It's never enough to talk about software.
Thank you a lot giving me a chance to have some fun with new tools and technologies.
Sorry for all the spelling or grammar mistakes I have done.

Looking forward to hear from you.

Daniel.
