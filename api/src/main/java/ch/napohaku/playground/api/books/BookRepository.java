package ch.napohaku.playground.api.books;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "books")
public interface BookRepository extends MongoRepository<Book, String>{
    @Query("{$text: {$search: ?0}}")
    @RestResource(path = "fulltext", rel = "fulltext")    
    public Page<Book> findFullText(@Param("criteria") String criteria, @Param("pageable") Pageable pageable);
}


