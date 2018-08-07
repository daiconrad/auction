package in.digo.auction;

import static java.util.Objects.requireNonNull;

public class Item {
    private final String itemId;
    private final String description;

    public Item(String itemId, String description) {
        this.itemId = requireNonNull(itemId);
        this.description = requireNonNull(description);
    }

    public String getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item[itemId="+itemId+", description="+description+"]";
    }
}
