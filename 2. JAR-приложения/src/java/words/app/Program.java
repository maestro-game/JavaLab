package words.app;

import com.beust.jcommander.JCommander;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Program {
    public static void main(String[] inputArgs) {
        Args args = new Args();

        JCommander jc = JCommander.newBuilder()
                .addObject(args)
                .build();

        jc.parse(inputArgs);

        if (args.help) {
            jc.usage();
            return;
        }

        ExecutorService pool = Executors.newFixedThreadPool(args.threads);
        args.urls.forEach(url -> {
            try{
                BufferedInputStream input = new BufferedInputStream((new URL(url)).openStream());
                FileOutputStream output = new FileOutputStream(url.substring(url.lastIndexOf('/') + 1));
                input.transferTo(output);

                input.close();
                output.close();
                System.out.println("Downloaded img from " + url);
            } catch(FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}