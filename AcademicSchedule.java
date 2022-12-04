import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 210252549
 Academic Schedule
 Описание задачи по программированию:
 Consider an academic program that consists of NN courses. Every course is designated by an integer index from 00 to N-1N−1.

 Some courses have others as prerequisites. For example, if course 2 is a prerequisite of course 3 you must finish course 2 before enrolling in course 3.

 You can attend only a single course at a time. Build a schedule to complete all courses in a linear sequence and to satisfy all prerequisites for every course.

 If more than one variant of possible schedule exists, use the schedule where courses with smaller indices are finished first.

 Исходные данные:
 The first line contains a positive integer representing the number of courses in the academic program.

 Each additional line describes the prerequisites of a given course as a space-delimited set of indices. Every set of prerequisites has at least two indices.

 The first index in the set denotes the course for which a prerequisite exists. All other indices designate which courses are required for the first.

 For example:

 4
 1 0
 2 0
 3 1 2
 The first row means that there are 4 courses in the academic program. The second and third rows define that course 0 must be taken before courses 1 and 2. And the fourth row defines that courses 1 and 2 must be taken before course 3.

 Результат:
 A schedule containing a space-delimited list of courses, in the order of their attendance. For example:

 0 1 2 3
 For this example, another schedule exists 0 2 1 3. But we must select the variant 0 1 2 3 where course 1 (with a smaller index) is attended before the course 2 (with a greater index).

 Тест 1
 Проверить вход
 Загрузить исходные данные теста 1
 4
 1 0
 2 0
 3 1 2
 Ожидаемый результат
 Загрузить результаты теста (1)
 0 1 2 3
 Тест 2
 Проверить вход
 Загрузить исходные данные теста 2
 7
 0 1 2
 1 3
 2 3
 3 4 5
 4 6
 5 6
 Ожидаемый результат
 Загрузить результаты теста (2)
 6 4 5 3 1 2 0
 Тест 3
 Проверить вход
 Загрузить исходные данные теста 3
 8
 4 0 2
 0 1 6
 2 3 7
 1 5
 6 5
 3 5
 7 5
 Ожидаемый результат
 Загрузить результаты теста (3)
 5 1 3 6 0 7 2 4


 Тест 4
 Проверить вход
 Загрузить исходные данные теста 4
 4
 2 1 3
 Ожидаемый результат
 Загрузить результаты теста (4)
 0 1 3 2
 */
public class AcademicSchedule {
    static List<List<Integer>> noneOrderedCourses;
    static List<Integer> orderedCourses;
    static List<Integer> coursesTemplate;

    public static void main(String[] args) {
        String[][] test1 = new String[][]{{"4"}, {"1", "0"}, {"2", "0"}, {"3", "1", "2"}};
        String[][] test2 = new String[][]{{"7"}, {"0", "1", "2"}, {"1", "3"}, {"2", "3"}, {"3", "4", "5"}, {"4", "6"}, {"5", "6"}};
        String[][] test3 = new String[][]{{"8"}, {"4", "0", "2"}, {"0", "1", "6"}, {"2", "3", "7"}, {"1", "5"}, {"6", "5"}, {"3", "5"}, {"7", "5"}};
        String[][] test4 = new String[][]{{"4"}, {"2", "1", "3"}};

        System.out.println(getOrderedCourses(test1)); //0 1 2 3
        System.out.println(getOrderedCourses(test2)); //6 4 5 3 1 2 0
        System.out.println(getOrderedCourses(test3)); //5 1 3 6 0 7 2 4
        System.out.println(getOrderedCourses(test4)); //0 1 3 2
    }

    private static String getOrderedCourses(String[][] providedCourses) {
        orderedCourses = new LinkedList<>();
        Integer numberOfCourses = Integer.valueOf(providedCourses[0][0]);
        createCoursesTemplate(numberOfCourses);
        getCoursesAsList(providedCourses);
        Integer prerequisiteCourse;
        int counter = numberOfCourses;
        while (counter > 0) {
            prerequisiteCourse = findPrerequisite();
            addToOrderedCourses(prerequisiteCourse);
            removeFromNoneOrderedCourses(prerequisiteCourse);
            removeFromTemplate(prerequisiteCourse);
            removeRowWhenItsSizeLessThanTwo();
            counter--;
        }

        return orderedCourses.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    private static Integer findPrerequisite() {
        Integer firstCourse = -1;
        for (Integer elem : coursesTemplate) {
            if (coursesTemplate.size() == 1) {
                return elem;
            }
            for (int i = 1; i < noneOrderedCourses.size(); i++) {
                if (elem.equals(noneOrderedCourses.get(i).get(0))) {
                    break;
                }
                if (i == noneOrderedCourses.size() - 1) {
                    firstCourse = elem;
                    return firstCourse;
                }
            }
        }
        return firstCourse;
    }

    private static void addToOrderedCourses(Integer course) {
        orderedCourses.add(course);
    }

    private static void removeFromTemplate(Integer course) {
        coursesTemplate.remove(course);
    }

    private static void removeFromNoneOrderedCourses(Integer course) {
        for (int i = 1; i < noneOrderedCourses.size(); i++) {
            for (int j = 0; j < noneOrderedCourses.get(i).size(); j++) {
                if (noneOrderedCourses.get(i).get(j).equals(course)) {
                    noneOrderedCourses.get(i).remove(j);
                }
            }
        }
    }

    private static void removeRowWhenItsSizeLessThanTwo() {
        for (int i = 1; i < noneOrderedCourses.size(); i++) {
            if (noneOrderedCourses.get(i).size() < 2) {
                noneOrderedCourses.remove(i);
            }
        }
    }

    private static void createCoursesTemplate(Integer numberOfCourses) {
        coursesTemplate = IntStream.range(0, numberOfCourses).boxed().collect(Collectors.toCollection(LinkedList::new));
    }

    private static void getCoursesAsList(String[][] courseList) {
        noneOrderedCourses = new LinkedList<>();
        for (String[] elem : courseList) {
            noneOrderedCourses.add(Arrays.stream(elem).map(Integer::valueOf).collect(Collectors.toList()));
        }
    }
}
