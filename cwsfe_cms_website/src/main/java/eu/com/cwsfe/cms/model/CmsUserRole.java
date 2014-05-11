package eu.com.cwsfe.cms.model;

public class CmsUserRole {

    private Long cmsUserId;
    private Long roleId;

    public CmsUserRole(Long cmsUserId, Long roleId) {
        this.cmsUserId = cmsUserId;
        this.roleId = roleId;
    }

    public Long getCmsUserId() {
        return cmsUserId;
    }

    public void setCmsUserId(Long cmsUserId) {
        this.cmsUserId = cmsUserId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
