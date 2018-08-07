CREATE TABLE Bids(
    BidId BIGINT AUTO_INCREMENT,
    AuctionItemId BIGINT NOT NULL,
    MaxAutoBidAmount DECIMAL(20, 2) NOT NULL,
    BidderName VARCHAR(255) NOT NULL);

CREATE TABLE AuctionItems(
    AuctionItemId BIGINT AUTO_INCREMENT,
    CurrentBid DECIMAL(20, 2) NOT NULL,
    ReservePrice DECIMAL(20, 2) NOT NULL,
    ItemId VARCHAR(64) NOT NULL,
    Description VARCHAR(255) NOT NULL);
