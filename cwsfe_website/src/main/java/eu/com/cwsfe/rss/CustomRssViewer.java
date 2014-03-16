package eu.com.cwsfe.rss;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Item;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import eu.com.cwsfe.cms.model.RssContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Radoslaw Osinski.
 */
public class CustomRssViewer extends AbstractRssFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
                                     HttpServletRequest request) {
        feed.setTitle("CWSFE");
        feed.setDescription("CWSFE blog");
        if (request.getServerName().contains(".pl")) {
            feed.setLink("http://cwsfe.pl");
        } else {
            feed.setLink("http://cwsfe.en");
        }
        super.buildFeedMetadata(model, feed, request);
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<RssContent> listContent = (List<RssContent>) model.get("feedContent");
        List<Item> items = new ArrayList<>(listContent.size());
        for (RssContent tempContent : listContent) {
            Item item = new Item();
            Content content = new Content();
            content.setValue(tempContent.getSummary());
            item.setContent(content);
            item.setTitle(tempContent.getTitle());
            item.setLink(tempContent.getUrl());
            item.setPubDate(tempContent.getCreatedDate());
            items.add(item);
        }

        return items;
    }

}
