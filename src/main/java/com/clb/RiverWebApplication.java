package com.clb;

import com.clb.utils.Calculator;
import com.clb.utils.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collection;

@SpringBootApplication
@ComponentScan("com.clb")       //加载@Service @Control注解类
@MapperScan(value = "com.clb.dao")  //mybatis 需要扫描mapper接口 dao层
public class RiverWebApplication  extends SpringBootServletInitializer {

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

	@Bean
	public ApplicationRunner calculationRunner(Calculator calculator) {
		return args -> {
			calculator.calculate(137, 21, '+');
			calculator.calculate(137, 21, '*');
			//calculator.calculate(137, 21, '-');
		};
	}
	 */

	@Bean
	public ApplicationRunner calculationRunner(Calculator calculator,
											   @Value("${lhs}") int lhs,
											   @Value("${rhs}") int rhs,
											   @Value("${op}") char op) {
		return args -> calculator.calculate(lhs, rhs, op);
	}


}
