package in.digo.auction;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AuctionItems")
public class AuctionItem {
    @Id
    @GeneratedValue
    private Long auctionItemId;

    private BigDecimal currentBid;
    private BigDecimal reservePrice;

    private String itemId;
    private String description;

    public AuctionItem() {
    }

    public AuctionItem(Long auctionItemId, BigDecimal currentBid,
            BigDecimal reservePrice, String itemId, String description) {
        this.auctionItemId = requireNonNull(auctionItemId);
        nonnegative(currentBid);
        this.currentBid = requireNonNull(currentBid);
        positive(reservePrice);
        this.reservePrice = requireNonNull(reservePrice);
        this.itemId = requireNonNull(itemId);
        this.description = requireNonNull(description);
    }

    public AuctionItem(AItem aitem) {
        if (aitem.getAuctionItemId() != null) {
            this.auctionItemId = Long.valueOf(aitem.getAuctionItemId());
        }
        this.currentBid = aitem.getCurrentBid();
        nonnegative(this.currentBid);
        this.reservePrice = aitem.getReservePrice();
        positive(this.reservePrice);
        this.itemId = aitem.getItem().getItemId();
        this.description = aitem.getItem().getDescription();
    }

    private final void positive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
    }

    private final void nonnegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
    }

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long auctionItemId) {
        this.auctionItemId = requireNonNull(auctionItemId);
    }

    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        nonnegative(currentBid);
        this.currentBid = requireNonNull(currentBid);
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        positive(reservePrice);
        this.reservePrice = requireNonNull(reservePrice);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = requireNonNull(itemId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = requireNonNull(description);
    }

    @Override
    public String toString() {
        return "AuctionItem[auctionItemId="+auctionItemId+", currentBid="+
            currentBid+", reservePrice="+reservePrice+", itemId="+itemId+
            ", description="+description+"]";
    }
}
