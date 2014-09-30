package model;

public class XssMeasures {
	public String htmlspecialchars(String content) {
		String text = new String(content);

		String[] escape = { "&", "<", ">", "\"", "\'", "\n", "\t" };
		String[] replace = { "&amp;", "&lt;", "&gt;", "&quot;", "&#39;",
				"<br>", "&#x0009;" };

		for (int i = 0; i < escape.length; i++) {
			text = text.replace(escape[i], replace[i]);
		}

		return text;
	}

}
