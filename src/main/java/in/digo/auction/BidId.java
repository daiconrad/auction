package in.digo.auction;

import static java.util.Objects.requireNonNull;

public class BidId extends BidResponse {
    private final String bidId;

    public BidId(Long bidId) {
        this.bidId = Long.toString(bidId);
    }

    public String getBidId() {
        return bidId;
    }

    @Override
    public String toString() {
        return "BidId[bidId="+bidId+"]";
    }
}
