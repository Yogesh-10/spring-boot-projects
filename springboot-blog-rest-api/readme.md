Development Process for JWT:
Step 1: Adding JWT Dependency
Step 2: Create JwtAuthenticationEntryPoint - This class will handle exception, that occurs 
        due to unauthorized access from resource
Step 3: Add jwt properties in application.properties file
Step 4: Create JwtTokenProvider - generate and validate jwt tokens
Step 5: JwtAuthenticationFilter
Step 6: Create JWTAuthResponse DTO
Step 7: Configure JWT in Spring Security Configuration
Step 8: Change login/signin API to return token to client

[spring security flow](https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.javainuse.com%2Fwebseries%2Fspring-security-jwt%2Fchap3&psig=AOvVaw3GG8BCLOTTAZhmTnv9L9jT&ust=1673183579588000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCPC_wdvEtfwCFQAAAAAdAAAAABAI)