package record;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nutz.lang.Strings;

public class CatchHtml {

	public String catchAttr(Pattern p, String target, int index) {
		if (p != null && !Strings.isEmpty(target)) {
			Matcher m = p.matcher(target);
			if (m.find()) {
				if (index <= m.groupCount())
					return m.group(index);
			}
		}
		return null;
	}

	public List<String> catchAttrs(Pattern p, String target, int index) {
		List<String> list = new ArrayList<String>();
		if (p != null && !Strings.isEmpty(target)) {
			Matcher m = p.matcher(target);
			while (m.find()) {
				if (index <= m.groupCount())
					list.add(m.group(index));
			}
		}
		return list;
	}

}
