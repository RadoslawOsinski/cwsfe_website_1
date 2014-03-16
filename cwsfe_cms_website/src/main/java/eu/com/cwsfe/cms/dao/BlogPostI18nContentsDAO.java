package eu.com.cwsfe.cms.dao;

import eu.com.cwsfe.cms.model.BlogPostI18nContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class BlogPostI18nContentsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public static List<BlogPostI18nContent> listAll() {
//        List<Object[]> dbResults = SQL.read("""
//                select
//                id, post_id, language_id, post_title, post_shortcut, post_description, status
//                from BLOG_POST_I18N_CONTENTS
//                where status <> 'D'
//        order by post_id
//        """);
//        List<BlogPostI18nContent> results;
//        if (dbResults == null) {
//            results = new ArrayList<BlogPostI18nContent>(0);
//        } else {
//            results = new ArrayList<BlogPostI18nContent>(dbResults.size());
//            for (Object[] dbResult : dbResults) {
//                results.add(new BlogPostI18nContent(
//                        id: dbResult[0],
//                        postId: dbResult[1],
//                        languageId: dbResult[2],
//                        postTitle: dbResult[3],
//                        postShortcut: dbResult[4],
//                        postDescription: dbResult[5],
//                        status: dbResult[6]
//                ));
//            }
//        }
//        return results;
//    }
//
//                    postId: dbResult[1],
//                    languageId: dbResult[2],
//                    postTitle: dbResult[3],
//                    postShortcut: dbResult[4],
//                    postDescription: dbResult[5],
//                    status: dbResult[6]
//            );
//        }
//        return blogPost;
//    }
    public BlogPostI18nContent get(Long id) {
        String query =
                "SELECT " +
                        " id, post_id, language_id, post_title, post_shortcut, post_description, status" +
                        " FROM BLOG_POST_I18N_CONTENTS " +
                        "WHERE id = ?";
        Object[] dbParams = new Object[1];
        dbParams[0] = id;
        return jdbcTemplate.queryForObject(query, dbParams, (resultSet, rowNum) -> mapBlogPostI18nContent(resultSet));
    }

    private BlogPostI18nContent mapBlogPostI18nContent(ResultSet resultSet) throws SQLException {
        BlogPostI18nContent blogPostI18nContent = new BlogPostI18nContent();
        blogPostI18nContent.setId(resultSet.getLong("ID"));
        blogPostI18nContent.setPostId(resultSet.getLong("POST_ID"));
        blogPostI18nContent.setLanguageId(resultSet.getLong("LANGUAGE_ID"));
        blogPostI18nContent.setPostTitle(resultSet.getString("POST_TITLE"));
        blogPostI18nContent.setPostShortcut(resultSet.getString("POST_SHORTCUT"));
        blogPostI18nContent.setPostDescription(resultSet.getString("POST_DESCRIPTION"));
        blogPostI18nContent.setStatus(resultSet.getString("STATUS"));
        return blogPostI18nContent;
    }

    public BlogPostI18nContent getByLanguageForPost(Long postId, Long languageId) {
        Object[] dbParams = new Object[2];
        dbParams[0] = postId;
        dbParams[1] = languageId;
        String query =
                "SELECT " +
                        " id, post_id, language_id, post_title, post_shortcut, post_description, status" +
                        " FROM BLOG_POST_I18N_CONTENTS " +
                        "WHERE post_id = ? and language_id = ?";
        BlogPostI18nContent blogPostI18nContent = null;
        try {
            blogPostI18nContent = jdbcTemplate.queryForObject(query, dbParams, (resultSet, rowNum) ->
                    mapBlogPostI18nContent(resultSet));
        } catch (DataAccessException ignored) {
        }
        return blogPostI18nContent;
    }

    public Long add(BlogPostI18nContent blogPostI18nContent) {
        Object[] dbParams = new Object[3];
        Long id = jdbcTemplate.queryForObject("select nextval('BLOG_POST_I18N_CONTENTS_S')", Long.class);
        dbParams[0] = id;
        dbParams[1] = blogPostI18nContent.getPostId();
        dbParams[2] = blogPostI18nContent.getLanguageId();
        jdbcTemplate.update("INSERT INTO BLOG_POST_I18N_CONTENTS(id, post_id, language_id, post_title, post_shortcut, post_description, status)" +
                " VALUES (?, ?, ?, '', '', '', 'H')", dbParams);
        return id;
    }

    public void updateContentWithStatus(BlogPostI18nContent blogPostI18nContent) {
        Object[] dbParams = new Object[5];
        dbParams[0] = blogPostI18nContent.getPostTitle();
        dbParams[1] = blogPostI18nContent.getPostShortcut();
        dbParams[2] = blogPostI18nContent.getPostDescription();
        dbParams[3] = blogPostI18nContent.getStatus();
        dbParams[4] = blogPostI18nContent.getId();
        jdbcTemplate.update("UPDATE BLOG_POST_I18N_CONTENTS SET post_title = ?, post_shortcut = ?, post_description = ?, status = ? WHERE id = ?", dbParams);
    }

    public void delete(BlogPostI18nContent blogPostI18nContent) {
        Object[] dbParams = new Object[1];
        dbParams[0] = blogPostI18nContent.getId();
        jdbcTemplate.update("update BLOG_POST_I18N_CONTENTS set status = 'D' where id = ?", dbParams);
    }

    public void undelete(BlogPostI18nContent blogPostI18nContent) {
        Object[] dbParams = new Object[1];
        dbParams[0] = blogPostI18nContent.getId();
        jdbcTemplate.update("update BLOG_POST_I18N_CONTENTS set status = 'H' where id = ?", dbParams);
    }

    public void publish(BlogPostI18nContent blogPostI18nContent) {
        Object[] dbParams = new Object[1];
        dbParams[0] = blogPostI18nContent.getId();
        jdbcTemplate.update("update BLOG_POST_I18N_CONTENTS set status = 'P' where id = ?", dbParams);
    }

}