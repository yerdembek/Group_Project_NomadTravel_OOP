# Group_Project_NomadTravel_OOP
Nomad Travel" project documentation v0.1 beta

Introduction
The Nomad Travel project is aimed at creating a convenient and functional catalog for selling tours within Kazakhstan. Its goal is to simplify the search and interaction with travel agencies for users, as well as to provide the administrator with effective tools for data management

Project Objectives
- Simplify user interaction with travel agents.
- Ensure transparency of information.
- Minimize manual operations through search and filter automation.

Objectives (Problems)
1.	Working with the user
(1) Validation of the data entered by the user (e.g. correctness of phone, mail format).
(2) Handling incorrect inputs - the user should be notified of input errors.
(3) Providing a simple user interface for interacting with the catalog.
(4) Organization of a user-friendly interface for searching by various criteria (by name, city, service).
(5) Working with multiple users (e.g. different roles - administrator and user).
(6) Keep a log of user actions (e.g., what was added/removed).
2.	Data handling
(1) Create travel agency objects with fields (name, city, services, contact information).
(2) Implement methods for adding and removing travel agencies to the directory.
(3) Exception handling when adding/removing non-existent objects.
(4) Storing travel agency information in a collection (e.g., a list).
(5) Create a method to search for a travel agency by name.
(6) Implementation of a search on a portion of the travel agency's name.
(7) Realization of filtering by city (for example, all travel agencies in Almaty).
(8) Implement filtering by service (e.g., all travel agencies that offer tours).
(9) Realization of search by several criteria at the same time (for example, by city and service).
(10) Sort travel agencies by name, city or other parameters.
(11) Implementation of a method for counting the number of travel agencies in a catalog.
(12) Adding a "rating" field for each travel agency (e.g. for sorting).
(13) Method implementation for filtering by rating.
Ability to edit travel agency data.
3.	Saving and downloading data
(1) Implementation of saving catalog data to a file (e.g., text file or JSON).
(2) Implementation of loading data from a file at program startup.
(3) Error handling when reading/writing to a file.
(4) Saving data after each catalog change.
(5) Formatting data for easy storage and reading (e.g., CSV or JSON).
(6) Protection against data loss in case of program failures.
4.	Performance and testing
(1) Optimize data search and filtering (if the number of travel agencies becomes very large).
(2) Implementation of a method to quickly clean up a directory (delete all data).
(3) Implementing tests to verify that methods work (e.g., via JUnit).
(4) Checking the correctness of filters and sorting on large amounts of data.
(5) Manual and automatic testing of various catalog scenarios.
5.	Extensions (for the future)
(1) Adding database functionality (e.g. SQLite or MySQL).
(2) Implement a web interface to interact with the catalog (e.g., via Spring Boot).
(3) Adding authorization for users (introducing a login and password system).
(4) Possibility to obtain statistics (for example, how many travel agencies in which city).
(5) Integration with external services (e.g. Google Maps to display travel agencies on a map).
(6) Ability to add images for travel agencies (e.g. logos or photos).
(7) Create a report on all travel agencies with the ability to export it to PDF.

Scope of application
The catalog will be used by individuals and travel agencies to search for tours, assess their quality and interact with organizations providing them. Main audience: domestic tourists of Kazakhstan.

Stages of development
1.	Research and refinement of requirements.
2.	Creating basic functionality (catalog, search, filters).
3.	User Interface Development.
4.	Testing and bug fixing.
5.	Adding additional features (rating, integration with maps).
