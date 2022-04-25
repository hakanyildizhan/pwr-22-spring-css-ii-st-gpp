# Computer Security and Cryptography II st. PWR 2022 Spring Group Programming Project Readme

Our group project work for the 2022 spring semester.

## Contributors

*  Bezawit Teklemariam
*  Grzegorz Zaborowski
*  Hakan Yildizhan
*  Marcin Umiński

## Project schedule

* ~~**March 21** (4th class): User stories, components diagram -> security aspects. sec. libraries.~~
* ~~**April 4** (6th class): Architecture, component diagram, schedule for alpha/beta/final~~

* ~~**April 25** (8th class): Alpha version: User can login, register, upload a file.
    * Minimal UI, 
    * Focus on login and register. 
    * Set up the AWS S3 and DynamoDB (can be mocked) 
    * Basic file upload functionality. 
    * Users have a max. space limit.~~

* **May 23** (11th class): Beta version: User can also delete and share a file, reset a password.
    * UI improvements
	* File upload functionality
    * Delete and share file functionalities 
    * Focus on role based authentication

* **Jun 13** (14th class): Final version: Fully functional product.
    * Complete UI
    * HTTPS support with SSL
    * Set up hosting on AWS EC2

## Feature list

*  This application is roughly a Google Drive clone, thus;
*  Lets users upload, download and share files with other **egistered** OR **guest** users
*  **Security features**: registration, identification, authorization - access control, role based (registered/guest)

## Tech stack

* Vue.js (Front-end)
* Node.js (Hosting, Request handling)  
* Java (REST API, data access)

# Target platform
- Web browsers (compatibility with Chrome, Safari, Opera, Firefox and Edge)

# Scalability requirements
- Given that the project requires free disk space in order to provide it to users, we assume that each 
user will need about 100MB of free space. As the free/test/student version of resources allow storing up to 20 GB of data
for free, we assume that the project might scale up to 200 users.
- Taking into account the above estimate, our endpoints should handle the load around 200 requests per minute