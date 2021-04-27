package Common;

import Common.Enums.TradeStatus;
import Common.Enums.TradeTransactionType;
import java.time.LocalDateTime;

public class Trade extends BaseClass {

    public TradeTransactionType transactionType;
    public int quantity;
    public double price;
    public LocalDateTime createdDate;
    public TradeStatus status;
    public OrganisationalUnit organisationalUnit;
    public Asset asset;

    public Trade() { }
}
