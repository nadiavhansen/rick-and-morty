# Rick and Morty API

his project is a Java Spring Boot API that consumes and processes data from the [Rick and Morty public API](https://rickandmortyapi.com/), enabling:

- Upload of .csv files with information about episodes and characters
- Persistent data storage in a SQL database
- Detailed search of episodes and characters
- Baginated search with filters by character name, episode name, and location


## Technologies used

- Java 17
- Spring Boot
- Spring Data JPA
- Microsoft SQL Server
- Docker
- Lombok
- Maven

## Prerequisites

- Java 17 instalado
- Docker e Docker Compose
- Git
- Lombok configurado na IDE
- IDE de sua preferência (recomendado: Spring Tool Suite ou IntelliJ)

## Running locally

### 1. Clone the repository

```bash
git clone https://github.com/nadiavhansen/rick-and-morty.git
cd rick-and-morty
```

### 2. Set up the database

Start the SQL Server container using Docker:

```bash
docker-compose up -d
```
Create the database manually (via DBeaver or another SQL client):

Once the container is running, open DBeaver (or your preferred database client) and create a new connection using the following settings:

- Type: SQL Server
- Host: localhost
- Port: 1433
- Database: leave blank (will connect to master)
- User: sa
- Password: Season07IsComing!

After connecting, run the following SQL command to create the database:

```sql
CREATE DATABASE rickandmorty;
```

### 3. Run the application

Using your IDE or with Maven:

```bash
./mvnw spring-boot:run
```

The API will be available at:
```bash
http://localhost:8080
```


### 4. Enpoints

- Upload CSV file with episodes and characters:
```http
POST http://localhost:8080/upload
```

Returns a link to check the upload status, for example:
```http
GET http://localhost:8080/upload/status?file=5abfcb31-26d4-4622-bb9d-2fb5036e6c2c_episodes.csv
```

- Enriched data from the Rick and Morty public API:
```http
GET http://localhost:8080/api/dashboard
```

- Upload history:
```http
GET http://localhost:8080/process-status
```

- Paginated episode search with filters:
```http
GET http://localhost:8080/api/dashboard/search
```
Available query parameters:
- characterName
- episodeName
- locationName
- sortBy
- sortDirection
- page
- size

## Notes
- Data from the uploaded CSV is used to query real episode and character information from the public Rick and Morty API.
- All characters from the listed episodes are persisted, even if the CSV includes only a few.

### Developed by
Nadia Vanzuita Hansen
