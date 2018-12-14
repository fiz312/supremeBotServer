package server.order.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.jcip.annotations.Immutable;

import java.awt.*;

@Immutable
public class Item {

    final String name;
    final String image;
    final String category;


    @JsonCreator
	public Item(@JsonProperty("name") String name, @JsonProperty("image") String image, @JsonProperty("category") String category) {
        this.name = name;
        this.image = image;
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
