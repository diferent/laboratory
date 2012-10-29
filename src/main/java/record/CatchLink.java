package record;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class CatchLink extends CatchHtml {

	public Pattern hrefPattern = Pattern.compile(" href=\"([^\"]+)\"");
	public Pattern srcPattern = Pattern.compile(" src=\"([^\"]+)\"");
	public Pattern backgroundPattern = Pattern.compile(" src=\"([^\"]+)\"");

	public String result;

	public Set<String> links = new HashSet<String>();

	protected Set<String> parse() {
		List<Pattern> list = new ArrayList<Pattern>();
		list.add(srcPattern);
		list.add(backgroundPattern);
		list.add(hrefPattern);
		for (Pattern p : list) {
			links.addAll(catchAttrs(p, result, 1));
		}
		return links;
	}

	public Set<String> parseHTML(String result) {
		this.result = result;
		return parse();
	}

	public void output() {
		for (String s : links) {
			System.out.println(s);
		}
	}
}
