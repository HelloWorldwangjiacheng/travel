package com.exam.travel.schedule;

import com.exam.travel.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author w1586
 * @Date 2020/3/23 23:00
 * @Cersion 1.0
 */
@Component
@Data
public class HotTagCache {
    /**
     * 优先队列的最大长度
     */
    private static final int MAX = 5;

    private Map<String, Integer> tags = new HashMap<>();
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(MAX);

        tags.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);

            if (priorityQueue.size() < MAX) {
                priorityQueue.add(hotTagDTO);
            } else {
                HotTagDTO minHot = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<String> sortedTags = new ArrayList<>();

        HotTagDTO poll = priorityQueue.poll();
        hots.add(poll.getName());
        while(poll!=null){
            sortedTags.add(0,poll.getName());
            poll = priorityQueue.poll();

        }
        hots = sortedTags;
        System.out.println(hots);
    }
}
