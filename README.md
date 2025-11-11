# User Management

Microservice to create and login users with a DB using JWT tokens
## 📋 Requirements

- Java 11
- Maven 3.6+ o Gradle
- Data Base (H2)

## END POINTS

## Sign Up

POST /api/v1/users/sign-up

Description: Creates a new user account

Request Body:
```json
{
  "name": "John Duran",
  "email": "john.duran@gl.com",
  "password": "dasda",
  "phones": [
    {
      "number": 3292299,
      "cityCode": 1,
      "countryCode": "57"
    }
  ]
}
```
## Login

POST /api/V1/auth/login

Description: User Authentication

Request Body

```json
{
  "email": "john.duran@gl.com",
  "password": "passw123"
}
```
## 🛠 Configuration and Installation 

1. **Clone repository**:
```bash
git clone

2. Run UserMicroserviceApplication main method

3. you can check local Data Base (H2 Console) in the following Url: http://localhost:8080/h2-console

   Data JDBC URL: jdbc:h2:mem:auth
        User Name : sa
        Password  : blank

4. Swageer documentation could be checked in the following Url: http://localhost:8080/swagger-ui/index.html
