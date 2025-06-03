package com.jjanggu.app.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PageUtil {

    /**
     * 페이징바를 제작하기 위해 필요한 값들을 연산해서 반환해주는 메소드
     * 서비스로부터 페이징처리 할 때 필요한 정보들을 전달받아 연산 수행
     * @param totalCount        게시글 총 개수 (db로부터 조회)
     * @param page              현재 페이지 번호 (요청 파라미터)
     * @param display           한 페이지에 표현할 게시글 최대 개수 (임의 설정)
     * @param pagePerBlock      페이징 바에 표현할 페이지 최대 개수 (임의 설정)
     */
    public Map<String, Object> getPageInfo(int totalCount, int page, int display, int pagePerBlock){

        int totalPage = (int)Math.ceil((double)totalCount / display);
        int beginPage = (page - 1) / pagePerBlock * pagePerBlock + 1;
        int endPage = Math.min(beginPage + pagePerBlock -1, totalPage);
        int offset = (page -1) * display;

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("page", page);
        map.put("display", display);
        map.put("pagePerBlock", pagePerBlock);
        map.put("totalPage", totalPage);
        map.put("beginPage", beginPage);
        map.put("endPage", endPage);
        map.put("offset", offset);

        return map;

    }
}
