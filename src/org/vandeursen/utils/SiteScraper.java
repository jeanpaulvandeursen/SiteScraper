package org.vandeursen.utils;

import java.util.LinkedList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class SiteScraper {

	public static void main(String[] args) {
	    
	    final List<String> result = new LinkedList<String>();
	    final String url = "http://nu.nl";
	    
	    try {
	    	final Parser htmlParser = new Parser(url);
	        final NodeList tagNodeList = htmlParser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
	        for (int j = 0; j < tagNodeList.size(); j++) {
	            final LinkTag loopLink = (LinkTag) tagNodeList.elementAt(j);
	            final String loopLinkStr = loopLink.getLink();
	            result.add(loopLinkStr);
	        }
	    } catch (ParserException e) {
	        e.printStackTrace(); // TODO handle error
	    }

	    result.forEach(System.out::println);
	}

}
