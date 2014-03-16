package eu.com.cwsfe.blog;

import eu.com.cwsfe.cms.model.BlogKeyword;
import eu.com.cwsfe.cms.model.BlogPost;
import eu.com.cwsfe.cms.model.BlogPostI18nContent;

import java.util.List;

/**
 * @author Radoslaw Osinski
 */
public class BlogListHelper {

    List<Object[]> postsArchiveStatistics;
    List<BlogKeyword> blogKeywords;
    List<BlogPost> blogPosts;
    List<BlogPostI18nContent> blogPostI18nContents;
    Integer currentPage;
    Long categoryId;
    Long archiveYear;
    Long archiveMonth;
    Integer articlesPerPage;
    Integer numberOfPages;
    String searchText;

}
