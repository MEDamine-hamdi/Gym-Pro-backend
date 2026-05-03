# 🚀 POSTMAN TESTING GUIDE - COMPLETE WORKFLOW

## THE PROBLEM (SOLVED)
You were getting **403 Forbidden** because you used `/api/auth/login` 
The correct endpoint is `/auth/login` (without the `/api/` prefix)

---

## STEP 1: REGISTER A NEW USER

### Request Details:
```
Method: POST
URL: http://localhost:8081/auth/register
```

### Headers:
```
Content-Type: application/json
```

### Body (Raw JSON):
```json
{
  "username": "reception1",
  "email": "reception1@gym.com",
  "password": "123456",
  "roles": ["receptionist"]
}
```

### Expected Response (200 OK):
```
User registered successfully!
```

---

## STEP 2: LOGIN TO GET JWT TOKEN

### Request Details:
```
Method: POST
URL: http://localhost:8081/auth/login
```

### Headers:
```
Content-Type: application/json
```

### Body (Raw JSON):
```json
{
  "email": "reception1@gym.com",
  "password": "123456"
}
```

### Expected Response (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWNlcHRpb24xQGd5bS5jb20iLCJpYXQiOjE3MTQ3MzkyMTgsImV4cCI6MTcxNDgyNTYxOH0.xYz...",
  "type": "Bearer",
  "id": 1,
  "email": "reception1@gym.com",
  "roles": ["ROLE_RECEPTIONIST"]
}
```

### ⚠️ IMPORTANT: Save This Token!
Copy the `token` value - you'll need it for the next steps.

---

## STEP 3: SET UP AUTHORIZATION IN POSTMAN

### Option A: Use Variable (Recommended)
1. Go to **Variables** tab in Postman
2. Set variable name: `token`
3. Paste the token value
4. Use `{{token}}` in Authorization header

### Option B: Manual Each Time
1. Copy the token from Step 2 response
2. In each request, go to **Authorization** tab
3. Select **Bearer Token** type
4. Paste token in the Token field

---

## STEP 4: TEST PROTECTED ENDPOINTS

### Example 1: Get All Clients

#### Request:
```
Method: GET
URL: http://localhost:8081/api/clients
```

#### Headers:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWNlcHRpb24xQGd5bS5jb20iLCJpYXQiOjE3MTQ3MzkyMTgsImV4cCI6MTcxNDgyNTYxOH0.xYz...
```

#### Expected Response (200 OK):
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phone": "123456789",
    "dateOfBirth": "1990-01-01",
    "joinDate": "2024-04-15",
    "active": true
  }
]
```

---

### Example 2: Create New Client

#### Request:
```
Method: POST
URL: http://localhost:8081/api/clients
```

#### Headers:
```
Authorization: Bearer <your_token>
Content-Type: application/json
```

#### Body (Raw JSON):
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane@example.com",
  "phone": "9876543210",
  "dateOfBirth": "1995-05-15",
  "joinDate": "2024-05-03",
  "active": true
}
```

#### Expected Response (200 OK):
```json
{
  "id": 2,
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane@example.com",
  "phone": "9876543210",
  "dateOfBirth": "1995-05-15",
  "joinDate": "2024-05-03",
  "active": true
}
```

---

## COMPLETE ENDPOINT REFERENCE

### Authentication (No Token Required)
| Method | URL | Body |
|--------|-----|------|
| POST | `/auth/register` | username, email, password, roles |
| POST | `/auth/login` | email, password |

### Clients (Token + ADMIN/RECEPTIONIST)
| Method | URL | Purpose |
|--------|-----|---------|
| GET | `/api/clients` | Get all clients |
| GET | `/api/clients/{id}` | Get specific client |
| POST | `/api/clients` | Create new client |
| PUT | `/api/clients/{id}` | Update client |
| DELETE | `/api/clients/{id}` | Delete client |

### Employees (Token + ADMIN Only)
| Method | URL | Purpose |
|--------|-----|---------|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get specific employee |
| POST | `/api/employees` | Create new employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

### Rooms (Token + ADMIN Only)
| Method | URL | Purpose |
|--------|-----|---------|
| GET | `/api/rooms` | Get all rooms |
| GET | `/api/rooms/{id}` | Get specific room |
| POST | `/api/rooms` | Create new room |
| PUT | `/api/rooms/{id}` | Update room |
| DELETE | `/api/rooms/{id}` | Delete room |

### Sessions (Token + ADMIN/RECEPTIONIST for GET)
| Method | URL | Purpose |
|--------|-----|---------|
| GET | `/api/sessions` | Get all sessions |
| GET | `/api/sessions/{id}` | Get specific session |
| POST | `/api/sessions` | Create new session (ADMIN only) |
| PUT | `/api/sessions/{id}` | Update session (ADMIN only) |
| DELETE | `/api/sessions/{id}` | Delete session (ADMIN only) |

### Bookings (Token + ADMIN/RECEPTIONIST)
| Method | URL | Purpose |
|--------|-----|---------|
| POST | `/api/bookings/{clientId}/{sessionId}` | Create booking |

### Offers (Token + ADMIN Only)
| Method | URL | Purpose |
|--------|-----|---------|
| GET | `/api/offers` | Get all offers |
| GET | `/api/offers/{id}` | Get specific offer |
| POST | `/api/offers` | Create new offer |
| PUT | `/api/offers/{id}` | Update offer |
| DELETE | `/api/offers/{id}` | Delete offer |

### Statistics (Token + ADMIN Only)
| Method | URL | Purpose |
|--------|-----|---------|
| GET | `/api/stats/overview` | Get overview stats |
| GET | `/api/stats/revenue-monthly` | Get monthly revenue |
| GET | `/api/stats/top-sessions` | Get top sessions |
| GET | `/api/stats/client-age-groups` | Get client age groups |

---

## 🧪 QUICK TEST WORKFLOW

1. **Register** → `POST /auth/register`
2. **Login** → `POST /auth/login`
3. **Save Token** → Copy from response
4. **Add to Variables** → Set `{{token}}` variable
5. **Test GET Clients** → `GET /api/clients` with token
6. **Test POST Client** → `POST /api/clients` with token and body

---

## ⚠️ COMMON ERRORS & SOLUTIONS

| Error | Cause | Solution |
|-------|-------|----------|
| **403 Forbidden** | Using `/api/auth/login` | Use `/auth/login` (no /api/) |
| **401 Unauthorized** | Missing/invalid token | Get new token via login |
| **404 Not Found** | Wrong endpoint URL | Check endpoint path |
| **400 Bad Request** | Missing required fields | Verify JSON body |
| **500 Internal Error** | Database issue | Check MySQL connection |

---

## 🔑 AUTHORIZATION HEADER FORMAT

Correct format:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

NOT:
```
Authorization: JWT eyJhbGciOiJIUzI1NiJ9...
```

---

## 💡 PRO TIPS FOR POSTMAN

### Auto-Extract Token
In the **Tests** tab of Login request, add:
```javascript
var jsonData = pm.response.json();
pm.environment.set("token", jsonData.token);
```

### Save for Later Use
In Postman, you can:
1. Save each endpoint as a separate request
2. Organize in folders by resource
3. Use pre-request scripts for dynamic data
4. Use tests to validate responses

### Collection for Team
You can export this as a collection and share with your team!

---

## ✅ YOU'RE READY!

Now you can:
1. ✅ Register users
2. ✅ Login and get JWT
3. ✅ Access protected endpoints
4. ✅ Test role-based access
5. ✅ Manage gym data

**Start with STEP 1 and follow the workflow above!** 🚀
