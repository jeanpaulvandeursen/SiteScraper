package org.vandeursen.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class SiteScraper {

	static String startPageUrl = "http://www.vandeursen.org";
	static List<String> allLinks = new ArrayList<>();
	
	public static void main(String[] args) {
		
		SiteScraper.getLinksOnSite(startPageUrl);
		
	}
	
	private static void getLinksOnSite(final String startPageUrl) {
		
		String pageUrl = "";
		boolean newLinkFound = false;

		
		for (String link : SiteScraper.getLinksOnPage(startPageUrl)) {
			System.out.println(link);
			if (!allLinks.contains(link)){
				allLinks.add(link);
				newLinkFound = true;
			}
		}
		
		while (newLinkFound) {
			newLinkFound = false;
			List<String> newLinks = new ArrayList<>();
			Iterator<String> it = allLinks.iterator();		
			
			while(it.hasNext()){

				pageUrl = it.next();
			
				for (String link : SiteScraper.getLinksOnPage(pageUrl)) {
					System.out.println(link);
					if (!allLinks.contains(link)){
						newLinks.add(link);
						newLinkFound = true;
					}
				}
			}
			if (newLinkFound) {
				allLinks.addAll(newLinks);
			}
		}
		
		System.out.println("");
		allLinks.forEach(System.out::println);
	}
	
	private static List<String> getLinksOnPage(final String url) {
	    final List<String> result = new LinkedList<>();
	    
	    try {
	    	final Parser htmlParser = new Parser(url);
	        final NodeList tagNodeList = htmlParser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
	        for (int j = 0; j < tagNodeList.size(); j++) {
	            final LinkTag loopLink = (LinkTag) tagNodeList.elementAt(j);
	            final String loopLinkStr = loopLink.getLink();
	            result.add(loopLinkStr);
	        }
	    } catch (ParserException e) {
	        e.printStackTrace();
	    }

	    return result;
	}

}
