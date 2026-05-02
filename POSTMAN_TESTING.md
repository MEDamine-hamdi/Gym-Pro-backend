# GymPro API - Postman Testing Guide

## Quick Start

### 1. Import the Collection into Postman

1. Open **Postman**
2. Click **File** → **Import**
3. Select the `GymPro-API.postman_collection.json` file
4. Click **Import**

The collection will be added to your Postman workspace with all endpoints organized by resource.

---

## 2. Configure Environment Variables

Before testing, set up the variables in Postman:

### Variables Tab:
- **base_url**: `http://localhost:8081` (default)
- **token**: Leave empty (will be populated after login)

---

## 3. Enable Dev Mode (Optional but Recommended for Testing)

To test without authentication, set **dev mode** to `true`:

**File**: `src/main/resources/application.properties`
```properties
app.dev-mode=true
```

With dev mode enabled:
- ✅ All endpoints are accessible
- ✅ No JWT token required
- ✅ Perfect for development/testing

Restart the application after changing this setting.

---

## 4. Testing Workflow

### **Option A: With Dev Mode (Easiest)**

1. Set `app.dev-mode=true` in `application.properties`
2. Restart the application
3. Open any endpoint in Postman and click **Send**
4. All requests will work without authentication ✅

### **Option B: With Authentication (Production-like)**

1. Ensure `app.dev-mode=false` in `application.properties`
2. **Register a user** first:
   - Request: `Auth → Register`
   - Sample body:
     ```json
     {
       "username": "testuser",
       "email": "test@example.com",
       "password": "Test123!",
       "roles": ["user"]
     }
     ```
   - Click **Send**

3. **Login** to get a JWT token:
   - Request: `Auth → Login`
   - Sample body:
     ```json
     {
       "email": "test@example.com",
       "password": "Test123!"
     }
     ```
   - Click **Send**
   - Copy the `token` value from the response

4. **Set the token variable** in Postman:
   - Go to **Variables** tab
   - Paste the token into the `token` field
   - All subsequent requests will automatically include the JWT header

5. Now test any endpoint! 🚀

---

## 5. Available Endpoints

### **Auth** (Public)
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login and get JWT token

### **Employees** (Admin only)
- `GET /api/employees` - List all employees
- `GET /api/employees/{id}` - Get employee by ID
- `POST /api/employees` - Create new employee
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

### **Clients** (Admin/Receptionist)
- `GET /api/clients` - List all clients
- `GET /api/clients/{id}` - Get client by ID
- `POST /api/clients` - Create new client
- `PUT /api/clients/{id}` - Update client
- `DELETE /api/clients/{id}` - Delete client

### **Rooms** (Admin only)
- `GET /api/rooms` - List all rooms
- `GET /api/rooms/{id}` - Get room by ID
- `POST /api/rooms` - Create new room
- `PUT /api/rooms/{id}` - Update room
- `DELETE /api/rooms/{id}` - Delete room

### **Sessions** (Admin reads, Receptionist reads)
- `GET /api/sessions` - List all sessions
- `GET /api/sessions/{id}` - Get session by ID
- `POST /api/sessions` - Create new session
- `PUT /api/sessions/{id}` - Update session
- `DELETE /api/sessions/{id}` - Delete session

### **Bookings** (Admin/Receptionist)
- `POST /api/bookings/{clientId}/{sessionId}` - Create booking

### **Offers**
- `GET /api/booking` - List all offers
- `GET /api/booking/{id}` - Get offer by ID
- `POST /api/booking` - Create new offer
- `PUT /api/booking/{id}` - Update offer
- `DELETE /api/booking/{id}` - Delete offer

### **Stats** (Admin only)
- `GET /api/stats/overview` - Get overview stats
- `GET /api/stats/revenue-monthly` - Get monthly revenue
- `GET /api/stats/top-sessions` - Get top sessions
- `GET /api/stats/client-age-groups` - Get client age groups

---

## 6. Sample Test Flow

### **Create and Manage Employees:**
1. Click `Employees → Create Employee`
2. Update the sample JSON if needed
3. Click **Send**
4. Note the employee ID from the response
5. Use that ID in `Get Employee by ID` or `Update Employee` requests

### **Create and Manage Clients:**
1. Click `Clients → Create Client`
2. Update the sample JSON with valid dates
3. Click **Send**
4. Use the client ID in subsequent requests

### **Create Sessions:**
1. First create an employee (coach) and room
2. Click `Sessions → Create Session`
3. Update `coachId` and `roomId` with IDs from above
4. Click **Send**

---

## 7. Tips & Tricks

### **Auto-populate Token**
After login, the response contains a `token` field. You can set up a **Post-response script** to auto-populate:

1. Go to `Auth → Login`
2. Click the **Tests** tab
3. Add this script:
   ```javascript
   var jsonData = pm.response.json();
   pm.environment.set("token", jsonData.token);
   ```
4. Now the token is automatically saved after login!

### **Use Pre-request Scripts**
Add timestamps or dynamic data to requests using Pre-request scripts in Postman.

### **Collection Runner**
Use Postman's Collection Runner to run all tests sequentially:
1. Click **Runner** in top-left
2. Select the collection
3. Click **Run**

---

## 8. Common Issues

| Issue | Solution |
|-------|----------|
| **401 Unauthorized** | Set `app.dev-mode=true` OR add valid JWT token to `{{token}}` variable |
| **404 Not Found** | Ensure base URL is correct (`http://localhost:8081`) |
| **Request Failed** | Check if MySQL is running and database exists |
| **Invalid JSON** | Verify JSON syntax in request body |
| **Field Validation Error** | Check required fields match entity definitions |

---

## 9. Database Reset

To reset the database and start fresh:

1. Stop the application
2. Drop the `gymprodb` database in MySQL
3. Restart the application (Hibernate will recreate it with `ddl-auto=update`)
4. Test again!

---

**Happy Testing! 🎉**

For questions or issues, check the application logs in the IDE console.
