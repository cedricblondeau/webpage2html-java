package com.cedricblondeau.webpage2html;

import com.cedricblondeau.webpage2html.transformers.HtmlTransformer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public final class Console {

    public static void main(String[] args)
    {
        try {
            // Arguments
            String url = args[0];
            String fileName = args[1];

            // Transform
            WebPage2Html webPage2Html = new WebPage2Html(new URL(url));
            HtmlTransformer htmlTransformer = webPage2Html.getHtmlTransformer();
            htmlTransformer.transform();

            // Write to given file
            PrintWriter printWriter = new PrintWriter(fileName, "utf-8");
            printWriter.print(htmlTransformer.getHtml());
            printWriter.close();

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid arguments");
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
