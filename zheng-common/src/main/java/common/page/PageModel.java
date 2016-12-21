package common.page;

import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
public class PageModel<T> {
    private List<T> model;
    private int currpage;
    private int pagecount;
    private int pagesize;

    public PageModel(List<T> _model,int _currpage,int _pagecount,int _pagesize){
        setModel(_model);
        setPagecount(_pagecount);
        setCurrpage(_currpage);
        setPagesize(_pagesize);
    }

    public List<T> getModel() {
        return model;
    }

    public void setModel(List<T> model) {
        this.model = model;
    }

    public int getCurrpage() {
        return currpage;
    }

    public void setCurrpage(int currpage) {
        this.currpage = currpage;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
