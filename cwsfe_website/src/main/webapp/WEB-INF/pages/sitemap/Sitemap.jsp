<%--$#params(Boolean withWWW)--%>
<%--$=(@wwwString, "")--%>
<%--$if($&&($isNotNull($@withWWW), $@withWWW), {--%>
<%--$=(@wwwString, "www.")--%>
<%--}, {--%>
<%--$=(@wwwString, "")--%>
<%--})--%>
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
    <%--$=(@domainPrefix, null)--%>
    <%--$if($==($lang.currentLanguage(),"en"),{--%>
    <%--$=(@domainPrefix, $+(["http://", $@wwwString, "cwsfe.eu"]))--%>
    <%--})--%>
    <%--$if($==($lang.currentLanguage(),"pl"),{--%>
    <%--$=(@domainPrefix, $+(["http://", $@wwwString, "cwsfe.pl"]))--%>
    <%--})--%>
    <%--$ifNotNull($@domainPrefix, {--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.Home"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.Home"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.8</priority>--%>
    <%--</url>--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.about.About"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.about.About"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.6</priority>--%>
    <%--</url>--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.services.Services"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.services.Services"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.6</priority>--%>
    <%--</url>--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.services.Services", "&amp;rh=browseServiceDetails"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.services.Services"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.6</priority>--%>
    <%--</url>--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.services.Services", "&amp;rh=browseServiceStages"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.services.Services"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.3</priority>--%>
    <%--</url>--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.portfolio.Portfolio"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.portfolio.Portfolio"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.7</priority>--%>
    <%--</url>--%>
    <%--$=(@portfolioItems, $sql.read("--%>
    <%--select--%>
    <%--cn.id, cni18n.id--%>
    <%--from CMS_NEWS cn, CMS_NEWS_I18N_CONTENTS cni18n--%>
    <%--where--%>
    <%--cn.id = cni18n.news_id and--%>
    <%--cn.news_type_id = (select id from news_types where status = 'N' and type = 'Projects') and--%>
    <%--cn.status = 'P' and cni18n.status = 'P'--%>
    <%--order by cn.creation_date desc--%>
    <%--"))--%>
    <%--$=(@portfolioItem, (Object[]) null)--%>
    <%--$for(@portfolioItem, $@portfolioItems, {--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.portfolio.Portfolio", $+(["&amp;rh=singleNewsView&amp;cmsNews.id=", $@portfolioItem[0], "&amp;cmsNewsI18nContent.id=", $@portfolioItem[1]])))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.portfolio.Portfolio"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.3</priority>--%>
    <%--</url>--%>
    <%--})--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.blog.Blog"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.blog.Blog"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>weekly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.7</priority>--%>
    <%--</url>--%>
    <%--$=(@blogItems, $sql.read("--%>
    <%--select--%>
    <%--bp.id, bpi18n.id--%>
    <%--from BLOG_POSTS bp, BLOG_POST_I18N_CONTENTS bpi18n--%>
    <%--where--%>
    <%--bp.id = bpi18n.post_id and--%>
    <%--bp.status = 'P' and bpi18n.status = 'P'--%>
    <%--order by bp.post_creation_date desc--%>
    <%--"))--%>
    <%--$=(@blogItem, (Object[]) null)--%>
    <%--$for(@blogItem, $@blogItems, {--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.blog.Blog", $+(["&amp;rh=singlePostView&amp;blogPost.id=", $@blogItem[0], "&amp;blogPostI18nContent.id=", $@blogItem[1]])))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.portfolio.Portfolio"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.3</priority>--%>
    <%--</url>--%>
    <%--})--%>
    <%--<url>--%>
        <%--<loc>$+($@domainPrefix, $page.url("cwsfe.contact.Contact"))</loc>--%>
        <%--$//      <lastmod>$toString($admin.getPageLastUpdated("cwsfe.contact.Contact"), "yyyy-MM-dd'T'hh:mm:ss")</lastmod>--%>
        <%--<changefreq>monthly</changefreq>	$//always, hourly, daily, weekly, monthly, yearly, never--%>
        <%--<priority>0.7</priority>--%>
    <%--</url>--%>
    <%--})--%>
</urlset>
