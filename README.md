# Auth Demo
Spring Boot authentication demo project.

## Current features

- Spring Boot backend runs on localhost:8080
- POST /api/auth/register receives JSON data
- Register endpoint saves a user to MySQL
- Uses Spring Web, Spring Data JPA, and MySQL Connector

## Current request example

POST /api/auth/register

```json
{
  "email": "bartukilic011@gmail.com",
  "password": "123"
}
```
## Next Steps:
- Add password hashing
- Add login endpoint
- Add simple frontend pages
- Add Docker
- Add Kafka event publishing