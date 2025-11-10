```mermaid
sequenceDiagram
participant C as Cliente
participant AC as AuthController
participant US as UserService
participant UR as UserRepository
participant JWT as JwtUtil
participant PE as PasswordEncoder

    C->>AC: POST /api/auth/login
    AC->>US: login(loginRequest)
    
    US->>UR: findByEmail(email)
    UR-->>US: Optional<User>
    
    alt Usuario no existe
        US-->>AC: InvalidCredentialsException
        AC-->>C: 401 Unauthorized
    else Password incorrecto
        US->>PE: matches(password, encodedPassword)
        PE-->>US: false
        US-->>AC: InvalidCredentialsException
        AC-->>C: 401 Unauthorized
    else Credenciales válidas
        US->>PE: matches(password, encodedPassword)
        PE-->>US: true
        US->>JWT: generateToken(email)
        JWT-->>US: newToken
        US->>UR: save(userWithNewToken)
        UR-->>US: updatedUser
        US->>US: getUserByToken(newToken)
        US-->>AC: LoginResponse
        AC-->>C: 200 OK + User Data
    end
```
