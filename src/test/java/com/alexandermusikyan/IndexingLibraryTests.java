package com.alexandermusikyan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IndexingLibraryTests {
    private final List<String> indexes = List.of("1,3-5", "2", "3-4");

    @Test
    public void indexesToIntegerListTestWithCorrectData() {
        List<Integer> list = IndexingLibrary.indexesToIntegerList(indexes);

        Assertions.assertNotNull(list);

        final int rightSize = 7;
        Assertions.assertEquals(list.size(), rightSize);

        final List<Integer> rightList = List.of(1, 3, 4, 5, 2, 3, 4);
        Assertions.assertEquals(list, rightList);
    }

    @Test
    public void indexesToIntegerListTestWithInCorrectData() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> IndexingLibrary.indexesToIntegerList(List.of("")));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> IndexingLibrary.indexesToIntegerList(List.of("1", "")));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> IndexingLibrary.indexesToIntegerList(List.of(" 1")));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> IndexingLibrary.indexesToIntegerList(List.of("1, 3-5")));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> IndexingLibrary.indexesToIntegerList(List.of("1,5-3")));
    }

    @Test
    public void indexesToUniqueOrderedGroupsOfElements() {
        List<List<Integer>> lists =
                IndexingLibrary.indexesToUniqueOrderedGroupsOfElements(indexes);

        Assertions.assertNotNull(lists);

        final int rightSize = 8;
        Assertions.assertEquals(lists.size(), rightSize);

        final List<List<Integer>> rightLists = List.of(
                List.of(1, 2, 3),
                List.of(1, 2, 4),
                List.of(3, 2, 3),
                List.of(3, 2, 4),
                List.of(4, 2, 3),
                List.of(4, 2, 4),
                List.of(5, 2, 3),
                List.of(5, 2, 4));
        Assertions.assertEquals(lists, rightLists);
    }
}