package in.digo.auction;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AuctionController {
    @Autowired
    BidRepository bids;

    @Autowired
    AuctionItemRepository auctionItems;

    private Logger logger = LoggerFactory.getLogger(AuctionController.class);

    @PostMapping("/auctionItems")
    public AuctionItemId auctionItem(@RequestBody AItem aitem) {
        AuctionItem auctionItem = new AuctionItem(aitem);
        auctionItem = auctionItems.save(auctionItem);

        AuctionItemId auctionItemId = new AuctionItemId(auctionItem.getAuctionItemId());
        logger.info("Creating auction item {} ({}, {}, {})",
                auctionItemId,
                auctionItem.getReservePrice(),
                auctionItem.getItemId(),
                auctionItem.getDescription());

        return auctionItemId;
    }

    @GetMapping("/auctionItems")
    public Iterable<AItem> auctionItems() {
        List<AItem> aitems = new ArrayList<>();
        for (AuctionItem auctionItem : auctionItems.findAll()) {
            aitems.add(new AItem(auctionItem));
        }
        return aitems;
    }

    @GetMapping("/auctionItems/{id}")
    public ResponseEntity<AItem> auctionItem(@PathVariable long id) {
        Optional<AuctionItem> auctionItem = auctionItems.findById(id);

        if (!auctionItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new AItem(auctionItem.get()));
    }

    @PostMapping("/bids")
    public ResponseEntity<? extends BidResponse> bids(@RequestBody Bid bid) {
        Optional<AuctionItem> auctionItem = auctionItems.findById(bid.getAuctionItemId());

        if (!auctionItem.isPresent()) {
            logger.warn("WARNING: Bidder {}'s bid for {} did not match any item",
                    bid.getBidderName(), bid.getAuctionItemId());
            return ResponseEntity.badRequest().body(new BidError(bid.getAuctionItemId()));
        }

        BigDecimal currentBid = auctionItem.get().getCurrentBid();
        BigDecimal reservePrice = auctionItem.get().getReservePrice();
        BigDecimal maxAutoBidAmount = bid.getMaxAutoBidAmount();

        if (currentBid.compareTo(maxAutoBidAmount) < 0) {
            // update current bid amount with new higher bid
            logger.info("Updating current bid from {} to {} for {}",
                    currentBid, maxAutoBidAmount, bid.getAuctionItemId());
            auctionItem.get().setCurrentBid(maxAutoBidAmount);
        }

        if (maxAutoBidAmount.compareTo(reservePrice) < 0) {
            bid = bids.save(bid);
            logger.warn("WARNING: Bidder {}'s bid of {} did not match reserve price of {} for {}",
                    bid.getBidderName(), maxAutoBidAmount, reservePrice, bid.getAuctionItemId());
            return ResponseEntity.badRequest().body(new BidInvalid(bid.getBidId(), reservePrice));
        }

        bid = bids.save(bid);
        logger.info("Creating bid {} on {} of {} for {}",
                bid.getBidId(), bid.getAuctionItemId(), maxAutoBidAmount, bid.getBidderName());
        return ResponseEntity.ok(new BidId(bid.getBidId()));
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<Price> price(@PathVariable long id) {
        Optional<AuctionItem> auctionItem = auctionItems.findById(id);

        if (!auctionItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        BigDecimal pay = auctionItem.get().getReservePrice();

        // top two highest bidders
        List<Bid> highest = bids.findTop2ByAuctionItemIdOrderByMaxAutoBidAmountDesc(id);
        if (highest.size() == 2) {
            Bid first = highest.get(0);
            Bid second = highest.get(1);
            if (second.getMaxAutoBidAmount().compareTo(pay) > 0) {
                pay = BigDecimal.ONE.add(second.getMaxAutoBidAmount());
            }
        }

        return ResponseEntity.ok(new Price(id, pay));
    }

    @GetMapping("/bid/{name}/for/{id}")
    public ResponseEntity<Bid> bidByFor(@PathVariable String name, @PathVariable long id) {
        Optional<Bid> bid = bids.findFirstByBidderNameAndAuctionItemIdOrderByMaxAutoBidAmountDesc(name, id);

        if (!bid.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bid.get());
    }

    @GetMapping("/bids/{name}")
    public Iterable<Bid> bidsBy(@PathVariable String name) {
        List<Bid> list = bids.findByBidderNameOrderByMaxAutoBidAmountDesc(name);

        return list;
    }

    /*
    @PostMapping("/bid")
    public ResponseEntity<URI> bid(@RequestBody Bid bid) {
        bid = bids.save(bid);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(bid.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    */
}
