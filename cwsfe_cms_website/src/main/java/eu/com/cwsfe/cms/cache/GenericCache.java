//package eu.com.cwsfe.cms.cache;
//
//import net.sf.ehcache.config.CacheConfiguration;
//import net.sf.ehcache.config.PersistenceConfiguration;
//import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
//
///**
// * @author radek
// */
//public class GenericCache {
//
//    protected final static CacheConfiguration CACHE_CONFIGURATION = new CacheConfiguration("CacheConfiguration", 500)
//            .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
//            .eternal(false)
//            .timeToLiveSeconds(900)
//            .timeToIdleSeconds(30)
//            .diskExpiryThreadIntervalSeconds(0)
//            .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE));
//}
