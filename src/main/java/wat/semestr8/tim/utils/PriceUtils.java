package wat.semestr8.tim.utils;

import java.math.BigDecimal;

public class PriceUtils
{
    public static BigDecimal getTicketPrice(BigDecimal standardTicketPrice, int percentsOfDiscount)
    {
        BigDecimal discountAmount = new BigDecimal(standardTicketPrice.doubleValue() * percentsOfDiscount / 100);
        BigDecimal actualPrice = standardTicketPrice.subtract(discountAmount);
        actualPrice.setScale(2,BigDecimal.ROUND_DOWN);
        return actualPrice;
    }
}
