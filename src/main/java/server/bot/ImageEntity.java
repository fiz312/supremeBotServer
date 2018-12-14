package server.bot;

import org.jsoup.nodes.Element;

public class ImageEntity {
	public final String href;
	public final long distance;
	
	public ImageEntity(String href, long distance) {
		this.href = href;
		this.distance = distance;
	}
}
