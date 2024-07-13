package dev.javarush.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import dev.javarush.springspel.NumberGuess;
import dev.javarush.springspel.TaxCalculator;

public class SpringSPELTest {

    @Test
    public void testSimpleStringExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello, World!'");
        String message = (String) expression.getValue();
        assertEquals("Hello, World!", message);
    }

    @Test
    public void testSimpelStringMethod() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello, '.concat('World').concat('!')");
        String message = (String) expression.getValue();
        assertEquals("Hello, World!", message);
    }

    @Test
    public void testGetMethodWithoutGetPrefix() {
        // Invoke `getBytes()`
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello, World!'.bytes");
        byte[] bytes = (byte[]) expression.getValue();
        assertEquals("Hello, World!", new String(bytes));
    }

    @Test
    public void testNestedProperties() {
        // Invoke `getBytes().length`
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello, World!'.bytes.length");
        int len = (int) expression.getValue();
        assertEquals(13, len);
    }

    @Test
    public void testConstructorCall() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("new String('Hello, World!').toUpperCase()");
        String shout = expression.getValue(String.class);
        assertEquals("HELLO, WORLD!", shout);
    }

    @Test
    public void testExpressionAgainstObject() {
        record Inventor (String name, Date birthDate, String nationality) {}

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(1856, 7, 9);

        var tesla = new Inventor("Nikola Tesla", calendar.getTime(), "Serbian");

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("name");
        String name = (String) expression.getValue(tesla);
        assertEquals(tesla.name(), name);

        Expression assertExpression = parser.parseExpression("name == 'Nikola Tesla'");
        boolean result = assertExpression.getValue(tesla, Boolean.class);
        assertTrue(result);
    }

    @Test
    public void testTypeConversion() {
        record Simple (List<Boolean> booleanList) {}

        Simple simple = new Simple(new ArrayList<>());
        simple.booleanList.add(true);

        EvaluationContext evaluationContext = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("booleanList[0]")
            .setValue(evaluationContext, simple, "false");

        assertFalse(simple.booleanList.get(0));

    }

    @Test
    public void testParserConfigurationAuthGrowList() {
        record Demo (List<String> list) {}

        Demo demo = new Demo(new ArrayList<>());

        // Turn on:
        // - auto null reference initialization
        // - auto collection growing
        SpelParserConfiguration configuration = new SpelParserConfiguration(true, true);
        ExpressionParser parser = new SpelExpressionParser(configuration);

        Expression expression = parser.parseExpression("list[3]");
        String item = expression.getValue(demo, String.class);

        assertEquals("", item);
        assertEquals(4, demo.list.size());

        Expression anotherExpression = parser.parseExpression("list[9]");
        anotherExpression.setValue(SimpleEvaluationContext.forReadOnlyDataBinding().build(), demo, "My String");

        assertEquals(10, demo.list.size());
        assertEquals("My String", demo.list.get(9));
    }

    @Test
    public void testImmediateModeCompilation() {

        record Message(String payload) {};

        SpelParserConfiguration config = new SpelParserConfiguration(
            SpelCompilerMode.IMMEDIATE,
            this.getClass().getClassLoader()
        );
        SpelExpressionParser parser = new SpelExpressionParser(config);

        Expression expression = parser.parseExpression("payload");

        Message message = new Message("Hello, world🌍");
        String payload = expression.getValue(message, String.class);
        System.out.println ("Payload - " + payload);
    }

    @Test
    public void textXMLConfiguration() {
        System.setProperty("user.region", "IN");
        var context = new ClassPathXmlApplicationContext("beans.xml");
        NumberGuess numberGuess = context.getBean(NumberGuess.class);
        assertNotNull(numberGuess.getRandomNumber());

        TaxCalculator taxCalculator = context.getBean(TaxCalculator.class);
        assertEquals("IN", taxCalculator.getDefaultLocale());
        assertEquals(numberGuess.getRandomNumber(), taxCalculator.getInitialRate());

        context.close();
    }
}
