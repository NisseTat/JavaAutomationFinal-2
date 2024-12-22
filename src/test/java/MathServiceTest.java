import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;

import java.util.stream.Stream;

public class MathServiceTest {

    private final MathService mathService = new MathService();

    // Параметризованные тесты для проверки корректных значений
    static Stream<Object[]> validRootsProvider() {
        return Stream.of(
                new Object[]{1, -3, 2, new Pair(2.0, 1.0)}, // два различных корня
                new Object[]{1, -2, 1, new Pair(1.0, 1.0)}, // один корень
                new Object[]{1, 0, -4, new Pair(2.0, -2.0)} // два различных корня
        );
    }

    @ParameterizedTest
    @MethodSource("validRootsProvider")
    void testGetAnswerValid(int a, int b, int c, Pair expected) throws NotFoundAnswerException {
        Pair result = mathService.getAnswer(a, b, c);
        Assertions.assertEquals(expected.first, result.first);
        Assertions.assertEquals(expected.second, result.second);
    }

    // Негативный тест для проверки исключения
    @Test
    void testGetAnswerNoRoots() {
        Exception exception = Assertions.assertThrows(NotFoundAnswerException.class, () -> {
            mathService.getAnswer(1, 0, 1); // дискриминант < 0
        });
        Assertions.assertEquals("Корни не могут быть найдены", exception.getMessage());
    }

    // Тест на проверку вычисления дискриминанта
    @Test
    void testGetD() {
        int d = mathService.getD(1, -3, 2); // 1 * (-3)^2 - 4 * 1 * 2 = 1
        Assertions.assertEquals(1, d);
    }
}