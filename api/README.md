
- A user should be able to list all books order by e.g. (date of publication, alphabetically), maybe the results should be limited
- A user should be able to add new books or edit information of an existing book.
- Of course a user needs to be able to search for a book.
- Only an admin is allowed to remove a book from the library
There are no restrictions on how the API endpoints need to be defined.




# API Backend Service

## GET /api/books
list all available books

## POST /api/books
add a new book to the library

## GET /api/book/{id}
list book with {id}

## PUT /api/book/{id}
update existing book
