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

	public void parse() {
		List<Pattern> list = new ArrayList<Pattern>();
		list.add(srcPattern);
		list.add(backgroundPattern);
		list.add(hrefPattern);
		for (Pattern p : list) {
			links.addAll(catchAttrs(p, result, 1));
		}
		for (String s : links) {
		}
	}

	public void output() {
		for (String s : links) {
			System.out.println(s);
		}
	}
}
