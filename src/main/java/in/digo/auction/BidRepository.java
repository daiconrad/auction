package in.digo.auction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<Bid> findFirstByBidderNameAndAuctionItemIdOrderByMaxAutoBidAmountDesc(String bidderName, Long auctionItemId);

    List<Bid> findByBidderNameOrderByMaxAutoBidAmountDesc(String bidderName);

    List<Bid> findTop2ByAuctionItemIdOrderByMaxAutoBidAmountDesc(Long auctionItemId);
}
