package com.clb;

import com.clb.utils.Calculator;
import com.clb.utils.Operation;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

@SpringBootApplication
public class RiverWebApplication {

	public static void main(String[] args) {

		var ctx = SpringApplication.run(RiverWebApplication.class, args);

		/*
		var calculator = ctx.getBean(Calculator.class);
		calculator.calculate(137, 21, '+');
		calculator.calculate(137, 21, '*');
		calculator.calculate(137, 21, '-');
		*/
	}

	/*
	@Bean
	public Calculator calculator(Collection<Operation> operations) {
		return new Calculator(operations);
	}
	*/

	@Bean
	public ApplicationRunner calculationRunner(Calculator calculator) {
		return args -> {
			calculator.calculate(137, 21, '+');
			calculator.calculate(137, 21, '*');
			calculator.calculate(137, 21, '-');
		};
	}

}
