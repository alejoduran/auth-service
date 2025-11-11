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
## 📋 API Examples

### Sign-Up 

### POST Request
```bash
curl -X 'POST' \
  'http://localhost:8080/api/v1/users/sign-up' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "John Duran",
  "email": "john.duran@gl.com",
  "password": "Alejodu12",
  "phones": [
    {
      "number": 3292299,
      "cityCode": 1,
      "countryCode": "57"
    }
  ]
}'
```

### Response:

```json
{
  "id": "bcbbed6c-a3ab-456f-a5f7-6619beb646df",
  "created": "2025-11-11T11:28:48.06817",
  "lastLogin": "2025-11-11T11:28:48.019756",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmR1cmFuQGdsLmNvbSIsImlhdCI6MTc2Mjg3ODUyOCwiZXhwIjoxNzYyODgyMTI4fQ.plee3bUALoWMM5RJFbMc3oD63kCk-_pPpx23xu2CcKI",
  "isActive": true
}
```


### Login 

### POST Request
```bash
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/login' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "john.duran@gl.com",
  "password": "Alejodur12"
}'
```

### Response Body:

```json
{
  "id": "f60b4048-ee67-42df-bd6e-bf07e7c310e0",
  "created": "2025-11-11T11:35:38.345572",
  "lastLogin": "2025-11-11T11:36:06.393477",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmR1cmFuQGdsLmNvbSIsImlhdCI6MTc2Mjg3ODk2NiwiZXhwIjoxNzYyODgyNTY2fQ.d5h6C9d-96MM7Uz4Bpf7xKtSk6tDkfwh_YuDJHqcUr4",
  "isActive": true,
  "name": "John Duran",
  "email": "john.duran@gl.com",
  "password": "$2a$10$9y7/Z8L22sorRgoo4iO0dOCYBTi6RwC8XmNnZgZEmho6H6zRpv.Km",
  "phones": [
    {
      "number": 2314432,
      "cityCode": 1,
      "countryCode": "57"
    }
  ]
}
```



## 🛠 Configuration and Installation 

1. **Clone repository**:
```bash
git clone git@github.com:alejoduran/auth-service.git
```

2. Run UserMicroserviceApplication main method

3. you can check local Data Base (H2 Console) in the following Url: http://localhost:8080/h2-console

   Data JDBC URL: jdbc:h2:mem:auth
        User Name : sa
        Password  : blank

4. Swageer documentation could be checked in the following Url: http://localhost:8080/swagger-ui/index.html
