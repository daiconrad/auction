package in.digo.auction;

public class BidError extends BidResponse {
    private final String errorMessage;

    public BidError(Long auctionItemId) {
        this.errorMessage = String.format(
                "Bid refers to nonexistent auction item %d.", auctionItemId);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "BidError[errorMessage="+errorMessage+"]";
    }
}
