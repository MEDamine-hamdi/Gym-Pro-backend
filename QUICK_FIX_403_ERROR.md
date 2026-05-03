# 🚨 URGENT FIX: Your 403 Error

## THE PROBLEM
You're using the **WRONG URL**!

❌ **INCORRECT:** `http://localhost:8081/api/auth/login`  
✅ **CORRECT:** `http://localhost:8081/auth/login`

The endpoint does NOT have `/api/` prefix. The `/api/` prefix is only for:
- /api/clients
- /api/employees
- /api/bookings
- /api/sessions
- /api/rooms
- /api/offers
- /api/stats

But **Auth endpoints** (`/auth/login` and `/auth/register`) do NOT use `/api/`

---

## CORRECT POSTMAN SETUP

### Login Request:
```
Method: POST
URL: http://localhost:8081/auth/login
Headers:
  Content-Type: application/json

Body (raw JSON):
{
  "email": "reception1@gym.com",
  "password": "123456"
}
```

### Register Request:
```
Method: POST
URL: http://localhost:8081/auth/register
Headers:
  Content-Type: application/json

Body (raw JSON):
{
  "username": "reception1",
  "email": "reception1@gym.com",
  "password": "123456",
  "roles": ["receptionist"]
}
```

---

## WHAT YOU NEED TO DO RIGHT NOW

### Option 1: Test in Postman (Recommended)
1. **Register first** (if user doesn't exist):
   - POST to `http://localhost:8081/auth/register`
   - Use the body above with role: `["receptionist"]` or `["user"]`
   
2. **Then Login**:
   - POST to `http://localhost:8081/auth/login`
   - Use your email/password
   - Get the JWT token from response

3. **Use Token for Protected Endpoints**:
   - Add header: `Authorization: Bearer <your_token>`
   - Access protected resources

### Option 2: Quick Test with curl (Windows PowerShell)
```powershell
# Register
$body = @{
    username = "reception1"
    email = "reception1@gym.com"
    password = "123456"
    roles = @("receptionist")
} | ConvertTo-Json

$response = Invoke-WebRequest -Uri "http://localhost:8081/auth/register" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $body

Write-Host "Registration Response: $response"

# Login
$loginBody = @{
    email = "reception1@gym.com"
    password = "123456"
} | ConvertTo-Json

$loginResponse = Invoke-WebRequest -Uri "http://localhost:8081/auth/login" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $loginBody

$token = ($loginResponse.Content | ConvertFrom-Json).token
Write-Host "Login successful! Token: $token"
```

---

## COMPLETE ENDPOINT MAPPING

| Method | URL | Purpose | Auth Required |
|--------|-----|---------|---|
| POST | `/auth/register` | Register new user | ❌ No |
| POST | `/auth/login` | Login & get JWT | ❌ No |
| GET | `/api/clients` | Get all clients | ✅ Yes (ADMIN/RECEPTIONIST) |
| POST | `/api/clients` | Create client | ✅ Yes (ADMIN/RECEPTIONIST) |
| GET | `/api/employees` | Get all employees | ✅ Yes (ADMIN only) |
| POST | `/api/employees` | Create employee | ✅ Yes (ADMIN only) |
| GET | `/api/rooms` | Get all rooms | ✅ Yes (ADMIN only) |
| POST | `/api/rooms` | Create room | ✅ Yes (ADMIN only) |
| GET | `/api/sessions` | Get all sessions | ✅ Yes (ADMIN/RECEPTIONIST) |
| POST | `/api/sessions` | Create session | ✅ Yes (ADMIN only) |
| GET | `/api/offers` | Get all offers | ✅ Yes (ADMIN only) |
| POST | `/api/offers` | Create offer | ✅ Yes (ADMIN only) |
| POST | `/api/bookings/{clientId}/{sessionId}` | Create booking | ✅ Yes (ADMIN/RECEPTIONIST) |
| GET | `/api/stats/overview` | Get stats | ✅ Yes (ADMIN only) |

---

## WHY YOU GOT 403

When you access `/api/auth/login`:
1. Spring Security doesn't recognize this endpoint
2. It's not in the permitAll() list
3. No authentication token provided
4. Result: **403 Forbidden**

When you use `/auth/login` (correct URL):
1. Spring Security matches the pattern `/auth/**`
2. This is explicitly permitAll()
3. Authentication is processed normally
4. Result: **200 OK + JWT Token**

---

## QUICK SUMMARY OF ALL CHANGES MADE

✅ **Files Modified:**
1. `application.properties` - Added `app.dev-mode=false`
2. `ERole.java` - Added `ROLE_RECEPTIONIST`
3. `OfferController.java` - Changed mapping from `/api/booking` to `/api/offers`

✅ **Files Created:**
1. `GlobalExceptionHandler.java` - Better error messages
2. `DataInitializer.java` - Auto-creates roles on startup
3. `ANALYSIS_AND_FIXES.md` - Detailed documentation

---

## VERIFY YOUR SETUP

### Check Application Started Successfully:
Look for these logs:
```
✓ ROLE_USER initialized
✓ ROLE_ADMIN initialized
✓ ROLE_RECEPTIONIST initialized
```

### Test Database Connection:
In MySQL terminal:
```sql
USE gymprodb;
SELECT * FROM roles;
SELECT * FROM users;
```

### Try Login NOW:
- **URL:** `http://localhost:8081/auth/login` (no /api/)
- **Email:** reception1@gym.com
- **Password:** 123456

It should work now! 🎉
