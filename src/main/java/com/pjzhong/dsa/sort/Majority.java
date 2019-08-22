package com.pjzhong.dsa.sort;

public class Majority<E extends Comparable<E>> {

    public E majority(E[] e) {
        E majorityCandidate = searchMajorityCandidate(e);
        return majorityCheck(e, majorityCandidate) ? majorityCandidate : null;
    }

    private boolean majorityCheck(E[] e, E majority) {
        int occurrence = 0;
        for(E maj : e) {
            if(maj.compareTo(majority) == 0) {
                occurrence++;
            }
        }
        return e.length < (occurrence * 2);
    }

    /**
     * 选取中众数候选者m，
     *
     * 众数定义：既m的出现次数在E的次数超过其规模一半以上
     * 2 1 2 3 2 4 2
     * 具体思路查看以下链接，真的很巧妙......
     * http://www.xuetangx.com/courses/course-v1:TsinghuaX+30240184_2X+sp/courseware/c15aad6e2dac4250934ea61d71deafd9/06f89de70afa43d39e98df96bec18662/
     * */
    private E searchMajorityCandidate(E[] E) {
        E maj = null;
        for(int c = 0, i = 0, size = E.length; i < size; i++) {
            if(0 == c) {
                maj = E[i]; c = 1;
            } else {
                if(maj.compareTo(E[i]) == 0) {
                    c++;
                } else {
                    c--;
                }
            }
        }
        return maj;
    }
}
