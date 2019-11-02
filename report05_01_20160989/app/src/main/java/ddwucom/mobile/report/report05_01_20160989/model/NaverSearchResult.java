package ddwucom.mobile.report.report05_01_20160989.model;

import java.util.List;

public class NaverSearchResult {

    private String lastBuildDate;

    private String total;

    private String start;

    private String display;

    private List<NaverSearchItems> items;

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<NaverSearchItems> getItems() {
        return items;
    }

    public void setItems(List<NaverSearchItems> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "NaverSearchResult{" +
                "lastBuildDate=" + lastBuildDate +
                ", total=" + total +
                ", start=" + start +
                ", display=" + display +
                ", items=" + items +
                '}';
    }
}
