#!/bin/bash
# GymPro Backend - Quick Testing Script
# Use this to quickly test the API without Postman

BASE_URL="http://localhost:8081"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== GymPro Backend Testing ===${NC}\n"

# 1. Register a new user
echo -e "${BLUE}Step 1: Registering new user...${NC}"
REGISTER_RESPONSE=$(curl -s -X POST $BASE_URL/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "Test123!",
    "roles": ["user"]
  }')

echo "Response: $REGISTER_RESPONSE"
echo ""

# 2. Login to get JWT token
echo -e "${BLUE}Step 2: Logging in...${NC}"
LOGIN_RESPONSE=$(curl -s -X POST $BASE_URL/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test123!"
  }')

echo "Response: $LOGIN_RESPONSE"

# Extract token using jq (requires jq to be installed)
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
  echo -e "${BLUE}Could not extract token. Using extracted manually or try logging in again.${NC}"
  exit 1
fi

echo -e "${GREEN}Token extracted: ${TOKEN:0:20}...${NC}\n"

# 3. Test protected endpoint
echo -e "${BLUE}Step 3: Testing protected endpoint (get clients)...${NC}"
curl -s -X GET $BASE_URL/api/clients \
  -H "Authorization: Bearer $TOKEN" | jq '.'

echo -e "\n${GREEN}✓ All tests completed!${NC}"
