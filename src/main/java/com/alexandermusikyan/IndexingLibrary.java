package com.alexandermusikyan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IndexingLibrary {
    private final static String NUMBER_REGEX = "^(?<number>\\d+)$";
    private final static String RANGE_REGEX = "^(?<start>\\d+)-(?<end>\\d+)$";
    private final static String INDEXES_REGEX = NUMBER_REGEX + "|" + RANGE_REGEX;

    private final static Pattern pattern = Pattern.compile(INDEXES_REGEX);

    private IndexingLibrary() {
    }

    public static List<Integer> indexesToIntegerList(final List<String> indexes) {
        return indexesToParsedList(indexes)
                .flatMap(Set::stream)
                .collect(Collectors.toList());
    }

    public static List<List<Integer>> indexesToUniqueOrderedGroupsOfElements(final List<String> indexes) {
        List<List<Integer>> uniqueOrderedGroupsOfElements = new ArrayList<>();

        List<Set<Integer>> parsedLists = indexesToParsedList(indexes).collect(Collectors.toList());

        recursiveCreationOfNumericalSequences(parsedLists, uniqueOrderedGroupsOfElements, new ArrayList<>(parsedLists.size()), 0, parsedLists.size());

        return uniqueOrderedGroupsOfElements;
    }

    private static Stream<Set<Integer>> indexesToParsedList(final List<String> indexes) {
        return indexes.stream()
                .map(s -> Arrays.stream(s.split(","))
                        .map(pattern::matcher)
                        .map(IndexingLibrary::parsing)
                        .flatMap(List::stream)
                        .collect(Collectors.toSet()));
    }

    private static List<Integer> parsing(final Matcher matcher) {
        if (matcher.find()) {
            String number = matcher.group("number");

            if (number != null) {
                return List.of(Integer.parseInt(number));
            } else {
                int start = Integer.parseInt(matcher.group("start"));
                int end = Integer.parseInt(matcher.group("end"));

                if (start > end) {
                    throw new IllegalArgumentException("Invalid \"indexes\" list passed");
                }

                return IntStream.range(start, end + 1)
                        .boxed()
                        .collect(Collectors.toList());
            }
        } else {
            throw new IllegalArgumentException("Invalid \"indexes\" list passed");
        }
    }

    private static void recursiveCreationOfNumericalSequences(final List<Set<Integer>> parsedLists,
                                          List<List<Integer>> allLists,
                                          List<Integer> curList,
                                          final int index,
                                          final int listSize) {
        if (index == listSize) {
            allLists.add(new ArrayList<>(curList));
            return;
        }

        parsedLists.get(index).forEach(num -> {
            curList.add(num);
            recursiveCreationOfNumericalSequences(parsedLists, allLists, curList, index + 1, listSize);
            curList.remove(curList.size() - 1);
        });
    }
}