package liliapaper.com;

import java.util.List;

public class Plants {

    public String name;

    public List<String> lights;

    public String family;

    public String soil;

    public String name() {
        return name;
    }

    public String family() {
        return family;
    }

    public Pests pests;

    public static class Pests {
        public boolean isTrue;
        public String insects;
        public String micro;
    }
}
