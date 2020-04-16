package premiertest;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculatorTest {
	
    private static Instant startedAt;

    private Calculator calculatorUnderTest;
    private SolutionFormatter solutionFormatter;
    
	@BeforeAll
	static public void initStartingTime() {
		System.out.println("Appel avant tous les tests");
		startedAt = Instant.now();
	}
	
	@BeforeEach
	public void initCalculator() {
		System.out.println("Appel avant chaque test");
		calculatorUnderTest = new Calculator();
	}
	

	@Test
	public void testAddTwoPositiveNumbers() {
		// Arrange
		int a = 2;
		int b = 3;

		// Act
		int somme = calculatorUnderTest.add(a, b);

		// Assert
		assertEquals(5, somme);
	}
	
	@Disabled
	@Test
	public void listDigits_shouldReturnsTheListOfDigits_ofPositiveInteger() {
		// GIVEN
		int number = 999;
		
		// WHEN
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

		// THEN
		assertThat(actualDigits).containsExactlyInAnyOrder(5, 7, 8, 9);
	}

	
	@ParameterizedTest(name = "{0} x 0 doit être égal à 0")
	@ValueSource(ints = { 1, 2, 42, 1011, 5089 })
	public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
		// Arrange -- Tout est prêt !

		// Act -- Multiplier par zéro
		int actualResult = calculatorUnderTest.multiply(arg, 0);

		// Assert -- ça vaut toujours zéro !
		assertEquals(0, actualResult);
	}
	
	@AfterEach
	public void undefCalculator() {
		System.out.println("Appel après chaque test");
		calculatorUnderTest = null;
	}
	


	@AfterAll
	static public void showTestDuration() {
		System.out.println("Appel après tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}
	
	
	// Tests sans isolation
	private int cacheFactorial;
	
	@Test
	public void fact12_shouldReturnsTheCorrectAnswer() {
		// GIVEN
		final int number = 12;

		// WHEN
		// Calculer 12! et sauve la valeur pour un autre test
		cacheFactorial = calculatorUnderTest.fact(number);

		// THEN
		assertThat(cacheFactorial).isEqualTo(12 * 11 * 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2);

	}

	@Test
	public void digitsSetOfFact12_shouldReturnsTheCorrectAnswser() {
		// GIVEN
		// 12! est mis en cache par le test précédent

		// WHEN
		final Set<Integer> actualDigits = calculatorUnderTest.digitsSet(cacheFactorial);

		// THEN
		assertThat(actualDigits).containsExactlyInAnyOrder(0, 1, 4, 6, 7, 9);
	}
	
	// test non reproductible
	@Test
	public void multiplyAndDivide_shouldBeIdentity() {
		// GIVEN
		final Random r = new Random();
		final int a = r.nextInt() % 100; // Nombre aléatoire entre 0 et 99
		final int b = r.nextInt() % 10; // Nombre aléatoire entre 0 et 9

		// WHEN on multiplie a par b puis on divise par b
		final int c = calculatorUnderTest.divide(calculatorUnderTest.multiply(a, b), b);

		// THEN on ré-obtient a
		assertThat(c).isEqualTo(a);
	}
	
	// test sans self-validation
	@Test
	public void format_shouldFormatAnyBigNumber() {
		// GIVEN
		int number = 1234567890;
		
		// WHEN
		String result = solutionFormatter.format(number);
		
		// THEN
		assertThat(result).isEqualTo("1 234 567 890");
	}

}
