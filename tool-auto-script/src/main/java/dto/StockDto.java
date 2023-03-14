package dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class StockDto {
    private int sku;
    private String location;
    private int stock;

    private ZonedDateTime updateTimestamp;
    private LocalDateTime updateDatetime;

    @Override
    public String toString() {
        return "StockDto{" +
                "sku=" + sku +
                ", location='" + location + '\'' +
                ", stock=" + stock +
                ", updateTimestamp=" + updateTimestamp +
                ", updateDatetime=" + updateDatetime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockDto stockDto = (StockDto) o;

        if (sku != stockDto.sku) return false;
        if (stock != stockDto.stock) return false;
        if (!location.equals(stockDto.location)) return false;
        if (!Objects.equals(updateTimestamp, stockDto.updateTimestamp))
            return false;
        return Objects.equals(updateDatetime, stockDto.updateDatetime);
    }

    @Override
    public int hashCode() {
        int result = sku;
        result = 31 * result + location.hashCode();
        result = 31 * result + stock;
        result = 31 * result + (updateTimestamp != null ? updateTimestamp.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        return result;
    }
}
