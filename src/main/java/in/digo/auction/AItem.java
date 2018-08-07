package in.digo.auction;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

public class AItem {
    private final String auctionItemId;
    private final BigDecimal currentBid;
    private final BigDecimal reservePrice;
    private final Item item;

    public final static BigDecimal ZERO = new BigDecimal("0.00");

    public AItem(String auctionItemId, BigDecimal currentBid,
            BigDecimal reservePrice, Item item) {
        this.auctionItemId = auctionItemId;
        this.currentBid = currentBid != null? currentBid : ZERO;
        this.reservePrice = requireNonNull(reservePrice);
        this.item = requireNonNull(item);
    }

    public AItem(AuctionItem auctionItem) {
        this.auctionItemId = Long.toString(auctionItem.getAuctionItemId());
        this.currentBid = auctionItem.getCurrentBid();
        this.reservePrice = auctionItem.getReservePrice();
        this.item = new Item(auctionItem.getItemId(), auctionItem.getDescription());
    }

    public String getAuctionItemId() {
        return auctionItemId;
    }

    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "AuctionItem[auctionItemId="+auctionItemId+", currentBid="+currentBid+
            ", reservePrice="+reservePrice+", item="+item+"]";
    }
}
