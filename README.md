# E-commerce-spring-mvc
 <!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->

<!-- TABLE OF CONTENTS -->
## 📝 Table of Contents

- [About The Project](#about-the-project)
- [Built With](#built-with)
- [Getting Started](#getting-started)
  - [Setup](#setup)
- [Acknowledgments](#acknowledgments)

<!-- ABOUT THE PROJECT -->
## About The Project

A Tech E-commerce application with admin panel where admins can add or edit products and admins, Authentication for customers with email verfication, and locking meachanism for accounts when they exceed maximum number of allowed login count.

Custmomer can checkout Products added to cart and pay by card which is provided by another payment service.

### Built With

This project is built with the following Technolgies.

* [![Spring][Spring]][Spring-url]
* [![Spring-boot][Spring-boot]][Spring-boot-url]
* [![Hibernate][Hibernate]][Hibernate-url]
* [![Mysql][Mysql]][Mysql-url]
* [![Bootstrap][Bootstrap]][Bootstrap-url]
* [![Thymeleaf][Thymeleaf]][Thymeleaf-url]

<!-- GETTING STARTED -->
## Getting Started

### Setup

To run this project you need to have Installed: 
* Docker to get the payment server and mail server running.
* local MySQL server for the database of the application.

Then run the following command.
```
docker compose up
```
This will start the following:
* Payment Service on localhost:8082
* local mail server UI on localhost:1080
* Mail smtp server on localhost:1025

Next go to properties file and configure the url, username, password of the database

```
Example of the default configuration

spring.datasource.url=jdbc:mysql://localhost:3306/eCommerce
spring.datasource.username=root
spring.datasource.password=P@ssw0rd
```

Next you should import the project in your IDE of choice and run the spring-boot application.

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

This project couldn't have been possible without the team

- Mohamed Bakr
- Ibrahem Khaled

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png

[Spring]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[Spring-boot]: https://img.shields.io/static/v1?style=for-the-badge&message=Spring+Boot&color=6DB33F&logo=Spring+Boot&logoColor=FFFFFF&label=
[Spring-boot-url]: https://spring.io/projects/spring-boot/
[Hibernate]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Hibernate-url]: https://hibernate.org/
[MySQL]: https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white
[MySQL-url]: https://www.mysql.com/
[Bootstrap]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[Thymeleaf]: https://img.shields.io/static/v1?style=for-the-badge&message=Thymeleaf&color=005F0F&logo=Thymeleaf&logoColor=FFFFFF&label=
[Thymeleaf-url]: https://getbootstrap.com

