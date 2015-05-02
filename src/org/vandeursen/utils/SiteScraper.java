package org.vandeursen.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class SiteScraper {

	static String startPageUrl = "http://www.abnamro.nl";
	static List<String> allLinks = new ArrayList<>();
	
	public static void main(String[] args) {
		
		SiteScraper.getLinksOnSite(startPageUrl);
		
	}
	
	private static void getLinksOnSite(final String startPageUrl) {
		
		String pageUrl = "";
		boolean newLinkFound = false;

		
		for (String link : SiteScraper.getLinksOnPage(startPageUrl)) {
			if (!allLinks.contains(link)){
				allLinks.add(link);
				System.out.println("start: "+link);
				newLinkFound = true;
			}
		}
		
		Collections.sort(allLinks);
		
		int itCounter = 0;
		
		while (newLinkFound) {
			newLinkFound = false;
			List<String> newLinks = new ArrayList<>();
			Iterator<String> it = allLinks.iterator();		
			
			while(it.hasNext()){

				pageUrl = it.next();
			
				for (String link : SiteScraper.getLinksOnPage(pageUrl)) {
					if (!allLinks.contains(link)){
						newLinks.add(link);
						newLinkFound = true;
					}
				}
			}
			if (newLinkFound) {
				allLinks.addAll(newLinks);
				Collections.sort(allLinks);
				System.out.println("");
				allLinks.forEach(System.out::println);
			}
			itCounter++;
			System.out.println(itCounter);
		}
		
		System.out.println("");
		allLinks.forEach(System.out::println);
	}
	
	private static List<String> getLinksOnPage(final String url) {
	    final List<String> result = new LinkedList<>();
	    
	    try {
			URI uri = new URI(url);
			String domain = uri.getHost();
			System.out.println(url);
	    	final Parser htmlParser = new Parser(url);
			String domainRegex = ".*"+domain+".*html$";
			LinkRegexFilter linkRegexFilter = new LinkRegexFilter(domainRegex);
	        final NodeList tagNodeList = htmlParser.extractAllNodesThatMatch(linkRegexFilter);
	        for (int j = 0; j < tagNodeList.size(); j++) {
	            final LinkTag loopLink = (LinkTag) tagNodeList.elementAt(j);
	            final String loopLinkStr = loopLink.getLink();
	            result.add(loopLinkStr);
	        }
	    } catch (URISyntaxException|ParserException e) {
	        e.printStackTrace();
	    }

	    return result;
	}

}
