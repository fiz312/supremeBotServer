package server.bot;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

import net.coobird.thumbnailator.Thumbnails;

public class ItemExtractor {
	long baseRed;
	long baseGreen;
	long baseBlue;

	public String extractItem(List<Element> items) {
		List<ImageEntity> itemImages = packItems(items);
		return itemImages.stream().min((o1, o2) -> Long.compare(o1.distance, o2.distance)).get().href;
	}

	private BufferedImage downloadImage(String item) throws IOException {
		return Thumbnails.of(new URL(item)).size(300, 300).asBufferedImage();
	}

	public void prepareBaseItem(String imageUrl) {
		BufferedImage baseImage;
		try {
			baseImage = Thumbnails.of(new URL(imageUrl)).size(300, 300).asBufferedImage();
			baseRed = 0;
			baseGreen = 0;
			baseBlue = 0;

			for (int x = 0; x < baseImage.getWidth(); x++) {
				for (int y = 0; y < baseImage.getHeight(); y++) {
					Color color = new Color(baseImage.getRGB(x, y));
					baseRed += color.getRed();
					baseGreen += color.getGreen();
					baseBlue += color.getBlue();
				}
			}
			int pixelCount = baseImage.getWidth() * baseImage.getHeight();
			baseRed /= pixelCount;
			baseGreen /= pixelCount;
			baseBlue /= pixelCount;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<ImageEntity> packItems(List<Element> items) {
		return items.parallelStream().map(e -> {
			BufferedImage image;
			try {
				String src = "http:" + e.getElementsByTag("img").get(0).attr("src");
				image = downloadImage(src);
				long red = 0;
				long green = 0;
				long blue = 0;

				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						Color color = new Color(image.getRGB(x, y));
						red += color.getRed();
						green += color.getGreen();
						blue += color.getBlue();
					}
				}
				int pixelCount = image.getWidth() * image.getHeight();
				red /= pixelCount;
				green /= pixelCount;
				blue /= pixelCount;

				long x = Math.round(Math.pow((red - baseRed), 2));
				long y = Math.round(Math.pow((green - baseGreen), 2));
				long z = Math.round(Math.pow((blue - baseBlue), 2));
				long distance = Math.round(Math.sqrt(x + y + z));

				return new ImageEntity(e.child(1).child(0).attr("href"), distance);
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		}).collect(Collectors.toList());
	}
}
