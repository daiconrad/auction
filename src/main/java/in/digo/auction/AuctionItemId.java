package in.digo.auction;

import static java.util.Objects.requireNonNull;

public class AuctionItemId {
    private final String auctionItemId;

    public AuctionItemId(Long auctionItemId) {
        this.auctionItemId = Long.toString(auctionItemId);
    }

    public String getAuctionItemId() {
        return auctionItemId;
    }

    @Override
    public String toString() {
        return "AuctionItemId[auctionItemId="+auctionItemId+"]";
    }
}
