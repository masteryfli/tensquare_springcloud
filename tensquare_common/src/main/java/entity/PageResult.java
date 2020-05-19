package entity;

import java.util.List;

/**
 * 分类结果查询
 * @param <T>
 */
public class PageResult<T> {

    /**
     * 总数量
     */
    private Long total;

    /**
     * 行数
     */
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
