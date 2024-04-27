package dev.javarush.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

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
}
