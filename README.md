# Idexing Library

Library for indexing a set of sequences of numbers

## Task
see task [here](task.pdf)

## Build
`mvn clean package`

## Tests

`mvn clean test`

## Usage examples

import:
`import com.alexandermusikyan.IndexingLibrary;`

Usage:
```
List<String> indexes = List.of("1,1,3-3", "4-5,4");

List<Integer> list = IndexingLibrary.indexesToIntegerList(indexes);
System.out.println(list);

List<List<Integer>> lists = IndexingLibrary.indexesToUniqueOrderedGroupsOfElements(indexes);
System.out.println(lists);
```

output:
```
[1, 3, 4, 5]
[[1, 4], [1, 5], [3, 4], [3, 5]]
```
