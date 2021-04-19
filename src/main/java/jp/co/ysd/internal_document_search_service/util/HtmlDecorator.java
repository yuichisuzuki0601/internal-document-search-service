package jp.co.ysd.internal_document_search_service.util;

import java.util.List;

import org.springframework.util.ObjectUtils;

/**
 *
 * @author yuichi
 *
 */
public class HtmlDecorator {

	private String original;

	public HtmlDecorator(String original) {
		this.original = original;
	}

	public String rawlize() {
		String result = original.replaceAll("<br />", "\n");
		result = result.replaceAll("<br/>", "\n");
		result = result.replaceAll("<br>", "\n");
		return result;
	}

	public HtmlDecorator htmlize() {
		if (original != null) {
			this.original = original.replaceAll("\n", "<br />");
		}
		return this;
	}

	public HtmlDecorator emphasize(List<String> targets) {
		if (original != null) {
			for (String target : targets) {
				if (!ObjectUtils.isEmpty(target)) {
					this.original = original.replaceAll("(?i)" + target, "<em>$0</em>");
				}
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return this.original;
	}

}
