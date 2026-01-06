# Monolit 
**Java Spring Monolith with 2FA Authentication**

##  About
Monolit is a Java Spring-based monolithic application that demonstrates **user authentication with Two-Factor Authentication (2FA)**.  
This project was created to practice building secure backend systems using Spring, JPA, and modern authentication methods.

---

## 🔧 Technologies
✨ **Stack:**  
- Java 8+  
- Spring Boot  
- Spring Security  
- Hibernate / JPA  
- MySQL
- Maven  
- Two-Factor Authentication (TOTP / email-based)

---

## 🚀 Features
-  User registration and login  
-  Two-Factor Authentication (2FA) for enhanced security  
-  Database integration via JPA/Hibernate  
-  Structured backend ready for extension into a full CRM SaaS  

---

## 📥 Installation & Setup

1. **Clone the repository**
```bash
git clone https://github.com/FedortsovDmytro/Monolit.git
cd Monolit
Configure Database

For PostgreSQL or others, update application.properties with your database config

Build with Maven

bash
mvn clean install
Run the application

bash
mvn spring-boot:run
 Usage
Open the application in your browser or via Postman

Register a new user

Log in and follow the 2FA setup (TOTP or email code)

Access secure endpoints once 2FA is verified

 What I Learned
✔ Implementing Two-Factor Authentication with Spring Security
✔ Structuring a Java monolith project for maintainability
✔ Integrating JPA/Hibernate for database operations
✔ Secure password storage and user authentication flows
✔ Applying principles from Effective Java and Spring best practices

 Roadmap (Planned)
 Add REST API endpoints for external services

 Integrate frontend UI

 Add unit and integration tests

 Deploy to cloud (Heroku / AWS / Docker)


Contact
Dmytro Fedortsov
 GitHub: https://github.com/FedortsovDmytro
