//package eu.com.cwsfe.cms.cache;
//
//import eu.com.cwsfe.cms.dao.BlogPostsDAO;
//import eu.com.cwsfe.cms.model.BlogPost;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.Element;
//
///**
// * @author radek
// */
//public class BlogPostsCache extends GenericCache {
//
//    private static final Cache cache = new Cache(CACHE_CONFIGURATION);
//
//    private BlogPostsDAO dao;
//
//    public void setDao(BlogPostsDAO dao) {
//        this.dao = dao;
//    }
//
//    public BlogPost get(Long id) {
//        Element element;
//        BlogPost value;
//        if ((element = cache.get(id)) != null) {
//            return (BlogPost) element.getObjectValue();
//        }
//        value = dao.get(id);
//        if (value != null) {
//            cache.put(new Element(id, value));
//        }
//        return value;
//    }
//
//}
