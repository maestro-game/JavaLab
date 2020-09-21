package words.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.IParameterSplitter;

import java.util.Arrays;
import java.util.List;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = "-threads", description = "Independent threads amount")
    public int threads = 1;

    @Parameter(names = "-urls", description = "1 or more source urls for images", splitter = MySplitter.class)
    public List<String> urls;

    @Parameter(names = "-folder", description = "Destination folder")
    public String path = ".";

    @Parameter(names = {"-?", "-help"}, description = "print help message")
    public boolean help;

    private static class MySplitter implements IParameterSplitter {

        @Override
        public List<String> split(String value) {
            return Arrays.asList(value.split(","));
        }
    }
}
