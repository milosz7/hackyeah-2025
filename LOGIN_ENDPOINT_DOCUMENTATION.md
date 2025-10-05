# Login Endpoint Documentation

## Overview
The login endpoint provides secure authentication for users by validating email and password credentials. It returns user information upon successful authentication.

## Endpoint Details

### URL
```
POST /api/auth/login
```

### Request Body
```json
{
  "email": "user@example.com",
  "password": "userpassword"
}
```

### Request Validation
- **email**: Required, must be a valid email format
- **password**: Required, minimum 6 characters

### Response Format

#### Successful Login (HTTP 200)
```json
{
  "success": true,
  "message": "Login successful",
  "user": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "userType": "EXTERNAL",
    "phoneNumber": "+1234567890",
    "roles": [
      {
        "id": 1,
        "name": "USER"
      }
    ]
  },
  "token": null
}
```

#### Failed Login (HTTP 401)
```json
{
  "success": false,
  "message": "Invalid email or password",
  "user": null,
  "token": null
}
```

## Security Features

### Password Security
- Passwords are hashed using BCrypt algorithm
- Password comparison is done securely using Spring Security's PasswordEncoder
- No plain text passwords are stored or transmitted

### Account Status Validation
- Only active users can log in
- Inactive accounts are rejected with appropriate error message

### Error Handling
- Generic error messages to prevent user enumeration attacks
- Proper HTTP status codes (200 for success, 401 for authentication failure)
- Input validation with detailed error messages

## Implementation Details

### DTOs Created
1. **LoginRequest**: Contains email and password with validation annotations
2. **LoginResponse**: Standardized response format with success status and user data

### Service Layer
- `UserService.login(LoginRequest)`: Main authentication logic
- Password verification using BCrypt
- User status validation
- Exception handling for security

### Controller Layer
- RESTful endpoint with proper HTTP methods
- Input validation using `@Valid` annotation
- Appropriate HTTP status codes
- JSON request/response handling

## Usage Examples

### cURL Example
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "userpassword"
  }'
```

### JavaScript/Fetch Example
```javascript
const loginData = {
  email: 'user@example.com',
  password: 'userpassword'
};

fetch('/api/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(loginData)
})
.then(response => response.json())
.then(data => {
  if (data.success) {
    console.log('Login successful:', data.user);
  } else {
    console.log('Login failed:', data.message);
  }
});
```

## Error Scenarios

1. **Invalid Email**: Returns "Invalid email or password"
2. **Wrong Password**: Returns "Invalid email or password"
3. **Inactive Account**: Returns "Account is deactivated"
4. **Missing Fields**: Returns validation errors
5. **Server Error**: Returns "Login failed due to an internal error"

## Security Considerations

- The endpoint is publicly accessible (no authentication required)
- Password validation is secure using BCrypt
- Error messages don't reveal whether email exists
- Input validation prevents injection attacks
- Proper HTTP status codes for different scenarios

## Future Enhancements

- JWT token generation for session management
- Rate limiting for login attempts
- Account lockout after failed attempts
- Two-factor authentication support
- Audit logging for login attempts
