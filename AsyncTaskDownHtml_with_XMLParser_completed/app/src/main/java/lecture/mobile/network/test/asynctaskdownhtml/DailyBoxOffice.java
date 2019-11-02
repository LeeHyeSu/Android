package lecture.mobile.network.test.asynctaskdownhtml;

/**
 * Created by cooling on 2016-10-05.
 */

public class DailyBoxOffice {

    private long _id;

    private String rank;
    private String movieNm;
    private String openDt;

    public long get_id() { return _id; }

    public void set_id(long _id) { this._id = _id;  }

    public String getMovieNm() {
        return movieNm;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank + ". '" + movieNm + "\'" +
                " (" + openDt + " 개봉)";
    }
}
