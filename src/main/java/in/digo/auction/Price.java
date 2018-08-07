package in.digo.auction;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

public class Price {
    private final Long auctionItemId;
    private final BigDecimal price;

    public Price(Long auctionItemId, BigDecimal price) {
        this.auctionItemId = requireNonNull(auctionItemId);
        this.price = requireNonNull(price);
    }

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Price[auctionItemId="+auctionItemId+", price="+price+"]";
    }
}
