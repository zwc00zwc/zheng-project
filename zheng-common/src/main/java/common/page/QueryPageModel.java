package common.page;

/**
 * Created by Administrator on 2017/1/18.
 */
public class QueryPageModel {
    /**
     * 查询起始条数
     */
    private Integer startRow;
    /**
     * 查询结束条数
     */
    private Integer endRow;
    /**
     * 当前页
     */
    private Integer currpage;
    /**
     * 页大小
     */
    private Integer pagesize=10;

    public Integer getStartRow() {
        return (currpage-1)*pagesize;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return currpage*pagesize;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Integer getCurrpage() {
        return currpage;
    }

    public void setCurrpage(Integer currpage) {
        this.currpage = currpage;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
}
