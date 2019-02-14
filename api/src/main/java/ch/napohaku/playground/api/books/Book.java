package ch.napohaku.playground.api.books;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/***
 * Book Model
 */
@Document
public class Book {    
    @Id
    private String id;

    @Size(min=2, message="Title must have at least 2 characters")
    @TextIndexed
    private String title;

    @TextIndexed
    private String description;

    @NotNull(message="Publication Date must not be empty")
    @Indexed
    private Date publicationDate;

    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
