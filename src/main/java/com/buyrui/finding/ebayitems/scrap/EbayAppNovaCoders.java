package com.buyrui.finding.ebayitems.scrap;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.FindItemsByKeywordsRequest;
import com.ebay.services.finding.FindItemsByKeywordsResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.ItemFilter;
import com.ebay.services.finding.ItemFilterType;
import com.ebay.services.finding.SortOrderType;

public class EbayAppNovaCoders {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final String                APPID = "NovaCode-5235-43b0-8024-bb03fec1b6b5";
    private FindItemsByKeywordsResponse result;
    private String                      inputStr;
    List<String>                        arr   = new ArrayList<String>();

    public FindingServicePortType createEbayConnection(String appID) {
        try {
            ClientConfig config = new ClientConfig();
            config.setApplicationId(appID);
            return FindingServiceClientFactory.getServiceClient(config);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public boolean isAuthenticatedUser(String uName, String pWord) {
        // TODO: need to implement
        return true;

    }

    public List<SearchItem> searchEbay(String inputStr, String inputNum) {
        int inputNum1 = Integer.parseInt(inputNum);
        result = getEbaySearchResults(inputStr, inputNum1, APPID);
        logger.info("FOUND " + result.getSearchResult().getCount()
                + " items.\n");
        List<SearchItem> items = result.getSearchResult().getItem();
        return items;
    }

    public  void findGalleryUrl() {
        EbayAppNovaCoders app = new EbayAppNovaCoders();
        List<SearchItem> items = app.searchEbay("bookcases", "10");
        for (SearchItem item : items) {
            logger.info(item.getGalleryURL());
        }

    }

    public List<SearchItem> searchSavedsearch(int inputNum) {
        inputStr = arr.get(inputNum - 1);
        result = getEbaySearchResults(inputStr, 5, APPID);
        logger.info("FOUND " + result.getSearchResult().getCount()
                + " items.\n");
        List<SearchItem> items = result.getSearchResult().getItem();
        return items;
    }

    public FindItemsByKeywordsResponse getEbaySearchResults(String searchTerm,
            int numItemsReturned, String appID) {

        FindItemsByKeywordsResponse result = null;
        try {
            FindingServicePortType serviceClient = this.createEbayConnection(
                    appID);

            // create request object
            FindItemsByKeywordsRequest request = new FindItemsByKeywordsRequest();

            // set request parameters
            request.setKeywords(searchTerm);

            ItemFilter i = new ItemFilter();
            i.setName(ItemFilterType.FREE_SHIPPING_ONLY);
            i.getValue().add("true");

            ItemFilter j = new ItemFilter();
            j.setName(ItemFilterType.LISTED_IN);
            j.getValue().add("EBAY-US");

            ItemFilter k = new ItemFilter();
            k.setName(ItemFilterType.LOCATED_IN);
            k.getValue().add("US");

            request.getItemFilter().add(i);
            request.getItemFilter().add(j);
            request.getItemFilter().add(k);

            PaginationInput pi = new PaginationInput();

            pi.setEntriesPerPage(numItemsReturned);
            request.setPaginationInput(pi);
            request.setBuyerPostalCode("19087");
            request.setSortOrder(SortOrderType.BEST_MATCH);

            // call service
            result = serviceClient.findItemsByKeywords(request);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return result;

    }

}
