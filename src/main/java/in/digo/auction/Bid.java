package in.digo.auction;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Bids")
public class Bid {
    @Id
    @GeneratedValue
    private Long bidId;

    private Long auctionItemId;
    private BigDecimal maxAutoBidAmount;
    private String bidderName;

    public Bid() {
    }

    public Bid(Long bidId, Long auctionItemId,
            BigDecimal maxAutoBidAmount, String bidderName) {
        this.bidId = requireNonNull(bidId);
        this.auctionItemId = requireNonNull(auctionItemId);
        positive(maxAutoBidAmount);
        this.maxAutoBidAmount = requireNonNull(maxAutoBidAmount);
        this.bidderName = requireNonNull(bidderName);
    }

    private final void positive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = requireNonNull(bidId);
    }

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long auctionItemId) {
        this.auctionItemId = requireNonNull(auctionItemId);
    }

    public BigDecimal getMaxAutoBidAmount() {
        return maxAutoBidAmount;
    }

    public void setMaxAutoBidAmount(BigDecimal maxAutoBidAmount) {
        positive(maxAutoBidAmount);
        this.maxAutoBidAmount = requireNonNull(maxAutoBidAmount);
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = requireNonNull(bidderName);
    }

    @Override
    public String toString() {
        return "Bid[bidId="+bidId+", auctionItemId="+auctionItemId+
            ", maxAutoBidAmount="+maxAutoBidAmount+", bidderName="+
            bidderName+"]";
    }
}
