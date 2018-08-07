package in.digo.auction;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

public class BidInvalid extends BidId {
    private final String errorMessage;
    private final BigDecimal reservePrice;

    public BidInvalid(Long bidId, BigDecimal reservePrice) {
        super(bidId);
        this.reservePrice = requireNonNull(reservePrice);
        this.errorMessage = String.format(
                "Bid did not meet reserve price of %.2f.", reservePrice);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    @Override
    public String toString() {
        return "BidInvalid[errorMessage="+errorMessage+", "+reservePrice+"]";
    }
}
