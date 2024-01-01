package mapper;

import dto.StockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockMapper {

    @Select("select sku, location, stock," + " update_timestamp, update_datetime" +
            " from stock_tbl")
    @Results({@Result(column = "update_timestamp", property = "updateTimestamp"), @Result(column = "update_datetime", property = "updateDatetime")
    })
    List<StockDto> selectAll();
}
