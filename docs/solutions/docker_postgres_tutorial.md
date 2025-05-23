
# 🧰 Docker & PostgreSQL: Support Guide

This document serves as a hands-on tutorial to manage your PostgreSQL database within Docker containers using `docker` and `docker-compose` commands.

## 📍 1. Where to Check PostgreSQL Database Configuration in Docker

Look in your `docker-compose.yml` file under the `db` service for:

```yaml
services:
  db:
    image: postgres
    environment:
      POSTGRES_DB: app_db
      POSTGRES_USER: app_user
      POSTGRES_PASSWORD: app_password
    volumes:
      - postgres_data:/var/lib/postgresql/data
```

## 🔐 2. Accessing the PostgreSQL Database

### 2.1 From Outside (Host Machine)

```bash
docker exec -it backend-db-1 psql -U app_user -d app_db
```

### 2.2 From Within the DB Container

```bash
docker-compose exec db bash
psql -U app_user -d app_db
```

## 💾 3. Backing Up and Restoring the Database

### Backup:

```bash
docker exec -t backend-db-1 pg_dump -U app_user -d app_db > backup.sql
```

### Restore:

```bash
cat backup.sql | docker exec -i backend-db-1 psql -U app_user -d app_db
```

## 🧭 4. List Tables, Users, and Roles

### From Inside the DB Shell:

```sql
\dt                -- List tables
\du                -- List users/roles
```

### From Outside:

```bash
docker exec -it backend-db-1 psql -U app_user -d app_db -c "\dt"
docker exec -it backend-db-1 psql -U app_user -d app_db -c "\du"
```

## 🛠 5. Create, Drop, and Update Tables or Users

### From Within DB Shell:

```sql
CREATE TABLE users (id SERIAL PRIMARY KEY, email TEXT, name TEXT);
ALTER TABLE users ADD COLUMN age INT;
DROP TABLE IF EXISTS users;

CREATE USER new_user WITH PASSWORD 'securepass';
DROP USER IF EXISTS new_user;
```

### From Outside:

```bash
docker exec -it backend-db-1 psql -U app_user -d app_db -c "CREATE TABLE users (id SERIAL PRIMARY KEY, email TEXT);"
docker exec -it backend-db-1 psql -U app_user -d app_db -c "ALTER TABLE users ADD COLUMN name TEXT;"
docker exec -it backend-db-1 psql -U app_user -d app_db -c "DROP TABLE IF EXISTS users;"

docker exec -it backend-db-1 psql -U app_user -d app_db -c "CREATE USER new_user WITH PASSWORD 'securepass';"
docker exec -it backend-db-1 psql -U app_user -d app_db -c "DROP USER IF EXISTS new_user;"
```

## 🧹 6. Alter, Drop, or Delete Tables

### From Within:

```sql
ALTER TABLE users RENAME TO customers;
DELETE FROM users WHERE email='test@example.com';
DROP TABLE IF EXISTS customers;
```

### From Outside:

```bash
docker exec -it backend-db-1 psql -U app_user -d app_db -c "ALTER TABLE users RENAME TO customers;"
docker exec -it backend-db-1 psql -U app_user -d app_db -c "DELETE FROM users WHERE email='test@example.com';"
docker exec -it backend-db-1 psql -U app_user -d app_db -c "DROP TABLE IF EXISTS customers;"
```

## 🔍 7. Query for a Specific User

### From Outside:

```bash
docker exec -it backend-db-1 psql -U app_user -d app_db -c "SELECT * FROM users WHERE email = 'test@example.com';"
docker exec -it backend-db-1 psql -U app_user -d app_db -c "SELECT * FROM users WHERE id = 1;"
```

---

## 8. Error Message - `psql: error: connection to server on socket "/var/run/postgresql/.s.PGSQL.5432"`

```
docker-compose run -T db psql -U user -d postgres

psql: error: connection to server on socket "/var/run/postgresql/.s.PGSQL.5432" failed: No such file or directory
Is the server running locally and accepting connections on that socket?
```

This error usually occurs when `psql` is running in a **new container** without access to the running PostgreSQL server.

---

## Why This Happened

Running:

```
docker-compose run -T db ...
```

This starts a **new container** based on the `db` service, runs `psql`, but **doesn't link to the existing PostgreSQL server** unless explicitly configured.

So `psql` tries to connect to a PostgreSQL server inside its **own temporary container**, which doesn't exist.

---

## Solutions

### ✅ Option 1: Use `docker exec` Instead

If your PostgreSQL container is already running, use:

```
docker exec -i backend-db-1 psql -U app_user -d app_db < app_backup.sql
```

> `-i` is required for input redirection (`<`) to work.

Alternatively, do it in two steps:

1. Copy the SQL file into the container:

    ```
    docker cp app_backup.sql backend-db-1:/tmp/app_backup.sql
    ```

2. Then run:

    ```
    docker exec -it backend-db-1 psql -U app_user -d app_db -f /tmp/app_backup.sql
    ```

---

## 9. 🔐 Default PostgreSQL Databases

1. `postgres` — Default admin database
2. `template1` — Used to create new databases
3. `template0` — Pristine, read-only version

---

## 10. ✅ Issue: Cannot Drop the Currently Open Database

If you see:

```
ERROR: cannot drop the currently open database
```

It means you're trying to drop the database you're currently connected to. PostgreSQL doesn't allow that.

---

### 10.1 ✅ Solution: Connect to a Different Database

Use `postgres` or `template1` to drop the database:

```
docker exec -it backend-db-1 psql -U app_user -d postgres -c "DROP DATABASE IF EXISTS app_db;"
```

Or:

```
docker exec -it backend-db-1 psql -U app_user -d template1 -c "DROP DATABASE IF EXISTS app_db;"
```

---

### 10.2 ✅ Recreate the Database

Once dropped, you can recreate the database:

```
docker exec -it backend-db-1 psql -U app_user -d postgres -c "CREATE DATABASE app_db;"
```

---

### 10.3 🧪 List Databases

To see available databases:

```
docker exec -it backend-db-1 psql -U app_user -d postgres -c "\l"
```

---

## 11 ❓ Do You Need to Stop the Container to Drop/Restore a Database?

**No**, you don't need to stop the container. You can drop, create, and restore PostgreSQL databases while the container is running.

---

### 11.1 🔍 When to Use `docker stop` or `docker-compose down`

| Command | When to Use | What It Does |
|---------|-------------|--------------|
| `docker stop` or `docker-compose stop` | Temporarily stop containers | Stops container, keeps volumes/data |
| `docker rm` or `docker-compose down` | Reset/remove container | Removes container (optionally networks/volumes) |
| `docker-compose down --volumes` | Full reset (DB and data) | Removes containers, networks, **volumes** (deletes DB data) |

---