package ch.napohaku.playground.api.books;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import ch.napohaku.playground.api.books.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class BookRepositoryTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private BookRepository bookRepository;

        private static final String TEST_ENTITIY = "{\"title\": \"A Book A Title\", \"description\":\"Some Description\",\"publicationDate\":\"2018-10-01\"}";
        private static final String TEST_ENTITIY_UPDATED = "{\"title\": \"A complete new Title\", \"description\":\"Some Description\",\"publicationDate\":\"2018-10-01\"}";

        private static final String TEST_ENTITY_ONE = "{\"title\": \"A Book Red Title\", \"description\":\"Some Description\",\"publicationDate\":\"2018-10-02\"}";
        private static final String TEST_ENTITY_TWO = "{\"title\": \"B Book Title\", \"description\":\"Some Blue Description\",\"publicationDate\":\"2018-10-01\"}";
        
        @Before
        public void deleteAllBeforeTests() throws Exception {
                bookRepository.deleteAll();
        }

        @Test
        public void shouldReturnRepositoryIndex() throws Exception {
                mockMvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books").exists())
                                .andDo(document("home",
                                links(
                                        linkWithRel("search").description("Link to the search api"),
                                        linkWithRel("profile").description("discover the api"),
                                        linkWithRel("self").description("Link to the search api")
                                ),
                                responseFields(
                                        subsectionWithPath("page").description("pagination infromation"),
                                        subsectionWithPath("page.size").description("max number of items per request"),
                                        subsectionWithPath("page.totalElements").description("total elements found in database"),
                                        subsectionWithPath("page.totalPages").description("number of pages (based on size parameter"),
                                        subsectionWithPath("page.number").description("current page number"),
                                        subsectionWithPath("_embedded.books").description("An array of <<resources-books,Book resources>>"),
                                        subsectionWithPath("_links").description("<<resources-tags-list-links, Links>> to other resources")
                                        )
                                ));
        }

        @Test
        public void shouldCreateEntity() throws Exception {

                mockMvc.perform(post("/api/books").content(TEST_ENTITIY)).andExpect(status().isCreated())
                                .andExpect(header().string("Location", containsString("books/")));
        }

        @Test
        public void shouldRetrieveEntity() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/api/books").content(TEST_ENTITIY))
                                .andExpect(status().isCreated()).andReturn();

                String location = mvcResult.getResponse().getHeader("Location");
                mockMvc.perform(get(location)).andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("A Book A Title"))
                                .andExpect(jsonPath("$.description").value("Some Description"));
        }

        @Test
        public void shouldQueryEntitySingle() throws Exception {

                mockMvc.perform(post("/api/books").content(TEST_ENTITIY)).andExpect(status().isCreated());

                mockMvc.perform(get("/api/books/search/fulltext?criteria={criteria}", "Book"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books[0].title").value("A Book A Title"))
                                .andDo(document("search"));;
        }

        /**
         * post two test data entities
         * 
         * @throws Exception
         */
        public void postTwoTestEntities() throws Exception {
                mockMvc.perform(post("/api/books").content(TEST_ENTITY_ONE)).andExpect(status().isCreated());

                mockMvc.perform(post("/api/books").content(TEST_ENTITY_TWO)).andExpect(status().isCreated());

        }

        @Test
        public void shouldQueryEntityMultipleResults() throws Exception {

                postTwoTestEntities();

                mockMvc.perform(get("/api/books/search/fulltext?criteria={criteria}", "Book"))
                                .andExpect(status().isOk()).andExpect(jsonPath("$._embedded.books.length()").value(2));
                mockMvc.perform(get("/api/books/search/fulltext?criteria={criteria}", "RED")).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books.length()").value(1))
                                .andExpect(jsonPath("$._embedded.books[0].title").value("A Book Red Title"));

                mockMvc.perform(get("/api/books/search/fulltext?criteria={criteria}", "ReD")).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books.length()").value(1))
                                .andExpect(jsonPath("$._embedded.books[0].title").value("A Book Red Title"));
        }

        @Test
        public void shouldListAndSortByTitle() throws Exception {

                postTwoTestEntities();

                mockMvc.perform(get("/api/books?sort={sort}", "title,asc")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books.length()").value(2))
                                .andExpect(jsonPath("$._embedded.books[0].title").value("A Book Red Title"))
                                .andExpect(jsonPath("$._embedded.books[1].title").value("B Book Title"));
                mockMvc.perform(get("/api/books?sort={sort}", "title,desc")).andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books.length()").value(2))
                                .andExpect(jsonPath("$._embedded.books[1].title").value("A Book Red Title"))
                                .andExpect(jsonPath("$._embedded.books[0].title").value("B Book Title"));
        }

        @Test
        public void shouldListAndSortByPublicationDate() throws Exception {

                postTwoTestEntities();

                mockMvc.perform(get("/api/books?sort={sort}", "publicationDate,asc")).andDo(print())
                                .andExpect(status().isOk()).andExpect(jsonPath("$._embedded.books.length()").value(2))
                                .andExpect(jsonPath("$._embedded.books[1].title").value("A Book Red Title"))
                                .andExpect(jsonPath("$._embedded.books[0].title").value("B Book Title"));
                mockMvc.perform(get("/api/books?sort={sort}", "publicationDate,desc")).andDo(print())
                                .andExpect(status().isOk()).andExpect(jsonPath("$._embedded.books.length()").value(2))
                                .andExpect(jsonPath("$._embedded.books[0].title").value("A Book Red Title"))
                                .andExpect(jsonPath("$._embedded.books[1].title").value("B Book Title"));
        }

        @Test
        public void shouldUpdateEntity() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/api/books").content(TEST_ENTITIY))
                                .andExpect(status().isCreated()).andReturn();

                String location = mvcResult.getResponse().getHeader("Location");

                mockMvc.perform(put(location).content(TEST_ENTITIY_UPDATED)).andExpect(status().isNoContent());

                mockMvc.perform(get(location)).andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("A complete new Title"))
                                .andExpect(jsonPath("$.title").value("A complete new Title"));
        }

        @Test
        public void shouldPartiallyUpdateEntity() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/api/books").content(TEST_ENTITIY))
                                .andExpect(status().isCreated()).andReturn();

                String location = mvcResult.getResponse().getHeader("Location");

                mockMvc.perform(patch(location).content("{\"title\": \"partially updated title\"}"))
                                .andExpect(status().isNoContent());

                mockMvc.perform(get(location)).andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("partially updated title"))
                                .andExpect(jsonPath("$.description").value("Some Description"));
        }

        @Test
        @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
        public void shouldDeleteEntityAsAdmin() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/api/books").content(TEST_ENTITIY))
                                .andExpect(status().isCreated()).andReturn();

                String location = mvcResult.getResponse().getHeader("Location");
                mockMvc.perform(delete(location)).andExpect(status().isNoContent());

                mockMvc.perform(get(location)).andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(username = "user", password = "user", roles = "USER")
        public void shouldNotDeleteEntityAsUser() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/api/books").content(TEST_ENTITIY))
                                .andExpect(status().isCreated()).andReturn();
                String location = mvcResult.getResponse().getHeader("Location");
                mockMvc.perform(delete(location)).andExpect(status().isForbidden());
                mockMvc.perform(get(location)).andExpect(status().isOk());

        }

        @Test
        public void shouldNotDeleteEntityWhenNotLoggedIn() throws Exception {

                MvcResult mvcResult = mockMvc.perform(post("/api/books").content(TEST_ENTITIY))
                                .andExpect(status().isCreated()).andReturn();
                String location = mvcResult.getResponse().getHeader("Location");
                mockMvc.perform(delete(location)).andExpect(status().isUnauthorized());
                mockMvc.perform(get(location)).andExpect(status().isOk());
        }
}