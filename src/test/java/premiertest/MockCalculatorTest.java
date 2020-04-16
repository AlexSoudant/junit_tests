package premiertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockCalculatorTest {
	
		@Spy MoussacProperties mockProperties;

		@Mock
		Calculator calculator;

		CalculatorService classUnderTest;

		@BeforeEach
		public void init() {
			classUnderTest = new CalculatorServiceImpl(calculator);
		}
		
		@Test
		public void calculate_shouldUseCalculator_forAnyAddition() {
			// GIVEN
			final Random r = new Random();
			when(calculator.add(any(Integer.class), any(Integer.class))).thenReturn(3);

			// WHEN
			final int result = (Integer) (classUnderTest.calculate(
					new CalculationModel(CalculationType.ADDITION,
					    r.nextInt(), r.nextInt())));

			// THEN
			verify(calculator).add(any(Integer.class), any(Integer.class));
			assertThat(result).isEqualTo(3);
		}
		
		// Mock qui lance une exception
		@Test
		public void calculate_shouldThrowIllegalArgumentException_forADivisionBy0() {
			// GIVEN
			when(calculator.divide(1, 0)).thenThrow(new ArithmeticException());

			// WHEN
			assertThrows(IllegalArgumentException.class, () -> classUnderTest.calculate(
					new CalculationModel(CalculationType.DIVISION, 1, 0)));

			// THEN
			verify(calculator, times(1)).divide(1, 0);
		}
		
		@Test
		public void spyTest() {
			//GIVEN
			final Random r = new Random();
			int expected = 2;
			
			//WHEN
			BoisseProperties boisseProperties = new BoisseProperties();
			Mockito.doReturn(boisseProperties).when(mockProperties).getBoisse();
			final int actual = (Integer) (classUnderTest.calculate(
					new CalculationModel(CalculationType.ADDITION,
					    r.nextInt(), r.nextInt())));
			
			//THEN
			assertEquals(expected, actual);
		}


}
