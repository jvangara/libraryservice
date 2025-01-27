# Library Service API

### Prerequisites
- Docker

### Build and Run

1. Clone the repository to a directory C:\app and build the project:
   ```sh
   git clone https://github.com/jvangara/libraryservice.git
   cd app
   
   RUN ->  mvn clean install 
   
2. Create Docker image
    ```
   docker build -t app .
   
3. Run the Docker container
    ```
   docker run -d -p 8080:8080 app

4. Access the API at http://localhost:8080/api/books
   Use the credentials, user/pwd

5. API provides the following endpoints 
    ```sh
    GET /api/books
    GET /api/books/{id}
    GET /api/books/search?query={author OR title}
    GET /api/books/author/{author}
    GET /api/books/title/{title}    
    POST /api/books
    PUT /api/books/{id}     
    DELETE /api/books/{id}
   
6. Sample REST API URLs
     ```sh 
    GET 
    http://localhost:8080/api/books
    http://localhost:8080/api/books/67944a6c141abfc74e6f3309
    http://localhost:8080/api/books/search?query=author1
    http://localhost:8080/api/books/search?query=title1
    http://localhost:8080/api/books/author?author=author1
    http://localhost:8080/api/books/title?title=book1
   
    POST http://localhost:8080/api/books
    {
         "id": "id2",
         "title": "book2",
         "author": "author2",
         "publishedDate": "2025-01-05",
         "isbn": "isbn2"
     }
   
    DELETE http://localhost:8080/api/books/xxx67944a6c141abfc74e6f3309
   
    PUT http://localhost:8080/api/books/id2
    {
         "id": "id2",
         "title": "book2222",
         "author": "author2222",
         "publishedDate": "2024-06-15",
         "isbn": "isbn2222"
     }
   
  7. Sample REST API calls are available in the PostmanCollection.json file, which can be run from Postman or any other REST API client 
       
