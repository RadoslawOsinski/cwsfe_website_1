<%--<?xml version="1.0" encoding="UTF-8" ?>--%>
<%--<rss version="2.0"--%>
     <%--xmlns:content="http://purl.org/rss/1.0/modules/content/"--%>
     <%--xmlns:wfw="http://wellformedweb.org/CommentAPI/"--%>
     <%--xmlns:dc="http://purl.org/dc/elements/1.1/"--%>
     <%--xmlns:atom="http://www.w3.org/2005/Atom"--%>
     <%--xmlns:sy="http://purl.org/rss/1.0/modules/syndication/"--%>
     <%--xmlns:slash="http://purl.org/rss/1.0/modules/slash/"--%>
        <%-->--%>
    <%--<channel>--%>
        <%--<title>CWSFE</title>--%>
        <%--<link>http://cwsfe.pl</link>--%>
        <%--<atom:link href="http://cwsfe.pl$page.url($currentPageCode())" rel="self" type="application/rss+xml"/>--%>
        <%--<description>CWSFE</description>--%>
        <%--<language>pl-PL</language>--%>
        <%--<sy:updatePeriod>hourly</sy:updatePeriod>--%>
        <%--<sy:updateFrequency>24</sy:updateFrequency>--%>
        <%--$=(@postIds, (List) null)--%>
        <%--$=(@postIds, (List) $*pl.com.cwsfe.cms.dao.BlogPostsDAO("listIdsOrderedByCreationDateDesc"))--%>
        <%--$=(@postId, (Long) null)--%>
        <%--$=(@blogPost, (pl.com.cwsfe.cms.model.BlogPost) null)--%>
        <%--$=(@blogPostI18nContent, (pl.com.cwsfe.cms.model.BlogPostI18nContent) null)--%>
        <%--$ifNot($isEmpty($@postIds), {	$//first post is the newest!--%>
        <%--$=(@blogPost, (pl.com.cwsfe.cms.model.BlogPost) $*pl.com.cwsfe.cms.blog.dispatcher.BlogPostDispatcher("getPost", $util.get($@postIds, 0)))--%>
        <%--<lastBuildDate>$palio.toString((Date) $@blogPost.getPostCreationDate(), "EEE, d MMM yyyy HH:mm:ss Z", "en")</lastBuildDate>--%>
        <%--<pubDate>$palio.toString((Date) $@blogPost.getPostCreationDate(), "EEE, d MMM yyyy HH:mm:ss Z", "en")</pubDate>--%>
        <%--})--%>
        <%--<copyright>CWSFE</copyright>--%>
        <%--$//		<pubDate>Tue, 08 Jul 2008 22:31:45 EDT</pubDate>--%>
        <%--$//		<lastBuildDate>Tue, 08 Jul 2008 22:31:45 EDT</lastBuildDate>--%>
        <%--<ttl>1440</ttl>$//24h Stands for time to live, which determines how long (in minutes) the feed should last in the cache before being refreshed.--%>
        <%--$//		<image>--%>
        <%--$//			<url>$net.getQueryProtocol()://$net.getQueryHost()$media.simpleUrl("cwsfe.layout.images.favicon.png")</url>--%>
        <%--$//			<title>CWSFE</title>--%>
        <%--$//			<link>http://cwsfe.pl</link>--%>
        <%--$//			<height>15</height>--%>
        <%--$//			<width>15</width>--%>
        <%--$//			<description>CWSFE</description>--%>
        <%--$//		</image>--%>
        <%--$=(@pLang, (pl.com.cwsfe.cms.model.Lang $*pl.com.cwsfe.cms.application.dispatcher.PLangDispatcher("getPLangByCode", "en"))--%>
        <%--$for(@postId, $@postIds, {--%>
        <%--$=(@blogPost, (pl.com.cwsfe.cms.model.BlogPost) $*pl.com.cwsfe.cms.blog.dispatcher.BlogPostDispatcher("getPost", $@postId))--%>
        <%--$=(@blogPostI18nContent, (pl.com.cwsfe.cms.model.BlogPostI18nContent) $*pl.com.cwsfe.cms.dao.BlogPostI18nContentsDAO("getByLanguageForPost", $@postId, $#{pLang.id}))--%>
        <%--$if($&&($isNotNull($@blogPost), $isNotNull($@blogPostI18nContent)), {--%>
        <%--$if($&&([--%>
        <%--$==($@blogPost.getStatus(), "P"),--%>
        <%--$==($@blogPostI18nContent.getStatus(), "P")--%>
        <%--]), {--%>
        <%--<item>--%>
            <%--<title>$@blogPostI18nContent.getPostTitle()</title>--%>
            <%--<link>http://cwsfe.pl$page.url("cwsfe.blog.Blog", $+(["&amp;rh=singlePostView&amp;blogPost.id=", $@blogPost.getId(), "&amp;blogPostI18nContent.id=", $@blogPostI18nContent.getId()]))</link>--%>
            <%--<description><![CDATA[$palio.execute($@blogPostI18nContent.getPostShortcut())]]></description>--%>
            <%--<content:encoded>--%>
                <%--<![CDATA[--%>
                <%--$palio.execute($@blogPostI18nContent.getPostDescription())--%>
                <%--]]>--%>
            <%--</content:encoded>--%>
            <%--$//					<author>email@example.com (generic author name)</author>--%>
            <%--<guid>http://cwsfe.pl$page.url("cwsfe.blog.Blog", $+(["&amp;rh=singlePostView&amp;blogPost.id=", $@blogPost.getId(), "&amp;blogPostI18nContent.id=", $@blogPostI18nContent.getId()]))</guid>--%>
            <%--<pubDate>$palio.toString((Date) $@blogPost.getPostCreationDate(), "EEE, d MMM yyyy HH:mm:ss Z", "en")</pubDate>--%>
        <%--</item>--%>
        <%--})--%>
        <%--})--%>
        <%--})--%>

    <%--</channel>--%>
<%--</rss>--%>