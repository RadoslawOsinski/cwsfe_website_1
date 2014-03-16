package eu.com.cwsfe.cms.model;

/**
 * @author radek
 */
public class CmsTextI18nCategory {

    private Long id;
    private String category;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
