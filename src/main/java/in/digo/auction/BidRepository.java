package in.digo.auction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionItemId(Long auctionItemId);

    List<Bid> findTop2ByAuctionItemIdOrderByMaxAutoBidAmountDesc(Long auctionItemId);
}
