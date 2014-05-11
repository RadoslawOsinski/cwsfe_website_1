package eu.com.cwsfe.cms.model;

public class BlogPostKeyword {

    private Long blogPostId;
    private Long blogKeywordId;

    public BlogPostKeyword(Long blogPostId, Long blogKeywordId) {
        this.blogPostId = blogPostId;
        this.blogKeywordId = blogKeywordId;
    }

    public Long getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(Long blogPostId) {
        this.blogPostId = blogPostId;
    }

    public Long getBlogKeywordId() {
        return blogKeywordId;
    }

    public void setBlogKeywordId(Long blogKeywordId) {
        this.blogKeywordId = blogKeywordId;
    }
}
