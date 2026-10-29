# Run Instructions

## 1. Configure environment variables

Create a `.env` file in the project root.

```env
POSTGRES_DB=winwin
POSTGRES_USER=winwin
POSTGRES_PASSWORD=winwin
POSTGRES_URL=jdbc:postgresql://postgres:5432/winwin
JWT_SECRET=0010101111001110111100110110011010011101011100110110001100110010100001010100110010001001010001110001100000101100100011011001010111111000110100011100100101110010100000100100011011001000000001000011110100010001101110000101111100010101010110111101111110000010
INTERNAL_TOKEN=7f3c9a2e-b814-4d67-a531-9e28c4f06b72
DATA_API_BASE_URL=http://data-api:8081
```

## 2. Build and start the services

Make sure Docker Desktop is running. From the project root, run:

```powershell
.\mvnw.cmd -f auth-api/pom.xml clean package -DskipTests
.\mvnw.cmd -f data-api/pom.xml clean package -DskipTests
docker compose up --build -d
```

Check the container status:

```powershell
docker compose ps
```

The services are available at:

- `auth-api`: http://localhost:8080
- `data-api`: http://localhost:8081
- PostgreSQL: `localhost:5432`

## 3. Register a user

Send a registration request to `POST /api/auth/register`:

```powershell
$body = @{
    email    = "a@a.com"
    password = "pass"
} | ConvertTo-Json

Invoke-RestMethod `
    -Uri "http://localhost:8080/api/auth/register" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body
```

Expected result: **201 Created**. If the email is already registered, the API returns **409 Conflict**.

## 4. Log in and obtain a JWT

Send a login request to `POST /api/auth/login`:

```powershell
$body = @{
    email    = "a@a.com"
    password = "pass"
} | ConvertTo-Json

$login = Invoke-RestMethod `
    -Uri "http://localhost:8080/api/auth/login" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body

$token = $login.token
```

The JWT is stored in `$token` and will be used to authenticate requests to protected endpoints.

## 5. Process text

Send a request to `POST /api/process` with the JWT in the `Authorization` header:

```powershell
$body = @{
    text = "hello"
} | ConvertTo-Json

Invoke-RestMethod `
    -Uri "http://localhost:8080/api/process" `
    -Method POST `
    -Headers @{ Authorization = "Bearer $token" } `
    -ContentType "application/json" `
    -Body $body
```

Expected response:

```json
{
    "result": "OLLEH"
}
```

## 6. Stop the services

Stop the containers:

```powershell
docker compose down
```

To remove the containers and the persisted PostgreSQL volume:

```powershell
docker compose down -v
```