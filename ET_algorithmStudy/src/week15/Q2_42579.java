// [프로그래머스] 베스트앨범
// https://programmers.co.kr/learn/courses/30/lessons/42579
// 미완

package week15;

import java.util.*;

public class Q2_42579 {

    public static void main(String[] args) {
        String[] genres = {"classic", "pop", "classic", "classic", "pop"};
        int[] plays = {500, 600, 150, 800, 2500};

        System.out.println(Arrays.toString(solution(genres, plays)));
    }

    public static int[] solution(String[] genres, int[] plays) {
        // 1. 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
        // 2. 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
        // 3. 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.

        ArrayList<Integer> answer = new ArrayList<>();
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, PriorityQueue<Integer>> map2 = new HashMap<>();

        // map1 : 장르별(key) 재생횟수 총합(value) 저장
        // map2 : 장르별(key) 개별 재생횟수 내림차순 정렬 우선순위 큐(value) 저장
        for(int i = 0; i < genres.length; i++) {
            map1.put(genres[i], map1.getOrDefault(genres[i], 0)+plays[i]);
            map2.put(genres[i], map2.getOrDefault(genres[i], new PriorityQueue<>(Collections.reverseOrder())));
            map2.get(genres[i]).add(plays[i]);
        }

        // 재생횟수 총합(value) 기준 정렬
        List<String> map1KeySet = new ArrayList<>(map1.keySet());
        map1KeySet.sort((o1, o2) -> (map1.get(o2).compareTo(map1.get(o1))));
        List<String> genre_order = new LinkedList<>(map1KeySet);

        for(String g : genre_order) {
            System.out.println(g);
        }

        // 정리 필
        for(String g : genre_order) {
            int[][] maxvalues = new int[2][2];
            for(int i = 0; i < genres.length; i++) {
                if(genres[i].equals(g)) {
                    for(int j = 0; j < plays.length; j++) {
                        if(plays[j] == map2.get(genres[i]).peek()) {
                            answer.add(j);
                            map2.get(genres[i]).poll();
                            break;
                        }
                    }
                }
            }

            answer.add(maxvalues[0][1]);
            answer.add(maxvalues[1][1]);
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
}
