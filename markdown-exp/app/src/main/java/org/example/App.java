package org.example;

import com.vladsch.flexmark.util.ast.Document;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        // System.out.println(new App().getGreeting());

        markDownDemo();
    }

    private static void markDownDemo() {
        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();

        try (
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("sample.md");
            InputStreamReader reader = new InputStreamReader(inputStream);
            FileWriter fileWriter = new FileWriter("classpath:/sample.html");

        ) {
            Document document = parser.parseReader(reader);
            HtmlRenderer renderer = HtmlRenderer.builder(options).build();        
            renderer.render(document, fileWriter);  // "<p>This is <em>Sparta</em></p>\n"
        } catch (Exception e) {
            System.err.println("Something went wront...");
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
