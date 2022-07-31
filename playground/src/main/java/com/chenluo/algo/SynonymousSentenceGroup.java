package com.chenluo.algo;

import org.apache.curator.shaded.com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SynonymousSentenceGroup {
    public static void main(String[] args) {
        List<List<String>> synonymousWords = new ArrayList<>();
        List<List<String>> sentences = new ArrayList<>();

        synonymousWords.add(Lists.newArrayList("main", "primary"));
        synonymousWords.add(Lists.newArrayList("main", "important"));

        sentences.add(Lists.newArrayList("main mail".split(" ")));
        sentences.add(Lists.newArrayList("primary mail".split(" ")));
        sentences.add(Lists.newArrayList("important mail".split(" ")));
        sentences.add(Lists.newArrayList("not important mail".split(" ")));
        sentences.add(Lists.newArrayList("not main mail".split(" ")));

        Map<String, Integer> synonymousMarker = new HashMap<>();
        int nextMarker = 1;
        for (List<String> synonymousWord : synonymousWords) {
            int marker = nextMarker;
            for (String s : synonymousWord) {
                if (synonymousMarker.containsKey(s)) {
                    marker = synonymousMarker.get(s);
                }
            }
            for (String s : synonymousWord) {
               synonymousMarker.put(s, marker);
            }
            nextMarker++;
        }

        Map<List<String>, List<List<String>>> markerToSentenceMap = new HashMap<>();
        for (List<String> sentence : sentences) {
            List<String> convertedSentence = new ArrayList<>();
            for (String s : sentence) {
                if (synonymousMarker.containsKey(s)) {
                    convertedSentence.add(String.valueOf(synonymousMarker.get(s)));
                } else {
                    convertedSentence.add(s);
                }
            }
            markerToSentenceMap.putIfAbsent(convertedSentence, new ArrayList<>());
            markerToSentenceMap.get(convertedSentence).add(sentence);
        }
        System.out.println(markerToSentenceMap.values().stream().filter(l -> l.size() > 1).collect(Collectors.toList()));
    }
}
