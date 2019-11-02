package lecture.mobile.map.test.report03_01_20160989;

import java.util.ArrayList;

/**
 * 실제 앱을 개발할 때는 적절한 Parser 로 구현할 것
 */

public class FakeParser {

    public ArrayList<POI> parse(String xml) {
        ArrayList<POI> poiList = new ArrayList<POI>();

        poiList.add(new POI("이디야 커피 동덕여대점", "02-942-6712", "서울 성북구 화랑로 13길 28"));
        poiList.add(new POI("마놀린", "02-919-7904", "서울 성북구 화랑로 13길 38"));
        poiList.add(new POI("요거프레소 동덕여대점", "02-942-4577", "서울 성북구 화랑로 13길 42-6"));
        poiList.add(new POI("투썸플레이스 동덕여대점", "02-913-3148", "서울 성북구 화랑로 95"));
        poiList.add(new POI("빽다방 월곡역점", "02-6369-2666", "서울 성북구 화랑로 91"));

        return poiList;
    }

}
