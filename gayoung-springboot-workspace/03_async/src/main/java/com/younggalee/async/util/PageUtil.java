package com.younggalee.async.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component  //을 붇여서 빈등록시킴
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
        /*
            1. totalPage : 마지막 페이지 수 (총 페이지 개수)
                → totalCount, display 통해 연산

                totalCount display totalPage
                    100       10      10
                    101       10      11
                    ...       10
                    110       10      11
                 111 ~ 120    10      12
                 121 ~ 130    10      13

             1) totalCount / display => 실수값으로 연산
             2) 올림 처리
         */
        int totalPage = (int)Math.ceil((double)totalCount / display);

        /*
            2. beginPage : 현재 페이지에 보여질 페이징바의 시작 수
                → page, pagePerBlock 통해 연산
                → ex) pagePerBlock이 10일 경우 : beginPage로 1, 11, 21, 31 ..
                →     즉, pagePerBlock 배수 + 1
                →          n * pagePerBlock + 1

                page pagePerBlock beginPage     n
                 1        10         1          0 * pagePerBlock + 1
                 5        10         1          0 * pagePerBlock + 1
                10        10         1          0 * pagePerBlock + 1
               11~20      10         11         1 * pagePerBlock + 1
               21~30      10         21         2 * pagePerBlock + 1

                → n에 들어갈 값 연산
                  1) 현재 페이지 - 1
                  2) pagePerBlock 만큼 나눈 몫
         */
        int beginPage = (page - 1) / pagePerBlock * pagePerBlock + 1;

        /*
            3. endPage : 현재 페이지에 보여질 페이징바의 끝 수
                → beginPage, pagePerBlock, totalPage 통해 연산

                beginPage  pagePerBlock  endPage
                    1           10          10
                    11          10          20
                    21          10          30

                1) beginPage + pagePerBlock - 1

                beginPage  pagePerBlock  endPage  totalPage
                    21          10          30(x)    27(o)

                2) totalPage와 비교하여 더 작은 수를 endPage로 설정
         */
        int endPage = Math.min(beginPage + pagePerBlock - 1, totalPage);

        /*
            4. offset : 요청 페이지에 필요한 게시글 조회시 limit 절에 제시할 숫자 (조회를 시작할 행수(인덱스))
                → page, display 통해 연산

                page display                        offset
                  1    10     0번~9번 (10개) 조회      0
                  2    10    10번~19번(10개) 조회     10
                  3    10    20번~29번(10개) 조회     20
         */
        int offset = (page - 1) * display;

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